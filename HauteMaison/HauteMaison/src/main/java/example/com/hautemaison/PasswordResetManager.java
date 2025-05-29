package example.com.hautemaison;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PasswordResetManager {
    // Elegant Real Estate Color Scheme (copied from Main.java for consistency)
    private static final String PRIMARY_GRADIENT = "linear-gradient(to bottom right, #2c3e50 0%, #34495e 50%, #3d566e 100%)";
    private static final String ACCENT_COLOR = "#d4af37";
    private static final String TEXT_PRIMARY = "#2c3e50";
    private static final String TEXT_LIGHT = "#ffffff";
    private static final String CARD_BACKGROUND = "rgba(255, 255, 255, 0.95)";
    private static final String INPUT_BACKGROUND = "rgba(255, 255, 255, 0.9)";

    public static void showResetPasswordDialog(Stage parentStage, AuthManager authManager) {
        Stage resetStage = new Stage();
        resetStage.setTitle("Reset Password");
        resetStage.initOwner(parentStage);
        resetStage.initModality(Modality.WINDOW_MODAL);

        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(30));
        formContainer.setStyle(
                "-fx-background-color: " + CARD_BACKGROUND + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);"
        );

        Label formTitle = new Label("Reset Your Password");
        formTitle.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Montserrat', sans-serif;"
        );

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle(getFieldLabelStyle());
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle(getInputFieldStyle());
        usernameField.setPrefWidth(250);

        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.setStyle(getFieldLabelStyle());
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter new password");
        newPasswordField.setStyle(getInputFieldStyle());
        newPasswordField.setPrefWidth(250);

        Button resetButton = new Button("Reset Password");
        resetButton.setStyle(getPrimaryButtonStyle());
        resetButton.setPrefWidth(180);
        addButtonHoverEffect(resetButton, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(getSecondaryButtonStyle());
        cancelButton.setPrefWidth(180);
        addButtonHoverEffect(cancelButton, getSecondaryButtonStyle(), getSecondaryButtonHoverStyle());

        HBox buttonBox = new HBox(15, resetButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(formTitle, usernameLabel, usernameField, newPasswordLabel, newPasswordField, buttonBox);

        resetButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String newPassword = newPasswordField.getText().trim();

            if (username.isEmpty() || newPassword.isEmpty()) {
                showElegantAlert("Input Required", "Please enter both username and new password.", Alert.AlertType.WARNING);
                return;
            }

            boolean success = authManager.resetPassword(username, newPassword);
            if (success) {
                showElegantAlert("Success", "Password reset successfully! You can now log in with your new password.", Alert.AlertType.INFORMATION);
                resetStage.close();
            } else {
                showElegantAlert("Error", "Username not found. Please check your username and try again.", Alert.AlertType.ERROR);
            }
        });

        cancelButton.setOnAction(e -> resetStage.close());

        StackPane formRoot = new StackPane(formContainer);
        formRoot.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
        formRoot.setPadding(new Insets(50));
        Scene scene = new Scene(formRoot, 400, 400);
        resetStage.setScene(scene);
        resetStage.show();
    }

    private static String getFieldLabelStyle() {
        return "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-font-family: 'Montserrat', sans-serif;";
    }

    private static String getInputFieldStyle() {
        return "-fx-background-color: " + INPUT_BACKGROUND + ";" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #bdc3c7;" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 1;" +
                "-fx-padding: 14;" +
                "-fx-font-size: 15px;" +
                "-fx-pref-height: 45;" +
                "-fx-font-family: 'Montserrat', sans-serif;";
    }

    private static String getPrimaryButtonStyle() {
        return "-fx-background-color: " + ACCENT_COLOR + ";" +
                "-fx-text-fill: " + TEXT_LIGHT + ";" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-border-radius: 8;" +
                "-fx-padding: 14;" +
                "-fx-font-size: 15px;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
                "-fx-font-family: 'Montserrat', sans-serif;";
    }

    private static String getPrimaryButtonHoverStyle() {
        return "-fx-background-color: derive(" + ACCENT_COLOR + ", -10%);" +
                "-fx-text-fill: " + TEXT_LIGHT + ";" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-border-radius: 8;" +
                "-fx-padding: 14;" +
                "-fx-font-size: 15px;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 3);" +
                "-fx-font-family: 'Montserrat', sans-serif;";
    }

    private static String getSecondaryButtonStyle() {
        return "-fx-background-color: transparent;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: " + TEXT_PRIMARY + ";" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-padding: 14;" +
                "-fx-font-size: 15px;" +
                "-fx-cursor: hand;" +
                "-fx-font-family: 'Montserrat', sans-serif;";
    }

    private static String getSecondaryButtonHoverStyle() {
        return "-fx-background-color: " + TEXT_PRIMARY + ";" +
                "-fx-text-fill: " + TEXT_LIGHT + ";" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: " + TEXT_PRIMARY + ";" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-padding: 14;" +
                "-fx-font-size: 15px;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
                "-fx-font-family: 'Montserrat', sans-serif;";
    }

    private static void addButtonHoverEffect(Button button, String normalStyle, String hoverStyle) {
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(normalStyle));
    }

    private static void showElegantAlert(String title, String message, Alert.AlertType type) {
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