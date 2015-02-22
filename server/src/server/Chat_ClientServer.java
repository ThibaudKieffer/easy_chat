package server;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.Socket;

@SuppressWarnings("serial")
public class Chat_ClientServer implements Runnable, Serializable {
	private Socket socket = null;
    private BufferedReader in= null;
    private String message = null;
    private TextArea log;
    private boolean run = true;
    private String login;

    public Chat_ClientServer(Socket s, TextArea log, String login){        
        socket = s;
        this.log=log;
        this.login=login;
    }

    public void run() {
    	
    		while(run)
    		{
		        try {
			        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		            message = in.readLine();
		            
		            if(message==null)
		            {
		            	Accept_connection.socket_table.remove(socket);
		            	this.log.append(login +" is disconnected "+System.getProperty("line.separator"));
		            	Message.Message_Groupe(login+" is disconnected");
		            	Message.Message_Groupe("QUIT"+login);
		            	Accept_connection.login.remove(login);
		            	run=false;
		            }
		            
		            else{
		            	log.append(message+System.getProperty("line.separator"));
		            	
		            	//Send the message for each clients
		            	Message.Message_Groupe(message);
			            Thread.sleep(100);	
		            }
		        } catch (IOException e) {

		            run=false;
		
		        } catch (InterruptedException e) {

					e.printStackTrace();
				}
    		}
	    }
    
    
    }
