package server;
import java.awt.TextArea;
import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Vector;

public class Accept_connection implements Runnable {
	 private ServerSocket socketserver = null;
	 public static int i=0;
	 private BufferedReader in= null;
	 private ObjectOutputStream out;
	 public static Vector<String> login= new Vector<String>();
	 private TextArea log;
	 public Thread t2;
	 public static Hashtable<String, Socket> socket_table= new Hashtable<String, Socket>();
	  
	 
	 public Accept_connection(ServerSocket ss, TextArea log){
			 socketserver = ss;
			 this.log=log;
	    }

	    public void run() {
	    	
	        try 
	        {
	            while(true){
	            	//Wait for a client
	            	socket_table.put(String.valueOf(i), socketserver.accept());
	            	
	            	//Get the client login who was just connected
	            	in= new BufferedReader(new InputStreamReader(socket_table.get(String.valueOf(i)).getInputStream()));
	            	login.add(in.readLine());
	            	
	            	//Send the list of clients login at the new client
	            	out=new ObjectOutputStream(socket_table.get(String.valueOf(i)).getOutputStream());
	            	out.writeObject(login);
	            	out.flush();
	            	           	
	            	//Warn server and client for the new connection
		            log.append("User "+login.lastElement()+" is connected ! "+System.getProperty("line.separator"));
		            Message.Message_Connexion("User "+login.lastElement()+" is connected !");
		            Message.Message_Connexion("CONNECTION"+login.lastElement());
		            
		            //Launch a thread for each connected clients
		            t2 = new Thread(new Chat_ClientServer(socket_table.get(String.valueOf(i)),log,login.lastElement()));
		            t2.start();
		            i++;
	            }
	        }
	        
	        catch (IOException e)
	        {
	        	log.append("Server disconnected"+System.getProperty("line.separator"));
	        }
	    }
}
