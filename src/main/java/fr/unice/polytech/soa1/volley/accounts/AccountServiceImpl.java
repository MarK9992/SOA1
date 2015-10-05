package fr.unice.polytech.soa1.volley.accounts;

import fr.unice.polytech.soa1.volley.Storage;
import fr.unice.polytech.soa1.volley.customexceptions.ConflictException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * @author Marc Karassev
 */
@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountServiceImpl implements AccountService {

    private Storage<Account> storage;

    public AccountServiceImpl() {
        storage = AccountStorageMock.getInstance();
    }

    @GET
    public Collection<Account> getAccounts() {
        return storage.findAll();
    }

    @Path("/{login}")
    @GET
    public Account getAccount(@PathParam("login") String login) {
        Account account = storage.read(login);

        if (account == null) {
            throw new NotFoundException();
        }
        return account;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createAccount(Account account) {
        if (account == null) {
            throw new BadRequestException("no POST data");
        }
        if (storage.read(account.getLogin()) != null) {
            throw new ConflictException("\"Existing login " + account.getLogin() + "\"");
        }
        storage.create(account);
    }

    @Path("/{login}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Account login(@PathParam("login") String login, PasswordWrapper password) {
        Account account = storage.read(login);

        if (account == null) {
            throw new NotFoundException();
        }
        if (!account.getPassword().equals(password.getValue())) {
            throw new BadRequestException("invalid password");
        }
        return account;
    }

    @Path("/{login}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateAccount(@PathParam("login") String login, Account account) {
        if (account == null) {
            throw new BadRequestException("no PUT data");
        }
        if (storage.read(login) == null) {
            throw new NotFoundException();
        }
        storage.delete(login);
        storage.create(account);
    }

    @Path("/{login}")
    @DELETE
    public void deleteAccount(@PathParam("login") String login) {
        if (storage.read(login) == null) {
            throw new NotFoundException();
        }
        storage.delete(login);
    }
}
