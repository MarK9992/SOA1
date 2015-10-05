package fr.unice.polytech.soa1.volley.basket;

import fr.unice.polytech.soa1.volley.Storage;
import fr.unice.polytech.soa1.volley.accounts.Account;
import fr.unice.polytech.soa1.volley.accounts.AccountStorageMock;
import fr.unice.polytech.soa1.volley.catalog.Color;
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
        String itemKey;
        Color color;

        for (BasketItem item: products) {
            if ((color = item.getColor()) != null) {
                itemKey = item.getName() + " " + color;
            }
            else {
                itemKey = item.getName();
            }
            if (basket.get(itemKey) != null) {
                throw new ConflictException("Basket already contains " + itemKey +" objects." +
                        " Use PUT to update quantity or DELETE to remove instead.");
            }
            if (stuffStorage.read(item.getName()) == null) {
                throw new BadRequestException("Added item does not exist.");
            }
            if (item.getQuantity() <= 0) {
                throw new BadRequestException("Item quantity should be greater or equal than 0.");
            }
            basket.put(itemKey, item.getQuantity());
        }
    }

    @Path("/{login}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void adjustQuantity(@PathParam("login") String login, BasketItem item) {
        Account account = accountStorage.read(login);
        String itemKey;
        Color color;

        if (account == null) {
            throw new NotFoundException();
        }
        if (item == null) {
            throw new BadRequestException("no PUT data");
        }
        if ((color = item.getColor()) != null) {
            itemKey = item.getName() + " " + color;
        }
        else {
            itemKey = item.getName();
        }
        if (item.getQuantity() < 0) {
            throw new BadRequestException("quantity should be greater or equal than 0");
        }
        if (!account.getBasket().containsKey(itemKey)) {
            throw new BadRequestException("Basket does not contain object " + itemKey + "use POST instead.");
        }
        if (item.getQuantity() == 0) {
            account.getBasket().remove(itemKey);
        }
        else {
            account.getBasket().put(itemKey, item.getQuantity());
        }
    }
}
