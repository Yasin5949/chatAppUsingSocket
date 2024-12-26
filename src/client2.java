import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

public class client2 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataInputStream dataIn = new DataInputStream(socket.getInputStream());
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            JFrame frame = new JFrame("Shalo");
            JTextArea screen = new JTextArea();
            screen.setEditable(false);
            frame.add(new JScrollPane(screen), BorderLayout.CENTER);
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();

                        screen.append("message: "+message + "\n");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();
            while (true) {
                System.out.println("type...: ");
                String message = stdIn.readLine();
                screen.append("YOU: " +message+"\n");
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
