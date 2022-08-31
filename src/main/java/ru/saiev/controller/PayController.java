package ru.saiev.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.Objects;

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
        // Подтверждения оплаты
        pay_button_confirm.setOnAction(event -> {
            String inputString = pay_inputSum_field.getText(); // Введенная сумма в поле
            BigDecimal bigDecimal = null;

            try {
                bigDecimal = new BigDecimal(inputString);
            } catch (NumberFormatException e) {
                pay_inputSum_field.setText(totalSum());
            }
            // Проверка пустая ли корзина
            if (Objects.requireNonNull(bigDecimal).compareTo(mainController.getCartService().getTotalBasketSum()) == 0) {
                mainController.refreshBasket(mainController.getCartService().newCheck());
                pay_button_confirm.getScene().getWindow().hide();
            } else {
                pay_inputSum_field.setText(totalSum());
            }
        });
        // Отмена оплаты
        pay_button_cancel.setOnAction(event -> {
            pay_button_cancel.getScene().getWindow().hide();
        });
    }

    // Возвращение итоговой суммы
    private String totalSum() {
        return String.valueOf(mainController.getCartService().getTotalBasketSum().doubleValue());
    }
}
