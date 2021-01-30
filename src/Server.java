package AdvancedChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5606);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 1;
        Socket socket = null;
        List<ServerService> list = new ArrayList<>();
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String temp;
                    while (true) {
                        if (systemIn.ready()) {
                            temp = systemIn.readLine();
                            for (ServerService serverService : list) {
                                serverService.flushed(temp);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        while (true) {
            try {
                assert serverSocket != null;
                socket = serverSocket.accept();

            } catch (IOException e) {
                e.printStackTrace();
            }
            ServerService t = new ServerService(socket, i);
            t.start();
            list.add(t);
            i++;
        }
    }
}

