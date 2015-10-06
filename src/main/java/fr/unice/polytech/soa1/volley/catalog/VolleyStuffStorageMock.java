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
        create(new VolleyStuff("net", 9.5, "", Color.BLUE));
        create(new VolleyStuff("kids net", 7.5, "", Color.BLUE));
        create(new VolleyStuff("volley ball", 4, "", Color.YELLOW));
        create(new VolleyStuff("pro volley ball", 17, "", Color.BLACK));
        create(new VolleyStuff("stake", 12, "", Color.BLACK));
        create(new VolleyStuff("flip flops", 10.5, "", Color.BLACK));
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

    public void create(VolleyStuff stuff) {
        contents.put(stuff.getName(), stuff);
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
