package br.com.maxtercreations.runmanager.displays.enrolment;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.displays.StartDisplay;
import br.com.maxtercreations.runmanager.graphics.animation.AnimatedPanel;
import br.com.maxtercreations.runmanager.graphics.animation.AnimatedPanel.AnimationVector;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.autocompleter.AutoTextField;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.Area;

public class RunnersDisplay {

	private Manager manager;
	private Utils utils;
	private JPanel mainPanel;
	private AnimatedPanel registerPanel;
	
	private int clicked = 0;
	private long time;

	public RunnersDisplay() {
		manager = Loader.getManager();
		utils = manager.getUtils();

		JFrame frame = manager.getMainFrame();
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(Constants.MAIN_DARK_COLOR);
		
		registerPanel = new AnimatedPanel();
		registerPanel.setLayout(null);
		registerPanel.setBackground(Constants.MAIN_DARK_COLOR);
		
		mainPanel.add(registerPanel);
		
		time  = System.currentTimeMillis();

		JLabel logo = new JLabel();
		logo.setBounds(407, 40, 466, 241);
		logo.setIcon(new ImageIcon(StartDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/application/logo.png")));
		logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (clicked >= 3) {
					System.out.println(System.currentTimeMillis() - time);
					if (System.currentTimeMillis() - time <= 5000) {
						clicked = 0;
						time = System.currentTimeMillis();
						new StartDisplay();
					} else {
						clicked = 0;
						time = System.currentTimeMillis();
					}
				}
				clicked++;
			}
		});
		registerPanel.add(logo);

		JLabel question1 = new JLabel("JÁ POSSUI CADASTRO?");
		question1.setForeground(Color.WHITE);
		question1.setBounds(259, 330, 500, 100);
		question1.setFont(utils.getBebasNeue(75));
		registerPanel.add(question1);
		
		ArrayList<String> names = new ArrayList<>();
		names.add("BUSQUE SUA MATRÍCULA DIGITANDO O SEU NOME...");
		
		@SuppressWarnings("unchecked")
		ArrayList<String> domains = new ArrayList<>(((JSONObject)manager.getRunnerManager().getRunners().get(0)).keySet());
		
		for (int i = 0; i < domains.size(); i++) {
			JSONObject runner = manager.getRunnerManager().getRunnerByDomain(domains.get(i));
			names.add(runner.get("name").toString());
		}
		
		AutoTextField find = new AutoTextField(names);
		find.setFocusTraversalKeysEnabled(false);
		find.setHorizontalAlignment(SwingConstants.LEFT);
		find.setForeground(Constants.MAIN_GRAY_COLOR);
		find.setOpaque(false);
		find.setBounds(270, 420, 741, 100);
		find.setFont(utils.getBebasNeue(50));
		find.setBorder(null);
		find.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				registerPanel.animate(5, 200, 500, registerPanel.getY() == 0 ? AnimationVector.TOP : AnimationVector.BOTTOM);
				if (find.getText().equalsIgnoreCase("BUSQUE SUA MATRÍCULA DIGITANDO O SEU NOME...")) {
					find.setText("");
				}
			}
		});
		find.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!find.getForeground().equals(Color.WHITE))
					find.setForeground(Color.WHITE);
				
				if ((e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) && !find.getText().equals("")) {
					find.setSelectionEnd(0);
					find.setSelectionStart(find.getText().length());
					registerPanel.animate(5, 200, 500, registerPanel.getY() == 0 ? AnimationVector.TOP : AnimationVector.BOTTOM);
					
					JSONObject runner = manager.getRunnerManager().getRunner(find.getText());
					RunnerUtils runnerUtils = new RunnerUtils(manager.getRunnerManager().getDomainId(runner));
					
					new DataVerificationDisplay(runnerUtils);
				}
			}
		});

		registerPanel.add(find);
		
		Callback nameCallback = new Callback() {			
			@Override
			public void done() {
				find.requestFocus();
			}
		};
		
		Area titleArea = new Area(new Rectangle(239, 425, 802, 92), nameCallback);
		titleArea.add(registerPanel);
		
		/* Register new */
		
		JLabel question2 = new JLabel("NÃO ENCONTROU SUA MATRÍCULA OU NÃO POSSUI CADASTRO?");
		question2.setForeground(Color.WHITE);
		question2.setBounds(250, 530, 778, 100);
		question2.setFont(utils.getBebasNeue(43));
		registerPanel.add(question2);
		
		JButton create = new JButton("CADASTRAR MATRÍCULA");
		create.setHorizontalAlignment(SwingConstants.CENTER);
		create.setForeground(Color.WHITE);
		create.setBounds(239, 608, 422, 93);
		create.setFont(utils.getBebasNeue(50));
		create.setBorder(null);
		create.setBorderPainted(false);
		create.setFocusPainted(false);
		create.setContentAreaFilled(false);
		create.setOpaque(false);
		create.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new RegisterDisplay();
			}
		});
		registerPanel.add(create);
		
		Callback createCallback = new Callback() {			
			@Override
			public void done() {
				
			}
		};
		
		Area createArea = new Area(new Rectangle(239, 610, 422, 92), createCallback);
		createArea.add(registerPanel);

		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

}
