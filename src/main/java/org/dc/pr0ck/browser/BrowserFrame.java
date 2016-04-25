package org.dc.pr0ck.browser;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BrowserFrame extends JFrame {

	private static final long serialVersionUID = -3709608831968441206L;

	public BrowserFrame() {
		super();
		init();
	}

	public BrowserFrame(GraphicsConfiguration gc) {
		super(gc);
		init();
	}

	public BrowserFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		init();
	}

	public BrowserFrame(String title) throws HeadlessException {
		super(title);
		init();
	}
	
	private void init() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public BrowserFrame build() {
		// build progress dialog box owned by this frame
		final JDialog progress = new JDialog(this, "Building ...", true);
		progress.add(BorderLayout.CENTER, new JLabel("Building your pr0ck"));
		progress.setSize(300, 75);
		progress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		progress.setLocationRelativeTo(this);

		// build panel with controls and define communication with dialog
		BuildPanel buildPanel = new BuildPanel();
		buildPanel.setBuildStartListener(() -> {
			new Thread(()-> {
				progress.setVisible(true);
			}).start();
		});
		buildPanel.setBuildSuccessListener(() -> {
			progress.setVisible(false);
			progress.dispose();
		});
		buildPanel.setBuildFailedListener((e) -> {
			progress.setVisible(false);
			progress.dispose();
		});

		add(buildPanel.build());
		pack();
		return this;
	}
}
