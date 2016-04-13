package org.dc.pr0ck.browser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileFilter;

import org.jdesktop.xswingx.PromptSupport;

public class BuildPanel extends JPanel {

	private static final long serialVersionUID = -4276800181199724766L;
	
	private int componentHeight = 30;
	private int componentWidth = 100;
	private int textFieldWidth = 500;
	
	private String rootDirectoryString = null;
	private String outputFileString = null;
	private String password = null;
	private boolean usePassword = false;
	
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
		final JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(textFieldWidth, componentHeight));
		PromptSupport.setPrompt("Root directory", textField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, textField);
		textField.getDocument().addDocumentListener(new TextFieldEventListener() {
			@Override
			public void onEvent(DocumentEvent e) {
				rootDirectoryString = textField.getText();
			}
		});

		pane.add(textField);
		
		JButton button = new JButton("Select");
		button.setPreferredSize(new Dimension(componentWidth, componentHeight));
		button.addActionListener((ActionEvent e) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    int returnVal = chooser.showOpenDialog(BuildPanel.this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       textField.setText(chooser.getSelectedFile().getAbsolutePath());
		    }	
		});

		pane.add(button);
		return pane;
	}
	
	private JPanel buildOutputPane() {
		JPanel pane = new JPanel();
		final JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(textFieldWidth, componentHeight));
		PromptSupport.setPrompt("Output file", textField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, textField);
		textField.getDocument().addDocumentListener(new TextFieldEventListener() {
			@Override
			public void onEvent(DocumentEvent e) {
				outputFileString = textField.getText();
			}
		});

		pane.add(textField);
		
		JButton button = new JButton("Select");
		button.setPreferredSize(new Dimension(componentWidth, componentHeight));
		button.addActionListener((ActionEvent e) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return ".pr0ck files";
				}
				
				@Override
				public boolean accept(File f) {
					return f.getAbsolutePath().endsWith(".pr0ck");
				}
			});

		    int returnVal = chooser.showOpenDialog(BuildPanel.this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       textField.setText(chooser.getSelectedFile().getAbsolutePath());
		    }	
		});

		pane.add(button);
		return pane;
	}
	
	private JPanel buildPasswordPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());

		JPanel passwordPanel = new JPanel();
		final JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(textFieldWidth, componentHeight));
		PromptSupport.setPrompt("Password", textField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, textField);
		textField.setEnabled(false);
		textField.getDocument().addDocumentListener(new TextFieldEventListener() {
			@Override
			public void onEvent(DocumentEvent e) {
				if (usePassword) {
					password = textField.getText();
				}
				System.out.println("password " + password);
			}
		});

		passwordPanel.add(textField);
		
		final JCheckBox checkBox = new JCheckBox("Password");
		checkBox.setPreferredSize(new Dimension(componentWidth, componentHeight));
		checkBox.addActionListener((ActionEvent e) -> {
			if (checkBox.isSelected()) {
				usePassword = true;
				textField.setEnabled(true);
				textField.setText(password);
				textField.requestFocusInWindow();
			} else {
				usePassword = false;
				textField.setText(null);
				textField.setEnabled(false);
			}
		});
		
		passwordPanel.add(checkBox);
		
		pane.add(passwordPanel, BorderLayout.NORTH);
		
		JPanel ignorePanel = new JPanel();
		JCheckBox ignoreCheckBox = new JCheckBox("Ignore *.db and similar files");
		ignorePanel.add(ignoreCheckBox);
		
		JButton buildButton = new JButton("Build");
		buildButton.setPreferredSize(new Dimension(componentWidth, componentHeight));
		buildButton.addActionListener((ActionEvent e) -> {
			if (rootDirectoryString == null || rootDirectoryString.trim().length() == 0) {
				return;
			}

			if (outputFileString == null || outputFileString.trim().length() == 0) {
				return;
			}

			final String password;
			if (usePassword) {
				if (this.password == null || this.password.trim().length() == 0) {
					return;
				}

				password = this.password;
			} else {
				password = null;
			}

			new Thread(() -> {
				try {
					ProckUtils.makeProck(rootDirectoryString, outputFileString, password);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}).start();
		});

		ignorePanel.add(buildButton);
		
		pane.add(ignorePanel, BorderLayout.SOUTH);
		return pane;
	}
	
}
