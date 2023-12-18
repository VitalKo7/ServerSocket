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
                    executorService.execute(clientHandler);

                    // this code is inside 'ClientHandler'
    /*                InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();

                    PrintWriter socketWriter = new PrintWriter(outputStream);
                    BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputStream));

                    while (true) {
                        String message = socketReader.readLine();
                        if (message == null){
                            break;              // breaks only one while() cycle,
                        }
                        System.out.println("Server received: " + message);

                        socketWriter.println(message + " " + LocalTime.now());
                        socketWriter.flush();
                    }*/
                }
            } finally {
                executorService.shutdown();
                executorService.awaitTermination(1, TimeUnit.MINUTES);
            }
        } catch (Exception e) { //  IOException e -> Exception e
            e.printStackTrace();
        }
    }
}