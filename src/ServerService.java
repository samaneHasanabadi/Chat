package AdvancedChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Formatter;

public class ServerService extends Thread {
    Socket socket;
    int i;
    Formatter out;

    public ServerService(Socket socket, int i) {
        this.i = i;
        this.socket = socket;

    }

    public void run() {
        try {
                out = new Formatter(socket.getOutputStream());
                String next;
                String temp = null;
                BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String next;
                            while (true) {
                                if (in.ready()) {
                                    next = in.readLine();
                                    System.out.println("Client " + i + ": " + next);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flushed(String temp){
        out.format(temp + "\n");
        out.flush();
    }
}
