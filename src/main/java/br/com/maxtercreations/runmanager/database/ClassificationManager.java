package br.com.maxtercreations.runmanager.database;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.maxtercreations.runmanager.constants.Constants;

public class ClassificationManager {

	private JSONParser jsonParser = new JSONParser();
	private JSONArray classifications;

	public ClassificationManager() {
		syncronizeFile();
	}

	private String getPath() {
		return Constants.DATABASE_FOLDER + "classification.json";
	}

	public void syncronizeFile() {
		
		try (FileReader reader = new FileReader(getPath())) {
			Object obj = jsonParser.parse(reader);

			classifications = (JSONArray) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean saveFile() {
		try (FileWriter file = new FileWriter(getPath())) {

			file.write(classifications.toJSONString());
			file.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public JSONObject getRunner(String domainId) {
		return (JSONObject) ((JSONObject) classifications.get(0)).get(domainId);
	}
	
	public boolean hasFinished(String domainId) {
		return ((JSONObject) classifications.get(0)).containsKey(domainId);
	}

	@SuppressWarnings("unchecked")
	public boolean updateClassification(String domainId, int classification, String time) {
		JSONObject classificationObj = new JSONObject();

		classificationObj.put("classification", classification);
		classificationObj.put("time", time);

		((JSONObject) classifications.get(0)).put(domainId, classificationObj);

		return saveFile();
	}

	public JSONArray getClassifications() {
		return classifications;
	}
	
	public ArrayList<RunnerUtils> transformToRunnerUtils() {
	
		ArrayList<RunnerUtils> runnerUtils = new ArrayList<RunnerUtils>();
		
		if (classifications.size() == 0)
			return runnerUtils;
		
		@SuppressWarnings("unchecked")
		ArrayList<String> domains = new ArrayList<>(((JSONObject)classifications.get(0)).keySet());
		
		domains.forEach(domain -> {
			runnerUtils.add(new RunnerUtils(domain));
		});
		
		return runnerUtils;
	}
	
	public ArrayList<RunnerUtils> transformToRunnerUtils(int min) {
		
		ArrayList<RunnerUtils> runnerUtils = new ArrayList<RunnerUtils>();
		
		if (classifications.size() == 0)
			return runnerUtils;
		
		@SuppressWarnings("unchecked")
		ArrayList<String> domains = new ArrayList<>(((JSONObject)classifications.get(0)).keySet());
		
		int i = 0;
		
		for (String domain : domains) {
			
			if (i > min) {
				runnerUtils.add(new RunnerUtils(domain));
			}
			
			i++;
		}
		
		return runnerUtils;
	}
	
	@SuppressWarnings("unchecked")
	public void clearClassifications() {
		classifications = new JSONArray();
		classifications.add(new JSONObject());
		saveFile();
	}

}
