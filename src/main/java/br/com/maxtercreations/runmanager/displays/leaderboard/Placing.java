package br.com.maxtercreations.runmanager.displays.leaderboard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedFillButton;

public class Placing {

	public enum AnimationVector {
		RIGHT, LEFT;
	}

	private int startX, y, place, start = 0;
	private JLabel time;
	private JPanel panel;
	private RoundedFillButton placing, background;

	public Placing(int place, int start, Map<RunnerUtils, Long> map, RunnerUtils runner, Font placeFont, Font nameFont, Font timeFont, Color color, Color color2, JPanel panel, int startX, int y, int space, int x) {
		this.startX = startX;
		this.y = y;
		this.panel = panel;
		this.place = place;
		this.start = start;
		
		double rowS = (place - start) / 10;
		
		int row = (int) Math.round(rowS) + 1;
		
		int index = (row * 400);
		
		x = -index;
		
		placing = new RoundedFillButton((place + 1) + "º", placeFont, Color.WHITE, color, new Rectangle(x, y, 46, 46), 46, 1, panel, null);

		placing.getTextLabel().setBounds(x, y - 2, 46, 46);

		String name = runner.getName();

		/* Background */

		if (name.length() > 14)
			name = name.substring(0, 14);
		
		RunnerUtils currentRunner = null;
		
		for (RunnerUtils runnerUtils : map.keySet()) {
			if (runnerUtils.getDomainId().equals(runner.getDomainId())) {
				currentRunner = runnerUtils;
			}
		}
		
		time = new JLabel(Loader.getManager().getUtils().convertTime(map.get(currentRunner)));
		time.setForeground(color2);
		time.setBounds(x + 280, y, 100, 42);
		time.setFont(timeFont);
		time.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(time);

		background = new RoundedFillButton(name, nameFont, Color.WHITE, Constants.MAIN_DARK_COLOR, new Rectangle(x, y + 2, 353, 42), 42, 1, panel, null);

		background.getTextLabel().setBounds(x + 60, y, 353, 42);
		background.getTextLabel().setHorizontalAlignment(SwingConstants.LEFT);

		this.y += space;

		/* Correcting visual bugs */

		panel.setComponentZOrder(background.getBackgroundButton(), 2);
		panel.setComponentZOrder(background.getTextLabel(), 2);

		panel.setComponentZOrder(placing.getBackgroundButton(), 2);
		panel.setComponentZOrder(placing.getTextLabel(), 2);

		panel.setComponentZOrder(time, 2);
	}

	public void animate(AnimationVector vector) {
		int indexX = 100;

		Timer timer = new Timer(2, null);

		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int cX = placing.getBackgroundButton().getX();
				
				boolean right = vector.equals(AnimationVector.RIGHT);
				
				double rowS = (place - start) / 10;
				
				int row = (int) Math.round(rowS) + 1;
				
				int index = ((row - 1) * 400);

				if ((right && cX >= startX && (cX >= index || row == 1)) || (!right && cX <= -400)) {
					//System.out.println("[LeaderBoard] Stopping schedule.");
					timer.stop();

					if (right) {
						int dX = startX + ((row - 1) * 400);
						
						background.setBackgroundX(dX);
						background.setTextX(dX + 60);
						placing.setX(dX);
						time.setBounds(dX + 240, time.getY(), time.getWidth(), time.getHeight());
					} else {
						dispose();
					}
 					return;
				}
				
				if (right) {
					background.addX(indexX);
					placing.addX(indexX);
					time.setBounds(time.getX() + indexX, time.getY(), time.getWidth(), time.getHeight());
				} else {
					background.removeX(indexX);
					placing.removeX(indexX);
					time.setBounds(time.getX() - indexX, time.getY(), time.getWidth(), time.getHeight());
				}
			}
		});

		timer.start();
	}
	
	public void dispose() {
		background.dispose();
		placing.dispose();
		panel.remove(time);
	}
	
	public int getY() {
		return y;
	}

}
