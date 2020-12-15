package br.com.maxtercreations.runmanager.displays;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.displays.enrolment.RunnersDisplay;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.ModernButton;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedButton;

public class StartDisplay {

	private Manager manager;
	private Utils utils;
	private JPanel panel;

	public StartDisplay() {
		manager = Loader.getManager();
		utils = manager.getUtils();

		JFrame frame = manager.getMainFrame();
		panel = new JPanel();

		panel.setLayout(null);
		panel.setBackground(Constants.MAIN_DARK_COLOR);

		JLabel logo = new JLabel();
		logo.setBounds(484, 30, 312, 162);
		logo.setIcon(new ImageIcon(StartDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/application/small-logo.png")));
		panel.add(logo);
		
		panel.add(new RoundedButton("X", utils.getBoldFont(20), Constants.MAIN_GOLD_COLOR, new Rectangle(1230, 10, 40, 40), new Callback() {	
			@Override
			public void done() {
				System.exit(0);
			}
		}));
		
		int height = 108, index = 15, y = 125;

		new ModernButton("RANKING", utils.getBoldFont(50), Constants.MAIN_LIGHT_COLOR, Constants.MAIN_LIGHT_COLOR, new Rectangle(458, y += height + index, 365, height), "/buttons/medal.png", panel, new Callback() {	
			@Override
			public void done() {
				
			}
		});
		
		new ModernButton("CORREDORES", utils.getBoldFont(50), Constants.MAIN_LIGHT_COLOR, Constants.MAIN_LIGHT_COLOR, new Rectangle(413, y += height + index, 456, height), "/buttons/men-running.png", panel, new Callback() {	
			@Override
			public void done() {
				new RunnersDisplay();
			}
		});
		
		new ModernButton("CONFIGURAÇÕES", utils.getBoldFont(50), Constants.MAIN_LIGHT_COLOR, Constants.MAIN_LIGHT_COLOR, new Rectangle(335, y += height + index, 610, height), "/buttons/settings.png", panel, new Callback() {	
			@Override
			public void done() {
				
			}
		});
		
		new ModernButton("INICIAR NOVA CORRIDA", utils.getBoldFont(50), Constants.MAIN_LIGHT_COLOR, Constants.MAIN_LIGHT_COLOR, new Rectangle(282, y += height + index, 717, height), "/buttons/flag.png", panel, new Callback() {	
			@Override
			public void done() {
				manager.getRunning().start();
				manager.instanceRunning();
			}
		});

		JLabel copyright = new JLabel("<html>© COPYRIGHT 2019 - DESENVOLVIDO POR <font color='#ffb957'>MAXTERCREATIONS</font>.</html>");
		copyright.setForeground(Color.WHITE);
		copyright.setBounds(855, 745, 522, 48);
		copyright.setFont(utils.getBebasNeue(22));
		panel.add(copyright);

		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();
	}

}
