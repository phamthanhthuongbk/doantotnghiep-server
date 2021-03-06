package net.thuong.clientthread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;

import net.thuong.execute.ClientStop;
import net.thuong.execute.GetInfor;
import net.thuong.execute.SendString;
import net.thuong.packetcustom.BasePacketCustom;
import net.thuong.ui.UIClient;

public class ProcessThread extends Thread {

	public SendUDPThread sendUDPThread;
	private int numberPacket = 0;
	public UIClient uiClient;
	
//	public ReceiveUDPThread receiveUDPThread;
	private final Queue<BasePacketCustom> queue ; 
	
	public ProcessThread(){
		ThreadLog("Khoi tao thread Process");
		queue = new LinkedList<BasePacketCustom>();
	}
	
	public void addQueue(BasePacketCustom packet) {
//		System.out.print("\nHam add queue thuc thi");
		

		
		synchronized (queue) {
			// Add work to the queue
			queue.add(packet);

			// Notify the monitor object that all the threads
			// are waiting on. This will awaken just one to
			// begin processing work from the queue
			queue.notify();
		}

	}
	
	@Override
	public void run() {

		uiClient = new UIClient(sendUDPThread);
		uiClient.setVisible(true);
		
		while (true) {
			try {
				Runnable work = null;

				synchronized (queue) {
					while (queue.isEmpty())
						queue.wait();

					// Get the next work item off of the queue
					BasePacketCustom packet = queue.remove();

					//Day la Doan de chen codexuli
					processPacket(packet);
					
					
//					System.out.print("\nHam gui thuc thi");
				}

				// Process the work item
//				work.run();
			} catch (InterruptedException ie) {
				ThreadLog("Error Thread");
			} 
		}
	}
	public void setLogEnable(boolean logEnable) {
		this.logEnable = logEnable;
	}

	private boolean logEnable = true;
	public void ThreadLog(String log) {
		if (logEnable)
			System.out.print("\nProcessThread: " + log);
	}

	
	private void processPacket(BasePacketCustom packet){
		numberPacket++;
		ThreadLog("Goi tin thu: " + numberPacket);
		ThreadLog("Data Type: " + packet.packetType);
		ThreadLog("Data Leng: " + packet.dataLeng);
		ThreadLog("Data : " + new String(packet.data));
		ThreadLog("\n");
		
		switch (packet.packetType) {
		case BasePacketCustom.PACKET_TYPE_SEND_STRING:
			SendString.SendString(packet, uiClient);
			break;
		case BasePacketCustom.PACKET_TYPE_GET_INFOR:
			GetInfor.GetInfor(sendUDPThread);
			break;
		case BasePacketCustom.PACKET_TYPE_STOP_SERVER:
			ClientStop.ClientStop(uiClient);
			break;
		default:
			break;
		}
		
	}
	
}
