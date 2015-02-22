package client;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
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
import java.net.Socket;

@SuppressWarnings("serial")
public class Chat extends Frame{ 
	public TextArea messages;
	public static Thread t1;
	private Socket socket = null;
	private TextField msg;
	private User user;
	private List lst;
	private Label lTitle;
	
	public Chat(String log, String ip, String port) throws Exception
	{
			
		super("Easy Chat");	
		
		user= new User(log);
		
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		this.requestFocus();
		this.setLayout(new BorderLayout());
		
		Panel toolBar=new Panel();
		Panel messageBar=new Panel();
		
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
		toolBar.setBackground(Color.LIGHT_GRAY);
		
		messageBar.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));
		messageBar.setBackground(Color.LIGHT_GRAY);
		
		lTitle=new Label("Easy Chat");
		Font TitreFont = new Font("Arial",Font.BOLD,20);
		lTitle.setFont(TitreFont);
		toolBar.add(lTitle);
		
		this.add(toolBar, BorderLayout.NORTH);
		
		messages=new TextArea("");
		this.add(messages, BorderLayout.CENTER);
		
		Label barreStatut=new Label("Mega chat :)", Label.LEFT);
		barreStatut.setBackground(Color.LIGHT_GRAY);
		this.add(barreStatut, BorderLayout.SOUTH);
		
		lst= new List(4,false);
		this.add(lst, BorderLayout.EAST);
		
		msg= new TextField("", 100);
		
		messageBar.add(msg);
		
		Button envoyer= new Button("Send");
		messageBar.add(envoyer);
		
		this.add(messageBar, BorderLayout.SOUTH);
		
		Ecouteur ecouteur=new Ecouteur();
		this.addWindowListener(ecouteur);
		this.addKeyListener(ecouteur);
		envoyer.addActionListener(ecouteur);
		msg.addKeyListener(ecouteur);

	    System.out.println("Request a connection");
        socket = new Socket(ip,Integer.valueOf(port));
        System.out.println("Connection established"); // Si le message s'affiche c'est que je suis connecté    
        
        t1 = new Thread(new Connection(socket, messages, user, lst));
        t1.start();
		
		this.pack();
		this.setVisible(true);
	}
	
	//Send the message to the server
	public void message()
	{
		Chat_ClientServer.out.println(this.user.getNom()+" : "+this.msg.getText());
		this.msg.setText("");
		Chat_ClientServer.out.flush();
	}
	
	//Listener
	public class Ecouteur implements WindowListener, KeyListener, ActionListener{

		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}

		@Override
		public void windowClosing(WindowEvent arg0) {
			try {
				socket.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
			{
				message();
			}
		}
		public void keyReleased(KeyEvent arg0) {}
		public void keyTyped(KeyEvent arg0) {}
		
		public void actionPerformed(ActionEvent e) {
			message();
		}

	}
}