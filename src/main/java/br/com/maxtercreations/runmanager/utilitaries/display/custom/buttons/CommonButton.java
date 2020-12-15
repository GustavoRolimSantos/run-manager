package br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

@SuppressWarnings("serial")
public abstract class CommonButton extends JButton {

	/* This class has the function to create a common button using custom details. */
	
	public CommonButton(String text, Font font, Color color, Rectangle rectangle) {
		setBounds(rectangle);
		setForeground(Color.WHITE);
		setFont(font);
		setText(text);
		setBackground(color);
		setBorderPainted(false);
		setFocusPainted(false);

		setContentAreaFilled(false);
		setOpaque(true);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick();
			}
		});

	}

	public abstract void onClick();

}
