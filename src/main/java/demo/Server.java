package demo;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        try (
                ServerSocket server = new ServerSocket(9999);
                Socket socket = server.accept();
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            output.write("Hello!\n");
            output.flush();

            while (!socket.isClosed()) {
                String line = input.readLine();
                System.out.println("read from client> " + line);
                output.write(line + "\n");
                output.flush();
            }
        }
    }

}
