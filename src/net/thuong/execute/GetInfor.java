package net.thuong.execute;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import net.thuong.clientthread.SendUDPThread;
import net.thuong.packetcustom.BasePacketCustom;
import net.thuong.packetcustom.PacketInfor;

public class GetInfor {
	public static void GetInfor(SendUDPThread sendUDPThread) {
		GetInfor getInfor = new GetInfor();
		getInfor.Info();

		PacketInfor packetInfor = new PacketInfor();
		packetInfor.maxMemory = getInfor.maxMemory;
		packetInfor.allocatedMemory = getInfor.allocatedMemory;
		packetInfor.freeMemory = getInfor.freeMemory;

		try {
			byte[] data = packetInfor.getData();
			if (sendUDPThread != null)
				sendUDPThread.addQueue(new BasePacketCustom(
						BasePacketCustom.PACKET_TYPE_SEND_INFOR, data));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	long maxMemory;
	long allocatedMemory;
	long freeMemory;

	private Runtime runtime = Runtime.getRuntime();

	public String Info() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.OsInfo());
		sb.append(this.MemInfo());
		sb.append(this.DiskInfo());
		return sb.toString();
	}

	public String OSname() {
		return System.getProperty("os.name");
	}

	public String OSversion() {
		return System.getProperty("os.version");
	}

	public String OsArch() {
		return System.getProperty("os.arch");
	}

	public long totalMem() {
		return Runtime.getRuntime().totalMemory();
	}

	public long usedMem() {
		return Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
	}

	public String MemInfo() {
		NumberFormat format = NumberFormat.getInstance();
		StringBuilder sb = new StringBuilder();
		maxMemory = runtime.maxMemory();
		allocatedMemory = runtime.totalMemory();
		freeMemory = runtime.freeMemory();
		sb.append("Free memory: ");
		sb.append(format.format(freeMemory / 1024));
		sb.append("\n");
		sb.append("Allocated memory: ");
		sb.append(format.format(allocatedMemory / 1024));
		sb.append("\n");
		sb.append("Max memory: ");
		sb.append(format.format(maxMemory / 1024));
		sb.append("\n");
		sb.append("Total free memory: ");
		sb.append(format
				.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
		sb.append("\n");
		return sb.toString();

	}

	public String OsInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("OS: ");
		sb.append(this.OSname());
		sb.append("\n");
		sb.append("Version: ");
		sb.append(this.OSversion());
		sb.append("\n");
		sb.append(": ");
		sb.append(this.OsArch());
		sb.append("\n");
		sb.append("Available processors (cores): ");
		sb.append(runtime.availableProcessors());
		sb.append("\n");
		return sb.toString();
	}

	public String DiskInfo() {
		/* Get a list of all filesystem roots on this system */
		File[] roots = File.listRoots();
		StringBuilder sb = new StringBuilder();

		/* For each filesystem root, print some info */
		for (File root : roots) {
			sb.append("File system root: ");
			sb.append(root.getAbsolutePath());
			sb.append("\n");
			sb.append("Total space (bytes): ");
			sb.append(root.getTotalSpace());
			sb.append("\n");
			sb.append("Free space (bytes): ");
			sb.append(root.getFreeSpace());
			sb.append("\n");
			sb.append("Usable space (bytes): ");
			sb.append(root.getUsableSpace());
			sb.append("\n");
		}
		return sb.toString();
	}
}
