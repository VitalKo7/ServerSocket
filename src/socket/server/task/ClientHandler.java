package socket.server.task;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler implements Runnable {
    private Socket socket;
//    private String userName = "you";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Socket socket = this.socket) {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            PrintWriter socketWriter = new PrintWriter(outputStream);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputStream));

//            userName = socketReader.readLine();
//                if (userName == null) {
//                    userName = "you";
//                }
//            System.out.println("userName received: " + userName);

            while (true) {
                String message = socketReader.readLine();
                if (message == null) {
                    break;              // breaks only one while() cycle,
                }
                System.out.println("Server received: " + message);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");

                //! can't get user directly
//                socketWriter.println("At " + LocalTime.now().format(formatter) + " " + userName + " wrote: " + message +
//                        " " + LocalTime.now());
                socketWriter.println("At " + LocalTime.now().format(formatter) + " " + message + " " + LocalTime.now());

                socketWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}