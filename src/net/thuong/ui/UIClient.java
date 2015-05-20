package net.thuong.ui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.thuong.clientthread.SendUDPThread;
import net.thuong.packetcustom.BasePacketCustom;

public class UIClient extends JFrame {

	private JPanel contentPane;
	public JTextField sendString;
	public JTextArea texLog;
	public JButton connectBtn;
	public SendUDPThread senUDPThread;

	/**
	 * Create the frame.
	 */
	public UIClient(SendUDPThread senUDPThread) {
		this.senUDPThread = senUDPThread;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHostOpenflow = new JLabel("    Host Openflow");
		lblHostOpenflow.setForeground(Color.RED);
		lblHostOpenflow.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblHostOpenflow.setBackground(Color.RED);
		lblHostOpenflow.setBounds(128, 12, 237, 85);
		contentPane.add(lblHostOpenflow);
		
		texLog = new JTextArea();
		texLog.setBounds(171, 174, 265, 87);
		contentPane.add(texLog);
		
		JLabel label = new JLabel("Log Controller");
		label.setBounds(43, 198, 110, 39);
		contentPane.add(label);
		
		JButton sendStringBtn = new JButton("SendString");
		sendStringBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendStringToController();
			}
		});
		sendStringBtn.setBackground(Color.ORANGE);
		sendStringBtn.setBounds(305, 120, 131, 25);
		contentPane.add(sendStringBtn);
		
		sendString = new JTextField();
		sendString.setColumns(10);
		sendString.setBounds(193, 123, 100, 19);
		contentPane.add(sendString);
		
		JLabel label_1 = new JLabel("String");
		label_1.setBounds(136, 125, 70, 15);
		contentPane.add(label_1);
		
		connectBtn = new JButton("Connect");
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connectController();
			}
		});
		connectBtn.setBackground(Color.ORANGE);
		connectBtn.setBounds(305, 12, 131, 25);
		contentPane.add(connectBtn);
	}
	
	public void sendStringToController(){
		senUDPThread.addQueue(new BasePacketCustom(BasePacketCustom.PACKET_TYPE_SEND_STRING, sendString.getText().getBytes()));
	}
	public void connectController(){
		senUDPThread.addQueue(new BasePacketCustom(BasePacketCustom.PACKET_TYPE_HELLO, "thuong123".getBytes()));
		connectBtn.setVisible(false);
	}
}

