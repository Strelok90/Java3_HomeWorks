package ru.geekbrains.java2.chat.server.chat;


import org.apache.log4j.Logger;
import ru.geekbrains.java2.chat.clientserver.Command;
import ru.geekbrains.java2.chat.server.chat.auth.AuthService;

import ru.geekbrains.java2.chat.server.chat.auth.DBAuthService;
import ru.geekbrains.java2.chat.server.chat.handler.ClientHandler;
import ru.geekbrains.java2.chat.server.chat.handler.SQLHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class MyServer {

    private final List<ClientHandler> clients = new ArrayList<>();
    private AuthService authService;
    private ExecutorService clientExecutorService;
    public MyServer() {
        this.authService = new DBAuthService();
    }
    public static Logger file = Logger.getLogger("file");

    public void start(int port) throws IOException {
        clientExecutorService = Executors.newCachedThreadPool();
        if (!SQLHandler.connect()){
            file.warn("Не удалось подключиться к БД");
            throw new RuntimeException("Не удалось подключиться к БД");
        }
        authService = new DBAuthService();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер был запущен");
            file.info("Сервер был запущен");
            runServerMessageThread();
            authService.start();
            while (true) {
                waitAndProcessNewClientConnection(serverSocket);
            }
        } catch (IOException e) {
            file.info("Failed to accept new connection");
            System.err.println("Failed to accept new connection");
            e.printStackTrace();
        } finally {
            SQLHandler.disconnect();
            authService.stop();
            clientExecutorService.shutdown();
        }
    }

    private void runServerMessageThread() {
        clientExecutorService.execute(() ->  {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String serverMessage = scanner.next();
                try {
                    broadcastMessage("Сервер: " + serverMessage, null);
                } catch (IOException e) {
                    file.warn("failed to process serverMessage");
                    System.err.println("failed to process serverMessage");
                    e.printStackTrace();
                }
            }
        });
    }

    private void waitAndProcessNewClientConnection(ServerSocket serverSocket) throws IOException {
        System.out.println("Ожидание нового подключения....");
        file.info("Ожидание нового подключения....");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Клиент подключился");// /auth login password
        file.info("Клиент подключился");
        processClientConnection(clientSocket);
    }

    private void processClientConnection(Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.handle();
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client == sender) {
                continue;
            }
            if (sender == null) {
                client.sendMessage(message);
            }
            else {
                client.sendMessage(sender.getNickname(), message);
            }
        }
    }

    public synchronized void subscribe(ClientHandler handler) throws IOException {
        clients.add(handler);
        notifyClientsUsersListUpdated(clients);
    }

    public synchronized void unsubscribe(ClientHandler handler) throws IOException {
        clients.remove(handler);
        notifyClientsUsersListUpdated(clients);
    }

    private void notifyClientsUsersListUpdated(List<ClientHandler> clients) throws IOException {
        List<String> usernames = new ArrayList<>();
        for (ClientHandler client : clients) {
            usernames.add(client.getNickname());
        }

        for (ClientHandler client : clients) {
            client.sendCommand(Command.updateUsersListCommand(usernames));
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized boolean isNickBusy(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(recipient)) {
                client.sendMessage(sender.getNickname(), privateMessage);
            }
        }
    }
}
