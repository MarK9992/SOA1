package fr.unice.polytech.soa1.volley;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Marc Karassev
 *
 * This mocks a database.
 */
public final class StorageMock implements Storage {

    private static volatile StorageMock instance = null;

    private static HashMap<String, VolleyStuff> contents;

    private StorageMock() {
        contents = new HashMap<String, VolleyStuff>();
        create("blue net");
    }

    public static StorageMock getInstance() {
        if (instance == null) {
            synchronized (StorageMock.class) {
                if (instance == null) {
                    instance = new StorageMock();
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
