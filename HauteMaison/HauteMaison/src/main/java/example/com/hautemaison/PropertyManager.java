package example.com.hautemaison;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PropertyManager {
    private static ObservableList<Property> propertyList;
    private static Broker currentBroker;

    // Reuse styling constants from Main class
    private static final String PRIMARY_GRADIENT = "linear-gradient(to bottom right, #2c3e50 0%, #34495e 50%, #3d566e 100%)";
    private static final String ACCENT_COLOR = "#d4af37";
    private static final String TEXT_PRIMARY = "#ffffff"; // Changed to white
    private static final String TEXT_LIGHT = "#ffffff";
    private static final String TEXT_SECONDARY = "#d1d5db"; // Lighter gray for subtitles
    private static final String CARD_BACKGROUND = "rgba(255, 255, 255, 0.3)"; // Slightly more opaque
    private static final String INPUT_BACKGROUND = "rgba(255, 255, 255, 0.9)";

    // New style for text in add/edit property forms and property details frame
    private static final String FORM_TEXT_COLOR = "#2c3e50"; // Navy blue
    private static final String DETAILS_TEXT_COLOR = "#2c3e50"; // Navy blue for details frame
    private static final String ALERT_TEXT_COLOR = "#2c3e50"; // Dark color for alert text

    public static void show(Stage stage, Broker broker) {
        currentBroker = broker;
        propertyList = FXCollections.observableArrayList(currentBroker.getProperties());

        StackPane root = new StackPane();
        try {
            Image backgroundImage = new Image(PropertyManager.class.getResourceAsStream("/example/com/hautemaison/background_image_login.png"));
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

        VBox cardContainer = new VBox(20);
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setPadding(new Insets(30, 40, 30, 40));
        cardContainer.setMaxWidth(900);
        cardContainer.setStyle(
            "-fx-background-color: " + CARD_BACKGROUND + ";" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);" +
            "-fx-backdrop-filter: blur(10px);" +
            "-fx-border-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 15;"
        );

        Label headerTitle = new Label("Property Management Dashboard");
        headerTitle.setStyle(
            "-fx-font-size: 24px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        Label subtitle = new Label("Manage your luxury property portfolio with elegance and precision");
        subtitle.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: " + TEXT_SECONDARY + ";" +
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-text-alignment: center;"
        );

        Label brokerUsernameLabel = new Label("Broker: " + currentBroker.getUsername());
        brokerUsernameLabel.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + ACCENT_COLOR + ";" +
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        TableView<Property> table = new TableView<>(propertyList);
        table.setPrefHeight(400);
        table.setStyle(
            "-fx-background-color: " + INPUT_BACKGROUND + ";" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-text-fill: #2c3e50;"
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
        TableColumn<Property, Boolean> activeCol = new TableColumn<>("Active");
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));

        table.getColumns().addAll(typeCol, locCol, priceCol, imageCol, statusCol, activeCol);

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

        ChoiceBox<String> activeStatusFilterBox = new ChoiceBox<>(FXCollections.observableArrayList("All", "Active", "Archived"));
        activeStatusFilterBox.setValue("Active");
        activeStatusFilterBox.setStyle(getInputFieldStyle());
        activeStatusFilterBox.setPrefWidth(130);

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

        HBox filterBox = new HBox(10, searchField, minPriceField, maxPriceField, statusFilterBox, activeStatusFilterBox, sortCriteriaBox, sortOrderBox, filterBtn);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setSpacing(10);

        filterContainer.getChildren().addAll(filterLabel, filterBox);

        Button addBtn = new Button("Add Property");
        addBtn.setStyle(getPrimaryButtonStyle());
        addBtn.setPrefWidth(200);
        addButtonHoverEffect(addBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button editBtn = new Button("Edit Property");
        editBtn.setStyle(getPrimaryButtonStyle());
        editBtn.setPrefWidth(200);
        addButtonHoverEffect(editBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button toggleActiveBtn = new Button("Toggle Active/Archive");
        toggleActiveBtn.setStyle(getPrimaryButtonStyle());
        toggleActiveBtn.setPrefWidth(200);
        addButtonHoverEffect(toggleActiveBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Button backBtn = new Button("Back");
        backBtn.setStyle(getSecondaryButtonStyle());
        backBtn.setPrefWidth(200);
        addButtonHoverEffect(backBtn, getSecondaryButtonStyle(), getSecondaryButtonHoverStyle());

        HBox btnBox = new HBox(15, addBtn, editBtn, toggleActiveBtn, backBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(10));

        cardContainer.getChildren().addAll(headerTitle, subtitle, brokerUsernameLabel, table, filterContainer, btnBox);
        root.getChildren().add(cardContainer);

        addBtn.setOnAction(e -> showPropertyForm(null, stage));
        editBtn.setOnAction(e -> {
            Property selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showPropertyForm(selected, stage);
            } else {
                showElegantAlert("Selection Required", "Please select a property to edit.", Alert.AlertType.WARNING);
            }
        });
        toggleActiveBtn.setOnAction(e -> {
            Property selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String action = selected.isActive() ? "archive" : "activate";
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Action");
                confirmationAlert.setHeaderText(action.equals("archive") ? "Archive Property" : "Activate Property");
                confirmationAlert.setContentText("Are you sure you want to " + action + " this property?");
                confirmationAlert.getDialogPane().setStyle(
                    "-fx-background-color: " + CARD_BACKGROUND + ";" +
                    "-fx-font-family: 'Montserrat', sans-serif;" +
                    "-fx-backdrop-filter: blur(10px);" +
                    "-fx-border-color: rgba(255, 255, 255, 0.5);" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 15;"
                );
                confirmationAlert.getDialogPane().lookupButton(ButtonType.OK).setStyle(getPrimaryButtonStyle());
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    selected.setActive(!selected.isActive());
                    List<Broker> brokers = Database.loadBrokers();
                    if (brokers == null) {
                        brokers = new ArrayList<>();
                    }
                    brokers.removeIf(b -> b.getUsername().equals(currentBroker.getUsername()));
                    brokers.add(currentBroker);
                    Database.saveBrokers(brokers);
                    table.refresh();
                    showElegantAlert("Success", "Property " + (selected.isActive() ? "activated" : "archived") + " successfully.", Alert.AlertType.INFORMATION);
                }
            } else {
                showElegantAlert("Selection Required", "Please select a property to toggle its active status.", Alert.AlertType.WARNING);
            }
        });
        backBtn.setOnAction(e -> new Main().start(stage));

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
            String selectedActiveStatus = activeStatusFilterBox.getValue();
            String sortCriteria = sortCriteriaBox.getValue();
            String sortOrder = sortOrderBox.getValue();

            ObservableList<Property> filtered = FXCollections.observableArrayList();
            for (Property p : currentBroker.getProperties()) {
                boolean locationMatch = p.getLocation().toLowerCase().contains(location);
                boolean priceMatch = p.getPrice() >= min && p.getPrice() <= max;
                boolean statusMatch = selectedStatus.equals("All") || p.getStatus().equals(selectedStatus);
                boolean activeStatusMatch = false;
                if (selectedActiveStatus.equals("All")) {
                    activeStatusMatch = true;
                } else if (selectedActiveStatus.equals("Active") && p.isActive()) {
                    activeStatusMatch = true;
                } else if (selectedActiveStatus.equals("Archived") && !p.isActive()) {
                    activeStatusMatch = true;
                }

                if (locationMatch && priceMatch && statusMatch && activeStatusMatch) {
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

        filterBtn.fire();

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Haute Maison - Property Management");
        stage.show();
    }

    private static void showPropertyForm(Property toEdit, Stage parentStage) {
        Stage formStage = new Stage();
        formStage.setTitle(toEdit == null ? "Add Property" : "Edit Property");
        formStage.initOwner(parentStage);
        formStage.initModality(Modality.WINDOW_MODAL);

        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle(
            "-fx-background-color: " + CARD_BACKGROUND + ";" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);" +
            "-fx-backdrop-filter: blur(10px);" +
            "-fx-border-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 15;"
        );

        Label formTitle = new Label(toEdit == null ? "Add New Property" : "Edit Property");
        formTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + FORM_TEXT_COLOR + ";" + // Changed to FORM_TEXT_COLOR
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        // Property Type ComboBox
        Label typeLabel = new Label("Property Type:");
        typeLabel.setStyle(getFieldLabelStyleForm()); // New style for form labels
        ComboBox<String> typeComboBox = new ComboBox<>(FXCollections.observableArrayList("House", "Apartment", "Plot"));
        typeComboBox.setStyle(getInputFieldStyle());
        typeComboBox.setPrefWidth(200);
        typeComboBox.setPromptText("Select Property Type");

        // Common fields
        Label locationLabel = new Label("Location:");
        locationLabel.setStyle(getFieldLabelStyleForm());
        TextField locationField = new TextField();
        locationField.setPromptText("Location");
        locationField.setStyle(getInputFieldStyle());

        Label priceCroreLabel = new Label("Price (Crore):");
        priceCroreLabel.setStyle(getFieldLabelStyleForm());
        TextField priceCroreField = new TextField();
        priceCroreField.setPromptText("Crore");
        priceCroreField.setStyle(getInputFieldStyle());
        priceCroreField.setPrefWidth(100);

        Label priceLacLabel = new Label("Price (Lac):");
        priceLacLabel.setStyle(getFieldLabelStyleForm());
        TextField priceLacField = new TextField();
        priceLacField.setPromptText("Lac");
        priceLacField.setStyle(getInputFieldStyle());
        priceLacField.setPrefWidth(100);

        HBox priceBox = new HBox(10, priceCroreLabel, priceCroreField, priceLacLabel, priceLacField);
        priceBox.setAlignment(Pos.CENTER_LEFT);

        Label facingLabel = new Label("Facing:");
        facingLabel.setStyle(getFieldLabelStyleForm());
        TextField facingField = new TextField();
        facingField.setPromptText("Facing (e.g., North)");
        facingField.setStyle(getInputFieldStyle());

        Label statusLabel = new Label("Status:");
        statusLabel.setStyle(getFieldLabelStyleForm());
        ChoiceBox<String> statusChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Available", "Under Contract", "Sold"));
        statusChoiceBox.setValue("Available");
        statusChoiceBox.setStyle(getInputFieldStyle());

        CheckBox isActiveCheckBox = new CheckBox("Is Active");
        isActiveCheckBox.setStyle(getFieldLabelStyleForm());
        isActiveCheckBox.setSelected(true);

        // House-specific fields
        Label bedroomsLabel = new Label("Number of Bedrooms:");
        bedroomsLabel.setStyle(getFieldLabelStyleForm());
        TextField bedroomsField = new TextField();
        bedroomsField.setPromptText("Number of Bedrooms");
        bedroomsField.setStyle(getInputFieldStyle());

        Label bathroomsLabel = new Label("Number of Bathrooms:");
        bathroomsLabel.setStyle(getFieldLabelStyleForm());
        TextField bathroomsField = new TextField();
        bathroomsField.setPromptText("Number of Bathrooms");
        bathroomsField.setStyle(getInputFieldStyle());

        Label plotWidthLabel = new Label("Plot Width (ft):");
        plotWidthLabel.setStyle(getFieldLabelStyleForm());
        TextField plotWidthField = new TextField();
        plotWidthField.setPromptText("Plot Width");
        plotWidthField.setStyle(getInputFieldStyle());

        Label plotDepthLabel = new Label("Plot Depth (ft):");
        plotDepthLabel.setStyle(getFieldLabelStyleForm());
        TextField plotDepthField = new TextField();
        plotDepthField.setPromptText("Plot Depth");
        plotDepthField.setStyle(getInputFieldStyle());

        CheckBox garageBox = new CheckBox("Has Garage");
        garageBox.setStyle(getFieldLabelStyleForm());

        // Apartment-specific fields
        Label aptBedroomsLabel = new Label("Number of Bedrooms:");
        aptBedroomsLabel.setStyle(getFieldLabelStyleForm());
        TextField aptBedroomsField = new TextField();
        aptBedroomsField.setPromptText("Number of Bedrooms");
        aptBedroomsField.setStyle(getInputFieldStyle());

        Label aptBathroomsLabel = new Label("Number of Bathrooms:");
        aptBathroomsLabel.setStyle(getFieldLabelStyleForm());
        TextField aptBathroomsField = new TextField();
        aptBathroomsField.setPromptText("Number of Bathrooms");
        aptBathroomsField.setStyle(getInputFieldStyle());

        Label floorNumberLabel = new Label("Floor Number:");
        floorNumberLabel.setStyle(getFieldLabelStyleForm());
        TextField floorNumberField = new TextField();
        floorNumberField.setPromptText("Floor Number");
        floorNumberField.setStyle(getInputFieldStyle());

        CheckBox elevatorBox = new CheckBox("Has Elevator Access");
        elevatorBox.setStyle(getFieldLabelStyleForm());

        // Plot-specific fields
        Label plotSizeLabel = new Label("Plot Size (sq ft):");
        plotSizeLabel.setStyle(getFieldLabelStyleForm());
        TextField plotSizeField = new TextField();
        plotSizeField.setPromptText("Plot Size");
        plotSizeField.setStyle(getInputFieldStyle());

        Label plotWidthPlotLabel = new Label("Plot Width (ft):");
        plotWidthPlotLabel.setStyle(getFieldLabelStyleForm());
        TextField plotWidthPlotField = new TextField();
        plotWidthPlotField.setPromptText("Plot Width");
        plotWidthPlotField.setStyle(getInputFieldStyle());

        Label plotDepthPlotLabel = new Label("Plot Depth (ft):");
        plotDepthPlotLabel.setStyle(getFieldLabelStyleForm());
        TextField plotDepthPlotField = new TextField();
        plotDepthPlotField.setPromptText("Plot Depth");
        plotDepthPlotField.setStyle(getInputFieldStyle());

        CheckBox commercialBox = new CheckBox("Is Commercial");
        commercialBox.setStyle(getFieldLabelStyleForm());

        // Common property feature checkboxes
        CheckBox electricBox = new CheckBox("Electric Wiring");
        electricBox.setStyle(getFieldLabelStyleForm());
        CheckBox cornerBox = new CheckBox("Corner");
        cornerBox.setStyle(getFieldLabelStyleForm());
        CheckBox masjidBox = new CheckBox("Near Masjid");
        masjidBox.setStyle(getFieldLabelStyleForm());
        CheckBox marketBox = new CheckBox("Near Market");
        marketBox.setStyle(getFieldLabelStyleForm());
        CheckBox schoolBox = new CheckBox("Near School");
        schoolBox.setStyle(getFieldLabelStyleForm());
        CheckBox hospitalBox = new CheckBox("Near Hospital");
        hospitalBox.setStyle(getFieldLabelStyleForm());
        CheckBox transportBox = new CheckBox("Near Transport");
        transportBox.setStyle(getFieldLabelStyleForm());
        CheckBox parkBox = new CheckBox("Park Facing");
        parkBox.setStyle(getFieldLabelStyleForm());

        // Image upload
        Button uploadBtn = new Button("Upload Images");
        uploadBtn.setStyle(getPrimaryButtonStyle());
        uploadBtn.setPrefWidth(200);
        addButtonHoverEffect(uploadBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        Label imageLabel = new Label("No images selected");
        imageLabel.setStyle(getFieldLabelStyleForm()); // New style for form labels

        List<String> selectedImagePaths = new ArrayList<>();

        uploadBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select Property Images");
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp");
            chooser.getExtensionFilters().add(imageFilter);

            List<File> files = chooser.showOpenMultipleDialog(formStage);
            if (files != null && !files.isEmpty()) {
                selectedImagePaths.clear();
                for (File file : files) {
                    selectedImagePaths.add(file.getAbsolutePath());
                }
                imageLabel.setText(selectedImagePaths.size() + " images selected");
            } else {
                imageLabel.setText("No images selected");
                selectedImagePaths.clear();
            }
        });

        // Save button
        Button saveBtn = new Button("Save");
        saveBtn.setStyle(getPrimaryButtonStyle());
        saveBtn.setPrefWidth(200);
        addButtonHoverEffect(saveBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());

        // Dynamic form panes
        VBox houseFields = new VBox(10, bedroomsLabel, bedroomsField, bathroomsLabel, bathroomsField,
                                  plotWidthLabel, plotWidthField, plotDepthLabel, plotDepthField, garageBox);
        VBox apartmentFields = new VBox(10, aptBedroomsLabel, aptBedroomsField, aptBathroomsLabel, aptBathroomsField,
                                      floorNumberLabel, floorNumberField, elevatorBox);
        VBox plotFields = new VBox(10, plotSizeLabel, plotSizeField, plotWidthPlotLabel, plotWidthPlotField,
                                 plotDepthPlotLabel, plotDepthPlotField, commercialBox);

        // Initially hide all specific fields
        houseFields.setVisible(false);
        apartmentFields.setVisible(false);
        plotFields.setVisible(false);

        // Form layout
        VBox form = new VBox(10,
            formTitle,
            typeLabel, typeComboBox,
            locationLabel, locationField,
            priceBox,
            facingLabel, facingField,
            statusLabel, statusChoiceBox,
            isActiveCheckBox,
            houseFields,
            apartmentFields,
            plotFields,
            electricBox, cornerBox, masjidBox,
            marketBox, schoolBox, hospitalBox,
            transportBox, parkBox,
            uploadBtn, imageLabel,
            saveBtn
        );
        form.setPadding(new Insets(20));
        formContainer.getChildren().add(form);

        // Handle type selection change
        typeComboBox.setOnAction(e -> {
            String selectedType = typeComboBox.getValue();
            houseFields.setVisible("House".equals(selectedType));
            apartmentFields.setVisible("Apartment".equals(selectedType));
            plotFields.setVisible("Plot".equals(selectedType));
        });

        // Populate fields if editing
        if (toEdit != null) {
            typeComboBox.setValue(toEdit.getType() != null ? toEdit.getType() : "House");
            locationField.setText(toEdit.getLocation() != null ? toEdit.getLocation() : "");
            double price = toEdit.getPrice();
            if (price >= 10000000) {
                int crore = (int) (price / 10000000);
                int lac = (int) ((price % 10000000) / 100000);
                priceCroreField.setText(String.valueOf(crore));
                priceLacField.setText(String.valueOf(lac));
            } else if (price >= 100000) {
                priceLacField.setText(String.valueOf((int) (price / 100000)));
            }
            facingField.setText(toEdit.getFacing() != null ? toEdit.getFacing() : "");
            electricBox.setSelected(toEdit.isElectricWiring());
            cornerBox.setSelected(toEdit.isCorner());
            masjidBox.setSelected(toEdit.isNearMasjid());
            marketBox.setSelected(toEdit.isNearMarket());
            schoolBox.setSelected(toEdit.isNearSchool());
            hospitalBox.setSelected(toEdit.isNearHospital());
            transportBox.setSelected(toEdit.isNearTransport());
            parkBox.setSelected(toEdit.isParkFacing());
            if (toEdit.getImagePaths() != null && !toEdit.getImagePaths().isEmpty()) {
                selectedImagePaths.addAll(toEdit.getImagePaths());
                imageLabel.setText(selectedImagePaths.size() + " images selected");
            }
            statusChoiceBox.setValue(toEdit.getStatus() != null ? toEdit.getStatus() : "Available");
            isActiveCheckBox.setSelected(toEdit.isActive());

            // Populate type-specific fields
            if ("House".equals(toEdit.getType())) {
                bedroomsField.setText(toEdit.getNumberOfBedrooms() > 0 ? String.valueOf(toEdit.getNumberOfBedrooms()) : "");
                bathroomsField.setText(toEdit.getNumberOfBathrooms() > 0 ? String.valueOf(toEdit.getNumberOfBathrooms()) : "");
                plotWidthField.setText(toEdit.getPlotWidth() > 0 ? String.valueOf(toEdit.getPlotWidth()) : "");
                plotDepthField.setText(toEdit.getPlotDepth() > 0 ? String.valueOf(toEdit.getPlotDepth()) : "");
                garageBox.setSelected(toEdit.isHasGarage());
                houseFields.setVisible(true);
            } else if ("Apartment".equals(toEdit.getType())) {
                aptBedroomsField.setText(toEdit.getNumberOfBedrooms() > 0 ? String.valueOf(toEdit.getNumberOfBedrooms()) : "");
                aptBathroomsField.setText(toEdit.getNumberOfBathrooms() > 0 ? String.valueOf(toEdit.getNumberOfBathrooms()) : "");
                floorNumberField.setText(toEdit.getFloorNumber() > 0 ? String.valueOf(toEdit.getFloorNumber()) : "");
                elevatorBox.setSelected(toEdit.isHasElevatorAccess());
                apartmentFields.setVisible(true);
            } else if ("Plot".equals(toEdit.getType())) {
                plotSizeField.setText(toEdit.getSize() > 0 ? String.valueOf(toEdit.getSize()) : "");
                plotWidthPlotField.setText(toEdit.getPlotWidth() > 0 ? String.valueOf(toEdit.getPlotWidth()) : "");
                plotDepthPlotField.setText(toEdit.getPlotDepth() > 0 ? String.valueOf(toEdit.getPlotDepth()) : "");
                commercialBox.setSelected(toEdit.isCommercial());
                plotFields.setVisible(true);
            }
        } else {
            typeComboBox.setValue("House");
            houseFields.setVisible(true);
        }

        saveBtn.setOnAction(e -> {
            try {
                String type = typeComboBox.getValue();
                String location = locationField.getText().trim();
                String facing = facingField.getText().trim();

                if (type == null || location.isEmpty() || facing.isEmpty()) {
                    showElegantAlert("Input Required", "Please select a property type and fill in Location and Facing.", Alert.AlertType.ERROR);
                    return;
                }

                double price = 0;
                try {
                    int crore = priceCroreField.getText().trim().isEmpty() ? 0 : Integer.parseInt(priceCroreField.getText().trim());
                    int lac = priceLacField.getText().trim().isEmpty() ? 0 : Integer.parseInt(priceLacField.getText().trim());
                    price = crore * 10000000 + lac * 100000;
                } catch (NumberFormatException ex) {
                    showElegantAlert("Invalid Input", "Invalid price format. Please enter numeric values for Crore and Lac.", Alert.AlertType.ERROR);
                    return;
                }

                Property p = (toEdit != null) ? toEdit : new Property();
                p.setType(type);
                p.setLocation(location);
                p.setPrice(price);
                p.setFacing(facing);
                p.setElectricWiring(electricBox.isSelected());
                p.setCorner(cornerBox.isSelected());
                p.setNearMasjid(masjidBox.isSelected());
                p.setNearMarket(marketBox.isSelected());
                p.setNearSchool(schoolBox.isSelected());
                p.setNearHospital(hospitalBox.isSelected());
                p.setNearTransport(transportBox.isSelected());
                p.setParkFacing(parkBox.isSelected());
                p.setImagePaths(new ArrayList<>(selectedImagePaths));
                p.setStatus(statusChoiceBox.getValue());
                p.setActive(isActiveCheckBox.isSelected());

                // Handle type-specific fields
                if ("House".equals(type)) {
                    try {
                        int bedrooms = bedroomsField.getText().trim().isEmpty() ? 0 : Integer.parseInt(bedroomsField.getText().trim());
                        int bathrooms = bathroomsField.getText().trim().isEmpty() ? 0 : Integer.parseInt(bathroomsField.getText().trim());
                        double plotWidth = plotWidthField.getText().trim().isEmpty() ? 0 : Double.parseDouble(plotWidthField.getText().trim());
                        double plotDepth = plotDepthField.getText().trim().isEmpty() ? 0 : Double.parseDouble(plotDepthField.getText().trim());
                        p.setNumberOfBedrooms(bedrooms);
                        p.setNumberOfBathrooms(bathrooms);
                        p.setPlotWidth(plotWidth);
                        p.setPlotDepth(plotDepth);
                        p.setHasGarage(garageBox.isSelected());
                    } catch (NumberFormatException ex) {
                        showElegantAlert("Invalid Input", "Invalid format for Bedrooms, Bathrooms, Plot Width, or Plot Depth. Please enter numeric values.", Alert.AlertType.ERROR);
                        return;
                    }
                } else if ("Apartment".equals(type)) {
                    try {
                        int bedrooms = aptBedroomsField.getText().trim().isEmpty() ? 0 : Integer.parseInt(aptBedroomsField.getText().trim());
                        int bathrooms = aptBathroomsField.getText().trim().isEmpty() ? 0 : Integer.parseInt(aptBathroomsField.getText().trim());
                        int floorNumber = floorNumberField.getText().trim().isEmpty() ? 0 : Integer.parseInt(floorNumberField.getText().trim());
                        p.setNumberOfBedrooms(bedrooms);
                        p.setNumberOfBathrooms(bathrooms);
                        p.setFloorNumber(floorNumber);
                        p.setHasElevatorAccess(elevatorBox.isSelected());
                    } catch (NumberFormatException ex) {
                        showElegantAlert("Invalid Input", "Invalid format for Bedrooms, Bathrooms, or Floor Number. Please enter numeric values.", Alert.AlertType.ERROR);
                        return;
                    }
                } else if ("Plot".equals(type)) {
                    try {
                        double plotSize = plotSizeField.getText().trim().isEmpty() ? 0 : Double.parseDouble(plotSizeField.getText().trim());
                        double plotWidth = plotWidthPlotField.getText().trim().isEmpty() ? 0 : Double.parseDouble(plotWidthPlotField.getText().trim());
                        double plotDepth = plotDepthPlotField.getText().trim().isEmpty() ? 0 : Double.parseDouble(plotDepthPlotField.getText().trim());
                        p.setSize(plotSize);
                        p.setPlotWidth(plotWidth);
                        p.setPlotDepth(plotDepth);
                        p.setCommercial(commercialBox.isSelected());
                    } catch (NumberFormatException ex) {
                        showElegantAlert("Invalid Input", "Invalid format for Plot Size, Plot Width, or Plot Depth. Please enter numeric values.", Alert.AlertType.ERROR);
                        return;
                    }
                }

                if (toEdit == null) {
                    currentBroker.addProperty(p);
                    propertyList.add(p);
                }

                List<Broker> brokers = Database.loadBrokers();
                if (brokers == null) {
                    brokers = new ArrayList<>();
                }
                brokers.removeIf(b -> b.getUsername().equals(currentBroker.getUsername()));
                brokers.add(currentBroker);
                Database.saveBrokers(brokers);
                showElegantAlert("Success", "Property saved successfully!", Alert.AlertType.INFORMATION);
                formStage.close();
            } catch (Exception ex) {
                showElegantAlert("Error", "An unexpected error occurred while saving the property: " + ex.getMessage(), Alert.AlertType.ERROR);
                ex.printStackTrace();
            }
        });

        StackPane formRoot = new StackPane(formContainer);
        formRoot.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
        formRoot.setPadding(new Insets(50));
        Scene scene = new Scene(new ScrollPane(formRoot), 450, 700);
        formStage.setScene(scene);
        formStage.show();
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
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);" +
            "-fx-backdrop-filter: blur(10px);" +
            "-fx-border-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 15;"
        );

        Label detailsTitle = new Label("Property Details");
        detailsTitle.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + DETAILS_TEXT_COLOR + ";" + // Changed to DETAILS_TEXT_COLOR
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        VBox detailsLayout = new VBox(10);
        detailsLayout.setPadding(new Insets(10));

        Label typeLabel = new Label("Type: " + (property.getType() != null ? property.getType() : "N/A"));
        typeLabel.setStyle(getFieldLabelStyleDetails()); // New style for details labels
        Label locationLabel = new Label("Location: " + (property.getLocation() != null ? property.getLocation() : "N/A"));
        locationLabel.setStyle(getFieldLabelStyleDetails());
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
        priceLabel.setStyle(getFieldLabelStyleDetails());
        Label facingLabel = new Label("Facing: " + (property.getFacing() != null ? property.getFacing() : "N/A"));
        facingLabel.setStyle(getFieldLabelStyleDetails());
        Label statusLabel = new Label("Status: " + (property.getStatus() != null ? property.getStatus() : "N/A"));
        statusLabel.setStyle(getFieldLabelStyleDetails());
        Label activeLabel = new Label("Active: " + (property.isActive() ? "Yes" : "No"));
        activeLabel.setStyle(getFieldLabelStyleDetails());

        // Type-specific details
        if ("House".equals(property.getType())) {
            Label bedroomsLabel = new Label("Bedrooms: " + (property.getNumberOfBedrooms() > 0 ? property.getNumberOfBedrooms() : "N/A"));
            bedroomsLabel.setStyle(getFieldLabelStyleDetails());
            Label bathroomsLabel = new Label("Bathrooms: " + (property.getNumberOfBathrooms() > 0 ? property.getNumberOfBathrooms() : "N/A"));
            bathroomsLabel.setStyle(getFieldLabelStyleDetails());
            Label plotWidthLabel = new Label("Plot Width: " + (property.getPlotWidth() > 0 ? property.getPlotWidth() + " ft" : "N/A"));
            plotWidthLabel.setStyle(getFieldLabelStyleDetails());
            Label plotDepthLabel = new Label("Plot Depth: " + (property.getPlotDepth() > 0 ? property.getPlotDepth() + " ft" : "N/A"));
            plotDepthLabel.setStyle(getFieldLabelStyleDetails());
            Label garageLabel = new Label("Has Garage: " + (property.isHasGarage() ? "Yes" : "No"));
            garageLabel.setStyle(getFieldLabelStyleDetails());
            detailsLayout.getChildren().addAll(bedroomsLabel, bathroomsLabel, plotWidthLabel, plotDepthLabel, garageLabel);
        } else if ("Apartment".equals(property.getType())) {
            Label bedroomsLabel = new Label("Bedrooms: " + (property.getNumberOfBedrooms() > 0 ? property.getNumberOfBedrooms() : "N/A"));
            bedroomsLabel.setStyle(getFieldLabelStyleDetails());
            Label bathroomsLabel = new Label("Bathrooms: " + (property.getNumberOfBathrooms() > 0 ? property.getNumberOfBathrooms() : "N/A"));
            bathroomsLabel.setStyle(getFieldLabelStyleDetails());
            Label floorNumberLabel = new Label("Floor Number: " + (property.getFloorNumber() > 0 ? property.getFloorNumber() : "N/A"));
            floorNumberLabel.setStyle(getFieldLabelStyleDetails());
            Label elevatorLabel = new Label("Has Elevator Access: " + (property.isHasElevatorAccess() ? "Yes" : "No"));
            elevatorLabel.setStyle(getFieldLabelStyleDetails());
            detailsLayout.getChildren().addAll(bedroomsLabel, bathroomsLabel, floorNumberLabel, elevatorLabel);
        } else if ("Plot".equals(property.getType())) {
            Label plotSizeLabel = new Label("Plot Size: " + (property.getSize() > 0 ? property.getSize() + " sq ft" : "N/A"));
            plotSizeLabel.setStyle(getFieldLabelStyleDetails());
            Label plotWidthLabel = new Label("Plot Width: " + (property.getPlotWidth() > 0 ? property.getPlotWidth() + " ft" : "N/A"));
            plotWidthLabel.setStyle(getFieldLabelStyleDetails());
            Label plotDepthLabel = new Label("Plot Depth: " + (property.getPlotDepth() > 0 ? property.getPlotDepth() + " ft" : "N/A"));
            plotDepthLabel.setStyle(getFieldLabelStyleDetails());
            Label commercialLabel = new Label("Is Commercial: " + (property.isCommercial() ? "Yes" : "No"));
            commercialLabel.setStyle(getFieldLabelStyleDetails());
            detailsLayout.getChildren().addAll(plotSizeLabel, plotWidthLabel, plotDepthLabel, commercialLabel);
        }

        Label electricLabel = new Label("Electric Wiring: " + (property.isElectricWiring() ? "Yes" : "No"));
        electricLabel.setStyle(getFieldLabelStyleDetails());
        Label cornerLabel = new Label("Corner: " + (property.isCorner() ? "Yes" : "No"));
        cornerLabel.setStyle(getFieldLabelStyleDetails());
        Label masjidLabel = new Label("Near Masjid: " + (property.isNearMasjid() ? "Yes" : "No"));
        masjidLabel.setStyle(getFieldLabelStyleDetails());
        Label marketLabel = new Label("Near Market: " + (property.isNearMarket() ? "Yes" : "No"));
        marketLabel.setStyle(getFieldLabelStyleDetails());
        Label schoolLabel = new Label("Near School: " + (property.isNearSchool() ? "Yes" : "No"));
        schoolLabel.setStyle(getFieldLabelStyleDetails());
        Label hospitalLabel = new Label("Near Hospital: " + (property.isNearHospital() ? "Yes" : "No"));
        hospitalLabel.setStyle(getFieldLabelStyleDetails());
        Label transportLabel = new Label("Near Transport: " + (property.isNearTransport() ? "Yes" : "No"));
        transportLabel.setStyle(getFieldLabelStyleDetails());
        Label parkLabel = new Label("Park Facing: " + (property.isParkFacing() ? "Yes" : "No"));
        parkLabel.setStyle(getFieldLabelStyleDetails());

        detailsLayout.getChildren().addAll(
            typeLabel, locationLabel, priceLabel, facingLabel,
            electricLabel, cornerLabel, masjidLabel, marketLabel, schoolLabel,
            hospitalLabel, transportLabel, parkLabel, statusLabel, activeLabel
        );

        if (property.getImagePaths() != null && !property.getImagePaths().isEmpty()) {
            Label imageLabel = new Label("Property Images:");
            imageLabel.setStyle(getFieldLabelStyleDetails());
            detailsLayout.getChildren().add(imageLabel);
            HBox imageGallery = new HBox(5);
            imageGallery.setPadding(new Insets(5, 0, 5, 0));
            imageGallery.setAlignment(Pos.CENTER_LEFT);
            for (String imagePath : property.getImagePaths()) {
                try {
                    Image image = new Image("file:" + imagePath);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setPreserveRatio(true);
                    imageGallery.getChildren().add(imageView);
                } catch (Exception e) {
                    Label errorLabel = new Label("Image not found: " + new File(imagePath).getName());
                    errorLabel.setStyle(getFieldLabelStyleDetails());
                    imageGallery.getChildren().add(errorLabel);
                }
            }
            detailsLayout.getChildren().add(new ScrollPane(imageGallery));
        } else {
            Label noImageLabel = new Label("No images available for this property.");
            noImageLabel.setStyle(getFieldLabelStyleDetails());
            detailsLayout.getChildren().add(noImageLabel);
        }

        Button closeBtn = new Button("Close");
        closeBtn.setStyle(getPrimaryButtonStyle());
        closeBtn.setPrefWidth(200);
        addButtonHoverEffect(closeBtn, getPrimaryButtonStyle(), getPrimaryButtonHoverStyle());
        closeBtn.setOnAction(e -> detailsStage.close());

        detailsContainer.getChildren().addAll(detailsTitle, detailsLayout, closeBtn);

        StackPane detailsRoot = new StackPane(detailsContainer);
        detailsRoot.setStyle("-fx-background: " + PRIMARY_GRADIENT + ";");
        detailsRoot.setPadding(new Insets(50));
        Scene scene = new Scene(new ScrollPane(detailsRoot), 450, 700);
        detailsStage.setScene(scene);
        detailsStage.show();
    }

    private static String getFieldLabelStyle() {
        return "-fx-font-size: 12px;" +
               "-fx-font-weight: bold;" +
               "-fx-text-fill: " + TEXT_PRIMARY + ";" +
               "-fx-font-family: 'Montserrat', sans-serif;";
    }

    // Method for labels in the add/edit property form
    private static String getFieldLabelStyleForm() {
        return "-fx-font-size: 12px;" +
               "-fx-font-weight: bold;" +
               "-fx-text-fill: " + FORM_TEXT_COLOR + ";" + // Use FORM_TEXT_COLOR
               "-fx-font-family: 'Montserrat', sans-serif;";
    }

    // New method for labels in the property details frame
    private static String getFieldLabelStyleDetails() {
        return "-fx-font-size: 12px;" +
               "-fx-font-weight: bold;" +
               "-fx-text-fill: " + DETAILS_TEXT_COLOR + ";" + // Use DETAILS_TEXT_COLOR
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
               "-fx-font-family: 'Montserrat', sans-serif;" +
               "-fx-text-fill: #2c3e50;";
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
               "-fx-text-fill: #2c3e50;" + // Dark text for contrast on white background
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
            "-fx-font-family: 'Montserrat', sans-serif;" +
            "-fx-backdrop-filter: blur(10px);" +
            "-fx-border-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 15;"
        );

        // Update text color in alert
        dialogPane.lookup(".content.label").setStyle(
            "-fx-text-fill: " + ALERT_TEXT_COLOR + ";" + // Changed to ALERT_TEXT_COLOR for readability
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Montserrat', sans-serif;"
        );

        dialogPane.lookupButton(ButtonType.OK).setStyle(getPrimaryButtonStyle());
        alert.showAndWait();
    }
}