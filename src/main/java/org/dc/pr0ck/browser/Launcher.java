package org.dc.pr0ck.browser;

public class Launcher {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				BrowserFrame frame = new BrowserFrame();
				frame.build().setVisible(true);
			}
		});
	}

}
