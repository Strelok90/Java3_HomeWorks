package ru.geekbrains.java2.chat.server.chat.auth;


import ru.geekbrains.java2.chat.server.chat.handler.SQLHandler;

public class DBAuthService implements AuthService {

    @Override
    public void start() {
        System.out.println("Auth service is running");
    }

    @Override
    public void stop() {
        System.out.println("Auth service has been stopped");
    }

    @Override
    public String getNickByLoginPass(String login, String password) {
        return SQLHandler.getNickByLoginPass(login,password);
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        return SQLHandler.registration(login,password,nickname);
    }

    public boolean changeNick(String oldNickname,String newNickname){
        return SQLHandler.changeNick(oldNickname, newNickname);
    }


}
