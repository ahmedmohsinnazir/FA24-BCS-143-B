package example.com.hautemaison;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private AuthManager authManager = new AuthManager();
    private Broker currentBroker;

    // Elegant Real Estate Color Scheme
    private static final String PRIMARY_GRADIENT = "linear-gradient(to bottom right, #2c3e50 0%, #34495e 50%, #3d566e 100%)";
    private static final String ACCENT_COLOR = "#d4af37";
    private static final String TEXT_PRIMARY = "#ffffff"; // Changed to white for visibility
    private static final String TEXT_LIGHT = "#ffffff";
    private static final String TEXT_SECONDARY = "#d1d5db"; // Lighter gray for subtitles
    private static final String CARD_BACKGROUND = "rgba(255, 255, 255, 0.3)"; // Slightly more opaque
    private static final String INPUT_BACKGROUND = "rgba(255, 255, 255, 0.9)";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Haute Maison - Defining luxury, one home at a time");
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        showLoginScene(primaryStage);
    }

    public void showLoginScene(Stage stage) {
        StackPane root = new StackPane();
        // Load the background image from resources
try {
    Image backgroundImage = new Image(getClass().getResourceAsStream("/example/com/hautemaison/background_image_login.png"));
    BackgroundImage bgImage = new BackgroundImage(
        backgroundImage,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
    );
            root.setBackground(new Background(bgImage));
        } catch (Exception e) {
            // Fallback to gradient if image fails to load
            root.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
            showElegantAlert("Image Error", "Failed to load background image. Using default gradient.", Alert.AlertType.WARNING);
        }
        root.setPadding(new Insets(50));

        VBox loginContainer = new VBox(20);
        loginContainer.setAlignment(Pos.CENTER);

        VBox loginCard = new VBox(30);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(50, 60, 50, 60));
        loginCard.setMaxWidth(600);
        loginCard.setStyle(
            "-fx-background-color: " + CARD_BACKGROUND + ";" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);" +
            "-fx-backdrop-filter: blur(10px);" +
            "-fx-border-color: rgba(255, 255, 255, 0.5);" + // More visible border
            "-fx-border-width: 1;" +
            "-fx-border-radius: 15;"
        );

        Label title = new Label("HAUTE MAISON");
        title.setStyle(
            "-fx-font-size: 32px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-letter-spacing: 2px;"
        );

        Label subtitle = new Label("Defining luxury, one home at a time");
        subtitle.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: " + TEXT_SECONDARY + ";" +
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        VBox inputContainer = new VBox(20);
        inputContainer.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle(getFieldLabelStyle());
        usernameLabel.setPadding(new Insets(5, 0, 5, 0));
        TextField usernameField = new TextField();
        usernameField.setStyle(getInputFieldStyle());
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefWidth(300);

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle(getFieldLabelStyle());
        passwordLabel.setPadding(new Insets(5, 0, 5, 0));
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle(getInputFieldStyle());
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(300);

        inputContainer.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);

        HBox buttonContainer = new HBox(20);
        buttonContainer.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Sign In");
        loginButton.setStyle(getPrimaryButtonStyle());
        loginButton.setPrefWidth(180);

        Button signupButton = new Button("Create Account");
        signupButton.setStyle(getSecondaryButtonStyle());
        signupButton.setPrefWidth(180);

        Button customerButton = new Button("View as Customer");
        customerButton.setStyle(getSecondaryButtonStyle());
        customerButton.setPrefWidth(180);

        buttonContainer.getChildren().addAll(loginButton, signupButton, customerButton);

        Button forgotPasswordButton = new Button("Forgot Password?");
        forgotPasswordButton.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + ACCENT_COLOR + ";" +
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-cursor: hand;"
        );
        forgotPasswordButton.setOnMouseEntered(e -> forgotPasswordButton.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: derive(" + ACCENT_COLOR + ", -20%);" +
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-cursor: hand;"
        ));
        forgotPasswordButton.setOnMouseExited(e -> forgotPasswordButton.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + ACCENT_COLOR + ";" +
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-cursor: hand;"
        ));

        loginCard.getChildren().addAll(title, subtitle, inputContainer, buttonContainer);
        loginContainer.getChildren().addAll(loginCard, forgotPasswordButton);
        root.getChildren().add(loginContainer);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                showElegantAlert("Authentication Required", "Please enter both username and password.", Alert.AlertType.WARNING);
                return;
            }
            Broker b = authManager.login(username, password);
            if (b != null) {
                currentBroker = b;
                showDashboard(stage);
            } else {
                showElegantAlert("Authentication Failed", "Invalid credentials. Please try again.", Alert.AlertType.ERROR);
            }
        });

        signupButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                showElegantAlert("Registration Error", "Username and password cannot be empty.", Alert.AlertType.WARNING);
                return;
            }
            boolean created = authManager.signup(username, password);
            if (created) {
                showElegantAlert("Account Created", "Your account has been created successfully!", Alert.AlertType.INFORMATION);
                usernameField.clear();
                passwordField.clear();
            } else {
                showElegantAlert("Registration Failed", "Username already exists. Please choose a different username.", Alert.AlertType.ERROR);
            }
        });

        customerButton.setOnAction(e -> CustomerManager.show(stage));

        forgotPasswordButton.setOnAction(e -> PasswordResetManager.showResetPasswordDialog(stage, authManager));

        addButtonHoverEffect(loginButton, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());
        addButtonHoverEffect(signupButton, getSecondaryButtonStyle(), getSecondaryButtonHoverStyle());
        addButtonHoverEffect(customerButton, getSecondaryButtonStyle(), getSecondaryButtonHoverStyle());

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();
    }

    private void showDashboard(Stage stage) {
        BorderPane root = new BorderPane();
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/example/com/hautemaison/background_image_login.png"));
            BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );
            root.setBackground(new Background(bgImage));
        } catch (Exception e) {
            root.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
            showElegantAlert("Image Error", "Failed to load background image. Using default gradient.", Alert.AlertType.WARNING);
        }
        root.setPadding(new Insets(50));

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);");

        Label headerTitle = new Label("HAUTE MAISON");
        headerTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT_LIGHT + ";" +
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-letter-spacing: 1px;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userInfo = new Label("Welcome, " + currentBroker.getUsername());
        userInfo.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: " + ACCENT_COLOR + ";" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        header.getChildren().addAll(headerTitle, spacer, userInfo);

        VBox contentCard = new VBox(30);
        contentCard.setAlignment(Pos.CENTER);
        contentCard.setPadding(new Insets(50));
        contentCard.setMaxWidth(500);
        contentCard.setStyle(
            "-fx-background-color: " + CARD_BACKGROUND + ";" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);" +
            "-fx-backdrop-filter: blur(10px);" +
            "-fx-border-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 15;"
        );

        Label welcomeMessage = new Label("Property Management Dashboard");
        welcomeMessage.setStyle(
            "-fx-font-size: 24px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        Label description = new Label("Manage your luxury property portfolio with elegance and precision");
        description.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: " + TEXT_SECONDARY + ";" +
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-text-alignment: center;" +
            "-fx-wrap-text: true;"
        );

        Button managePropsButton = new Button("Manage Properties");
        managePropsButton.setStyle(getPrimaryButtonStyle());
        managePropsButton.setPrefWidth(250);
        managePropsButton.setPrefHeight(45);

        Button logoutButton = new Button("Sign Out");
        logoutButton.setStyle(getSecondaryButtonStyle());
        logoutButton.setPrefWidth(250);
        logoutButton.setPrefHeight(45);

        addButtonHoverEffect(managePropsButton, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());
        addButtonHoverEffect(logoutButton, getSecondaryButtonStyle(), getSecondaryButtonHoverStyle());

        contentCard.getChildren().addAll(welcomeMessage, description, managePropsButton, logoutButton);

        StackPane centerContainer = new StackPane(contentCard);
        centerContainer.setPadding(new Insets(30));

        root.setTop(header);
        root.setCenter(centerContainer);

        managePropsButton.setOnAction(e -> PropertyManager.show(stage, currentBroker));
        logoutButton.setOnAction(e -> {
            currentBroker = null;
            showLoginScene(stage);
        });

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();
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
               "-fx-font-family: 'Montserrat', sans-serif;" +
               "-fx-text-fill: #2c3e50;"; // Darker text for input fields
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
               "-fx-text-fill: #2c3e50;" + // Dark text for contrast on white background
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
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-backdrop-filter: blur(10px);" +
            "-fx-border-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 15;"
        );

        // Update text color in alert
        dialogPane.lookup(".content.label").setStyle(
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        dialogPane.lookupButton(ButtonType.OK).setStyle(getPrimaryButtonStyle());
        alert.showAndWait();
    }
}