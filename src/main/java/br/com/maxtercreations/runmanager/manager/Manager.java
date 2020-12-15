package br.com.maxtercreations.runmanager.manager;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.ClassificationManager;
import br.com.maxtercreations.runmanager.database.RaceManager;
import br.com.maxtercreations.runmanager.database.RunnerManager;
import br.com.maxtercreations.runmanager.displays.leaderboard.LeaderBoard;
import br.com.maxtercreations.runmanager.displays.running.RankingDisplay;
import br.com.maxtercreations.runmanager.displays.running.RunningDisplay;
import br.com.maxtercreations.runmanager.running.Running;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.display.FrameUtils;
import br.com.maxtercreations.runmanager.utilitaries.file.FileManager;

public class Manager {

	private Running running;
	private FileManager fileManager;
	private RaceManager raceManager;
	private Utils utils;

	private RunningDisplay runningDisplay;
	private RankingDisplay rankingDisplay;

	private JFrame mainFrame;

	private RunnerManager runnerManager;
	private ClassificationManager classificationManager;

	public Manager() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth(), height = screenSize.getHeight();

		Constants.TABLET_MODE = !(width > 1280 || height > 880);

		if (Constants.TABLET_MODE)
			Constants.DEVELOPER_MODE = false;

		this.runnerManager = new RunnerManager();
		this.classificationManager = new ClassificationManager();
		this.running = new Running(this);
		this.raceManager = new RaceManager();
		this.fileManager = new FileManager();
		this.utils = new Utils();

		this.mainFrame = new FrameUtils().createFrame("RunManager", 100, 100, 1280, 800, Constants.DEVELOPER_MODE);

		if (Constants.DEVELOPER_MODE)
			Constants.TABLET_MODE = false;

		if (Constants.TABLET_MODE)
			this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public Running getRunning() {
		return running;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public Utils getUtils() {
		return utils;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void instanceRunning() {
		runningDisplay = new RunningDisplay();
		rankingDisplay = null;
	}

	public void instanceRanking() {
		rankingDisplay = new RankingDisplay(0);
		runningDisplay = null;
	}

	public RankingDisplay getRankingDisplay() {
		return rankingDisplay;
	}

	public RunningDisplay getRunningDisplay() {
		return runningDisplay;
	}

	public LeaderBoard getCurrentLeaderboard() {
		return rankingDisplay == null ? runningDisplay.getLeaderBoard() : rankingDisplay.getLeaderBoard();
	}

	public RaceManager getRaceManager() {
		return raceManager;
	}

	public RunnerManager getRunnerManager() {
		return runnerManager;
	}

	public ClassificationManager getClassificationManager() {
		return classificationManager;
	}

}
