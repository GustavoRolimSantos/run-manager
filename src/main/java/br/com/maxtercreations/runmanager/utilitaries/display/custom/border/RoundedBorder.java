package br.com.maxtercreations.runmanager.utilitaries.display.custom.border;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.Border;

public class RoundedBorder implements Border {
	
	/* This class has the function to round the borders. */

	private int radius, index = -1, stroke = 1;
	private Color color;
	private boolean fill = false;

	public RoundedBorder(int radius) {
		this.radius = radius;
	}
	
	public RoundedBorder(int radius, int index) {
		this.radius = radius;
		this.index = index;
	}
	
	public RoundedBorder(int radius, Color color) {
		this.radius = radius;
		this.color = color;
	}
	
	public RoundedBorder(int radius, Color color, boolean fill) {
		this.radius = radius;
		this.color = color;
		this.fill = fill;
	}

	public Insets getBorderInsets(Component c) {
		int index = this.index != -1 ? this.index : this.radius / 10;
		
		return new Insets(index + 2, index + 1, index + 2, index);
	}

	public boolean isBorderOpaque() {
		return true;
	}
	
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D)g;
		
		Color oldColor = g2d.getColor();
		
		if (color != null)
			g2d.setColor(color);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setStroke(new BasicStroke(stroke));
		
		if (fill)
			g2d.fillRoundRect(x + (stroke * 2), y + (stroke * 2) - 2, width - (stroke * 4), height - (stroke * 4), radius, radius);
		else 
			g2d.drawRoundRect(x + (stroke * 2), y + (stroke * 2) - 2, width - (stroke * 4), height - (stroke * 4), radius, radius);
		
		g2d.setColor(oldColor);
	}
}