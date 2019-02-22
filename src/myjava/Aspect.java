
/*
	Class: Aspect
	Description: Contains Aspects and their table names and flag values
	Author: Md. Reyadus Salahin (CSE, CUET)
	
*/

package myjava;

public class Aspect {
	private int env, san, job, prom, behave, count;
	private String[] name = new String[5];
	public Aspect() {
		env = 0;
		san = 1;
		job = 2;
		prom = 3;
		behave = 4;
		count = 5;
		name[0] = "env";
		name[1] = "san";
		name[2] = "job";
		name[3] = "prom";
		name[4] = "behave";
	}
	public String getName(int i) {
		return name[i];
	}
	public int environment() {
		return env;
	}
	public int sanitary() {
		return san;
	}
	public int jobSecurity() {
		return job;
	}
	public int promotion() {
		return prom;
	}
	public int behaviour() {
		return behave;
	}
	public int getCount() {
		return count;
	}
}
