package br.com.maxtercreations.runmanager.displays.enrolment;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedButton;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedFillButton;

public class EnrolmentDoneDisplay {

	private Manager manager;
	private Utils utils;
	private JPanel panel;
	
	private Timer timer;

	public EnrolmentDoneDisplay(RunnerUtils runnerUtils) {
		manager = Loader.getManager();
		utils = manager.getUtils();

		JFrame frame = manager.getMainFrame();
		panel = new JPanel();

		panel.setLayout(null);
		panel.setBackground(Constants.MAIN_DARK_COLOR);

		
		/* Ok Icon  */
		RoundedFillButton okIcon = new RoundedFillButton("", null, null, Constants.MAIN_DARK_COLOR, new Rectangle(157, 205, 196, 196), 196, 1, panel, null);
		okIcon.getTextLabel().setIcon(new ImageIcon(EnrolmentDoneDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/ok-icon.png")));

		/* Texts */
		
		JLabel scanText1 = new JLabel("CADASTRO EFETUADO COM SUCESSO!");
		scanText1.setForeground(Color.WHITE);
		scanText1.setBounds(382, 267, 702, 67);
		scanText1.setFont(utils.getBebasNeue(65));
		panel.add(scanText1);
		
		/* Ok icon background */
		
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(187, 245, 936, 115), 115, 1, panel, null);

		/* Name */
		
		JLabel nameText = new JLabel(runnerUtils.getName());
		nameText.setForeground(Color.WHITE);
		nameText.setBounds(470, 452, 466, 48);
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		nameText.setFont(utils.getBebasNeue(47));
		panel.add(nameText);
		
		RoundedFillButton nameIcon = new RoundedFillButton("", null, null, Color.WHITE, new Rectangle(317, 418, 116, 116), 116, 1, panel, null);
		nameIcon.getTextLabel().setIcon(new ImageIcon(EnrolmentDoneDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/user-icon.png")));
		new RoundedFillButton("", null, null, Constants.MAIN_DARK_COLOR, new Rectangle(310, 411, 130, 130), 130, 1, panel, null);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(316, 434, 669, 83), 83, 1, panel, null);

		panel.add(new RoundedButton(null, null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(300, 400, 150, 150), 150, 5, null));
		
		/* Age */
		
		JLabel ageText = new JLabel(String.valueOf(runnerUtils.getAge()));
		ageText.setForeground(Color.WHITE);
		ageText.setBounds(471, 569, 63, 35);
		ageText.setFont(utils.getBebasNeue(33));
		panel.add(ageText);
		
		JLabel ageTextSub = new JLabel("ANOS");
		ageTextSub.setForeground(Color.WHITE);
		ageTextSub.setBounds(500, 570, 63, 35);
		ageTextSub.setFont(utils.getBebasNeue(20));
		panel.add(ageTextSub);
		
		RoundedFillButton ageIcon = new RoundedFillButton("", null, null, Color.WHITE, new Rectangle(410, 560, 57, 57), 57, 1, panel, null);
		ageIcon.getTextLabel().setIcon(new ImageIcon(EnrolmentDoneDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/calendar-icon.png")));
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(404, 566, 146, 45), 38, 1, panel, null);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(404, 554, 69, 69), 69, 1, panel, null);
		
		/* Age */
		
		JLabel genderText = new JLabel(runnerUtils.getRaceType().equals("M") ? "HOMEM" : "MULHER");
		genderText.setForeground(Color.WHITE);
		genderText.setBounds(650, 569, 63, 35);
		genderText.setFont(utils.getBebasNeue(26));
		panel.add(genderText);
		
		RoundedFillButton genderIcon = new RoundedFillButton("", null, null, Color.WHITE, new Rectangle(586, 560, 57, 57), 57, 1, panel, null);
		genderIcon.getTextLabel().setIcon(new ImageIcon(EnrolmentDoneDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/gender-icon.png")));
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(580, 566, 146, 45), 38, 1, panel, null);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(580, 554, 69, 69), 69, 1, panel, null);
		
		/* Phone */
		
		JLabel phoneText = new JLabel(runnerUtils.getPhone());
		phoneText.setForeground(Color.WHITE);
		phoneText.setBounds(826, 569, 150, 35);
		phoneText.setFont(utils.getBebasNeue(26));
		panel.add(phoneText);
		
		RoundedFillButton phoneIcon = new RoundedFillButton("", null, null, Color.WHITE, new Rectangle(762, 560, 57, 57), 57, 1, panel, null);
		phoneIcon.getTextLabel().setIcon(new ImageIcon(EnrolmentDoneDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/phone-icon.png")));
		phoneIcon.getTextLabel().setVerticalAlignment(SwingConstants.CENTER);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(756, 566, 220, 45), 38, 1, panel, null);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(756, 554, 69, 69), 69, 1, panel, null);

		/* Back */
		
		JLabel backText = new JLabel("RETORNANDO A PÁGINA DE CADASTROS EM");
		backText.setForeground(Color.WHITE);
		backText.setBounds(709, 696, 466, 48);
		backText.setHorizontalAlignment(SwingConstants.CENTER);
		backText.setFont(utils.getBebasNeue(22));
		panel.add(backText);
		
		JLabel timeText = new JLabel("10 SEGUNDOS");
		timeText.setForeground(Constants.MAIN_GOLD_COLOR);
		timeText.setBounds(915, 695, 466, 48);
		timeText.setHorizontalAlignment(SwingConstants.CENTER);
		timeText.setFont(utils.getBebasNeue(32));
		panel.add(timeText);

		timer = new Timer(1000, new ActionListener() {
			int time = 10;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (time > 1) {
					time--;
					timeText.setText(time + " SEGUNDO" + (time > 1 ? "S" : ""));
				} else {
					timer.stop();
					new RunnersDisplay();
				}
			}
		});
		timer.start();
		
		Callback callback = new Callback() {		
			@Override
			public void done() {
				timer.stop();
				new RunnersDisplay();
			}
		};
		
		RoundedFillButton backIcon = new RoundedFillButton("", null, null, Color.WHITE, new Rectangle(717, 687, 70, 70), 70, 1, panel, callback);
		backIcon.getTextLabel().setIcon(new ImageIcon(EnrolmentDoneDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/back-icon.png")));
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(710, 680, 85, 85), 85, 1, panel, callback);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(711, 691, 530, 60), 60, 1, panel, callback);
		
		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();
		frame.setAlwaysOnTop(true);
	}

}
