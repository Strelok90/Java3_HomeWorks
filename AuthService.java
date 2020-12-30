package ru.geekbrains.java2.chat.server.chat.auth;

import java.sql.*;

public interface AuthService {


    void start();
    void stop();

    String getNickByLoginPass(String login, String password);
    boolean registration(String login, String password, String nickname);
    boolean changeNick(String oldNickname, String newNickname);

}
