package socket.server;

import socket.server.task.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerSocketAppl {
    public static void main(String[] args) {
        int port = 9000;

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            try {
                while (true) {
                    System.out.println("Server waits...");
                    Socket socket = serverSocket.accept();

                    System.out.println("Connection established");
                    System.out.println("Client host: " + socket.getInetAddress() + ":" + socket.getPort());

                    ClientHandler clientHandler = new ClientHandler(socket);
                    executorService.execute(clientHandler); // some code is inside 'ClientHandler'
                }
            } finally {
                executorService.shutdown();
                executorService.awaitTermination(1, TimeUnit.MINUTES);
            }
        } catch (Exception e) {     //  IOException e -> Exception e
            e.printStackTrace();
        }
    }
}