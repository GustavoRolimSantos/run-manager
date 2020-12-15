package br.com.maxtercreations.runmanager.running;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.displays.running.RankingDisplay;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.running.export.Export;
import br.com.maxtercreations.runmanager.scanner.Scanner;
import br.com.maxtercreations.runmanager.stopwatch.StopWatch;

public class Running {

	private Manager manager;
	private long startTime;

	private Timer timer;
	private Scanner scanner;

	public Running(Manager manager) {
		this.manager = manager;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void start() {
		System.out.println("Starting the running...");
		
		System.out.println(Constants.dateAndTimeFormat.format(startTime) + " vindo");
		
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
		
		manager.getRaceManager().updateStartedTime(startTime);
		
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StopWatch stopWatch = null;
				if (manager.getRunningDisplay() != null || manager.getRankingDisplay() != null)	
					stopWatch = manager.getRunningDisplay() != null ? manager.getRunningDisplay().getStopWatch() : manager.getRankingDisplay().getStopWatch();
					
				if (stopWatch != null)
					stopWatch.update(startTime);
			}
		});
		timer.start();
	}

	public void stop() {
		manager.getRaceManager().stop();
		timer.stop();
		timer = null;
		new Export().export("gustavorolimdossantos@gmail.com");

		new RankingDisplay(0);
		
		manager.getClassificationManager().clearClassifications();
		startTime = 0;
	}

	public long getStartTime() {
		return startTime;
	}
	
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public Scanner getScanner() {
		return scanner;
	}

	public boolean isStopped() {
		return timer == null || !timer.isRunning();
	}
	
}
