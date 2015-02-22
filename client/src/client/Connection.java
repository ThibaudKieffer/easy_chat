package client;
import java.net.*;
import java.util.Vector;
import java.awt.List;
import java.awt.TextArea;
import java.io.*;

public class Connection implements Runnable  {

    private Socket socket = null;
    public static Thread t2;
    private User user;
    private TextArea messages;
    private List lst;
    private PrintWriter out;
    private Vector<String> users= new Vector<String>();

    

    public Connection(Socket s, TextArea messages, User u, List lst){
        socket = s;
        this.messages=messages;
        user=u;
        this.lst=lst;
    }

    

    @SuppressWarnings("unchecked")
	public void run(){
    	
    	try {
    		//send the name of the client who is just connected
			out = new PrintWriter(socket.getOutputStream());
			out.println(user.getNom());
		    out.flush();
		    
		    //get the users list
		    InputStream socketStream = socket.getInputStream();
		    ObjectInputStream in = new ObjectInputStream(socketStream);
		    users=(Vector<String>)in.readObject();
		    
		    //add a client in the list
		    for(int i=0;i<users.size();i++)
		    {
		    	this.lst.add(users.elementAt(i).toString());
		    }
		    
		    t2 = new Thread(new Chat_ClientServer(socket, messages,lst));
	        t2.start();
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}           

    }

}