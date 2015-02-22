package server;

import java.io.IOException;
import java.io.PrintWriter;

//A class who manage the message sending
public class Message {
	private static PrintWriter out[] = new PrintWriter[100];
	
	public static void Message_Groupe(String message) throws IOException
	{
		for(int i=0;i<Accept_connection.socket_table.size();i++)
		{
        	out[i] = new PrintWriter(Accept_connection.socket_table.get(String.valueOf(i)).getOutputStream());   
            out[i].println(message);
		    out[i].flush();
		}
	}
	
	public static void Message_Connexion(String message) throws IOException
	{
		for(int i=0;i<Accept_connection.socket_table.size()-1;i++)
		{
        	out[i] = new PrintWriter(Accept_connection.socket_table.get(String.valueOf(i)).getOutputStream());   
            out[i].println(message);
		    out[i].flush();
		}
	}

}
