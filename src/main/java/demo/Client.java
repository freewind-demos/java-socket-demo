package demo;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        try (
                Socket socket = new Socket("localhost", 9999);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            handleUserInput(output);

            String line;
            while (!socket.isClosed() && (line = input.readLine()) != null) {
                System.out.println("read from server> " + line);
            }
        }
    }

    private static void handleUserInput(final BufferedWriter output) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sendUserInput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void sendUserInput() throws IOException {
                try (BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
                    String line;
                    while ((line = userInput.readLine()) != null) {
                        output.write(line + "\n");
                        output.flush();
                    }
                }

            }
        };
        thread.setDaemon(true);
        thread.start();
    }

}
