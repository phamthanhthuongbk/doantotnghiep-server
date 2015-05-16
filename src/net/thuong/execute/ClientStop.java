package net.thuong.execute;

import java.io.IOException;

public class ClientStop {
	public static void ClientStop() {

		try {
			Process child = Runtime.getRuntime().exec(
					"sudo shutdown -r now");
		} catch (IOException e) {
			System.out.print("Create bi loi");
		}
	}
}
