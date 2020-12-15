package br.com.maxtercreations.runmanager.utilitaries.display;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class TextFieldUtil {

	/*
	 * This class has the function to limit and set a reset section for a
	 * jTextField.
	 */

	private JTextField field;
	private Color color;

	public TextFieldUtil(JTextField field) {
		this.field = field;
		this.color = new Color(165, 165, 165);
	}

	public TextFieldUtil setColor(Color color) {
		this.color = color;
		return this;
	}

	public TextFieldUtil addReset(String... text) {
		field.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				for (String texts : text) {
					if (field.getText().equals(texts)) {
						field.setText("");
						field.setForeground(color);
					}
				}
			}
		});

		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				for (String texts : text) {
					if (field.getText().equals(texts)) {
						field.setText(event.getKeyChar() + "");
						field.setForeground(color);
					}
				}
			}
		});

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				if (((JTextField) event.getSource()).getText().length() == 0)
					((JTextField) event.getSource()).setText(text[0]);
			}
		});
		return this;
	}

	public TextFieldUtil addLimit(int limit) {
		field.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
				if (field.getText().length() >= limit)
					event.consume();
			}
		});
		return this;
	}
	
	public TextFieldUtil addDictionary(String dictionary) {
		field.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent event) {
				if (!dictionary.contains(event.getKeyChar()+""))
					event.consume();
			}
		});
		return this;
	}

}
