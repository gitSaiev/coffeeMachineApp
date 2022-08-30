package ru.saiev.service;

import ru.saiev.model.CheckLines;
import ru.saiev.repository.CartRepository;
import ru.saiev.repository.CheckRepository;

import java.math.BigDecimal;
import java.util.List;

public class CartService {

    CheckRepository checkRepository = new CheckRepository();
    CartRepository cartRepository = new CartRepository();

    public void newCheck(List<CheckLines> checkLines, BigDecimal totalSum) {
        long checkId = checkRepository.newCheck(totalSum);
        cartRepository.saveAll(checkLines, checkId);
    }
}
