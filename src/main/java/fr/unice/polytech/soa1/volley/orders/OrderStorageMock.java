package fr.unice.polytech.soa1.volley.orders;

import fr.unice.polytech.soa1.volley.Storage;
import fr.unice.polytech.soa1.volley.basket.BasketItem;
import fr.unice.polytech.soa1.volley.catalog.Color;

import java.util.*;

/**
 * @author : Laureen Ginier
 */
public class OrderStorageMock implements Storage<Orders> {

    private static volatile OrderStorageMock instance = null;

    private static Map<String, Orders> contents;

    private OrderStorageMock(){
        contents = new HashMap<String, Orders>();
        List<BasketItem> basket = new ArrayList<BasketItem>();
        basket.add(new BasketItem("net", 1, Color.BLACK));
        create(new Orders("Gareth", basket, 9.5, "Polytech Nice Sophia"));
        basket.add(new BasketItem("flip flops", 2, Color.GREEN));
        create(new Orders("Celia", basket, 30.5, "Miami Beach"));
        basket.clear();
        basket.add(new BasketItem("kids net", 1, Color.BLUE));
        basket.add(new BasketItem("volley ball", 1, Color.BLUE));
        create(new Orders("Luc", basket, 11.5, "rue du soleil"));
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
