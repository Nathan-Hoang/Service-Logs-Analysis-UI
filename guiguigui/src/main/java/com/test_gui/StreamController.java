package com.test_gui;

import javafx.fxml.FXML;

public class StreamController {

    @FXML private void onDashboardButtonPressed() {
        App.switchToDashboard();
    }

    @FXML private void onStreamButtonPressed() {
        App.switchToStream();
    }
    
    @FXML private void onExplorerButtonPressed() {
        App.switchToExplorer();;
    }
}
