package br.com.maxtercreations.runmanager.logger;

import java.text.SimpleDateFormat;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.utilitaries.file.FileManager;

public class Logger {

	/* This class has the function to register and notify the console about the system details. */
	
	public static void debug(Object object) {
		System.out.println("[DEBUG] " + object);
	}
	
	public static void log(Object object) {
		System.out.println("[LOG] " + object);
	}
	
	public static void error(Object object, boolean register) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String string = "[" + dateFormat.format(System.currentTimeMillis()) + "] " + object.toString();
		
		if (register) {
			System.out.println(string);
		} else {
			System.out.println("[LOG] " + object.toString());
			return;
		}
		
		if (Loader.getManager() == null) {
			new FileManager().write(Constants.LOG_FILE, string);
		} else {
			Loader.getManager().getFileManager().write(Constants.LOG_FILE, string);
		}
	}
	
}
