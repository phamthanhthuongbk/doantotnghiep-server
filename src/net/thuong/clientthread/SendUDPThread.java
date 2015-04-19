package net.thuong.clientthread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

import net.thuong.packetcustom.BasePacketCustom;

public class SendUDPThread extends Thread {
	
	private DatagramSocket clientSocket;
	private InetAddress IPAddress;
	private int port;

	private final Queue<BasePacketCustom> queue; 

	public SendUDPThread(DatagramSocket clientSocket, InetAddress IPAddress,
			int port) {
		ThreadLog("Khoi tao thread Send");
		queue = new LinkedList<BasePacketCustom>();
		this.clientSocket = clientSocket;
		this.IPAddress = IPAddress;
		this.port = port;
//		this.addQueue(new BasePacketCustom(1, "thuong".getBytes()));
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
		while (true) {
			try {
				Runnable work = null;

				synchronized (queue) {
					while (queue.isEmpty())
						queue.wait();

					// Get the next work item off of the queue
					BasePacketCustom packet = queue.remove();

					// send packet header
					DatagramPacket sendPacket = new DatagramPacket(
							packet.getBytePacket(), packet.getLengPacket(),
							IPAddress, port);
					clientSocket.send(sendPacket);

//					System.out.print("\nHam gui thuc thi");
				}

				// Process the work item
//				work.run();
			} catch (InterruptedException ie) {
				ThreadLog("Error Thread");
			} catch (IOException e) {
				ThreadLog("Khong lay duoc byte data goi tin");
			}
		}
	}

	public void setLogEnable(boolean logEnable) {
		this.logEnable = logEnable;
	}

	private boolean logEnable = true;
	public void ThreadLog(String log) {
		if (logEnable)
			System.out.print("\nSendUDPThread: " + log);
	}
}
