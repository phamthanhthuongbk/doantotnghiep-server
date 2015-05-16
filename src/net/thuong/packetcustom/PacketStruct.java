package net.thuong.packetcustom;

import java.io.IOException;
public interface PacketStruct {
	public byte[] getData() throws IOException;
	public void processData(byte[] data) throws IOException;
}
