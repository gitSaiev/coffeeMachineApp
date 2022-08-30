package ru.saiev.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.math.BigDecimal;

public class PayController {

    protected MainController mainController;

    @FXML
    private Button pay_button_cancel;

    @FXML
    private Button pay_button_confirm;

    @FXML
    private TextField pay_inputSum_field;

    @FXML
    void initialize() {
        pay_button_confirm.setOnAction(event -> {
            String inputString = pay_inputSum_field.getText();
            BigDecimal bigDecimal = null;
            try {
                bigDecimal = new BigDecimal(inputString);
            } catch (NumberFormatException e) {
                pay_inputSum_field.setText(totalSum());
            }
            if (bigDecimal != null && bigDecimal.compareTo(mainController.getCartService().getTotalBasketSum()) == 0) {
                mainController.refreshBasket(mainController.getCartService().newCheck());
                pay_button_confirm.getScene().getWindow().hide();
            } else {
                pay_inputSum_field.setText(totalSum());
            }
        });
        pay_button_cancel.setOnAction(event -> {
            pay_button_cancel.getScene().getWindow().hide();
        });
    }

    private String totalSum() {
        return String.valueOf(mainController.getCartService().getTotalBasketSum().doubleValue());
    }
}
