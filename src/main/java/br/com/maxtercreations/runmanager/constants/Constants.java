package br.com.maxtercreations.runmanager.constants;

import java.awt.Color;
import java.text.SimpleDateFormat;

public class Constants {

	public static final String MAIN_DIRECTORY = System.getenv("APPDATA") + "/RunManager/";
	
	public static final String BACKUP_FOLDER = MAIN_DIRECTORY + "/backup/";
	
	public static final String DATABASE_FOLDER = MAIN_DIRECTORY + "/database/";
	
	/* The log file is where all the logs is going to be saved, mainly errors. */
	public static final String LOG_FILE = MAIN_DIRECTORY + "/logger/log.txt";
	
	/* The tablet mode is a boolean to communicate the application that the device is a tablet or not. */
	public static boolean TABLET_MODE = true;
	
	public static boolean DEVELOPER_MODE = true;
	
	public static final String RESOURCES_DIRECTORY = "/br/com/maxtercreations/runmanager/resources";
	
	public static final Color MAIN_DARK_COLOR = new Color(0, 70, 102);
	public static final Color MAIN_LIGHT_COLOR = new Color(0, 89, 130);
	public static final Color MAIN_GRAY_COLOR = new Color(117, 117, 117);
	public static final Color MAIN_GOLD_COLOR = new Color(255, 185, 87);
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final SimpleDateFormat dateAndTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

}
