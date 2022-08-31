package ru.saiev.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.saiev.MainApp;
import ru.saiev.model.CheckLines;
import ru.saiev.model.Product;
import ru.saiev.service.CartService;
import ru.saiev.service.GoodsService;

import java.io.IOException;
import java.util.*;

public class MainController {

    private final GoodsService goodsService = new GoodsService();
    private final CartService cartService = new CartService();

    @FXML
    private Button main_basket_button_clear;

    @FXML
    private Button main_basket_button_pay;

    @FXML
    private TextField main_basket_field_totalSum;

    @FXML
    private Button main_filter_button_clear;

    @FXML
    private Button main_table_button_add;

    @FXML
    private TextField main_filter_field;

    @FXML
    private TableView<CheckLines> main_table_basket;

    @FXML
    private TableColumn<?, ?> main_table_basket_count;

    @FXML
    private TableColumn<Product, Integer> main_table_basket_name;

    @FXML
    private TableColumn<?, ?> main_table_basket_total;

    @FXML
    private TableView<Product> main_table_products;

    @FXML
    private TableColumn<?, ?> main_table_products_ean;

    @FXML
    private TableColumn<?, ?> main_table_products_name;

    @FXML
    private TableColumn<?, ?> main_table_products_price;

    @FXML
    private Button main_basket_button_delete;

    @FXML
    void initialize() {
        refreshTable(goodsService.getAll());

        // Поиск товара
        main_filter_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refreshTable(goodsService.getFilter(newValue));
            }
        });

        // Сброс фильтра поиска товара
        main_filter_button_clear.setOnAction(event -> {
            main_filter_field.setText("");
        });

        // Фокус на выбранный товар
        main_table_products.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                Product product = main_table_products.getFocusModel().getFocusedItem();
                if (product != null) {
                    refreshBasket(cartService.addProductToBasket(product));
                }
            }
        });

        // Добавления выбранного товара
        main_table_button_add.setOnAction(event -> {
            Product product = main_table_products.getFocusModel().getFocusedItem();
            if (product != null) {
                refreshBasket(cartService.addProductToBasket(product));
            }
        });

        // События на очистку корзины
        main_basket_button_clear.setOnAction(event -> {
            refreshBasket(cartService.clearBasket());
        });

        // Удаляем выбранную товар из корзины
        main_basket_button_delete.setOnAction(event -> {
            CheckLines product = main_table_basket.getFocusModel().getFocusedItem();
            if (product != null) {
                refreshBasket(cartService.deletePositionBasket(product));
            }
        });

        // События вызова окна для оплаты
        main_basket_button_pay.setOnAction(event -> {
            if (!cartService.basketIsEmpty()) {
                Stage newWindow = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/application/pay.fxml"));
                Map<String, String> params = new HashMap<>();
                params.put("totalSum", String.valueOf(cartService.getTotalBasketSum()));
                fxmlLoader.getNamespace().putAll(params);

                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 210, 320);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PayController lc = fxmlLoader.getController();
                lc.mainController = this;
                newWindow.setTitle("Payment");
                newWindow.setScene(scene);
                newWindow.setResizable(false);
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.initOwner(main_basket_button_pay.getScene().getWindow());
                newWindow.show();
            }
        });
    }

    // События на обновления списка товаров
    public void refreshTable(List<Product> productList) {
        ObservableList<Product> productsData = FXCollections.observableArrayList();
        productsData.addAll(productList);
        main_table_products_ean.setCellValueFactory(new PropertyValueFactory<>("ean"));
        main_table_products_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        main_table_products_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        main_table_products.setItems(productsData);
    }

    // События на обновление корзины
    public void refreshBasket(Map<String, CheckLines> basketList) {
        ObservableList<CheckLines> basketData = FXCollections.observableArrayList();
        basketData.addAll(new ArrayList<>(basketList.values()));
        main_table_basket_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        main_table_basket_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        main_table_basket_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        main_table_basket.setItems(basketData);
        main_basket_field_totalSum.setText(String.format("%1$,.2f", cartService.getTotalBasketSum()));
        main_table_basket.refresh();
    }

    public CartService getCartService() {
        return this.cartService;
    }

}
