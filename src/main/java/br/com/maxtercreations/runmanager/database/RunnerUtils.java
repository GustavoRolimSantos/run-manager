package br.com.maxtercreations.runmanager.database;

import org.json.simple.JSONObject;

import br.com.maxtercreations.runmanager.loader.Loader;

public class RunnerUtils {

	private JSONObject runner;
	private String domainId;
	
	public RunnerUtils(String domainId) {
		this.runner = Loader.getManager().getRunnerManager().getRunnerByDomain(domainId);
		this.domainId = domainId;
	}
	
	public int getAge() {
		return Loader.getManager().getUtils().getAge(runner.get("birthday").toString());
	}
	
	public String getName() {
		return runner.get("name").toString();
	}
	
	public String getRaceType() {
		return runner.get("race_type").toString().replace(" ", "");
	}
	
	public boolean isInOfficalRunning() {
		return getRaceType().contains("O");
	}
	
	public String getPhone() {
		return runner.get("phone").toString();
	}
	
	public String getBarcode() {
		return runner.get("id").toString();
	}
	
	public String getDomainId() {
		return domainId;
	}
	
	public String getBithdate() {
		return runner.get("birthday").toString();
	}
	
	public JSONObject getRunner() {
		return runner;
	}
	
}
