package fr.unice.polytech.soa1.volley.accounts;

import fr.unice.polytech.soa1.volley.Storage;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Marc Karassev
 */
public class AccountStorageMock implements Storage<Account> {

    private static volatile AccountStorageMock instance = null;

    private static HashMap<String, Account> contents;

    private AccountStorageMock() {
        contents = new HashMap<String, Account>();
        create(new Account("admin", "admin", ""));
    }

    public static AccountStorageMock getInstance() {
        if (instance == null) {
            synchronized (AccountStorageMock.class) {
                if (instance == null) {
                    instance = new AccountStorageMock();
                }
            }
        }
        return instance;
    }

    public void create(Account account) {
        contents.put(account.getLogin(), account);
    }

    public Account read(String login) {
        return contents.get(login);
    }

    public void delete(String login) {
        contents.remove(login);
    }

    public Collection<Account> findAll() {
        return contents.values();
    }
}
