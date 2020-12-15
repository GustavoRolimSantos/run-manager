package br.com.maxtercreations.runmanager.loader;


import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.displays.StartDisplay;
import br.com.maxtercreations.runmanager.manager.Manager;

public class Loader {

	private static Manager manager;
	

	public static void load() {
		manager = new Manager();
				
		if (!manager.getRaceManager().hasStarted()) {
			new StartDisplay();
		} else {
			System.out.println(Constants.dateAndTimeFormat.format(manager.getRaceManager().getFormattedStartedTime()) + "aaaaa");
			manager.getRunning().setStartTime(manager.getRaceManager().getFormattedStartedTime());
			manager.getRunning().start();
			manager.instanceRunning();
		}
		manager.getMainFrame().setLocationRelativeTo(null);
		manager.getMainFrame().setVisible(true);	
	}
	
	public static Manager getManager() {
		return manager;
	}
	
}
