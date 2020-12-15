package br.com.maxtercreations.runmanager.utilitaries;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.maxtercreations.runmanager.constants.Constants;

public class Utils {

	public Utils() {
		this.registerFont("BEBAS.ttf", 1);
		this.registerFont("BebasNeueRegular.otf", 1);
		this.registerFont("theboldfont.ttf", 1);
	}

	public String zeroFill(int i, int quantity) {
		return String.format("%0" + quantity + "d", i);
	}

	public String zeroFillLast(int i, int quantity) {
		String id = i + "";
		int format = 10;

		for (int j = 0; j < quantity; j++) {
			format = Integer.valueOf(format + "0");

			if (i < format)
				id = "0" + id;
		}
		return id;
	}

	public String convertTime(long milliseconds) {
		long diffSeconds = milliseconds / 1000 % 60;
		long diffMinutes = milliseconds / (60 * 1000) % 60;
		long diffHours = milliseconds / (60 * 60 * 1000) % 24;
	
		return zeroFill((int) diffHours, 2) + ":" + zeroFill((int) diffMinutes, 2) + ":" + zeroFill((int) diffSeconds, 2);
	}

	public String compareTime(long current, long end) {
		long ms = end - current;
		int s = (int) (ms / 1000) % 60;
		int m = (int) (ms / (1000 * 60)) % 60;
		int h = (int) (ms / (1000 * 60 * 60) % 24);
		int d = (int) (ms / (1000 * 60 * 60 * 24));

		return (d > 0 ? zeroFillLast(d, 1) : "0") + ":" + (h > 0 ? zeroFillLast(h, 1) : "0") + ":" + (m > 0 ? zeroFillLast(m, 1) : "0") + ":" + (s > 0 ? zeroFillLast(s, 1) : "0");
	}

	public void registerFont(String fontName, float size) {
		try {
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, Utils.class.getResourceAsStream(Constants.RESOURCES_DIRECTORY + "/fonts/" + fontName));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public Font getBebas(int size) {
		return new Font("Bebas", 0, size);
	}

	public Font getBebasNeue(int size) {
		return new Font("Bebas Neue Regular", 0, size);
	}

	public Font getBoldFont(int size) {
		return new Font("The Bold Font", 0, size);
	}

	@SuppressWarnings("deprecation")
	public int getAge(String birthDate) {
		try {
			Date now = new Date(System.currentTimeMillis()), birthday = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
			int age = now.getYear() - birthday.getYear();

			if (now.getMonth() <= birthday.getMonth()) {
				if (now.getMonth() == birthday.getMonth()) {
					if (now.getDay() < birthday.getDay()) {
						age--;
					}
				} else {
					age--;
				}
			}

			return age;
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return 0;
	}

	/* Keyboard */

	public void openKeyboard() {
		if (Constants.TABLET_MODE) {

		}
		try {
			Runtime.getRuntime().exec("cmd /c \"C:\\Program Files\\Common Files\\microsoft " + "shared\\ink\\TabTip.exe\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeKeyboard() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM WindowsInternal.ComposableShell.Experiences.TextInput.InputApp.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isInteger(String args) {
		try {
			Integer.valueOf(args);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

}
