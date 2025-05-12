package com.test_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class FilterController {

    // Closes the filter window (discarding any uncommitted changes)
    @FXML
    private void onBackButtonPressed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Closes the filter window (intended to apply selected filters later)
    @FXML
    private void onApplyButtonPressed(ActionEvent event) {
        // TODO: Pass selected filter values to the dashboard controller or data layer

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
