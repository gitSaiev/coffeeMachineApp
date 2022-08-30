package ru.saiev.service;

import ru.saiev.model.CheckLines;
import ru.saiev.model.Product;
import ru.saiev.repository.CartRepository;
import ru.saiev.repository.CheckRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {

    private final HashMap<String, CheckLines> basketList;

    private CheckRepository checkRepository = new CheckRepository();
    private CartRepository cartRepository = new CartRepository();


    public CartService() {
        this.basketList = new HashMap<>();
    }

    public Map<String, CheckLines> newCheck() {
        if (!this.basketList.isEmpty()) {
            long checkId = checkRepository.newCheck(getTotalBasketSum());
            cartRepository.saveAll(this.basketList.values(), checkId);
        }
        return clearBasket();
    }

    public Map<String, CheckLines> addProductToBasket(Product product) {
        CheckLines checkLine;
        if (basketList.containsKey(product.getName())) {
            checkLine = basketList.get(product.getName());
            checkLine.addIncrease(product);
        } else {
            checkLine = new CheckLines(product);
        }
        basketList.put(product.getName(), checkLine);
        return this.basketList;
    }

    public BigDecimal getTotalBasketSum() {
        return this.basketList.values().stream()
                .map(CheckLines::getTotal)
                .reduce(BigDecimal::add).orElse(new BigDecimal("0.0"));
    }

    public Map<String, CheckLines> clearBasket() {
        this.basketList.clear();
        return this.basketList;
    }

    public Map<String, CheckLines> deletePositionBasket(CheckLines product) {
        basketList.remove(product.getName());
        return this.basketList;
    }

    public boolean basketIsEmpty() {
        return this.basketList.isEmpty();
    }
}
