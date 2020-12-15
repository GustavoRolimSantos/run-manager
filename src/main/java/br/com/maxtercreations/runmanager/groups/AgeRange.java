package br.com.maxtercreations.runmanager.groups;

public class AgeRange {

	private int start, end, id;
	private String name;
	
	public AgeRange(int id, int start, int end) {
		this.start = start;
		this.end = end;
		this.id = id;
	}
	
	public AgeRange(int id, int start, int end, String name) {
		this.start = start;
		this.end = end;
		this.name = name;
		this.id = id;
	}
	
	public int getStartAge() {
		return start;
	}
	
	public int getEndAge() {
		return end;
	}
	
	public String getRange() {
		return start + "-" + end;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
}
