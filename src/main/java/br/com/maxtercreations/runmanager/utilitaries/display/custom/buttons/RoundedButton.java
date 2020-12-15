package br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.border.RoundedBorder;

@SuppressWarnings("serial")
public class RoundedButton extends JButton {
	
	/* This class has the function to create a custom button using the rounded border. */

	public RoundedButton(String text, Font font, Color color, Rectangle rectangle, Callback callback) {
		draw(text, font, color, null, rectangle, 50, 1, callback);
	}
	
	public RoundedButton(String text, Font font, Color color, Rectangle rectangle, int radius, Callback callback) {
		draw(text, font, color, null, rectangle, radius, 1, callback);
	}
	
	public RoundedButton(String text, Font font, Color color, Rectangle rectangle, int radius, int stroke, Callback callback) {
		draw(text, font, color, null, rectangle, radius, stroke, callback);
	}
	
	public RoundedButton(String text, Font font, Color foreground, Color background, Rectangle rectangle, int radius, int stroke, Callback callback) {
		draw(text, font, foreground, background, rectangle, radius, stroke, callback);
	}
	
	
	private void draw(String text, Font font, Color foreground, Color background, Rectangle rectangle, int radius, int stroke, Callback callback) {
		setOpaque(false);
		RoundedBorder border = new RoundedBorder(radius, background);
		border.setStroke(stroke);
		setBorder(border);
		setBounds(rectangle);
		setForeground(foreground);
		setFont(font);
		setText(text);
		setContentAreaFilled(false);
		setFocusPainted(false);

		if (callback != null) {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					callback.done();
				}
			});
		}
	}
	
	public void recolor(Color color, int radius, boolean fill) {
		RoundedBorder border = new RoundedBorder(radius, color, fill);
		border.setStroke(2);
		setBorder(border);
	}
	
}
