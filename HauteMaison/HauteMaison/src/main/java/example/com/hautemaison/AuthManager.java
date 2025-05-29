package example.com.hautemaison;

import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class AuthManager {
    // Styling constants to match CustomerManager.java
    private static final String CARD_BACKGROUND = "rgba(255, 255, 255, 0.95)";
    private static final String TEXT_PRIMARY = "#2c3e50";
    private static final String ACCENT_COLOR = "#d4af37";
    private static final String TEXT_LIGHT = "#ffffff";

    private static String getPrimaryButtonStyle() {
        return "-fx-background-color: " + ACCENT_COLOR + ";" +
               "-fx-text-fill: " + TEXT_LIGHT + ";" +
               "-fx-font-weight: bold;" +
               "-fx-background-radius: 8;" +
               "-fx-border-radius: 8;" +
               "-fx-padding: 12;" +
               "-fx-font-size: 14px;" +
               "-fx-cursor: hand;" +
               "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
               "-fx-font-family: 'Montserrat', sans-serif;";
    }

    public Broker login(String username, String password) {
        List<Broker> brokers = Database.loadBrokers();
        if (brokers == null) {
            showElegantAlert("Database Error", "Failed to load brokers.", Alert.AlertType.ERROR);
            return null;
        }
        for (Broker broker : brokers) {
            if (broker.getUsername().equals(username) && broker.getPassword().equals(password)) {
                return broker;
            }
        }
        return null;
    }

    public boolean signup(String username, String password) {
        List<Broker> brokers = Database.loadBrokers();
        if (brokers == null) {
            showElegantAlert("Database Error", "Failed to load brokers.", Alert.AlertType.ERROR);
            return false;
        }
        for (Broker broker : brokers) {
            if (broker.getUsername().equals(username)) {
                showElegantAlert("Signup Failed", "Username already exists.", Alert.AlertType.WARNING);
                return false;
            }
        }
        Broker newBroker = new Broker(username, password);
        brokers.add(newBroker);
        try {
            Database.saveBrokers(brokers);
            return true;
        } catch (Exception e) {
            showElegantAlert("Database Error", "Error saving account: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    public boolean resetPassword(String username, String newPassword) {
        List<Broker> brokers = Database.loadBrokers();
        if (brokers == null) {
            showElegantAlert("Database Error", "Failed to load brokers.", Alert.AlertType.ERROR);
            return false;
        }
        for (Broker broker : brokers) {
            if (broker.getUsername().equals(username)) {
                broker.setPassword(newPassword);
                try {
                    Database.saveBrokers(brokers);
                    return true;
                } catch (Exception e) {
                    showElegantAlert("Database Error", "Error saving password: " + e.getMessage(), Alert.AlertType.ERROR);
                    return false;
                }
            }
        }
        showElegantAlert("Not Found", "Username not found.", Alert.AlertType.WARNING);
        return false;
    }

    private void showElegantAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: " + CARD_BACKGROUND + ";" +
                "-fx-font-family: 'Montserrat', sans-serif;"
        );

        dialogPane.lookupButton(ButtonType.OK).setStyle(getPrimaryButtonStyle());
        alert.showAndWait();
    }
}