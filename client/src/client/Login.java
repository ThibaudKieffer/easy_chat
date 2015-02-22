package client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.UnknownHostException;

@SuppressWarnings("serial")
public class Login extends Frame{
	private TextField log;
	private TextField ip;
	private TextField port;
	private TextArea info;
	private Label lIp;
	private Label lPort;
	private Label lLog; 
	private Button validate;
	private Panel param;
	private GridLayout grid;
	
	Login() throws UnknownHostException, IOException
	{
		super("Easy Chat");
		this.setSize(300, 200);
		this.setResizable(false);
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		//select a BorderLayout to main panel layout
		this.setLayout(new BorderLayout());
		
		//create an other panel
		param=new Panel();
		//create an other layout
		grid = new GridLayout(3,2);
		param.setLayout(grid);
		param.setBackground(Color.LIGHT_GRAY);
		//add this layout in the new panel
		this.add(param, BorderLayout.NORTH);
		
		//Declare params components
		ip= new TextField("127.0.0.1",12);
		lIp=new Label("IP Adress : ");
		port= new TextField("2009",12);
		lPort= new Label("Port : ");
		log= new TextField("login",12);
		lLog= new Label("Login : ");
		
		//Add component in the panel
		param.add(lIp);
		param.add(ip);
		param.add(lPort);
		param.add(port);
		param.add(lLog);
		param.add(log);
		
		info=new TextArea("");
		info.setEditable(false);
		this.add(info, BorderLayout.CENTER);

		validate= new Button("Validate");
		this.add(validate, BorderLayout.SOUTH);
		
		Ecouteur ecouteur=new Ecouteur();
		this.addWindowListener(ecouteur);
		this.addKeyListener(ecouteur);
		
		validate.addActionListener(ecouteur);		
		
		this.pack();
		this.setVisible(true);	
	}
	
	public static void main(String argv[]) throws UnknownHostException, IOException
	{
		new Login();
	}
	
	// a function for connect to the server
	public void connection()
	{
		try {
			info.append("Request a connection to the server ..."+System.getProperty("line.separator"));
			new Chat(this.log.getText(), this.ip.getText(), this.port.getText());
			info.append("Connection established."+System.getProperty("line.separator"));
			this.dispose();
			
		} catch (Exception ex) {
			info.append("Error : Check if the IP or the Port is correct."+System.getProperty("line.separator"));
		}
	}
	
	//Listeners
	public class Ecouteur implements WindowListener, KeyListener, ActionListener{

		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}

		@Override
		public void windowClosing(WindowEvent arg0) {
			System.exit(0);
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
			{
				connection();
			}
		}
		
		public void keyReleased(KeyEvent arg0) {}
		public void keyTyped(KeyEvent arg0) {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			connection();
		}
	}
}




