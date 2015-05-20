package net.thuong.execute;

import java.io.IOException;

import net.thuong.ui.UIClient;

public class ClientStop {
	public static void ClientStop(UIClient uiClient) {

		uiClient.texLog.setText("Controller ngat ket noi");
		uiClient.connectBtn.setVisible(true);
		try {
			Process child = Runtime.getRuntime().exec(
					"sudo shutdown -r now");
		} catch (IOException e) {
			System.out.print("Create bi loi");
		}
	}
}
