package br.com.maxtercreations.runmanager.displays.running;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.displays.leaderboard.LeaderBoard;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.running.Running;
import br.com.maxtercreations.runmanager.scanner.Scanner;
import br.com.maxtercreations.runmanager.stopwatch.StopWatch;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.ModernButton;

public class RunningDisplay {

	private Manager manager;
	private Utils utils;
	private JPanel panel;
	
	private LeaderBoard leaderboard;
	private StopWatch stopWatch;

	public RunningDisplay() {
		manager = Loader.getManager();
		utils = manager.getUtils();

		JFrame frame = manager.getMainFrame();
		panel = new JPanel();

		panel.setLayout(null);
		panel.setBackground(Constants.MAIN_DARK_COLOR);	
		
		JLabel arrival = new JLabel("CHEGADA");
		arrival.setForeground(Color.WHITE);
		arrival.setBounds(111, 12, 169, 63);
		arrival.setFont(utils.getBebasNeue(60));
		arrival.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(arrival);
		
		JLabel rankingType = new JLabel("RANKING GERAL");
		rankingType.setForeground(Color.WHITE);
		rankingType.setBounds(114, 73, 168, 38);
		rankingType.setFont(utils.getBebasNeue(35));
		rankingType.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(rankingType);
		
		String viewText = "<html><div style='text-align: center;'>VISUALIZAR TODAS AS<br> <p style='margin-top:4'><font size=\"6\">CLASSIFICAÇÕES</font></p></div></html>";
		
		if (!manager.getRunning().isStopped()) {
			new ModernButton(viewText, utils.getBoldFont(17), Constants.MAIN_DARK_COLOR, Constants.MAIN_DARK_COLOR, new Rectangle(54, 682, 284, 84), 80, "/buttons/medal-small.png", panel, new Callback() {	
				@Override
				public void done() {
					manager.instanceRanking();
				}
			}).getButton().getTextLabel().setIconTextGap(15);
			
			new ModernButton("FINALIZAR", utils.getBoldFont(35), Constants.MAIN_LIGHT_COLOR, Constants.MAIN_LIGHT_COLOR, new Rectangle(698, 682, 302, 73), 70, "/buttons/report.png", panel, new Callback() {	
				@Override
				public void done() {
					manager.getRunning().stop();
				}
			});
		}
		
		leaderboard = new LeaderBoard(RankingDisplay.doneMap(manager.getClassificationManager().transformToRunnerUtils()));
		
		leaderboard.setMaxLimit(10);
		leaderboard.build(17, 118, panel);
		
		/* Timer */
		
		stopWatch = new StopWatch(new Rectangle(570, 256, 528, 288), utils.getBebasNeue(134), panel);
		
		JLabel toolBar = new JLabel();
		toolBar.setBackground(Constants.MAIN_LIGHT_COLOR);
		toolBar.setBounds(0, 0, 389, 800);
		toolBar.setOpaque(true);
		panel.add(toolBar);

		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();
		
		Running running = manager.getRunning();
		
		running.setScanner(new Scanner(panel, new Callback() {		
			@Override
			public void done() {
				String code = running.getScanner().getCode().replaceAll("\\D+", "");
				JSONObject runner = manager.getRunnerManager().getRunnerByBarcode(code);

				if (runner != null) {
					String domainId = runner.get("domain_id").toString();
					if (!manager.getClassificationManager().hasFinished(domainId)) {
						manager.getClassificationManager().updateClassification(domainId, (((JSONObject)manager.getClassificationManager().getClassifications().get(0)).size()), String.valueOf(System.currentTimeMillis()));

						System.out.println("The runner " + runner.get("name") + " has just finished the running.");
		
						manager.getCurrentLeaderboard().update(RankingDisplay.doneMap(manager.getClassificationManager().transformToRunnerUtils()), new RunnerUtils(domainId), true);
					}
				}	
			}
		}));
	}
	
	public StopWatch getStopWatch() {
		return stopWatch;
	}

	public LeaderBoard getLeaderBoard() {
		return leaderboard;
	}
	
}
