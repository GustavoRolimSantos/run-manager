package br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;

@SuppressWarnings("serial")
public class ModernButton extends JComponent {
	
	private RoundedFillButton button;
	
	public ModernButton(String text, Font font, Color foreground, Color background, Rectangle rect, String icon, JPanel panel, Callback callback) {
		Rectangle rect2 = new Rectangle((int) rect.getX() + 10, (int) rect.getY() + 5, (int) rect.getWidth() - 20, (int) rect.getHeight() - 10);
		
		button = new RoundedFillButton(text, font, foreground, Color.WHITE, rect2, 100, 1, panel, callback);
		
		new RoundedFillButton("", null, foreground, background, rect, 100, 1, panel, callback);

		button.getTextLabel().setHorizontalAlignment(SwingConstants.CENTER);
		button.getTextLabel().setVerticalAlignment(SwingConstants.CENTER);
		button.getTextLabel().setIconTextGap(25);
		button.getTextLabel().setIcon(new ImageIcon(ModernButton.class.getResource(Constants.RESOURCES_DIRECTORY + icon)));	
	}
	
	public ModernButton(String text, Font font, Color foreground, Color background, Rectangle rect, int radius, String icon, JPanel panel, Callback callback) {
		Rectangle rect2 = new Rectangle((int) rect.getX() + 10, (int) rect.getY() + 5, (int) rect.getWidth() - 20, (int) rect.getHeight() - 10);
		
		button = new RoundedFillButton(text, font, foreground, Color.WHITE, rect2, radius, 1, panel, callback);
		
		new RoundedFillButton("", null, foreground, background, rect, radius, 1, panel, callback);

		button.getTextLabel().setHorizontalAlignment(SwingConstants.CENTER);
		button.getTextLabel().setVerticalAlignment(SwingConstants.CENTER);
		button.getTextLabel().setIconTextGap(25);
		button.getTextLabel().setIcon(new ImageIcon(ModernButton.class.getResource(Constants.RESOURCES_DIRECTORY + icon)));	
	}
	
	public RoundedFillButton getButton() {
		return button;
	}
	
}
