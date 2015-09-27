package fr.unice.polytech.soa1.volley;


import java.util.Collection;
import java.util.HashMap;

public class Storage {

	// this mocks a database.
	private static HashMap<String, VolleyStuff> contents = new HashMap<String, VolleyStuff>();

	public static void create(String name) {
		contents.put(name, new VolleyStuff(name));
	}

	public static VolleyStuff read(String name) {
		return contents.get(name);
	}

	public static void delete(String name) {
		contents.remove(name);
	}

	public static Collection<VolleyStuff> findAll() {
		return contents.values();
	}


	static {
		Storage.create("blue net");
	}

}
