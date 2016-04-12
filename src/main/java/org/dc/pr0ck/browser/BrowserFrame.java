package org.dc.pr0ck.browser;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

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
		BuildPanel loadPanel = new BuildPanel();
		add(loadPanel.build());
		pack();
		return this;
	}
}
