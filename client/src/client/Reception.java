package client;
import java.awt.List;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Reception implements Runnable, Serializable {

    private BufferedReader in;
    private String message = null;
    private TextArea messages;
    private List lst;


    public Reception(BufferedReader in, TextArea messages, List lst){
        this.in = in;
        this.messages=messages;
        this.lst = lst;
    }

    public void run() {

        while(true){
        	
            try {

            message = in.readLine();
            //Check if a client was disconnected
            if ((message.length()>=4) && (message.substring(0, 4).equals("QUIT")))
            {
            	//get the login and remove it from the list of connected clients
            	String login=message.substring(4);
    		    lst.remove(login);
    		    lst.repaint();
    		    login=null;
            }
            
            //Check if a new client is connected
            else if ((message.length()>=9) && (message.substring(0, 10).equals("CONNECTION")))
            {
            	String login=message.substring(10);
            	lst.add(login);
            	lst.repaint();
            	
            }
            
            else{
	            this.messages.append(message);
	            this.messages.append(System.getProperty("line.separator"));
            }

            } catch (IOException e) {
            	 System.exit(0);
                 e.printStackTrace();

            }

        }

    }

}