package com.igeek.bams.server;

import com.igeek.bams.entity.Account;
import com.igeek.bams.exception.LoginException;
import com.igeek.bams.service.Bank;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * @version 1.0
 * @Description TODO
 * @Author chenmin
 * @Date 2020/12/14 16:43
 */
public class Tasker implements Runnable {

    Socket socket;
    BufferedReader br;
    ObjectOutputStream oos;

    Bank bank = Bank.getInstance();

    public Tasker(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        Account acc = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            oos = new ObjectOutputStream(socket.getOutputStream());

            //读取你客户端发过来的指令  login#name#pwd
            String str = null;
            while ((str=br.readLine())!=null){  //阻塞
                //解析
                String[] split = str.split("#");

                switch (split[0]){
                    case "login":
                        //ObjectOutputStream 写对象（Account/String）
                        try {
                            acc = bank.login(Long.valueOf(split[1]), split[2]);
                            oos.writeObject(acc);
                            oos.flush();
                        }catch (NumberFormatException ex){
                            oos.writeObject("您输入的ID格式不正确");
                            oos.flush();
                        }catch (LoginException ex) {
                            oos.writeObject(ex.getMessage());
                            oos.flush();
                        }
                        break;
                    case "register":
                        break;
                    case "close":
                        //关闭客户端
                        oos.writeObject("");
                        oos.flush();
                        break;
                    default:
                        break;

                }
            }
        } catch (SocketException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
