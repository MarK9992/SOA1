package fr.unice.polytech.soa1.volley.catalog;

import fr.unice.polytech.soa1.volley.Storage;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Marc Karassev
 *
 * This mocks a database.
 */
public final class VolleyStuffStorageMock implements Storage<VolleyStuff> {

    private static volatile VolleyStuffStorageMock instance = null;

    private static HashMap<String, VolleyStuff> contents;

    private VolleyStuffStorageMock() {
        contents = new HashMap<String, VolleyStuff>();
        create("blue net");
    }

    public static VolleyStuffStorageMock getInstance() {
        if (instance == null) {
            synchronized (VolleyStuffStorageMock.class) {
                if (instance == null) {
                    instance = new VolleyStuffStorageMock();
                }
            }
        }
        return instance;
    }

    public void create(String name) {
        contents.put(name, new VolleyStuff(name));
    }

    public VolleyStuff read(String name) {
        return contents.get(name);
    }

    public void delete(String name) {
        contents.remove(name);
    }

    public Collection<VolleyStuff> findAll() {
        return contents.values();
    }
}
