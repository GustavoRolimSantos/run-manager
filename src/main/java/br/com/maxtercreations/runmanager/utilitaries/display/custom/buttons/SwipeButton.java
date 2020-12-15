package br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.border.RoundedBorder;

public class SwipeButton {
	
	private boolean firstOption = false;
	private RoundedFillButton selectedBack;
	private Callback changesCallback;
	
	public boolean firstOption() {
		return firstOption;
	}
	
	public void setFirst(boolean active) {
		this.firstOption = !active;
		this.callback.done();
	}
	
	public void reverse() {
		setFirst(!firstOption);
	}
	
	private Callback callback = new Callback() {	
		@Override
		public void done() {
			Timer timer = new Timer(2, null);
			timer.addActionListener(new ActionListener() {
				JButton back = selectedBack.getBackgroundButton();
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (firstOption && back.getX() >= 270 || !firstOption && back.getX() <= 104) {
						back.setBounds(firstOption ? 278 : back.getX(), back.getY(), back.getWidth(), back.getHeight());
						changesCallback.done();
						timer.stop();
						return;
					}

					back.setBounds(firstOption ? back.getX() + 15 : back.getX() - 15, back.getY(), back.getWidth(), back.getHeight());
					back.repaint();
				}
			});
			timer.start();
			firstOption = !firstOption;
		}
	};

	public SwipeButton(String text1, String text2, int x, int y, JPanel panel, Callback changesCallback) {
		this.changesCallback = changesCallback;
		
		Utils utils = Loader.getManager().getUtils();

		JLabel option1 = new JLabel(text1);
		option1.setForeground(Color.WHITE);
		option1.setBounds(x + 16, y, 88, 53);
		option1.setHorizontalAlignment(SwingConstants.CENTER);
		option1.setFont(utils.getBebas(23));
		option1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				callback.done();
			}
		});
		panel.add(option1);
		
		JLabel option2 = new JLabel(text2);
		option2.setForeground(Color.WHITE);
		option2.setBounds(x + 199, y, 88, 53);
		option2.setHorizontalAlignment(SwingConstants.CENTER);
		option2.setFont(utils.getBebas(23));
		option2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				callback.done();
			}
		});
		panel.add(option2);
		
		selectedBack = new RoundedFillButton("", null, Color.WHITE, Constants.MAIN_DARK_COLOR, new Rectangle(x + 8, y + 6, 104, 41), 20, 1, panel, callback);	
		RoundedBorder circleBorder = new RoundedBorder(38, Constants.MAIN_DARK_COLOR, true);
		selectedBack.getBackgroundButton().setBorder(circleBorder);
		
		new RoundedFillButton("", null, Color.WHITE, Constants.MAIN_LIGHT_COLOR, new Rectangle(x, y, 304, 53), 50, 1, panel, callback);
	}
	
}
