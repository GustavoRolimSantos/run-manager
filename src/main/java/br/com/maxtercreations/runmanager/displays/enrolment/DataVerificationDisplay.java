package br.com.maxtercreations.runmanager.displays.enrolment;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.displays.StartDisplay;
import br.com.maxtercreations.runmanager.graphics.animation.AnimatedPanel;
import br.com.maxtercreations.runmanager.graphics.animation.AnimatedPanel.AnimationVector;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.logger.Logger;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.Area;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.ModernButton;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.RoundedFillButton;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.SwipeButton;

public class DataVerificationDisplay {

	private Manager manager;
	private Utils utils;
	private JPanel mainPanel;
	private AnimatedPanel registerPanel;

	public DataVerificationDisplay(RunnerUtils runnerUtils) {
		System.out.println(runnerUtils.getDomainId() + " ====================");
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

		JLabel logo = new JLabel();
		logo.setBounds(407, 40, 466, 241);
		logo.setIcon(new ImageIcon(StartDisplay.class.getResource(Constants.RESOURCES_DIRECTORY + "/application/logo.png")));
		registerPanel.add(logo);
		
		/* Date of birth */

		JLabel agerangeLabel = new JLabel("FAIXA ETÁRIA");
		agerangeLabel.setForeground(Color.WHITE);
		agerangeLabel.setBounds(1160, 376, 88, 41);
		agerangeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		agerangeLabel.setFont(utils.getBebasNeue(18));
		registerPanel.add(agerangeLabel);

		RoundedFillButton ageRange = new RoundedFillButton("0-100", utils.getBebas(24), Color.WHITE, Constants.MAIN_GOLD_COLOR, new Rectangle(1160, 408, 88, 41), 41, 1, registerPanel, null);
		JTextField birthdate = createBox("DATA DE NASCIMENTO", "CONFIRA SE A SUA DATA DE NASCIMENTO ESTÁ CORRETA", "00/00/0000", new Rectangle(835, 380, 430, 78), createMask("##/##/####", "0123456789", "10/04/1969"));
		
		ageRange.getTextLabel().setText("-");
		birthdate.setText(runnerUtils.getBithdate());
		
		birthdate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (birthdate.getText().matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
					utils.closeKeyboard();
					ageRange.getTextLabel().setText("-");
					
					if (registerPanel.getY() != 0)
						registerPanel.animate(5, 200, 500, registerPanel.getY() == 0 ? AnimationVector.TOP : AnimationVector.BOTTOM);
				}
				
			}
		});

		/* Phone */

		JTextField phone = createBox("TELEFONE PARA CONTATO", "CONFIRA SE O SEU NÚMERO DE TELEFONE ESTÁ CORRETO", "(19) 99654-3578", new Rectangle(57, 557, 369, 78), createMask("(##) #####-####", "0123456789", "(19) 99654-3578"));
		phone.setHorizontalAlignment(SwingConstants.CENTER);
		phone.setText(runnerUtils.getPhone());
		
		phone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String cell = "^\\(?(\\d{2})\\)? (\\d{5})[- ]?(\\d{4})$", fix ="^\\(?(\\d{2})\\)? (\\d{5})[- ]?(\\d{3})$";
				
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
					birthdate.requestFocus();
					birthdate.setText("");
					return;
				}
				
				if (phone.getText().matches(cell) || phone.getText().matches(fix)) {
					utils.closeKeyboard();
					
					if (registerPanel.getY() != 0)
						registerPanel.animate(5, 200, 500, registerPanel.getY() == 0 ? AnimationVector.TOP : AnimationVector.BOTTOM);
				}
				
			}
		});
		
		/* Name */

		JTextField name = createBox("NOME COMPLETO", "CONFIRA SE O SEU NOME COMPLETO ESTÁ CORRETO", "EX. GUSTAVO ROLIM DOS SANTOS", new Rectangle(57, 380, 670, 78));
		name.setText(runnerUtils.getName());
		name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
					phone.requestFocus();
					phone.setText("");
				}
			}
		});
		
		JLabel option = new JLabel("VOCÊ ESTÁ PARTICIPANDO DA CATEGORIA");
		option.setForeground(Color.WHITE);
		option.setBounds(64, 650, 352, 41);
		option.setHorizontalAlignment(SwingConstants.CENTER);
		option.setFont(utils.getBebasNeue(27));
		registerPanel.add(option);
		
		SwipeButton raceTypeButton = new SwipeButton("5 KM", "OFICIAL", 88, 690, registerPanel, new Callback() {
			@Override
			public void done() {
				
			}
		});
		
		raceTypeButton.setFirst(runnerUtils.getRaceType().equals("O"));
		
		String concludeText = "<html><div style='text-align: center;'>CONFIRMAR<br> <font size=\"6\">DADOS</font></div></html>";
		
		new ModernButton(concludeText, utils.getBoldFont(15), Constants.MAIN_LIGHT_COLOR, Constants.MAIN_LIGHT_COLOR, new Rectangle(973, 700, 284, 84), 70, "/buttons/medal-small.png", registerPanel, new Callback() {	
			@Override
			public void done() {
				String raceType = raceTypeButton.firstOption() ? "O" : "N";
				
				if (runnerUtils.getAge() != manager.getUtils().getAge(birthdate.getText()) || !runnerUtils.getName().equalsIgnoreCase(name.getText()) || 
						!runnerUtils.getRaceType().equals(raceType) || !runnerUtils.getPhone().equals(phone.getText())) {
					
					String phoneT = phone.getText().contains("(00) 00000-0000") ? "" : phone.getText();
					String birthday = birthdate.getText().contains("00/00/0000") ? "" : birthdate.getText();
					
					manager.getRunnerManager().updateRunner(runnerUtils.getDomainId(), name.getText(), "", phoneT, birthday, raceType, runnerUtils.getBarcode());
					
					Logger.log("The enrolment #" + runnerUtils.getDomainId() + " for the runner " + name.getText() + " has been modified.");
					if (runnerUtils.getBarcode().trim().isEmpty()) {
						new BarcodeRegisterDisplay(runnerUtils);
					} else {
						new EnrolmentDoneDisplay(runnerUtils);
					}
				} else {
					if (runnerUtils.getBarcode().trim().isEmpty()) {
						new BarcodeRegisterDisplay(runnerUtils);
					} else {
						new EnrolmentDoneDisplay(runnerUtils);
					}
				}
			}
		});
		
		String cancelText = "<html><div style='text-align: center;'>NÃO SÃO MEUS<br> <font size=\"6\">DADOS</font></div></html>";

		new ModernButton(cancelText, utils.getBoldFont(15), Constants.MAIN_LIGHT_COLOR, Constants.MAIN_LIGHT_COLOR, new Rectangle(650, 700, 284, 84), 70, "/buttons/cancel.png", registerPanel, new Callback() {	
			@Override
			public void done() {
				new RunnersDisplay();
			}
		});

		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

	private JTextField createBox(String title, String subtitle, String example, Rectangle rect, MaskFormatter mask) {
		int x = (int) rect.getX(), y = (int) rect.getY(), w = (int) rect.getWidth(), h = (int) rect.getHeight();

		JLabel titleLabel = new JLabel(title);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(x + 31, y - 100, w * 2, h);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setFont(utils.getBebasNeue(45));
		registerPanel.add(titleLabel);

		JLabel subTitleLabel = new JLabel(subtitle);
		subTitleLabel.setForeground(Color.WHITE);
		subTitleLabel.setBounds(x + 31, y - 65, w * 2, h);
		subTitleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		subTitleLabel.setFont(utils.getBebasNeue(22));
		registerPanel.add(subTitleLabel);

		JTextField textField;
		JFormattedTextField formatedText;

		/* Normal */
		
		if (mask == null) {
			textField = new JTextField(example);

			applyProperties(textField, x, y, w, h, example);
			textField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					utils.openKeyboard();
					if (registerPanel.getY() == 0)
						registerPanel.animate(5, 200, 500, AnimationVector.TOP);
					if (textField.getText().equalsIgnoreCase(example)) {
						textField.setText("");
					}
				}
			});
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (!textField.getForeground().equals(Color.WHITE))
						textField.setForeground(Color.WHITE);

					if ((e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) && !textField.getText().equals("")) {
						textField.setSelectionEnd(0);
						textField.setSelectionStart(textField.getText().length());
					}
				}
			});
			textField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent event) {
					if (((JTextField) event.getSource()).getText().length() == 0)
						((JTextField) event.getSource()).setText(example);
				}
			});
			registerPanel.add(textField);
			new Area(rect).add(registerPanel);
			return textField;
		}
		
		/* Formated */

		formatedText = new JFormattedTextField(mask);

		applyProperties(formatedText, x, y, w, h, example);
		formatedText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (registerPanel.getY() == 0)
					registerPanel.animate(5, 200, 500, AnimationVector.TOP);
				if (formatedText.getText().equalsIgnoreCase(example)) {
					formatedText.setText("");
				}
			}
		});
		formatedText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!formatedText.getForeground().equals(Color.WHITE))
					formatedText.setForeground(Color.WHITE);

				if ((e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) && !formatedText.getText().equals("")) {
					formatedText.setSelectionEnd(0);
					formatedText.setSelectionStart(formatedText.getText().length());
				}
			}
		});
		formatedText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				if (((JTextField) event.getSource()).getText().length() == 0)
					((JTextField) event.getSource()).setText(example);
			}
		});
		registerPanel.add(formatedText);
		new Area(rect).add(registerPanel);
		return formatedText;
	}

	private JTextField createBox(String title, String subtitle, String example, Rectangle rect) {
		return createBox(title, subtitle, example, rect, null);
	}

	private void applyProperties(JComponent component, int x, int y, int w, int h, String example) {
		JTextField textField = null;
		JFormattedTextField formatedText = null;

		if (component instanceof JTextField) {
			textField = (JTextField) component;
			textField.setHorizontalAlignment(SwingConstants.LEFT);
		} else if (component instanceof JFormattedTextField) {
			formatedText = (JFormattedTextField) component;
			formatedText.setHorizontalAlignment(SwingConstants.LEFT);
		}

		component.setFocusTraversalKeysEnabled(false);

		component.setForeground(Constants.MAIN_GRAY_COLOR);
		component.setOpaque(false);
		component.setBounds(x + 31, y - 2, w - 62, h);
		component.setFont(utils.getBebasNeue(43));
		component.setBorder(null);
	}

	private MaskFormatter createMask(String format, String valid, String ignored) {
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter(format);
			mask.setValidCharacters(valid);
			mask.setPlaceholder(ignored);
		} catch (Exception e) {
		}
		return mask;
	}

}
