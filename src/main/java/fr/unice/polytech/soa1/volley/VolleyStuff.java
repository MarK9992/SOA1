package fr.unice.polytech.soa1.volley;


public class VolleyStuff {

	private String name;

	private int cpt = 0;

	public VolleyStuff(String s) {
		this.name = s;
	}

	public String run() {
		cpt++;
		return name+cpt;
	}

	public String getName() {
		return name;
	}

}
