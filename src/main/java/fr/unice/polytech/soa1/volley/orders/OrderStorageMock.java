package fr.unice.polytech.soa1.volley.orders;

import fr.unice.polytech.soa1.volley.Storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Laureen Ginier
 */
public class OrderStorageMock implements Storage<Orders> {

    private static volatile OrderStorageMock instance = null;

    private static Map<String, Orders> contents;

    public OrderStorageMock(){
        contents = new HashMap<String, Orders>();
    }

    public static OrderStorageMock getInstance() {
        if (instance == null) {
            synchronized (OrderStorageMock.class) {
                if (instance == null) {
                    instance = new OrderStorageMock();
                }
            }
        }
        return instance;
    }

    public void create(Orders theOrder) {
        contents.put(theOrder.getRef(), theOrder);
    }

    public Orders read(String ref) {
        return contents.get(ref);
    }

    public void delete(String ref) {
        contents.remove(ref);
    }

    public Collection<Orders> findAll() {
        return contents.values();
    }
}
