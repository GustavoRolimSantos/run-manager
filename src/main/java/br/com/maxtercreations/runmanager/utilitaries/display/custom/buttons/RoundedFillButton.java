package br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.border.RoundedBorder;

public class RoundedFillButton {

	private RoundedButton backgroundButton;
	private JLabel textLabel;
	private JPanel panel;
	
	public RoundedFillButton(String text, Font font, Color foreground, Color background, Rectangle rectangle, int radius, int stroke, JPanel panel, Callback callback) {
		this.panel = panel;
		
		this.backgroundButton = new RoundedButton(text, font, background, rectangle, radius, stroke, callback);
		this.backgroundButton.setBorder(new RoundedBorder(radius, background, true));
		this.textLabel = new JLabel(text);
		this.textLabel.setBounds(rectangle);
		this.textLabel.setForeground(foreground);
		this.textLabel.setFont(font);
		this.textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.textLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (callback != null)
					callback.done();
			}
		});
		
		panel.add(textLabel);
		panel.add(backgroundButton);
	}
	
	public RoundedButton getBackgroundButton() {
		return backgroundButton;
	}
	
	public JLabel getTextLabel() {
		return textLabel;
	}
	
	public void recolor(Color color, int radius, boolean fill) {
		RoundedBorder border = new RoundedBorder(radius, color, fill);
		border.setStroke(2);
		backgroundButton.setBorder(border);
	}
	
	public int getBackgroundX() {
		return backgroundButton.getX();
	}
	
	public int getText() {
		return textLabel.getX();
	}
	
	public void setX(int x) {
		this.backgroundButton.setBounds(x, backgroundButton.getY(), backgroundButton.getWidth(), backgroundButton.getHeight());
		this.textLabel.setBounds(x, textLabel.getY(), textLabel.getWidth(), textLabel.getHeight());
	}
	
	public void addX(int x) {
		this.backgroundButton.setBounds(backgroundButton.getX() + x, backgroundButton.getY(), backgroundButton.getWidth(), backgroundButton.getHeight());
		this.textLabel.setBounds(textLabel.getX() + x, textLabel.getY(), textLabel.getWidth(), textLabel.getHeight());
	}
	
	public void removeX(int x) {
		this.backgroundButton.setBounds(backgroundButton.getX() - x, backgroundButton.getY(), backgroundButton.getWidth(), backgroundButton.getHeight());
		this.textLabel.setBounds(textLabel.getX() - x, textLabel.getY(), textLabel.getWidth(), textLabel.getHeight());
	}
	
	public void setTextX(int x) {
		this.textLabel.setBounds(x, textLabel.getY(), textLabel.getWidth(), textLabel.getHeight());
	}
	
	public void setTextY(int y) {
		this.textLabel.setBounds(textLabel.getX(), y, textLabel.getWidth(), textLabel.getHeight());
	}
	
	public void setBackgroundX(int x) {
		this.backgroundButton.setBounds(x, backgroundButton.getY(), backgroundButton.getWidth(), backgroundButton.getHeight());
	}
	
	public void dispose() {
		panel.remove(backgroundButton);
		panel.remove(textLabel);
		panel.repaint();
	}
	
}
