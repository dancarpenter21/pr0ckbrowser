package org.dc.pr0ck.browser;

import java.awt.BorderLayout;
import java.awt.Color;
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
	
	private static Color TEXTFIELD_BGCOLOR_DEFAULT = null;

	private static final long serialVersionUID = -4276800181199724766L;
	
	private int componentHeight = 30;
	private int componentWidth = 100;
	private int textFieldWidth = 500;
	
	private String rootDirectoryString = null;
	private String outputFileString = null;
	private String password = null;
	private boolean usePassword = false;
	private boolean ignoreThumbsDb = true;
	
	private OnBuildFailureListener failure = null;
	private OnBuildStartedListener start = null;
	private OnBuildSuccessListener success = null;
	
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
	
	public void setBuildStartListener(OnBuildStartedListener l) {
		start = l;
	}
	
	public void setBuildSuccessListener(OnBuildSuccessListener l) {
		success = l;
	}
	
	public void setBuildFailedListener(OnBuildFailureListener l) {
		failure = l;
	}
	
	private JPanel buildRootDirectoryPane() {
		JPanel pane = new JPanel();
		final JTextField textField = new JTextField();
		TEXTFIELD_BGCOLOR_DEFAULT = textField.getBackground();
		textField.setPreferredSize(new Dimension(textFieldWidth, componentHeight));
		PromptSupport.setPrompt("Root directory", textField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, textField);
		textField.getDocument().addDocumentListener(new TextFieldEventListener() {
			@Override
			public void onEvent(DocumentEvent e) {
				rootDirectoryString = textField.getText();
				if (ProckUtils.exists(rootDirectoryString) && ProckUtils.isDirectory(rootDirectoryString)) {
					textField.setBackground(Constants.TEXTFIELD_BGCOLOR_GREEN);
				} else {
					textField.setBackground(Constants.TEXTFIELD_BGCOLOR_RED);
				}
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
		    	if (ProckUtils.isFilePath(outputFileString)) {
		    		if (ProckUtils.exists(outputFileString)) {
						textField.setBackground(Constants.TEXTFIELD_BGCOLOR_YELLOW);
		    		} else {
						textField.setBackground(Constants.TEXTFIELD_BGCOLOR_GREEN);
		    		}
		    	} else {
		    		textField.setBackground(Constants.TEXTFIELD_BGCOLOR_RED);
		    	}
			}
		});

		pane.add(textField);
		
		JButton button = new JButton("Select");
		button.setPreferredSize(new Dimension(componentWidth, componentHeight));
		button.addActionListener((ActionEvent e) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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
		final JCheckBox checkBox = new JCheckBox("Password");
		textField.setPreferredSize(new Dimension(textFieldWidth, componentHeight));
		PromptSupport.setPrompt("Password", textField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, textField);
		textField.setEnabled(false);
		textField.getDocument().addDocumentListener(new TextFieldEventListener() {
			@Override
			public void onEvent(DocumentEvent e) {
				if (usePassword) {
					password = textField.getText();
					if (checkBox.isSelected()) {
						if (password == null || password.trim().length() == 0) {
							textField.setBackground(Constants.TEXTFIELD_BGCOLOR_RED);
						} else {
							textField.setBackground(Constants.TEXTFIELD_BGCOLOR_GREEN);
						}
					} else {
						textField.setBackground(TEXTFIELD_BGCOLOR_DEFAULT);
					}
				}
			}
		});

		passwordPanel.add(textField);
		
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

			if (checkBox.isSelected()) {
				if (password == null || password.trim().length() == 0) {
					textField.setBackground(Constants.TEXTFIELD_BGCOLOR_RED);
				} else {
					textField.setBackground(Constants.TEXTFIELD_BGCOLOR_GREEN);
				}
			} else {
				textField.setBackground(TEXTFIELD_BGCOLOR_DEFAULT);
			}
		});
		
		passwordPanel.add(checkBox);
		
		pane.add(passwordPanel, BorderLayout.NORTH);
		
		JPanel ignorePanel = new JPanel();
		final JCheckBox ignoreCheckBox = new JCheckBox("Ignore *.db and similar files");
		ignoreCheckBox.setSelected(ignoreThumbsDb);
		ignoreCheckBox.addActionListener((ActionEvent e) -> {
			ignoreThumbsDb = ignoreCheckBox.isSelected();
		});
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
					if (start != null) {
						start.onStart();
					}

					ProckUtils.makeProck(rootDirectoryString, outputFileString, password, ignoreThumbsDb);

					if (success != null) {
						success.onSuccess();
					}
				} catch (Exception e1) {
					if (failure != null) {
						failure.onFailure(e1);
					}
				}
			}).start();
		});

		ignorePanel.add(buildButton);
		
		pane.add(ignorePanel, BorderLayout.SOUTH);
		return pane;
	}
	
}
