package fr.unice.polytech.soa1.volley.accounts;

import javax.ws.rs.*;
import java.util.Collection;

public interface AccountService {

    @GET
    Collection<Account> getAccounts();

    @Path("/{login}")
    @GET
    Account getAccount(@PathParam("login") String login);

    @POST
    void createAccount(Account account);

    @Path("/{login}")
    @POST
    Account login(@PathParam("login") String login, PasswordWrapper password);

    @Path("/{login}")
    @PUT
    void updateAccount(@PathParam("login") String login, Account account);

    @Path("/{login}")
    @DELETE
    void deleteAccount(@PathParam("login") String login);

}
