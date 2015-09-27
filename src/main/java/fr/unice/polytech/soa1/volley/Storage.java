package fr.unice.polytech.soa1.volley;


import java.util.Collection;

public interface Storage {

	void create(String name);

	VolleyStuff read(String name);

	void delete(String name);

	Collection<VolleyStuff> findAll();

}
