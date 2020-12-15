package br.com.maxtercreations.runmanager.stopwatch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JPanel;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedButton;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedFillButton;

public class StopWatch {

	private final Color EXTERNAL_COLOR = new Color(197, 62, 62);
	private final Color INTERNAL_COLOR = new Color(255, 185, 87);
	private final int STROKE = 7;
	private int D_INDEX = 25, RADIUS = 250;
	
	private RoundedButton external, internal;
	private RoundedFillButton watch;
	
	public StopWatch(Rectangle rect, Font font, JPanel panel) {
		build(rect, font, (int) (RADIUS*rect.getWidth()/528), panel);
	}
	
	public StopWatch(Rectangle rect, Font font, int radius, JPanel panel) {
		build(rect, font, (int) (radius*rect.getWidth()/528), panel);
	}
	
	private void build(Rectangle rect, Font font, int radius, JPanel panel) {
		int x = (int) rect.getX(), y = (int) rect.getY(), w = (int) rect.getWidth(), h = (int) rect.getHeight();
		
		RADIUS = radius;
		D_INDEX = 25*w/528;
		
		this.external = new RoundedButton("", null, EXTERNAL_COLOR, rect, RADIUS, STROKE, null);
		
		int watchIndex = 20;
		
		Rectangle internalBounds = new Rectangle(x + (D_INDEX / 2), y + (D_INDEX / 2), w - D_INDEX, h - D_INDEX);
		Rectangle watchBounds = new Rectangle(x + D_INDEX + (watchIndex / 2), y + D_INDEX + (watchIndex / 2), w - (D_INDEX * 2) - watchIndex, h - (D_INDEX * 2) - watchIndex);
		
		this.internal = new RoundedButton("", null, INTERNAL_COLOR, internalBounds, RADIUS, STROKE, null);

		this.watch = new RoundedFillButton("00:00:00", font, Color.WHITE, Constants.MAIN_LIGHT_COLOR, watchBounds, RADIUS, 1, panel, null);
		
		panel.add(external);
		panel.add(internal);
	}
	
	public RoundedFillButton getWatch() {
		return watch;
	}
	
	public void setBackground(Color color) {
		this.watch.recolor(color, RADIUS, true);
	}
	
	public void update(long time) {
		long ms = System.currentTimeMillis() - time;
		int seconds = (int) (ms / 1000) % 60;
		int minutes = (int) (ms / (1000 * 60)) % 60;
		int hours = (int) (ms / (1000 * 60 * 60) % 24);
		
		watch.getTextLabel().setText((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
	}
	
}
