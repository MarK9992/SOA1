package fr.unice.polytech.soa1.volley;


import java.util.Collection;

public interface Storage<T> {

	void create(String name);

	T read(String name);

	void delete(String name);

	Collection<T> findAll();

}
