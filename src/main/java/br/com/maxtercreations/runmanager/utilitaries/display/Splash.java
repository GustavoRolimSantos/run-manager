package br.com.maxtercreations.runmanager.utilitaries.display;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class Splash {

	private JWindow window;

	public void splash(ImageIcon icon, int x, int y, int width, int hight) {
		this.window = new JWindow();
		this.window.getContentPane().add(new JLabel(icon, SwingConstants.CENTER));
		this.window.setBounds(x, y, width, hight);
		this.window.setLocationRelativeTo(null);
		this.window.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		this.window.setVisible(true);
	}
	
	public Splash(ImageIcon icon) {
		splash(icon, 128, 128, 200, 200);
	}
	
	public Splash(ImageIcon icon, int x, int y, int width, int hight) {
		splash(icon, x, y, width, hight);
	}

	public void dispose() {
		window.dispose();
	}

}