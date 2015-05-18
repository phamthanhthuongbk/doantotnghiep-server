package net.thuong.execute;

import net.thuong.packetcustom.BasePacketCustom;
import net.thuong.ui.UIClient;

public class SendString {
	public static void SendString(BasePacketCustom packet, UIClient uiClient){
		String logtex = "Controller " +  "  vua gui doan string: \n"
				+ new String(packet.data);
			uiClient.texLog.setText(logtex);
	}
}
