package br.com.maxtercreations.runmanager.displays.leaderboard;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;

import br.com.maxtercreations.runmanager.database.RunnerUtils;
import br.com.maxtercreations.runmanager.displays.leaderboard.Placing.AnimationVector;
import br.com.maxtercreations.runmanager.loader.Loader;
import br.com.maxtercreations.runmanager.manager.Manager;
import br.com.maxtercreations.runmanager.utilitaries.Utils;

public class LeaderBoard {

	private Map<RunnerUtils, Long> runners;
	private Manager manager;
	private Utils utils;

	private JPanel panel;

	private int x, space, startX, startY, maxLimit, limit;
	private Color color1 = new Color(128, 195, 66), color2 = new Color(255, 185, 87), color3 = new Color(197, 62, 62), color4 = new Color(41, 41, 41);

	private Font placeFont, nameFont, timeFont;

	private ArrayList<Placing> placings = new ArrayList<>();
	
	private int place = 0, pY;

	private int start = 0;
	
	public LeaderBoard(Map<RunnerUtils, Long> map) {
		this.manager = Loader.getManager();
		this.utils = manager.getUtils();
		this.runners = map;

		this.placeFont = utils.getBebasNeue(32);
		this.nameFont = utils.getBebasNeue(30);
		this.timeFont = utils.getBebasNeue(20);
	}

	public void build(int x, int startY, JPanel panel) {
		this.x = -400;
		this.startX = x;
		this.startY = startY;
		this.panel = panel;
		this.space = 50;
		
		for (RunnerUtils runner : runners.keySet()) {
			if (!update(runners, runner, false))
				break;
		}
		
		this.build(AnimationVector.RIGHT);
	}

	public boolean update() {
		return update(runners, null, false);
	}

	public boolean update(Map<RunnerUtils, Long> map, RunnerUtils runner, boolean animate) {
		this.runners = map;
		
		for (RunnerUtils r : map.keySet()) {
			if (runner.getDomainId() == r.getDomainId())
				break;
			place++;
		}
		
		if (maxLimit != 0 && place >= maxLimit)
			return false;

		if (limit != 0 && place > 0 && place % limit == 0) {
			x += 400;
		}

		if (runner == null) {
			if (map.size() == 0) {
				System.out.println("[Leaderboard] Nobody has finished the running, therefore, is not possible to show results.");
				return false;
			}
			
			runner = new ArrayList<>(map.keySet()).get(place);
		}

		Color color = color4;

		if (place == 0)
			color = color1;
		if (place == 1)
			color = color2;
		if (place == 2)
			color = color3;
		
		if (place % 10 == 0)
			pY = 0;

		Placing placing = new Placing(place,start, map, runner, placeFont, nameFont, timeFont, color, color2, panel, startX, startY + (pY * space), space, x);

		placings.add(placing);
		
		if (animate) {
			placing.animate(AnimationVector.RIGHT);
		}
		place = start;
		pY++;
		return true;
	}

	public void build(AnimationVector vector) {
		Thread thread = new Thread(new Runnable() {
			int index = 0;
			@Override
			public void run() {
				while (true) {
					try {
						if (placings.size() <= 0 || index == placings.size()) {
							//System.out.println("[LeaderBoard] Animation finished.");
							place = 0;					
							placings.clear();
							break;
						}

						placings.get(index).animate(vector);
						
						index++;
						
						Thread.sleep(vector.equals(AnimationVector.RIGHT) ? 100 : 50);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		thread.start();
	}
	
	public void cleanUp(ArrayList<RunnerUtils> runners) {
		for (Placing placing : placings) {
			placing.dispose();
		}
		//System.out.println("[LeaderBoard] Clean Up finished");
		place = 0;					
		placings.clear();
		build(x, startY, panel);
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setStart(int i) {
		this.place = i;
		this.start = i;
	}

}
