package br.com.maxtercreations.runmanager.database;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.maxtercreations.runmanager.constants.Constants;

public class RunnerManager {

	private JSONParser jsonParser = new JSONParser();
	private JSONArray runners;

	public RunnerManager() {
		syncronizeFile();
	}

	private String getPath() {
		return Constants.DATABASE_FOLDER + "running.json";
	}

	public void syncronizeFile() {
		try (FileReader reader = new FileReader(getPath())) {
			Object obj = jsonParser.parse(reader);

			runners = (JSONArray) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean saveFile() {
		try (FileWriter file = new FileWriter(getPath())) {

			file.write(runners.toJSONString());
			file.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public JSONObject getRunner(String name) {
		@SuppressWarnings("unchecked")
		ArrayList<String> domains = new ArrayList<>(((JSONObject) runners.get(0)).keySet());

		for (int i = 0; i < domains.size(); i++) {
			JSONObject runner = getRunnerByDomain(domains.get(i));
			if (runner.get("name").equals(name)) {
				return runner;
			}
		}

		return null;
	}

	public JSONObject getRunnerByBarcode(String barcode) {
		@SuppressWarnings("unchecked")
		ArrayList<String> domains = new ArrayList<>(((JSONObject) runners.get(0)).keySet());

		for (int i = 0; i < domains.size(); i++) {
			JSONObject runner = getRunnerByDomain(domains.get(i));
			if (runner.get("id").equals(barcode)) {
				return runner;
			}
		}

		return null;
	}

	public String getDomainId(JSONObject runner) {
		return runner.get("domain_id").toString();
	}

	public JSONObject getRunnerByDomain(String domainId) {
		return (JSONObject) ((JSONObject) runners.get(0)).get(domainId);
	}

	@SuppressWarnings("unchecked")
	public String addRunner(String name, String email, String phone, String birthday, String race_type, String id) {
		JSONObject account = new JSONObject();
		String domainId = UUID.randomUUID().toString();

		account.put("name", name);
		account.put("email", email);
		account.put("phone", phone);
		account.put("birthday", birthday);
		account.put("race_type", race_type);
		account.put("date_entered", Constants.dateFormat.format(new Date()));
		account.put("id", id);
		account.put("domain_id", domainId);

		((JSONObject) runners.get(0)).put(domainId, account);

		saveFile();

		return domainId;
	}

	@SuppressWarnings("unchecked")
	public boolean updateRunner(String domainId, String name, String email, String phone, String birthday, String race_type, String id) {
		JSONObject account = getRunnerByDomain(domainId);

		account.put("name", name);
		account.put("email", email);
		account.put("phone", phone);
		account.put("birthday", birthday);
		account.put("race_type", race_type);
		account.put("id", id);

		((JSONObject) runners.get(0)).put(domainId, account);

		return saveFile();
	}

	@SuppressWarnings("unchecked")
	public boolean updateRunnerId(JSONObject runner, String id) {
		runner.put("id", id);

		return saveFile();
	}

	public JSONArray getRunners() {
		return runners;
	}

	/*public static void main(String[] args) {
		ArrayList<String> keys = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		ArrayList<String> raceTypes = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Gustavo\\Desktop\\teste.csv"), "UTF-8"));
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					values.add(line.split(";")[0]);
					raceTypes.add(line.split(";")[1]);
					keys.add(String.format("%06d", Integer.valueOf(line.split(";")[2].replaceAll("[^0-9]+", ""))));
					line = br.readLine();
				}
				String everything = sb.toString();
				System.out.println(everything);
			} finally {
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		RunnerManager runnerManager = new RunnerManager();

		for (int i = 0; i < keys.size(); i++) {
			runnerManager.addRunner(values.get(i), "", "", "", raceTypes.get(i), keys.get(i));
		}
	}*/

}
