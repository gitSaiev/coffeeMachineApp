package ru.saiev.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PayController {

    public static final String PAGE_URL = "/application/pay_page.fxml";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button pay_button_cancel;

    @FXML
    private Button pay_button_confirm;

    @FXML
    private TextField pay_inputSum_field;

    @FXML
    private Label pay_totalSum_text;

    @FXML
    void initialize() {
        pay_button_cancel.setOnAction(event -> {
            pay_button_cancel.getScene().getWindow().hide();
        });
    }
}
