package fr.unice.polytech.soa1.volley;


import java.util.Collection;

public interface Storage<T> {

	void create(T object);

	T read(String id);

	void delete(String id);

	Collection<T> findAll();

}
