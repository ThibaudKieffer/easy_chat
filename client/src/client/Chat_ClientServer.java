package client;
import java.awt.List;
import java.awt.TextArea;
import java.io.*;
import java.net.*;

public class Chat_ClientServer implements Runnable {

    private Socket socket;
    public static PrintWriter out = null;
    private BufferedReader in = null;
    private Thread t3;
    private TextArea messages;
    private List lst;

    

    public Chat_ClientServer(Socket s, TextArea messages, List lst){
        this.socket = s;
        this.messages=messages;
        this.lst=lst;
    }

    

    public void run() {

        try {

            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            //Run the thread who check messages
            t3 = new Thread(new Reception(in,messages,lst));
            t3.start();


        } catch (IOException e) {

            System.err.println("The server is disconnected");

        }

    }

}