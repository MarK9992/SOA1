package fr.unice.polytech.soa1.volley.catalog;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VolleyStuff {

	private String name;

	private int cpt = 0;

	@JsonCreator
	public VolleyStuff(@JsonProperty("name") String s) {
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
