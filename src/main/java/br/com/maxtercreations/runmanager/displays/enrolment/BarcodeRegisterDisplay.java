package br.com.maxtercreations.runmanager.displays.enrolment;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.scanner.Scanner;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedButton;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedFillButton;

public class BarcodeRegisterDisplay {

	private Manager manager;
	private Utils utils;
	private JPanel panel;
	
	private Scanner scanner;

	public BarcodeRegisterDisplay(RunnerUtils runnerUtils) {
		manager = Loader.getManager();
		utils = manager.getUtils();

		JFrame frame = manager.getMainFrame();
		panel = new JPanel();

		panel.setLayout(null);
		panel.setBackground(Constants.MAIN_DARK_COLOR);

		/* Back */
		
		RoundedFillButton back = new RoundedFillButton("<", utils.getBebasNeue(50), Constants.MAIN_GOLD_COLOR, Constants.MAIN_LIGHT_COLOR, new Rectangle(1216, 13, 49, 49), 49, 1, panel, new Callback() {	
			@Override
			public void done() {
				new DataVerificationDisplay(runnerUtils);
			}
		});
		back.setTextY(11);
		
		/* Barcode Icon  */
		RoundedFillButton barcodeB = new RoundedFillButton("", null, null, Constants.MAIN_DARK_COLOR, new Rectangle(213, 196, 138, 138), 138, 1, panel, null);
		barcodeB.getTextLabel().setIcon(new ImageIcon(BarcodeRegisterDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/barcode-icon.png")));

		/* Texts */
		
		JLabel scanText1 = new JLabel("USE O SCANNER PARA REGISTRAR O");
		scanText1.setForeground(Color.WHITE);
		scanText1.setBounds(425, 198, 547, 56);
		scanText1.setFont(utils.getBebasNeue(53));
		panel.add(scanText1);
		
		JLabel scanText2 = new JLabel("CÓDIGO DE BARRAS");
		scanText2.setForeground(Color.WHITE);
		scanText2.setBounds(462, 251, 486, 89);
		scanText2.setFont(utils.getBebasNeue(86));
		panel.add(scanText2);
		
		/* Barcode icon background */
		
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(199, 185, 882, 160), 160, 1, panel, null);


		/* Name */
		
		JLabel nameText = new JLabel(runnerUtils.getName());
		nameText.setForeground(Color.WHITE);
		nameText.setBounds(470, 452, 466, 48);
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		nameText.setFont(utils.getBebasNeue(47));
		panel.add(nameText);
		
		RoundedFillButton nameIcon = new RoundedFillButton("", null, null, Color.WHITE, new Rectangle(317, 418, 116, 116), 116, 1, panel, null);
		nameIcon.getTextLabel().setIcon(new ImageIcon(BarcodeRegisterDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/user-icon.png")));
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
		ageIcon.getTextLabel().setIcon(new ImageIcon(BarcodeRegisterDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/calendar-icon.png")));
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(404, 566, 146, 45), 38, 1, panel, null);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(404, 554, 69, 69), 69, 1, panel, null);
		
		/* Age */
		
		JLabel genderText = new JLabel(runnerUtils.getRaceType().equals("M") ? "HOMEM" : "MULHER");
		genderText.setForeground(Color.WHITE);
		genderText.setBounds(650, 569, 63, 35);
		genderText.setFont(utils.getBebasNeue(26));
		panel.add(genderText);
		
		RoundedFillButton genderIcon = new RoundedFillButton("", null, null, Color.WHITE, new Rectangle(586, 560, 57, 57), 57, 1, panel, null);
		genderIcon.getTextLabel().setIcon(new ImageIcon(BarcodeRegisterDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/gender-icon.png")));
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(580, 566, 146, 45), 38, 1, panel, null);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(580, 554, 69, 69), 69, 1, panel, null);
		
		/* Phone */
		
		JLabel phoneText = new JLabel(runnerUtils.getPhone());
		phoneText.setForeground(Color.WHITE);
		phoneText.setBounds(826, 569, 150, 35);
		phoneText.setFont(utils.getBebasNeue(26));
		panel.add(phoneText);
		
		RoundedFillButton phoneIcon = new RoundedFillButton("", null, null, Color.WHITE, new Rectangle(762, 560, 57, 57), 57, 1, panel, null);
		phoneIcon.getTextLabel().setIcon(new ImageIcon(BarcodeRegisterDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/several/phone-icon.png")));
		phoneIcon.getTextLabel().setVerticalAlignment(SwingConstants.CENTER);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(756, 566, 220, 45), 38, 1, panel, null);
		new RoundedFillButton("", null, null, Constants.MAIN_LIGHT_COLOR, new Rectangle(756, 554, 69, 69), 69, 1, panel, null);

		/* Id */
		
		JLabel barcodeNumber = new JLabel("SEM CORRESPONDÊNCIA");
		barcodeNumber.setForeground(Color.WHITE);
		barcodeNumber.setBounds(713, 728, 224, 35);
		barcodeNumber.setFont(utils.getBebasNeue(31));
		panel.add(barcodeNumber);
		
		RoundedFillButton id = new RoundedFillButton("NÚMERO DE IDENTIFICAÇÃO DO CÓDIGO DE BARRAS:", utils.getBebasNeue(23), Color.WHITE, Constants.MAIN_LIGHT_COLOR, new Rectangle(327, 720, 627, 52), 52, 1, panel, null);
		id.getTextLabel().setHorizontalAlignment(SwingConstants.LEFT);
		id.setTextX(357);
		
		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();
		
		scanner = new Scanner(panel, new Callback() {	
			@Override
			public void done() {
				String code = scanner.getCode().replaceAll("\\D+","");
				
				barcodeNumber.setText(code);
				
				manager.getRunnerManager().updateRunnerId(runnerUtils.getRunner(), code);
				
				ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
				es.schedule(new Runnable(){
				    @Override
				    public void run() {
						new EnrolmentDoneDisplay(runnerUtils);
				    }
				}, 2, TimeUnit.SECONDS);
			}
		});
	}

}
