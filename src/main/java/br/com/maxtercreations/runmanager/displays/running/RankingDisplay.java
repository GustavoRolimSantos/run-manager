package br.com.maxtercreations.runmanager.displays.running;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.database.ClassificationUtils;
import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.displays.leaderboard.LeaderBoard;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.running.Running;
import br.com.maxtercreations.runmanager.scanner.Scanner;
import br.com.maxtercreations.runmanager.stopwatch.StopWatch;
import br.com.maxtercreations.runmanager.utilitaries.SortMapByValue;
import br.com.maxtercreations.runmanager.utilitaries.Utils;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;
import br.com.maxtercreations.runmanager.utilitaries.display.custom.buttons.ModernButton;

public class RankingDisplay {

	private Manager manager;
	private Utils utils;
	private JPanel panel;

	private LeaderBoard leaderboard;
	private StopWatch stopWatch;
	
	private static final int TOTAL_RUNNERS_IN_PAGE = 30;

	public RankingDisplay(int page) {
		manager = Loader.getManager();
		utils = manager.getUtils();

		JFrame frame = manager.getMainFrame();
		panel = new JPanel();

		panel.setLayout(null);
		panel.setBackground(Constants.MAIN_LIGHT_COLOR);

		JLabel arrival = new JLabel("CHEGADA");
		arrival.setForeground(Color.WHITE);
		arrival.setBounds(556, 12, 169, 63);
		arrival.setFont(utils.getBebasNeue(60));
		arrival.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(arrival);

		JLabel rankingType = new JLabel("RANKING GERAL");
		rankingType.setForeground(Color.WHITE);
		rankingType.setBounds(556, 73, 168, 38);
		rankingType.setFont(utils.getBebasNeue(35));
		rankingType.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(rankingType);

		String viewText = "<html><div style='text-align: center;'>VISUALIZAR PRINCIPAIS<br> <p style='margin-top:4'><font size=\"6\">CLASSIFICAÇÕES</font></p></div></html>";

		if (!manager.getRunning().isStopped()) {
			new ModernButton(viewText, utils.getBoldFont(17), Constants.MAIN_DARK_COLOR, Constants.MAIN_DARK_COLOR, new Rectangle(117, 675, 284, 84), 80, "/buttons/medal-small.png", panel, new Callback() {
				@Override
				public void done() {
					manager.instanceRunning();
				}
			}).getButton().getTextLabel().setIconTextGap(15);
			
			String nextPage = "<html><div style='text-align: center;'>PRÓXIMA<br> <p style='margin-top:4'><font size=\"6\">PÁGINA</font></p></div></html>";
			
			new ModernButton(nextPage, utils.getBoldFont(17), Constants.MAIN_DARK_COLOR, Constants.MAIN_DARK_COLOR, new Rectangle(879, 675, 284, 84), 80, "/buttons/medal-small.png", panel, new Callback() {
				@Override
				public void done() {
					if (hasNextPage(page)) {
						new RankingDisplay(page + 1);
					} else if (page > 0) {
						new RankingDisplay(page - 1);
					}
				}
			}).getButton().getTextLabel().setIconTextGap(15);
		}

		/* Leaderboard */

		leaderboard = new LeaderBoard(doneMap(manager.getClassificationManager().transformToRunnerUtils(page * TOTAL_RUNNERS_IN_PAGE)));
		leaderboard.setStart(page * TOTAL_RUNNERS_IN_PAGE);
		leaderboard.setLimit(10);
		
		if (page == 0)
			leaderboard.setMaxLimit(30);
		
		leaderboard.build(60, 140, panel);

		/* Timer */

		stopWatch = new StopWatch(new Rectangle(545, 654, 209, 114), utils.getBebasNeue(45), 230, panel);
		stopWatch.setBackground(Constants.MAIN_DARK_COLOR);

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
						manager.getClassificationManager().updateClassification(domainId, manager.getClassificationManager().getClassifications().size(), String.valueOf(System.currentTimeMillis()));

						System.out.println("The runner " + runner.get("name") + " has just finished the running.");
		
						manager.getCurrentLeaderboard().update(RankingDisplay.doneMap(manager.getClassificationManager().transformToRunnerUtils(page * TOTAL_RUNNERS_IN_PAGE)), new RunnerUtils(domainId), true);
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
	
	public boolean hasNextPage(int page) {
		System.out.println(manager.getClassificationManager().getClassifications().size());
		return ((JSONObject)manager.getClassificationManager().getClassifications().get(0)).size()  > ((page + 1) * TOTAL_RUNNERS_IN_PAGE);
	}
	
	public static Map<RunnerUtils, Long> doneMap(ArrayList<RunnerUtils> runners) {
		Map<RunnerUtils, Long> finished = new HashMap<>();

		for (RunnerUtils runnerUtils : runners) {
			ClassificationUtils utils = new ClassificationUtils(runnerUtils.getDomainId());
			finished.put(runnerUtils, utils.getMilliSpentTime());
		}
		
		Map<RunnerUtils, Long> copy = new SortMapByValue().sortByComparator(finished, true);
		
		return copy;
	}

}
