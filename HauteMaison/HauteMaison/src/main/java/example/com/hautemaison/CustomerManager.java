package example.com.hautemaison;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerManager {
    private static ObservableList<Property> propertyList;
    private static List<Property> favoriteProperties;

    // Reuse styling constants from Main class
    private static final String PRIMARY_GRADIENT = "linear-gradient(to bottom right, #2c3e50 0%, #34495e 50%, #3d566e 100%)";
    private static final String ACCENT_COLOR = "#d4af37";
    private static final String TEXT_PRIMARY = "#2c3e50";
    private static final String TEXT_LIGHT = "#ffffff";
    private static final String CARD_BACKGROUND = "rgba(255, 255, 255, 0.95)";
    private static final String INPUT_BACKGROUND = "rgba(255, 255, 255, 0.9)";

    // Custom CSS to override Modena stylesheet conflicts
    private static final String CUSTOM_CSS =
            ".scroll-bar:vertical .increment-button .increment-arrow, " +
                    ".scroll-bar:vertical .decrement-button .decrement-arrow, " +
                    ".scroll-bar:horizontal .increment-button .increment-arrow, " +
                    ".scroll-bar:horizontal .decrement-button .decrement-arrow {" +
                    "    -fx-background-color: #2c3e50;" +
                    "    -fx-effect: none;" +
                    "}" +
                    ".scroll-bar:vertical, .scroll-bar:horizontal {" +
                    "    -fx-background-color: " + INPUT_BACKGROUND + ";" +
                    "}" +
                    ".label {" +
                    "    -fx-text-fill: " + TEXT_PRIMARY + ";" +
                    "}" +
                    ".table-view .column-header .label {" +
                    "    -fx-text-fill: " + TEXT_LIGHT + ";" +
                    "    -fx-font-family: 'Montserrat', sans-serif;" +
                    "}" +
                    ".table-view .column-header {" +
                    "    -fx-background-color: " + ACCENT_COLOR + ";" +
                    "}";

    public static void show(Stage stage) {
        // Initialize favorites list
        favoriteProperties = new ArrayList<>();

        // Load all properties from all brokers
        List<Broker> brokers = Database.loadBrokers();
        List<Property> allProperties = new ArrayList<>();
        if (brokers != null) {
            for (Broker broker : brokers) {
                allProperties.addAll(broker.getProperties());
            }
        }
        propertyList = FXCollections.observableArrayList(allProperties);

        StackPane root = new StackPane();
        root.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
        root.setPadding(new Insets(50));

        VBox cardContainer = new VBox(20);
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setPadding(new Insets(30, 40, 30, 40));
        cardContainer.setMaxWidth(900);
        cardContainer.setStyle(
                "-fx-background-color: " + CARD_BACKGROUND + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);"
        );

        Label headerTitle = new Label("Customer Property Explorer");
        headerTitle.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Montserrat', sans-serif;"
        );

        Label subtitle = new Label("Discover luxury properties with elegance and ease");
        subtitle.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #7f8c8d;" +
                        "-fx-font-family: 'Montserrat', sans-serif;" +
                        "-fx-text-alignment: center;"
        );

        TableView<Property> table = new TableView<>(propertyList);
        table.setPrefHeight(400);
        table.setStyle(
                "-fx-background-color: " + INPUT_BACKGROUND + ";" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-font-family: 'Montserrat', sans-serif;"
        );

        TableColumn<Property, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Property, String> locCol = new TableColumn<>("Location");
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumn<Property, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> {
            Property p = cellData.getValue();
            double price = p.getPrice();
            if (price >= 10000000) {
                int crore = (int) (price / 10000000);
                int lac = (int) ((price % 10000000) / 100000);
                return new SimpleStringProperty(crore + " Cr" + (lac > 0 ? " " + lac + " Lac" : ""));
            } else if (price >= 100000) {
                int lac = (int) (price / 100000);
                return new SimpleStringProperty(lac + " Lac");
            } else {
                return new SimpleStringProperty(String.format("%.2f", price));
            }
        });
        TableColumn<Property, String> imageCol = new TableColumn<>("Images");
        imageCol.setCellValueFactory(cellData -> {
            Property property = cellData.getValue();
            List<String> paths = property.getImagePaths();
            if (paths != null && !paths.isEmpty()) {
                if (paths.size() == 1) {
                    File file = new File(paths.get(0));
                    return new SimpleStringProperty(file.getName());
                } else {
                    return new SimpleStringProperty(paths.size() + " Images");
                }
            } else {
                return new SimpleStringProperty("No Image");
            }
        });
        TableColumn<Property, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(typeCol, locCol, priceCol, imageCol, statusCol);

        table.setRowFactory(tv -> {
            TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Property rowData = row.getItem();
                    showPropertyDetails(rowData, stage);
                }
            });
            return row;
        });

        VBox filterContainer = new VBox(15);
        filterContainer.setAlignment(Pos.CENTER);
        filterContainer.setPadding(new Insets(10));
        filterContainer.setStyle("-fx-background-color: " + CARD_BACKGROUND + ";");

        Label filterLabel = new Label("Filter & Sort Properties");
        filterLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Montserrat', sans-serif;"
        );

        TextField searchField = new TextField();
        searchField.setPromptText("Search by location...");
        searchField.setStyle(getInputFieldStyle());
        searchField.setPrefWidth(150);

        TextField minPriceField = new TextField();
        minPriceField.setPromptText("Min Price (Lac)");
        minPriceField.setStyle(getInputFieldStyle());
        minPriceField.setPrefWidth(100);

        TextField maxPriceField = new TextField();
        maxPriceField.setPromptText("Max Price (Lac)");
        maxPriceField.setStyle(getInputFieldStyle());
        maxPriceField.setPrefWidth(100);

        ChoiceBox<String> statusFilterBox = new ChoiceBox<>(FXCollections.observableArrayList("All", "Available", "Under Contract", "Sold"));
        statusFilterBox.setValue("All");
        statusFilterBox.setStyle(getInputFieldStyle());
        statusFilterBox.setPrefWidth(130);

        ChoiceBox<String> sortCriteriaBox = new ChoiceBox<>(FXCollections.observableArrayList("None", "Price", "Size", "Location", "Type"));
        sortCriteriaBox.setValue("None");
        sortCriteriaBox.setStyle(getInputFieldStyle());
        sortCriteriaBox.setPrefWidth(120);

        ChoiceBox<String> sortOrderBox = new ChoiceBox<>(FXCollections.observableArrayList("Ascending", "Descending"));
        sortOrderBox.setValue("Ascending");
        sortOrderBox.setStyle(getInputFieldStyle());
        sortOrderBox.setPrefWidth(120);

        Button filterBtn = new Button("Apply Filters & Sort");
        filterBtn.setStyle(getPrimaryButtonStyle());
        filterBtn.setPrefWidth(180);
        addButtonHoverEffect(filterBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        HBox filterBox = new HBox(10, searchField, minPriceField, maxPriceField, statusFilterBox, sortCriteriaBox, sortOrderBox, filterBtn);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setSpacing(10);
        filterBox.setStyle("-fx-background-color: " + CARD_BACKGROUND + ";");

        filterContainer.getChildren().addAll(filterLabel, filterBox);

        Button favoriteBtn = new Button("Add to Favorites");
        favoriteBtn.setStyle(getPrimaryButtonStyle());
        favoriteBtn.setPrefWidth(200);
        addButtonHoverEffect(favoriteBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button viewFavoritesBtn = new Button("View Favorites");
        viewFavoritesBtn.setStyle(getPrimaryButtonStyle());
        viewFavoritesBtn.setPrefWidth(200);
        addButtonHoverEffect(viewFavoritesBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button contactBtn = new Button("Contact Broker");
        contactBtn.setStyle(getPrimaryButtonStyle());
        contactBtn.setPrefWidth(200);
        addButtonHoverEffect(contactBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button backBtn = new Button("Back to Login");
        backBtn.setStyle(getSecondaryButtonStyle());
        backBtn.setPrefWidth(200);
        addButtonHoverEffect(backBtn, getSecondaryButtonStyle(), getSecondaryButtonHoverStyle());

        HBox btnBox = new HBox(15, favoriteBtn, viewFavoritesBtn, contactBtn, backBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(10));
        btnBox.setStyle("-fx-background-color: " + CARD_BACKGROUND + ";");

        cardContainer.getChildren().addAll(headerTitle, subtitle, table, filterContainer, btnBox);
        root.getChildren().add(cardContainer);

        filterBtn.setOnAction(e -> {
            String location = searchField.getText().toLowerCase().trim();
            double min = 0;
            double max = Double.MAX_VALUE;
            try {
                if (!minPriceField.getText().trim().isEmpty()) {
                    min = Double.parseDouble(minPriceField.getText().trim()) * 100000;
                }
                if (!maxPriceField.getText().trim().isEmpty()) {
                    max = Double.parseDouble(maxPriceField.getText().trim()) * 100000;
                }
            } catch (NumberFormatException ex) {
                showElegantAlert("Invalid Input", "Invalid price format. Please enter numeric values for price.", Alert.AlertType.ERROR);
                return;
            }

            String selectedStatus = statusFilterBox.getValue();
            String sortCriteria = sortCriteriaBox.getValue();
            String sortOrder = sortOrderBox.getValue();

            ObservableList<Property> filtered = FXCollections.observableArrayList();
            for (Property p : allProperties) {
                boolean locationMatch = p.getLocation().toLowerCase().contains(location);
                boolean priceMatch = p.getPrice() >= min && p.getPrice() <= max;
                boolean statusMatch = selectedStatus.equals("All") || p.getStatus().equals(selectedStatus);

                if (locationMatch && priceMatch && statusMatch) {
                    filtered.add(p);
                }
            }

            if (!sortCriteria.equals("None")) {
                Comparator<Property> comparator = null;
                switch (sortCriteria) {
                    case "Price":
                        comparator = Comparator.comparing(Property::getPrice);
                        break;
                    case "Size":
                        comparator = Comparator.comparing(Property::getSize);
                        break;
                    case "Location":
                        comparator = Comparator.comparing(Property::getLocation);
                        break;
                    case "Type":
                        comparator = Comparator.comparing(Property::getType);
                        break;
                }

                if (comparator != null) {
                    if (sortOrder.equals("Descending")) {
                        comparator = comparator.reversed();
                    }
                    FXCollections.sort(filtered, comparator);
                }
            }

            table.setItems(filtered);
        });

        favoriteBtn.setOnAction(e -> {
            Property selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (!favoriteProperties.contains(selected)) {
                    favoriteProperties.add(selected);
                    showElegantAlert("Success", "Property added to favorites!", Alert.AlertType.INFORMATION);
                } else {
                    showElegantAlert("Duplicate", "This property is already in your favorites.", Alert.AlertType.WARNING);
                }
            } else {
                showElegantAlert("Selection Required", "Please select a property to add to favorites.", Alert.AlertType.WARNING);
            }
        });

        viewFavoritesBtn.setOnAction(e -> {
            showFavorites(stage);
        });

        contactBtn.setOnAction(e -> {
            Property selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showContactForm(selected, stage);
            } else {
                showElegantAlert("Selection Required", "Please select a property to contact the broker.", Alert.AlertType.WARNING);
            }
        });

        backBtn.setOnAction(e -> new Main().showLoginScene(stage));

        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add("data:text/css," + CUSTOM_CSS.replaceAll("\n", ""));
        stage.setScene(scene);
        stage.setTitle("Haute Maison - Customer Property Explorer");
        stage.show();

        filterBtn.fire();
    }

    private static void showFavorites(Stage parentStage) {
        Stage favoritesStage = new Stage();
        favoritesStage.setTitle("Favorite Properties");
        favoritesStage.initOwner(parentStage);
        favoritesStage.initModality(Modality.WINDOW_MODAL);

        VBox cardContainer = new VBox(20);
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setPadding(new Insets(30, 40, 30, 40));
        cardContainer.setStyle(
                "-fx-background-color: " + CARD_BACKGROUND + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);"
        );

        Label title = new Label("Favorite Properties");
        title.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Montserrat', sans-serif;"
        );

        TableView<Property> table = new TableView<>(FXCollections.observableArrayList(favoriteProperties));
        table.setPrefHeight(400);
        table.setStyle(
                "-fx-background-color: " + INPUT_BACKGROUND + ";" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-font-family: 'Montserrat', sans-serif;"
        );

        TableColumn<Property, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Property, String> locCol = new TableColumn<>("Location");
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumn<Property, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> {
            Property p = cellData.getValue();
            double price = p.getPrice();
            if (price >= 10000000) {
                int crore = (int) (price / 10000000);
                int lac = (int) ((price % 10000000) / 100000);
                return new SimpleStringProperty(crore + " Cr" + (lac > 0 ? " " + lac + " Lac" : ""));
            } else if (price >= 100000) {
                int lac = (int) (price / 100000);
                return new SimpleStringProperty(lac + " Lac");
            } else {
                return new SimpleStringProperty(String.format("%.2f", price));
            }
        });
        TableColumn<Property, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(typeCol, locCol, priceCol, statusCol);

        table.setRowFactory(tv -> {
            TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Property rowData = row.getItem();
                    showPropertyDetails(rowData, favoritesStage);
                }
            });
            return row;
        });

        Button removeBtn = new Button("Remove from Favorites");
        removeBtn.setStyle(getPrimaryButtonStyle());
        removeBtn.setPrefWidth(200);
        addButtonHoverEffect(removeBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button closeBtn = new Button("Close");
        closeBtn.setStyle(getSecondaryButtonStyle());
        closeBtn.setPrefWidth(200);
        addButtonHoverEffect(closeBtn, getSecondaryButtonStyle(), getSecondaryButtonHoverStyle());

        HBox btnBox = new HBox(15, removeBtn, closeBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(10));
        btnBox.setStyle("-fx-background-color: " + CARD_BACKGROUND + ";");

        cardContainer.getChildren().addAll(title, table, btnBox);

        removeBtn.setOnAction(e -> {
            Property selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                favoriteProperties.remove(selected);
                table.setItems(FXCollections.observableArrayList(favoriteProperties));
                showElegantAlert("Success", "Property removed from favorites!", Alert.AlertType.INFORMATION);
            } else {
                showElegantAlert("Selection Required", "Please select a property to remove from favorites.", Alert.AlertType.WARNING);
            }
        });

        closeBtn.setOnAction(e -> favoritesStage.close());

        StackPane root = new StackPane(cardContainer);
        root.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
        root.setPadding(new Insets(50));
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("data:text/css," + CUSTOM_CSS.replaceAll("\n", ""));
        favoritesStage.setScene(scene);
        favoritesStage.show();
    }

    private static void showContactForm(Property property, Stage parentStage) {
        Stage contactStage = new Stage();
        contactStage.setTitle("Contact Broker");
        contactStage.initOwner(parentStage);
        contactStage.initModality(Modality.WINDOW_MODAL);

        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle(
                "-fx-background-color: " + CARD_BACKGROUND + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);"
        );

        Label formTitle = new Label("Contact Broker for " + property.getLocation());
        formTitle.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Montserrat', sans-serif;"
        );

        Label nameLabel = new Label("Your Name:");
        nameLabel.setStyle(getFieldLabelStyle());
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setStyle(getInputFieldStyle());

        Label emailLabel = new Label("Your Email:");
        emailLabel.setStyle(getFieldLabelStyle());
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle(getInputFieldStyle());

        Label messageLabel = new Label("Message:");
        messageLabel.setStyle(getFieldLabelStyle());
        TextArea messageField = new TextArea();
        messageField.setPromptText("Enter your inquiry");
        messageField.setStyle(getInputFieldStyle());
        messageField.setPrefRowCount(5);

        Button sendBtn = new Button("Send Inquiry");
        sendBtn.setStyle(getPrimaryButtonStyle());
        sendBtn.setPrefWidth(200);
        addButtonHoverEffect(sendBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(getSecondaryButtonStyle());
        cancelBtn.setPrefWidth(200);
        addButtonHoverEffect(cancelBtn, getSecondaryButtonStyle(), getSecondaryButtonHoverStyle());

        HBox btnBox = new HBox(15, sendBtn, cancelBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setStyle("-fx-background-color: " + CARD_BACKGROUND + ";");

        formContainer.getChildren().addAll(formTitle, nameLabel, nameField, emailLabel, emailField, messageLabel, messageField, btnBox);

        sendBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String message = messageField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                showElegantAlert("Input Required", "Please fill in all fields.", Alert.AlertType.ERROR);
                return;
            }

            // Simulate sending inquiry
            showElegantAlert("Success", "Your inquiry has been sent for the property at " + property.getLocation() + "!", Alert.AlertType.INFORMATION);
            contactStage.close();
        });

        cancelBtn.setOnAction(e -> contactStage.close());

        StackPane formRoot = new StackPane(formContainer);
        formRoot.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
        formRoot.setPadding(new Insets(50));
        Scene scene = new Scene(formRoot, 400, 500);
        scene.getStylesheets().add("data:text/css," + CUSTOM_CSS.replaceAll("\n", ""));
        contactStage.setScene(scene);
        contactStage.show();
    }

    private static void showPropertyDetails(Property property, Stage parentStage) {
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Property Details");
        detailsStage.initOwner(parentStage);
        detailsStage.initModality(Modality.WINDOW_MODAL);

        VBox detailsContainer = new VBox(15);
        detailsContainer.setPadding(new Insets(30));
        detailsContainer.setMaxWidth(350);
        detailsContainer.setStyle(
                "-fx-background-color: " + CARD_BACKGROUND + ";" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);"
        );

        Label detailsTitle = new Label("Property Details");
        detailsTitle.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-font-family: 'Montserrat', sans-serif;"
        );

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10));
        detailsLayout.setStyle("-fx-background-color: " + CARD_BACKGROUND + ";");

        Label typeLabel = new Label("Type: " + (property.getType() != null ? property.getType() : "N/A"));
        typeLabel.setStyle(getFieldLabelStyle());
        Label locationLabel = new Label("Location: " + (property.getLocation() != null ? property.getLocation() : "N/A"));
        locationLabel.setStyle(getFieldLabelStyle());
        String priceText;
        double price = property.getPrice();
        if (price >= 10000000) {
            int crore = (int) (price / 10000000);
            int lac = (int) ((price % 10000000) / 100000);
            priceText = crore + " Cr" + (lac > 0 ? " " + lac + " Lac" : "");
        } else if (price >= 100000) {
            int lac = (int) (price / 100000);
            priceText = lac + " Lac";
        } else {
            priceText = "N/A";
        }
        Label priceLabel = new Label("Price: " + priceText);
        priceLabel.setStyle(getFieldLabelStyle());
        Label facingLabel = new Label("Facing: " + (property.getFacing() != null ? property.getFacing() : "N/A"));
        facingLabel.setStyle(getFieldLabelStyle());
        Label statusLabel = new Label("Status: " + (property.getStatus() != null ? property.getStatus() : "N/A"));
        statusLabel.setStyle(getFieldLabelStyle());

        if ("House".equals(property.getType())) {
            Label bedroomsLabel = new Label("Bedrooms: " + (property.getNumberOfBedrooms() > 0 ? property.getNumberOfBedrooms() : "N/A"));
            bedroomsLabel.setStyle(getFieldLabelStyle());
            Label bathroomsLabel = new Label("Bathrooms: " + (property.getNumberOfBathrooms() > 0 ? property.getNumberOfBathrooms() : "N/A"));
            bathroomsLabel.setStyle(getFieldLabelStyle());
            Label plotWidthLabel = new Label("Plot Width: " + (property.getPlotWidth() > 0 ? property.getPlotWidth() + " ft" : "N/A"));
            plotWidthLabel.setStyle(getFieldLabelStyle());
            Label plotDepthLabel = new Label("Plot Depth: " + (property.getPlotDepth() > 0 ? property.getPlotDepth() + " ft" : "N/A"));
            plotDepthLabel.setStyle(getFieldLabelStyle());
            Label garageLabel = new Label("Has Garage: " + (property.isHasGarage() ? "Yes" : "No"));
            garageLabel.setStyle(getFieldLabelStyle());
            detailsLayout.getChildren().addAll(bedroomsLabel, bathroomsLabel, plotWidthLabel, plotDepthLabel, garageLabel);
        } else if ("Apartment".equals(property.getType())) {
            Label bedroomsLabel = new Label("Bedrooms: " + (property.getNumberOfBedrooms() > 0 ? property.getNumberOfBedrooms() : "N/A"));
            bedroomsLabel.setStyle(getFieldLabelStyle());
            Label bathroomsLabel = new Label("Bathrooms: " + (property.getNumberOfBathrooms() > 0 ? property.getNumberOfBathrooms() : "N/A"));
            bathroomsLabel.setStyle(getFieldLabelStyle());
            Label floorNumberLabel = new Label("Floor Number: " + (property.getFloorNumber() > 0 ? property.getFloorNumber() : "N/A"));
            floorNumberLabel.setStyle(getFieldLabelStyle());
            Label elevatorLabel = new Label("Has Elevator Access: " + (property.isHasElevatorAccess() ? "Yes" : "No"));
            elevatorLabel.setStyle(getFieldLabelStyle());
            detailsLayout.getChildren().addAll(bedroomsLabel, bathroomsLabel, floorNumberLabel, elevatorLabel);
        } else if ("Plot".equals(property.getType())) {
            Label plotSizeLabel = new Label("Plot Size: " + (property.getSize() > 0 ? property.getSize() + " sq ft" : "N/A"));
            plotSizeLabel.setStyle(getFieldLabelStyle());
            Label plotWidthLabel = new Label("Plot Width: " + (property.getPlotWidth() > 0 ? property.getPlotWidth() + " ft" : "N/A"));
            plotWidthLabel.setStyle(getFieldLabelStyle());
            Label plotDepthLabel = new Label("Plot Depth: " + (property.getPlotDepth() > 0 ? property.getPlotDepth() + " ft" : "N/A"));
            plotDepthLabel.setStyle(getFieldLabelStyle());
            Label commercialLabel = new Label("Is Commercial: " + (property.isCommercial() ? "Yes" : "No"));
            commercialLabel.setStyle(getFieldLabelStyle());
            detailsLayout.getChildren().addAll(plotSizeLabel, plotWidthLabel, plotDepthLabel, commercialLabel);
        }

        Label electricLabel = new Label("Electric Wiring: " + (property.isElectricWiring() ? "Yes" : "No"));
        electricLabel.setStyle(getFieldLabelStyle());
        Label cornerLabel = new Label("Corner: " + (property.isCorner() ? "Yes" : "No"));
        cornerLabel.setStyle(getFieldLabelStyle());
        Label masjidLabel = new Label("Near Masjid: " + (property.isNearMasjid() ? "Yes" : "No"));
        masjidLabel.setStyle(getFieldLabelStyle());
        Label marketLabel = new Label("Near Market: " + (property.isNearMarket() ? "Yes" : "No"));
        marketLabel.setStyle(getFieldLabelStyle());
        Label schoolLabel = new Label("Near School: " + (property.isNearSchool() ? "Yes" : "No"));
        schoolLabel.setStyle(getFieldLabelStyle());
        Label hospitalLabel = new Label("Near Hospital: " + (property.isNearHospital() ? "Yes" : "No"));
        hospitalLabel.setStyle(getFieldLabelStyle());
        Label transportLabel = new Label("Near Transport: " + (property.isNearTransport() ? "Yes" : "No"));
        transportLabel.setStyle(getFieldLabelStyle());
        Label parkLabel = new Label("Park Facing: " + (property.isParkFacing() ? "Yes" : "No"));
        parkLabel.setStyle(getFieldLabelStyle());

        detailsLayout.getChildren().addAll(
                typeLabel, locationLabel, priceLabel, facingLabel,
                electricLabel, cornerLabel, masjidLabel, marketLabel, schoolLabel,
                hospitalLabel, transportLabel, parkLabel, statusLabel
        );

        if (property.getImagePaths() != null && !property.getImagePaths().isEmpty()) {
            Label imageLabel = new Label("Property Images:");
            imageLabel.setStyle(getFieldLabelStyle());
            detailsLayout.getChildren().add(imageLabel);
            HBox imageGallery = new HBox(5);
            imageGallery.setPadding(new Insets(5, 0, 5, 0));
            imageGallery.setAlignment(Pos.CENTER);
            imageGallery.setStyle("-fx-background-color: " + CARD_BACKGROUND + ";");
            for (String imagePath : property.getImagePaths()) {
                try {
                    Image image = new Image("file:" + imagePath);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setPreserveRatio(true);
                    imageGallery.getChildren().add(imageView);
                } catch (Exception ex) {
                    Label errorLabel = new Label("Invalid Image: " + new File(imagePath).getName());
                    errorLabel.setStyle(getFieldLabelStyle());
                    imageGallery.getChildren().add(errorLabel);
                }
            }
            detailsLayout.getChildren().add(new ScrollPane(imageGallery));
        } else {
            Label noImageLabel = new Label("No Images available for property.");
            noImageLabel.setStyle(getFieldLabelStyle());
            detailsLayout.getChildren().add(noImageLabel);
        }

        Button closeBtn = new Button("Close");
        closeBtn.setStyle(getPrimaryButtonStyle());
        closeBtn.setPrefWidth(100);
        addButtonHoverEffect(closeBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());
        closeBtn.setOnAction(e -> detailsStage.close());

        detailsContainer.getChildren().addAll(detailsTitle, detailsLayout, closeBtn);

        StackPane detailsRoot = new StackPane(detailsContainer);
        detailsRoot.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
        detailsRoot.setPadding(new Insets(50));
        Scene scene = new Scene(new ScrollPane(detailsRoot), 450, 600);
        scene.getStylesheets().add("data:text/css," + CUSTOM_CSS.replaceAll("\n", ""));
        detailsStage.setScene(scene);
        detailsStage.show();
    }

    private static String getFieldLabelStyle() {
        return "-fx-font-size: 12px;" +
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
                "-fx-padding: 12;" +
                "-fx-font-size: 14px;" +
                "-fx-pref-height: 40;" +
                "-fx-font-family: 'Montserrat', sans-serif;";
    }

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

    private static String getPrimaryButtonHoverStyle() {
        return "-fx-background-color: derive(" + ACCENT_COLOR + ", -10%);" +
                "-fx-text-fill: " + TEXT_LIGHT + ";" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-border-radius: 8;" +
                "-fx-padding: 12;" +
                "-fx-font-size: 14px;" +
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
                "-fx-padding: 12;" +
                "-fx-font-size: 14px;" +
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
                "-fx-padding: 12;" +
                "-fx-font-size: 14px;" +
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