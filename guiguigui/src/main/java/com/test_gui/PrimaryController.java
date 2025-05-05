package com.test_gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryController {

    @FXML private TableView<?> logTable;
    @FXML private TableView<?> statusTable;

    @FXML private HBox tableSection;
    @FXML private TextField searchField;

    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;

    @FXML private TextField fromTimeField;
    @FXML private TextField toTimeField;

    @FXML private Button applyFilterButton;

    @FXML private HBox dateFilterBox;
    @FXML private HBox timeFilterBox;

    @FXML private HBox locationFilterBox;

    @FXML private TextField countryField;
    @FXML private TextField regionField;
    @FXML private TextField cityField;

    
    public void bindTableHeightsToScene(Scene scene) {
        tableSection.prefHeightProperty().bind(scene.heightProperty().multiply(0.5));}

    // MenuButton actions
    @FXML private void onFilterIP() {
        resetFilterUI();
        showSearchField("Enter IP Address...");
        showApplyButton();
    }
    
    @FXML private void onTimestamp() {
        resetFilterUI();
        dateFilterBox.setVisible(true);
        dateFilterBox.setManaged(true);
        timeFilterBox.setVisible(true);
        timeFilterBox.setManaged(true);
        showApplyButton();
    }
    
    @FXML private void onStatus() {
        resetFilterUI();
        showSearchField("Enter Status Code...");
        showApplyButton();
    }
    
    @FXML private void onRequest() {
        resetFilterUI();
        showSearchField("Enter Request...");
        showApplyButton();
    }
    
    @FXML private void onSize() {
        resetFilterUI();
        showSearchField("Enter Size...");
        showApplyButton();
    }
    
    @FXML private void onReferrer() {
        resetFilterUI();
        showSearchField("Enter Referrer...");
        showApplyButton();
    }
    
    @FXML private void onFilterUserAgent() {
        resetFilterUI();
        showSearchField("Enter User Agent...");
        showApplyButton();
    }
    
    @FXML private void onLocation() {
        resetFilterUI();
        locationFilterBox.setVisible(true);
        locationFilterBox.setManaged(true);
        showApplyButton();
    }
    
    // Show a standard search field
    private void showSearchField(String prompt) {
        searchField.setPromptText(prompt);
        searchField.setVisible(true);
        searchField.setManaged(true);
        searchField.requestFocus();
    }

    // Hide all other filters
    private void resetFilterUI() {
        searchField.clear();
        searchField.setVisible(false);
        searchField.setManaged(false);
    
        fromDatePicker.setValue(null);
        toDatePicker.setValue(null);
        dateFilterBox.setVisible(false);
        dateFilterBox.setManaged(false);
    
        fromTimeField.clear();
        toTimeField.clear();
        timeFilterBox.setVisible(false);
        timeFilterBox.setManaged(false);
    
        countryField.clear();
        regionField.clear();
        cityField.clear();
        locationFilterBox.setVisible(false);
        locationFilterBox.setManaged(false);
    
        applyFilterButton.setVisible(false);
        applyFilterButton.setManaged(false);
    }
    
    private void showApplyButton() {
        applyFilterButton.setVisible(true);
        applyFilterButton.setManaged(true);
    }
    

    // Placeholder until filtering is wired to backend
    @FXML
    private void onDashboardButtonPressed() {
        App.switchToDashboard();
    }

    @FXML
    private void onStreamButtonPressed() {
        App.switchToStream();
    }
    
    @FXML
    private void onExplorerButtonPressed() {
        App.switchToExplorer();
    }

    @FXML
    private void onRefreshButtonPressed() {
        resetFilterUI();
        System.out.println("Filters cleared. Refreshed.");
    }

    @FXML
    private PieChart countryPieChart;

    private final String DATA_FILE = "data_placeholder/country_requests.txt"; // adjust the path if needed

    @FXML
    public void initialize() {
        Map<String, Integer> countryData = loadDataFromFile();
        if (countryData == null || countryData.isEmpty()) return;

        int totalRequests = countryData.values().stream().mapToInt(Integer::intValue).sum();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : countryData.entrySet()) {
            String country = entry.getKey();
            int requests = entry.getValue();
            double percentage = (requests * 100.0) / totalRequests;

            String label = (country.equals("-") ? "Undefined" : country) + " (" + String.format("%.1f", percentage) + "%)";
            pieChartData.add(new PieChart.Data(label, requests));
        }

        countryPieChart.setData(pieChartData);
    }

    private Map<String, Integer> loadDataFromFile() {
        Map<String, Integer> data = new HashMap<>();
    
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/data_placeholder/country_requests.txt")))) {
    
            String line = reader.readLine(); // entire line
            if (line != null) {
                String[] entries = line.split(",");
    
                for (String entry : entries) {
                    String[] parts = entry.trim().split("="); // clean and split
                    if (parts.length == 2) {
                        String country = parts[0].trim();
                        int count = Integer.parseInt(parts[1].trim());
                        data.put(country, count);
                    }
                }
            }
        } catch (IOException | NumberFormatException | NullPointerException e) {
            System.err.println("Error reading data: " + e.getMessage());
        }
    
        return data;
    }
    
    // Apply timestamp filter — placeholder for now
    @FXML
    private void onApplyFilterButton() {
        String fromTime = normalizeTimeInput(fromTimeField.getText());
        String toTime = normalizeTimeInput(toTimeField.getText());

        System.out.println("Date Range: " + fromDatePicker.getValue() + " to " + toDatePicker.getValue());
        System.out.println("Time Range: " + fromTime + " to " + toTime);
    }

    // Ensures time format is HH:mm:ss with fallback values
    private String normalizeTimeInput(String input) {
        if (input == null || input.trim().isEmpty()) return "00:00:00";

        String[] parts = input.trim().split(":");
        String hour = "00", minute = "00", second = "00";

        try {
            if (parts.length >= 1) hour = String.format("%02d", Integer.parseInt(parts[0]));
            if (parts.length >= 2) minute = String.format("%02d", Integer.parseInt(parts[1]));
            if (parts.length == 3) second = String.format("%02d", Integer.parseInt(parts[2]));
        } catch (NumberFormatException e) {
            System.out.println("Invalid time format. Defaulting to 00:00:00.");
        }

        return hour + ":" + minute + ":" + second;
    }
}

