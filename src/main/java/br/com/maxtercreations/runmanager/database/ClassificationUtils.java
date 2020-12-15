package br.com.maxtercreations.runmanager.database;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import br.com.maxtercreations.runmanager.loader.Loader;

public class ClassificationUtils {

	private JSONObject classification;
	private String domainId;
	
	public ClassificationUtils(String domainId) {
		this.classification = Loader.getManager().getClassificationManager().getRunner(domainId);
		this.domainId = domainId;
	}
	
	public long getMilliSpentTime() {
		try {
			return Long.valueOf(classification.get("time").toString()) - Loader.getManager().getRunning().getStartTime();
		} catch (Exception e) {
			return 0;
		}	
	}
	
	public int getSpentTime() {
		return (int) TimeUnit.MILLISECONDS.toSeconds(getMilliSpentTime());
	} 
	
	public String getDomainId() {
		return domainId;
	}
}
