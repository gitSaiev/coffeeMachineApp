package ru.saiev.service;

import ru.saiev.model.Product;
import ru.saiev.repository.GoodsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GoodsService {

    private final List<Product> productsList;

    public GoodsService() {
        GoodsRepository goodsRepository = new GoodsRepository();
        this.productsList = goodsRepository.getAll();
    }

    // Возвращения всех товаров
    public List<Product> getAll() {
        return this.productsList;
    }

    // Фильтр товаров по имени (lower case)
    public List<Product> getFilter(String filter) {
        return productsList.stream().filter(product -> product.getName().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
    }
}
