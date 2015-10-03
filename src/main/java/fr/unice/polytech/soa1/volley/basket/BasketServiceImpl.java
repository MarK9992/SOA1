package fr.unice.polytech.soa1.volley.basket;

import fr.unice.polytech.soa1.volley.Storage;
import fr.unice.polytech.soa1.volley.accounts.Account;
import fr.unice.polytech.soa1.volley.accounts.AccountStorageMock;
import fr.unice.polytech.soa1.volley.catalog.VolleyStuff;
import fr.unice.polytech.soa1.volley.catalog.VolleyStuffStorageMock;
import fr.unice.polytech.soa1.volley.customexceptions.ConflictException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * @author Marc Karassev
 */
@Path("/basket")
@Produces(MediaType.APPLICATION_JSON)
public class BasketServiceImpl implements BasketService {

    private Storage<Account> accountStorage;
    private Storage<VolleyStuff> stuffStorage;

    public BasketServiceImpl() {
        accountStorage = AccountStorageMock.getInstance();
        stuffStorage = VolleyStuffStorageMock.getInstance();
    }

    public Map<String, Integer> getBasket(String login) {
        Account account = accountStorage.read(login);

        if (account == null) {
            throw new NotFoundException();
        }
        return account.getBasket();
    }

    @Path("/{login}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addProductToBasket(@PathParam("login") String login, List<BasketItem> products) {
        Account account = accountStorage.read(login);

        if (account == null) {
            throw new NotFoundException();
        }
        if (products == null) {
            throw new BadRequestException("no POST data");
        }

        Map<String, Integer> basket = account.getBasket();

        for (BasketItem item: products) {
            if (basket.get(item.getName()) != null) {
                throw new ConflictException("Basket already contains " + item.getName() +" objects." +
                        " Use PUT to update quantity or DELETE to remove instead.");
            }
            if (stuffStorage.read(item.getName()) == null) {
                throw new BadRequestException("Added item does not exist.");
            }
            if (item.getQuantity() <= 0) {
                throw new BadRequestException("Item quantity should be greater or equal than 0.");
            }
            basket.put(item.getName(), item.getQuantity());
        }
    }

    @Path("/{login}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void adjustQuantity(@PathParam("login") String login, BasketItem item) {
        Account account = accountStorage.read(login);

        if (account == null) {
            throw new NotFoundException();
        }
        if (item == null) {
            throw new BadRequestException("no PUT data");
        }
        if (item.getQuantity() < 0) {
            throw new BadRequestException("quantity should be greater or equal than 0");
        }
        if (!account.getBasket().containsKey(item.getName())) {
            throw new BadRequestException("Basket does not contain object " + item.getName() + ".");
        }
        if (item.getQuantity() < 0) {
            account.getBasket().remove(item.getName());
        }
        else {
            account.getBasket().put(item.getName(), item.getQuantity());
        }
    }
}
