package br.com.maxtercreations.runmanager.graphics.animation;

import java.awt.Rectangle;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AnimatedPanel extends JPanel {

	public enum AnimationVector {
		TOP, BOTTOM, RIGHT, LEFT
	}
	
	public AnimatedPanel() {
		setBounds(new Rectangle(0, 0, 1280, 880));
	}
	
	public void animate(int delay, int pixels, int time, AnimationVector vector) {
		/*Timer timer = new Timer(delay, null);
		
		boolean vertical = vector.equals(AnimationVector.TOP) || vector.equals(AnimationVector.BOTTOM);
		int index = vertical ? getY() : getX(), add = time / delay;

		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int x = getX(), y = getY(), w = getWidth(), h = getHeight();
				
				if (Math.abs((vertical ? y : x) - index) >= pixels) { //stop
					timer.stop();
				}
				
				if (vector.equals(AnimationVector.TOP)) {
					setBounds(x, y - add, w, h);
				} else 	if (vector.equals(AnimationVector.BOTTOM)) {
					setBounds(x, y + add, w, h);
				} else 	if (vector.equals(AnimationVector.RIGHT)) {
					setBounds(x + add, y, w, h);
				} else 	if (vector.equals(AnimationVector.LEFT)) {
					setBounds(x - add, y, w, h);
				}

				repaint();
			}
		});
		timer.start();*/
	}
	
}
