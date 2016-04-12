package org.dc.pr0ck.browser;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.xswingx.PromptSupport;

public class BuildPanel extends JPanel {

	private static final long serialVersionUID = -4276800181199724766L;
	
	private int componentHeight = 30;
	private int componentWidth = 100;
	private int textFieldWidth = 500;

	public BuildPanel setComponentHeight(int componentHeight) {
		this.componentHeight = componentHeight;
		return this;
	}
	
	public BuildPanel setButtonWidth(int buttonWidth) {
		this.componentWidth = buttonWidth;
		return this;
	}
	
	public BuildPanel setTextFieldWidth(int textFieldWidth) {
		this.textFieldWidth = textFieldWidth;
		return this;
	}
	
	public BuildPanel build() {
		setLayout(new BorderLayout());

		JPanel rootDirectoryPane = buildRootDirectoryPane();
		add(rootDirectoryPane, BorderLayout.NORTH);
		
		JPanel outputPane = buildOutputPane();
		add(outputPane, BorderLayout.CENTER);
		
		JPanel passwordPane = buildPasswordPane();
		add(passwordPane, BorderLayout.SOUTH);

		return this;
	}
	
	private JPanel buildRootDirectoryPane() {
		JPanel pane = new JPanel();
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(textFieldWidth, componentHeight));
		PromptSupport.setPrompt("Root directory", textField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, textField);
		pane.add(textField);
		
		JButton button = new JButton("Select");
		button.setPreferredSize(new Dimension(componentWidth, componentHeight));
		pane.add(button);
		
		return pane;
	}
	
	public JPanel buildOutputPane() {
		JPanel pane = new JPanel();
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(textFieldWidth, componentHeight));
		PromptSupport.setPrompt("Output file", textField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, textField);
		pane.add(textField);
		
		JButton button = new JButton("Select");
		button.setPreferredSize(new Dimension(componentWidth, componentHeight));
		pane.add(button);
		
		return pane;
	}
	
	public JPanel buildPasswordPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());

		JPanel passwordPanel = new JPanel();
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(textFieldWidth, componentHeight));
		PromptSupport.setPrompt("Password", textField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, textField);
		passwordPanel.add(textField);
		
		JCheckBox checkBox = new JCheckBox("Password");
		checkBox.setPreferredSize(new Dimension(componentWidth, componentHeight));
		passwordPanel.add(checkBox);
		
		pane.add(passwordPanel, BorderLayout.NORTH);
		
		JPanel ignorePanel = new JPanel();
		JCheckBox ignoreCheckBox = new JCheckBox("Ignore *.db and similar files");
		ignorePanel.add(ignoreCheckBox);
		
		JButton buildButton = new JButton("Build");
		buildButton.setPreferredSize(new Dimension(componentWidth, componentHeight));
		ignorePanel.add(buildButton);
		
		pane.add(ignorePanel, BorderLayout.SOUTH);
		
		return pane;
	}
}
