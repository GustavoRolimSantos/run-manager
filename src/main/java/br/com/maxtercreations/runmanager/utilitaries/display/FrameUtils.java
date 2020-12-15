package br.com.maxtercreations.runmanager.utilitaries.display;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import br.com.maxtercreations.runmanager.constants.Constants;

public class FrameUtils {
	
	/* This class has the function to create a frame that is used to display all the program information */

	private Point mouseDownCompCoords;

	public JFrame createFrame(String name, int x, int y, int width, int height, boolean moveable) {
		return createFrame(Constants.RESOURCES_DIRECTORY + "/application/icon.png", name, x, y, width, height, moveable);
	}
	
	public JFrame createFrame(String icon, String name, int x, int y, int width, int height, boolean moveable) {
		JFrame frame = new JFrame(name);

		frame.setUndecorated(true);
		frame.setBounds(x, y, width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FrameUtils.class.getResource(icon)));
		
		frame.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				if (moveable)
					mouseDownCompCoords = event.getPoint();
			}
			
			public void mouseReleased(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseClicked(MouseEvent event) {}
		});

		frame.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent event) {}

			public void mouseDragged(MouseEvent event) {
				if (moveable && mouseDownCompCoords != null)
					frame.setLocation(event.getLocationOnScreen().x - mouseDownCompCoords.x, event.getLocationOnScreen().y - mouseDownCompCoords.y);
			}
		});
		
		return frame;
	}

}