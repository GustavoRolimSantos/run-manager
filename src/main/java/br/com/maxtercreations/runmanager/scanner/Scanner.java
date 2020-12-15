package br.com.maxtercreations.runmanager.scanner;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;

public class Scanner {

	private JPanel panel;
	private Callback callback;
	
	public Scanner(JPanel panel, Callback callback) {
		this.callback = callback;
		this.panel = panel;
		inputListener();
	}
	
	private long time = 0;
	private String code = new String();
	
	public void inputListener() {
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		time = System.currentTimeMillis();
		panel.addKeyListener(new KeyAdapter() {
			boolean start = true;
			@Override
			public void keyReleased(KeyEvent e) {
				
				if (System.currentTimeMillis() - time <= 25 || start) {
					time = System.currentTimeMillis();
					code += e.getKeyChar();
					start = false;
				} else {
					code = new String();
					code += e.getKeyChar();
					time = System.currentTimeMillis();
				}
				
				if (code.length() == 6) {
					System.out.println(code);
					callback.done();
					code = new String();
					time = System.currentTimeMillis();
					start = true;
				}
			}
		});
	}
	
	public String getCode() {
		return code;
	}
	
}
