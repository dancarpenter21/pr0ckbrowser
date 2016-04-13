package org.dc.pr0ck.browser;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class TextFieldEventListener implements DocumentListener {

	@Override
	public final void insertUpdate(DocumentEvent e) {
		onEvent(e);
	}

	@Override
	public final void removeUpdate(DocumentEvent e) {
		onEvent(e);
	}

	@Override
	public final void changedUpdate(DocumentEvent e) {
		onEvent(e);
	}
	
	public abstract void onEvent(DocumentEvent e);

}
