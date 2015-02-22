package server;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

@SuppressWarnings("serial")
public class Server extends Frame implements ActionListener {
public static ServerSocket ss = null;
public static Thread t;
private TextArea log;
private TextField port;
private Label lPort;
private Panel pPort;
private Button connection;
 
 Server() throws InterruptedException
 {
	 super("Server");
	 this.setLayout(new BorderLayout());
	 this.setSize(200, 200);
	 this.setResizable(false);
	 
	 pPort=new Panel();
	 pPort.setLayout(new GridLayout(1,2));
	 
	 log=new TextArea();
	 log.setEditable(false);
	 this.add(log, BorderLayout.CENTER);
	 
	 lPort=new Label("Port : ");
	 port=new TextField("2009",5);
	 
	 pPort.add(lPort);
	 pPort.add(port);
	 
	 this.add(pPort, BorderLayout.NORTH);
	 
	 connection= new Button("Connection");
	 this.add(connection, BorderLayout.SOUTH);
	 
	 connection.addActionListener(this);
	 
	 //Quit
	 this.addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent PeV){
				try {
					System.exit(0);
					
				} catch (Exception e) {	
					e.printStackTrace();
				}
			}
		});
	 
	 this.pack();
	 this.setVisible(true);
 }

	public static void main(String[] args) throws InterruptedException {
		new Server();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Disconnect the server
		if(e.getActionCommand().equals("Deconnection"))
		{
			try {
				connection.setLabel("Connection");
				port.setEditable(true);
				ss.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		//Launch the server
		else if(e.getActionCommand().equals("Connection"))
		{
			try {
				ss = new ServerSocket(Integer.parseInt(port.getText()));
				log.append("The server is Listening the port "+ss.getLocalPort()+System.getProperty("line.separator"));
				port.setEditable(false);
				connection.setLabel("Deconnection");
				t = new Thread(new Accept_connection(ss, log));
				t.start();
				
			} catch (IOException ex) {
				log.append("The port "+ss.getLocalPort()+" is already used !");
	
			}
		}		
	}
}