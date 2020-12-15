package br.com.maxtercreations.runmanager.database;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.maxtercreations.runmanager.constants.Constants;
import br.com.maxtercreations.runmanager.loader.Loader;

public class RaceManager {

	private JSONParser jsonParser = new JSONParser();
	private JSONObject race;

	public RaceManager() {
		syncronizeFile();
	}

	private String getPath() {
		return Constants.DATABASE_FOLDER + "race.json";
	}

	public void syncronizeFile() {
		try (FileReader reader = new FileReader(getPath())) {
			Object obj = jsonParser.parse(reader);

			race = (JSONObject) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean saveFile() {
		try (FileWriter file = new FileWriter(getPath())) {

			file.write(race.toJSONString());
			file.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getStartedTime() {
		return race.get("started").toString();
	}

	public long getFormattedStartedTime() {
		try {
			return Constants.dateAndTimeFormat.parse(race.get("started").toString()).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean updateStartedTime(long time) {
		race.put("started", Constants.dateAndTimeFormat.format(time));

		return saveFile();
	}

	@SuppressWarnings("unchecked")
	public void stop() {
		JSONObject races = null;

		if (race.containsKey("races")) {
			races = (JSONObject) race.get("races");
		} else {
			races = new JSONObject();
		}

		races.put(String.format("%06d", (races.size() + 1)), Constants.dateAndTimeFormat.format(Loader.getManager().getRunning().getStartTime()));

		race.put("races", races);
		
		race.put("started", "");
		
		saveFile();
	}
	
	public boolean hasStarted() {
		return !race.get("started").toString().isEmpty();
	}

}
