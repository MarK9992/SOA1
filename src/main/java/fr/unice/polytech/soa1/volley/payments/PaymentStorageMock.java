package fr.unice.polytech.soa1.volley.payments;

import fr.unice.polytech.soa1.volley.Storage;
import fr.unice.polytech.soa1.volley.orders.OrderStorageMock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Laureen Ginier
 */
public class PaymentStorageMock implements Storage<Payment> {

    private static volatile PaymentStorageMock instance = null;

    private static Map<String, Payment> contents;

    private PaymentStorageMock(){
        contents = new HashMap<String, Payment>();
        create(new Payment("Gareth", "Polytech Nice Sophia", 9.5, "1", 122, (short)1, "tomorrow",PaymentStatus.APPROVED));
        create(new Payment("Celia", "Miami Beach", 30.5, "2", 244, (short) 2, "today", PaymentStatus.UNCHECKED));
        create(new Payment("Luc", "rue du soleil", 11.5, "3", 20, (short) 3, "tomorrow", PaymentStatus.UNCHECKED));
    }

    public static PaymentStorageMock getInstance() {
        if (instance == null) {
            synchronized (OrderStorageMock.class) {
                if (instance == null) {
                    instance = new PaymentStorageMock();
                }
            }
        }
        return instance;
    }

    public void create(Payment payment) { contents.put(payment.getId(), payment); }

    public Payment read(String paymentId) { return contents.get(paymentId); }

    public Collection<Payment> findAll() { return contents.values(); }

    public void delete(String paymentId) { contents.remove(paymentId); }

}
