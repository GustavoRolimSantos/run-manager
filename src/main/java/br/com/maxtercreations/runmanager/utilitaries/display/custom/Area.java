package br.com.maxtercreations.runmanager.utilitaries.display.custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.border.RoundedBorder;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedButton;

public class Area {

	private ArrayList<JComponent> components = new ArrayList<>();

	private void build(Rectangle rectangle, int radius, Callback callback) {
		RoundedButton background = new RoundedButton(null, null, Constants.MAIN_LIGHT_COLOR, rectangle, null);
		background.setBorder(new RoundedBorder(radius, Constants.MAIN_LIGHT_COLOR, true));
		
		if (callback != null) {
			background.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					callback.done();
				}
			});
		}

		components.add(background);
	}
	
	public Area(Rectangle rectangle, int radius, Callback callback) {
		build(rectangle, radius, callback);
	}
	
	public Area(Rectangle rectangle, Callback callback) {
		build(rectangle, ((int) rectangle.getHeight() * 100) / 110, callback);
	}

	public Area(Rectangle rectangle) {
		build(rectangle, ((int) rectangle.getHeight() * 100) / 110, null);
	}
	
	public Area(Rectangle rectangle, int radius) {
		build(rectangle, radius, null);
	}
	
	public Area() {

	}

	public void addLine(Rectangle rectangle) {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(Color.GRAY);
		label.setBounds(rectangle);
		components.add(label);
	}

	public void addTitle(String text, Font font, Rectangle rectangle, Callback callback) {
		JLabel title = new JLabel(text);
		title.setForeground(Color.GRAY);
		title.setBounds(rectangle);
		title.setFont(font);
		
		if (callback != null) {
			title.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					callback.done();
				}
			});
		}
		
		components.add(title);
	}

	public void addTitle(String text, Font font, Rectangle rectangle) {
		addTitle(text, font, rectangle, null);
	}

	public void addArrow(Font font, Rectangle rectangle, Callback callback) {
		JLabel title = new JLabel(">");
		title.setForeground(Color.GRAY);
		title.setBounds(rectangle);
		title.setFont(font);
		
		if (callback != null) {
			title.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					callback.done();
				}
			});
		}
		
		components.add(title);
	}

	public void addArrow(Font font, Rectangle rectangle) {
		addArrow(font, rectangle, null);
	}

	public void add(JPanel panel) {
		Collections.reverse(components);
		for (JComponent component : components) {
			panel.add(component);
		}
	}

}
