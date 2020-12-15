package br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.border.RoundedBorder;

public class ToggleButton {
	
	private int x;
	private boolean activating = false;
	private RoundedButton circle, background;
	private Callback changesCallback;
	private Color disabledColor = Color.DARK_GRAY;
	
	public boolean isActive() {
		return activating;
	}
	
	public void setActive(boolean active) {
		this.activating = !active;
		this.callback.done();
	}
	
	public void reverse() {
		setActive(!activating);
	}
	
	private Callback callback = new Callback() {	
		@Override
		public void done() {
			Timer timer = new Timer(5, null);
			timer.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (activating && circle.getX() >= (x + 24) || !activating && circle.getX() <= (x - 1)) {
						circle.recolor(activating ? Color.WHITE : disabledColor, 90, true);
						background.recolor(activating ? new Color(37, 133, 236) : disabledColor, 23, activating);
						changesCallback.done();
						timer.stop();
						return;
					}

					circle.setBounds(activating ? circle.getX() + 3 : circle.getX() - 3, circle.getY(), circle.getWidth(), circle.getHeight());
					circle.repaint();
				}
			});
			timer.start();
			activating = !activating;
		}
	};

	public ToggleButton(int x, int y, JPanel panel, Callback changesCallback) {
		this.x = x;
		this.changesCallback = changesCallback;
		
		circle = new RoundedButton("", null, disabledColor, new Rectangle(x, y, 19, 19), 90, callback);	
		RoundedBorder circleBorder = new RoundedBorder(30, disabledColor, true);
		circle.setBorder(circleBorder);
		panel.add(circle);
		
		background = new RoundedButton("", null, disabledColor, new Rectangle(x - 11, y - 6, 60, 31), 23, callback);
		panel.add(background);
	}
	
}
