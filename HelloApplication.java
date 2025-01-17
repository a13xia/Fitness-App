package org.example.fitness_app_fx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;



public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) {
        // Initial Screen
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #f5deb3;"); // Yellowish beige background

        // Title Text
        Text title = new Text("Fitness App");
        title.setFont(Font.font("Arial", 48));
        title.setFill(Color.web("#ffb3b3"));

        // Buttons
        Button btnSignIn = new Button("Sign In");
        Button btnSignUp = new Button("Sign Up");

        // Style buttons
        btnSignIn.setFont(Font.font("Arial", 20));
        btnSignUp.setFont(Font.font("Arial", 20));
        btnSignIn.setStyle("-fx-padding: 10 30 10 30; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
        btnSignUp.setStyle("-fx-padding: 10 30 10 30; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");

        // Layout for Title and Buttons
        VBox layout = new VBox(25); // Spacing between elements
        layout.getChildren().addAll(title, btnSignIn, btnSignUp);
        layout.setStyle("-fx-alignment: center;"); // Center elements in the VBox

        // Add layout to root
        root.getChildren().add(layout);

        // Scene
        Scene mainScene = new Scene(root, 600, 500);
        stage.setTitle("Fitness App");
        stage.setScene(mainScene);
        stage.show();

        // Event handling for "Sign In" button
        btnSignIn.setOnAction(e -> {
            StackPane signInRoot = new StackPane();
            signInRoot.setStyle("-fx-background-color: #f5deb3;"); // Same background color

            // Welcome Text
            Text welcomeText = new Text("Welcome back!");
            welcomeText.setFont(Font.font("Arial", 36));
            welcomeText.setFill(Color.web("#ffb3b3")); // Dark pink

            // Input Fields
            ComboBox<String> roleBox = new ComboBox<>();
            roleBox.getItems().addAll("admin", "user");
            roleBox.setPromptText("Choose your role");

            TextField first_name_field = new TextField();
            first_name_field.setPromptText("First Name");

            TextField last_name_field = new TextField();
            last_name_field.setPromptText("Last Name");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");



            // Sign In Button
            Button signInButton = new Button("Sign In");
            signInButton.setFont(Font.font("Arial", 20));
            signInButton.setStyle("-fx-padding: 10 30 10 30; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");


            // Sign In Button Action
            signInButton.setOnAction(event -> {
                try {
                    DatabaseConnection.connect(); // Establish database connection

                    // Placeholder logic: You should replace this with actual database query and verification
                    String role = roleBox.getValue();
                    String first_name = first_name_field.getText();
                    String last_name = last_name_field.getText();
                    String password = passwordField.getText();

                    if (role == null || first_name.isEmpty() || last_name.isEmpty() || password.isEmpty()) {
                        showAlert("Error", "All fields must be filled!");
                    }
                    else if (!first_name.matches("[a-zA-Z]+") || !last_name.matches("[a-zA-Z]+")) {
                        showAlert("Invalid Input", "Please enter a valid name (letters only, no spaces or special characters).");
                    }
                    else if (first_name.length() > 20 || last_name.length() > 20) {  // Limit input length to 50 characters
                    showAlert("Error", "Input must be no longer than 20 characters.");
                    }
                    else if (password.length() > 8 || !password.matches(".*[0-9].*")) {
                        showAlert("Invalid Password", "Password must be at most 8 characters long and contain only digits.");
                    }
                    else {
                        Person existingPerson = DatabaseConnection.findPersonByNamePasswordAndRole(first_name, last_name, password, role);

                        try {
                            if (existingPerson != null) {

                                if (existingPerson.getRole().equals("user")) {

                                    // Create a new scene for the welcome message and action selection
                                    StackPane actionRoot = new StackPane();
                                    actionRoot.setStyle("-fx-background-color: #f5deb3;"); // Set beige background color

                                    // Welcome Text
                                    Text welcome_user = new Text("Hello " + existingPerson.getFirstName() + ", welcome back!");
                                    welcome_user.setFont(Font.font("Arial", 24));
                                    welcome_user.setFill(Color.web("#ffb3b3")); // Dark pink

                                    // Subtext
                                    Text subText = new Text("We’re excited to help you on your fitness journey! 😊 🎽 🏆\n\n" +
                                            "Let’s get started! Please choose an action:");
                                    subText.setFont(Font.font("Arial", 18));
                                    subText.setFill(Color.DARKGREEN);
                                    subText.setStyle("-fx-text-alignment: center;");

                                    // Top layout (Text)
                                    VBox topLayout = new VBox(10);
                                    topLayout.getChildren().addAll(welcome_user, subText);
                                    topLayout.setAlignment(Pos.TOP_CENTER); // Align to the top
                                    topLayout.setPadding(new Insets(40, 0, 50, 0));  // Add top padding to separate from the top

                                    // Action Buttons

                                    Button btnEditAccount = new Button("1. Edit Your Account");
                                    Button btnViewAccount = new Button("2. View Your Account");
                                    Button btnViewBMI = new Button("3. View Your BMI");
                                    Button btnPersonalizedPlan = new Button("4. Get a Personalized Plan");
                                    Button btnDeleteAccount = new Button("5. Delete Your Account");

// Style Buttons
                                    btnEditAccount.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                                    btnViewAccount.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                                    btnViewBMI.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                                    btnPersonalizedPlan.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                                    btnDeleteAccount.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");

                                    // Create VBox for each column
                                    VBox leftColumn = new VBox(15);
                                    leftColumn.getChildren().addAll(btnEditAccount, btnViewAccount);
                                    leftColumn.setAlignment(Pos.CENTER);  // Center the buttons in the column

                                    VBox rightColumn = new VBox(15);
                                    rightColumn.getChildren().addAll(btnViewBMI, btnPersonalizedPlan);
                                    rightColumn.setAlignment(Pos.CENTER);  // Center the buttons in the column

                                    // Create VBox for the Delete button centered under both columns
                                    VBox deleteButtonColumn = new VBox(15);
                                    deleteButtonColumn.getChildren().add(btnDeleteAccount);
                                    deleteButtonColumn.setAlignment(Pos.CENTER);

                                    // Create HBox to arrange the two columns side by side
                                    HBox actionLayout = new HBox(50); // 50px space between the columns
                                    actionLayout.getChildren().addAll(leftColumn, rightColumn);
                                    actionLayout.setAlignment(Pos.CENTER); // Align the columns to the center
                                    actionLayout.setPadding(new Insets(20, 0, 20, 0)); // Add padding at the bottom

                                    // Create VBox to add the Delete button below the columns
                                    VBox mainActionLayout = new VBox(20);
                                    mainActionLayout.getChildren().addAll(actionLayout, deleteButtonColumn);
                                    mainActionLayout.setStyle("-fx-background-color: #f5deb3;");  // Ensure the background is beige

                                    // Main layout container for everything
                                    VBox mainLayout = new VBox(20);
                                    mainLayout.getChildren().addAll(topLayout, mainActionLayout);
                                    mainLayout.setStyle("-fx-background-color: #f5deb3;");  // Ensure the background is beige

                                    // Apply background color to the scene
                                    Scene actionScene = new Scene(mainLayout, 600, 500);
                                    actionScene.setFill(Color.web("#f5deb3")); // Ensure the scene's background is beige

                                    // Set the scene
                                    stage.setScene(actionScene);

                                    btnEditAccount.setOnAction(event3 -> editAccount(stage, existingPerson));
                                    btnViewAccount.setOnAction(event4 -> viewAccount(stage, existingPerson));
                                    btnViewBMI.setOnAction(event5 -> viewBMI(stage, existingPerson));
                                    btnPersonalizedPlan.setOnAction(event6 -> getPersonalizedPlan(stage, existingPerson));
                                    btnDeleteAccount.setOnAction(event7 -> deleteAccount(stage, existingPerson.getFirstName(), existingPerson.getLastName(), existingPerson.getPassword(), existingPerson.getRole()));

                                }

                                else if (existingPerson.getRole().equals("admin")) {
                                    // Navigate to admin-specific scene
                                    VBox adminPanelLayout = new VBox(20);
                                    adminPanelLayout.setStyle("-fx-background-color: #f5deb3;"); // Set beige background color

                                    // Welcome text for admin
                                    Text adminWelcome = new Text("You have successfully logged into your admin account");
                                    adminWelcome.setFont(Font.font("Arial", FontWeight.BOLD, 28)); // Bigger text
                                    adminWelcome.setFill(Color.web("#C71585")); // Pink color

                                    // Subtext
                                    Text adminSubText = new Text("Choose what you would like to do:");
                                    adminSubText.setFont(Font.font("Arial", 20)); // Smaller text than the welcome message
                                    adminSubText.setFill(Color.DARKGREEN);

                                    // Buttons
                                    Button btnViewAccountInfo = new Button("View Account Info");
                                    Button btnEditUsersInfo = new Button("Edit Users Information");

                                    // Style Buttons
                                    btnViewAccountInfo.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                                    btnEditUsersInfo.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");

                                    // Add elements to layout
                                    adminPanelLayout.getChildren().addAll(adminWelcome, adminSubText, btnViewAccountInfo, btnEditUsersInfo);
                                    adminPanelLayout.setAlignment(Pos.CENTER); // Center all elements

                                    // Create the admin scene and set it
                                    Scene adminScene = new Scene(adminPanelLayout, 800, 450);
                                    adminScene.setFill(Color.web("#f5deb3")); // Ensure the scene's background matches the layout

                                    // Set the scene
                                    stage.setScene(adminScene);

                                    // Event Handling for Buttons (implement these methods as needed)
                                    btnViewAccountInfo.setOnAction(event3 -> viewAccount(stage, existingPerson)); // Replace with your method
                                    btnEditUsersInfo.setOnAction(event5 -> {
                                        System.out.println("Edit Users Info button clicked!"); // Debug log
                                        try {
                                            editUsersInfo();
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            showAlert("Error", "An error occurred: " + ex.getMessage());
                                        }}
                                    );

                                }
                            } else {
                                showAlert("Error", "Incorrect credentials or role!");
                            }

                        } catch (PersonNotFoundError err) {
                            System.out.println(err.getMessage());
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Unable to connect to the database.");
                }
            });





            // Layout for the Sign-In Panel
            VBox signInLayout = new VBox(15);
            signInLayout.getChildren().addAll(welcomeText, roleBox, first_name_field, last_name_field, passwordField, signInButton);
            signInLayout.setStyle("-fx-alignment: center;");

            // Add layout to the sign-in root
            signInRoot.getChildren().add(signInLayout);

            // Scene for the Sign-In Panel
            Scene signInScene = new Scene(signInRoot, 600, 500);
            stage.setScene(signInScene);


        });

        btnSignUp.setOnAction(e -> {
            StackPane signUpRoot = new StackPane();
            signUpRoot.setStyle("-fx-background-color: #f5deb3;"); // Same background color

            // Create Account Text
            Text createAccountText = new Text("Create an Account");
            createAccountText.setFont(Font.font("Arial", 36));
            createAccountText.setFill(Color.web("#e68a8a")); // Dark pink

            // Input Fields
            TextField first_name_field = new TextField();
            first_name_field.setPromptText("First Name");

            TextField last_name_field = new TextField();
            last_name_field.setPromptText("Last Name");

            TextField phone_field = new TextField();
            phone_field.setPromptText("Phone Number (xxx-xxx-xxxx)");

            TextField heightField = new TextField();
            heightField.setPromptText("Height (in cm)");

            TextField weightField = new TextField();
            weightField.setPromptText("Weight (in kg)");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Choose a password");

            PasswordField confirmPasswordField = new PasswordField();
            confirmPasswordField.setPromptText("Confirm your password");


            // Sign Up Button
            Button signUpButton = new Button("Sign Up");
            signUpButton.setFont(Font.font("Arial", 20));
            signUpButton.setStyle("-fx-padding: 10 30 10 30; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");

            // Sign Up Button Action
            signUpButton.setOnAction(event -> {
                try {

                    String first_name = first_name_field.getText();
                    String last_name = last_name_field.getText();
                    String phone_number = phone_field.getText();
                    String height_text = heightField.getText();  // Get the height input from the TextField
                    String weight_text = weightField.getText();
                    String password = passwordField.getText();
                    String confirmPassword = confirmPasswordField.getText();

                    if (first_name.isEmpty() || last_name.isEmpty() || phone_number.isEmpty() || height_text.isEmpty() || weight_text.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        showAlert("Error", "All fields must be filled!");
                    }
                    else if (!first_name.matches("[a-zA-Z]+") || !last_name.matches("[a-zA-Z]+")) {
                        showAlert("Invalid Input", "Please enter a valid name (letters only, no spaces or special characters).");
                    }
                    else if (first_name.length() > 20 || last_name.length() > 20) {  // Limit input length to 50 characters
                        showAlert("Error", "Input must be no longer than 20 characters.");
                    }
                    else if (!phone_number.matches("\\d{3}-\\d{3}-\\d{4}")) {  // Validate phone number format (___-___-____)
                        showAlert("Invalid Phone Number", "Please enter a valid phone number (XXX-XXX-XXXX).");
                    }
                    else if (!height_text.matches("\\d+(\\.\\d+)?")) {  // Validate height (e.g., 180 or 1.8)
                        showAlert("Invalid Height", "Please enter a valid height.");
                    }
                    else if (!weight_text.matches("\\d+(\\.\\d+)?")) {  // Validate weight
                        showAlert("Invalid Weight", "Please enter a valid weight.");
                    }
                    else if (Float.parseFloat(height_text) <= 0 || Float.parseFloat(weight_text) <= 0) {
                        showAlert("Invalid Values", "Height and weight must be positive values.");
                    }
                    else if(Float.parseFloat(weight_text) < 20 || Float.parseFloat(weight_text) > 200){
                        showAlert("Invalid Value", "Please enter a valid weight (between 20 and 200).");
                    }
                    else if(Float.parseFloat(height_text) < 70 || Float.parseFloat(height_text) > 230){
                        showAlert("Invalid Value", "Please enter a valid height (between 70 and 230).");
                    }
                    else if (!password.equals(confirmPassword)) {
                        showAlert("Password Mismatch", "Passwords do not match!");
                    }
                    else if (password.length() > 8 || !password.matches(".*[0-9].*")) {
                        showAlert("Invalid Password", "Password must be at most 8 characters long and contain only digits.");
                    } else {
                        // Check if person already exists
                        Person existingPerson = DatabaseConnection.findPersonByNamePasswordAndRole(first_name, last_name, password, "user");
                        if (existingPerson != null) {
                            showAlert("Account Exists", "An account with this name already exists.");
                        }else {

                            // Parse the height and weight as floats
                            float height = Float.parseFloat(height_text);
                            float weight = Float.parseFloat(weight_text);
                            Person newPerson = new Person(first_name, last_name, height, weight, phone_number, "user", password);
                            DatabaseConnection.insertPerson(newPerson);

                            // Show the menu (reuse the same logic from sign-in)
                            StackPane actionRoot = new StackPane();
                            actionRoot.setStyle("-fx-background-color: #f5deb3;"); // Set beige background color

                            Text welcome_user = new Text("Hello " + first_name + ", welcome to the Fitness App!");
                            welcome_user.setFont(Font.font("Arial", 24));
                            welcome_user.setFill(Color.web("#C71585")); // Dark pink

                            // Subtext
                            Text subText = new Text("We’re excited to help you on your fitness journey! 😊 🎽 🏆\n\n" + "Let’s get started! Please choose an action:");
                            subText.setFont(Font.font("Arial", 18));
                            subText.setFill(Color.DARKGREEN);
                            subText.setStyle("-fx-text-alignment: center;");

                            VBox topLayout = new VBox(10);
                            topLayout.getChildren().addAll(welcome_user, subText);
                            topLayout.setAlignment(Pos.TOP_CENTER);
                            topLayout.setPadding(new Insets(40, 0, 50, 0));

                            Button btnEditAccount = new Button("1. Edit Your Account");
                            Button btnViewAccount = new Button("2. View Your Account");
                            Button btnViewBMI = new Button("3. View Your BMI");
                            Button btnPersonalizedPlan = new Button("4. Get a Personalized Plan");
                            Button btnDeleteAccount = new Button("5. Delete Your Account");

                            btnEditAccount.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                            btnViewAccount.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                            btnViewBMI.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                            btnPersonalizedPlan.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
                            btnDeleteAccount.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");


                            // Create VBox for each column
                            VBox leftColumn = new VBox(15);
                            leftColumn.getChildren().addAll(btnEditAccount, btnViewAccount);
                            leftColumn.setAlignment(Pos.CENTER);  // Center the buttons in the column

                            VBox rightColumn = new VBox(15);
                            rightColumn.getChildren().addAll(btnViewBMI, btnPersonalizedPlan);
                            rightColumn.setAlignment(Pos.CENTER);  // Center the buttons in the column

                            // Create VBox for the Delete button centered under both columns
                            VBox deleteButtonColumn = new VBox(15);
                            deleteButtonColumn.getChildren().add(btnDeleteAccount);
                            deleteButtonColumn.setAlignment(Pos.CENTER);

                            // Create HBox to arrange the two columns side by side
                            HBox actionLayout = new HBox(50); // 50px space between the columns
                            actionLayout.getChildren().addAll(leftColumn, rightColumn);
                            actionLayout.setAlignment(Pos.CENTER); // Align the columns to the center
                            actionLayout.setPadding(new Insets(20, 0, 20, 0)); // Add padding at the bottom

                            // Create VBox to add the Delete button below the columns
                            VBox mainActionLayout = new VBox(20);
                            mainActionLayout.getChildren().addAll(actionLayout, deleteButtonColumn);
                            mainActionLayout.setStyle("-fx-background-color: #f5deb3;");  // Ensure the background is beige

                            // Main layout container for everything
                            VBox mainLayout = new VBox(20);
                            mainLayout.getChildren().addAll(topLayout, mainActionLayout);
                            mainLayout.setStyle("-fx-background-color: #f5deb3;");  // Ensure the background is beige

                            // Apply background color to the scene
                            Scene actionScene = new Scene(mainLayout, 600, 500);
                            actionScene.setFill(Color.web("#f5deb3")); // Ensure the scene's background is beige

                            // Set the scene
                            stage.setScene(actionScene);

                            // Event Handling for Buttons (implement these methods as needed)

                            btnEditAccount.setOnAction(event3 -> editAccount(stage, newPerson));
                            btnViewAccount.setOnAction(event4 -> viewAccount(stage, newPerson));
                            btnViewBMI.setOnAction(event5 -> viewBMI(stage, newPerson));
                            btnPersonalizedPlan.setOnAction(event6 -> getPersonalizedPlan(stage, newPerson));
                            btnDeleteAccount.setOnAction(event7 -> deleteAccount(stage, newPerson.getFirstName(), newPerson.getLastName(), newPerson.getPassword(), newPerson.getRole()));



                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Unable to create account.");
                }
            });

            // Layout for the Sign-Up Panel
            VBox signUpLayout = new VBox(15);
            signUpLayout.getChildren().addAll(
                    createAccountText,
                    first_name_field,
                    last_name_field,
                    phone_field,
                    heightField,
                    weightField,
                    passwordField,
                    confirmPasswordField,
                    signUpButton
            );
            signUpLayout.setStyle("-fx-alignment: center;");

            // Create a ScrollPane to allow scrolling
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(signUpLayout);
            scrollPane.setFitToWidth(true);  // Ensure it adjusts to the screen width

            // Add layout to the sign-up root
            signUpRoot.getChildren().add(signUpLayout);

            // Scene for the Sign-Up Panel
            Scene signUpScene = new Scene(signUpRoot, 600, 500);
            stage.setScene(signUpScene);
        });


    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void editAccount(Stage stage, Person oldPerson) {
        // Create the main layout for the edit section
        VBox editLayout = new VBox(20);
        editLayout.setPadding(new Insets(20));
        editLayout.setAlignment(Pos.CENTER);
        editLayout.setStyle("-fx-background-color: #f5deb3;"); // Light blue background

        // Title
        Text editTitle = new Text("Edit Your Account");
        editTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        editTitle.setFill(Color.DARKBLUE);

        // Subtext
        Text editSubText = new Text("Please choose one of the following options:");
        editSubText.setFont(Font.font("Arial", 18));
        editSubText.setFill(Color.DARKGREEN);

        // Buttons for editing
        Button editWholeButton = new Button("Edit the Whole Account");
        Button editOneThingButton = new Button("Edit One Thing");

        editWholeButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");
        editOneThingButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");

        // Back button
//        Button backButton = new Button("Back");
//        backButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");

        // Add components to the layout
        editLayout.getChildren().addAll(editTitle, editSubText, editWholeButton, editOneThingButton);

        // Set the scene
        Scene editScene = new Scene(editLayout, 600, 500);
        stage.setScene(editScene);

        // Event handling
        editWholeButton.setOnAction(event -> editWholeAccount(stage, oldPerson));  // Navigate to "Edit Whole Account"
        editOneThingButton.setOnAction(event -> editSpecificField(stage, oldPerson));  // Navigate to "Edit One Field"
       // backButton.setOnAction(event -> editAccount(stage, oldPerson));  // Navigate back to the main menu
    }


    private void editWholeAccount(Stage stage, Person oldPerson) {
        // Create a new layout for editing the whole account
        VBox editWholeLayout = new VBox(20);
        editWholeLayout.setPadding(new Insets(20));
        editWholeLayout.setAlignment(Pos.CENTER);
        editWholeLayout.setStyle("-fx-background-color: #f5deb3;"); // Light yellow background

        Text title = new Text("Edit the Whole Account");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKORANGE);

        Text instruction = new Text("Update all fields below:");
        instruction.setFont(Font.font("Arial", 18));
        instruction.setFill(Color.DARKGREEN);

        // Fields for updating account information
        TextField newFirstName = new TextField();
        newFirstName.setPromptText("First Name");

        TextField newLastName = new TextField();
        newLastName.setPromptText("Last Name");

        TextField newPhone = new TextField();
        newPhone.setPromptText("Phone Number (XXX-XXX-XXXX)");

        TextField newHeight = new TextField();
        newHeight.setPromptText("Height (cm)");

        TextField newWeight = new TextField();
        newWeight.setPromptText("Weight (kg)");

        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("Password");

        PasswordField confirmNewPasswordField = new PasswordField();
        confirmNewPasswordField.setPromptText("Confirm your password");


        Button saveButton = new Button("Save Changes");
        Button backButton = new Button("Back");


        saveButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #98fb98; -fx-border-color: green; -fx-border-width: 2;");
        backButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");

        // Add elements to the layout
        editWholeLayout.getChildren().addAll(title, instruction, newFirstName, newLastName, newPhone, newHeight, newWeight, newPassword, confirmNewPasswordField, saveButton, backButton);

        // Set the scene
        Scene editWholeScene = new Scene(editWholeLayout, 600, 500);
        stage.setScene(editWholeScene);

        // Event handling
        saveButton.setOnAction(event -> {

            try {
                // Placeholder logic for Sign-Up
                String first_name = newFirstName.getText();
                String last_name = newLastName.getText();
                String phone_number = newPhone.getText();
                String height_text = newHeight.getText();  // Get the height input from the TextField
                String weight_text = newWeight.getText();
                String password = newPassword.getText();
                String confirmPassword = confirmNewPasswordField.getText();

                if (first_name.isEmpty() || last_name.isEmpty() || phone_number.isEmpty() || height_text.isEmpty() || weight_text.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    showAlert("Error", "All fields must be filled!");
                }
                else if (!first_name.matches("[a-zA-Z]+") || !last_name.matches("[a-zA-Z]+")) {
                    showAlert("Invalid Input", "Please enter a valid name (letters only, no spaces or special characters).");
                }
                else if (first_name.length() > 20 || last_name.length() > 20) {  // Limit input length to 50 characters
                    showAlert("Error", "Input must be no longer than 20 characters.");
                }
                else if (!phone_number.matches("\\d{3}-\\d{3}-\\d{4}")) {  // Validate phone number format (___-___-____)
                    showAlert("Invalid Phone Number", "Please enter a valid phone number (XXX-XXX-XXXX).");
                }
                else if (!height_text.matches("\\d+(\\.\\d+)?")) {  // Validate height (e.g., 180 or 1.8)
                    showAlert("Invalid Height", "Please enter a valid height.");
                }
                else if (!weight_text.matches("\\d+(\\.\\d+)?")) {  // Validate weight
                    showAlert("Invalid Weight", "Please enter a valid weight.");
                }
                else if (Float.parseFloat(height_text) <= 0 || Float.parseFloat(weight_text) <= 0) {
                    showAlert("Invalid Values", "Height and weight must be positive values.");
                }
                else if(Float.parseFloat(weight_text) < 20 || Float.parseFloat(weight_text) > 200){
                    showAlert("Invalid Value", "Please enter a valid weight (between 20 and 200).");
                }
                else if(Float.parseFloat(height_text) < 70 || Float.parseFloat(height_text) > 230){
                    showAlert("Invalid Value", "Please enter a valid height (between 70 and 230).");
                }
                else if (!password.equals(confirmPassword)) {
                    showAlert("Password Mismatch", "Passwords do not match!");
                }
                else if (password.length() > 8 || !password.matches(".*[0-9].*")) {
                    showAlert("Invalid Password", "Password must be at most 8 characters long and contain only digits.");
                } else {
                    // Parse the height and weight as floats
                    float height = Float.parseFloat(height_text);
                    float weight = Float.parseFloat(weight_text);
                    Person newPerson = new Person(first_name, last_name, height, weight, phone_number, oldPerson.getRole(), password);

                    DatabaseConnection.updatePerson(oldPerson, newPerson);
                    // new scene with message  and back to menu button

                    // Create a success message and navigate to the success scene
                    VBox successLayout = new VBox(20);
                    successLayout.setAlignment(Pos.CENTER);
                    Text successMessage = new Text("Account successfully updated!");
                    successMessage.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                    successMessage.setFill(Color.GREEN);
                    Button goBackButton = new Button("Back to Menu");

                    goBackButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #98fb98; -fx-border-color: green; -fx-border-width: 2;");
                    successLayout.getChildren().addAll(successMessage, goBackButton);

                    Scene successScene = new Scene(successLayout, 400, 300);
                    stage.setScene(successScene);

                    // goBackButton.setOnAction(event2 -> returnToMainMenu(stage));  // Navigate back to the main menu

                }

                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert("Error", "An error occurred while updating the account: " + ex.getMessage());
                }

            });
        backButton.setOnAction(event2 -> editAccount(stage, oldPerson));  // Navigate back to the edit section
    }


    private void editSpecificField(Stage stage, Person oldPerson) {
        // Create a layout for selecting which field to edit
        VBox selectFieldLayout = new VBox(20);
        selectFieldLayout.setPadding(new Insets(20));
        selectFieldLayout.setAlignment(Pos.CENTER);
        selectFieldLayout.setStyle("-fx-background-color: #f0f8ff;"); // Light blue background

        Text title = new Text("Edit One Field");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);

        Text instruction = new Text("Select the field you want to edit:");
        instruction.setFont(Font.font("Arial", 18));
        instruction.setFill(Color.DARKGREEN);

        // Buttons for each field
        Button editFirstNameButton = new Button("Edit First Name");
        Button editLastNameButton = new Button("Edit Last Name");
        Button editPhoneButton = new Button("Edit Phone Number");
        Button editHeightButton = new Button("Edit Height");
        Button editWeightButton = new Button("Edit Weight");
        Button editPasswordButton = new Button("Edit Password");
        Button backButton = new Button("Back to Edit Menu");

        // Set button styles
        editFirstNameButton.setStyle("-fx-padding: 10 20; -fx-background-color: #add8e6; -fx-border-color: blue;");
        editLastNameButton.setStyle("-fx-padding: 10 20; -fx-background-color: #add8e6; -fx-border-color: blue;");
        editPhoneButton.setStyle("-fx-padding: 10 20; -fx-background-color: #add8e6; -fx-border-color: blue;");
        editHeightButton.setStyle("-fx-padding: 10 20; -fx-background-color: #add8e6; -fx-border-color: blue;");
        editWeightButton.setStyle("-fx-padding: 10 20; -fx-background-color: #add8e6; -fx-border-color: blue;");
        editPasswordButton.setStyle("-fx-padding: 10 20; -fx-background-color: #add8e6; -fx-border-color: blue;");
        backButton.setStyle("-fx-padding: 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink;");

        // Add elements to the layout
        selectFieldLayout.getChildren().addAll(title, instruction, editFirstNameButton, editLastNameButton, editPhoneButton, editHeightButton, editWeightButton, editPasswordButton, backButton);

        // Set the scene
        Scene selectFieldScene = new Scene(selectFieldLayout, 600, 500);
        stage.setScene(selectFieldScene);

        // Button actions
        editFirstNameButton.setOnAction(event -> editFirstName(stage, oldPerson));
        editLastNameButton.setOnAction(event -> editLastName(stage, oldPerson));
        editPhoneButton.setOnAction(event -> editPhone(stage, oldPerson));
        editHeightButton.setOnAction(event -> editHeight(stage, oldPerson));
        editWeightButton.setOnAction(event -> editWeight(stage, oldPerson));
        editPasswordButton.setOnAction(event -> editPassword(stage, oldPerson));
        backButton.setOnAction(event -> editAccount(stage, oldPerson)); // Navigate back to the edit menu
    }


    private void editFirstName(Stage stage, Person oldPerson) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fffacd;"); // Light yellow background

        Text title = new Text("Edit First Name");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter new first name");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");

        saveButton.setStyle("-fx-padding: 10 20; -fx-background-color: #98fb98; -fx-border-color: green;");
        backButton.setStyle("-fx-padding: 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink;");

        layout.getChildren().addAll(title, firstNameField, saveButton, backButton);

        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);

        // Button actions
        saveButton.setOnAction(event -> {
            String newFirstName = firstNameField.getText();
            if (newFirstName.isEmpty()) {
                showAlert("Error", "First name cannot be empty.");
            } else if (!newFirstName.matches("[a-zA-Z]+")) {
                showAlert("Invalid Input", "First name must only contain letters.");
            } else if (newFirstName.length() > 50) {
                showAlert("Error", "First name must be no longer than 50 characters.");
            } else {
                Person newPerson = new Person(newFirstName, oldPerson.getLastName(), oldPerson.getHeight(), oldPerson.getWeight(), oldPerson.getPhone(), oldPerson.getRole(), oldPerson.getPassword());
                DatabaseConnection.updatePerson(oldPerson, newPerson); // Save to the database
                showAlert("Success", "First name updated successfully!");
                // create a new scene
            }
        });

        backButton.setOnAction(event -> editSpecificField(stage, oldPerson));
    }

private void editLastName(Stage stage, Person oldPerson) {
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.CENTER);
    layout.setStyle("-fx-background-color: #fffacd;"); // Light yellow background

    Text title = new Text("Edit Last Name");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    title.setFill(Color.DARKBLUE);

    TextField lastNameField = new TextField();
    lastNameField.setPromptText("Enter new last name");

    Button saveButton = new Button("Save");
    Button backButton = new Button("Back");

    saveButton.setStyle("-fx-padding: 10 20; -fx-background-color: #98fb98; -fx-border-color: green;");
    backButton.setStyle("-fx-padding: 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink;");

    layout.getChildren().addAll(title, lastNameField, saveButton, backButton);

    Scene scene = new Scene(layout, 600, 500);
    stage.setScene(scene);

    saveButton.setOnAction(event -> {
        String newLastName = lastNameField.getText();
        if (newLastName.isEmpty()) {
            showAlert("Error", "Last name cannot be empty.");
        } else if (!newLastName.matches("[a-zA-Z]+")) {
            showAlert("Invalid Input", "Last name must only contain letters.");
        } else if (newLastName.length() > 20) {
            showAlert("Error", "Last name must be no longer than 20 characters.");
        } else {
            Person newPerson = new Person(oldPerson.getFirstName(), newLastName, oldPerson.getHeight(), oldPerson.getWeight(), oldPerson.getPhone(), oldPerson.getRole(), oldPerson.getPassword());
            DatabaseConnection.updatePerson(oldPerson, newPerson); // Save to the database
            showAlert("Success", "Last name updated successfully!");
            // create a new scene

        }
    });

    backButton.setOnAction(event -> editSpecificField(stage, oldPerson));
}


    private void editPhone(Stage stage, Person oldPerson) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fffacd;");

        Text title = new Text("Edit Phone Number");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter new phone number");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");

        saveButton.setStyle("-fx-padding: 10 20; -fx-background-color: #98fb98; -fx-border-color: green;");
        backButton.setStyle("-fx-padding: 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink;");

        layout.getChildren().addAll(title, phoneField, saveButton, backButton);

        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);

        saveButton.setOnAction(event -> {
            String newPhone = phoneField.getText();
            if (!newPhone.matches("\\d{3}-\\d{3}-\\d{4}")) {  // Validate phone number format (___-___-____)
                showAlert("Invalid Phone Number", "Please enter a valid phone number (XXX-XXX-XXXX).");
            } else {
                Person newPerson = new Person(oldPerson.getFirstName(), oldPerson.getLastName(), oldPerson.getHeight(), oldPerson.getWeight(), newPhone, oldPerson.getRole(), oldPerson.getPassword());
                DatabaseConnection.updatePerson(oldPerson, newPerson); // Save to the database
                showAlert("Success", "Phone updated successfully!");
                // create a new scene

            }
        });

        backButton.setOnAction(event -> editSpecificField(stage, oldPerson));
    }


    private void editHeight(Stage stage, Person oldPerson) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fffacd;");

        Text title = new Text("Edit Height");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);

        TextField heightField = new TextField();
        heightField.setPromptText("Enter new height (in cm)");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");

        saveButton.setStyle("-fx-padding: 10 20; -fx-background-color: #98fb98; -fx-border-color: green;");
        backButton.setStyle("-fx-padding: 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink;");

        layout.getChildren().addAll(title, heightField, saveButton, backButton);

        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);

        saveButton.setOnAction(event -> {
            try {
                String height_text = heightField.getText();  // Get the height input from the TextField

                if (!height_text.matches("\\d+(\\.\\d+)?")) {  // Validate height (e.g., 180 or 1.8)
                    showAlert("Invalid Height", "Please enter a valid height.");
                }
                else if (Float.parseFloat(height_text) <= 0 ) {
                    showAlert("Invalid Value", "Height must be a positive value.");
                }
                else if(Float.parseFloat(height_text) < 70 || Float.parseFloat(height_text) > 230){
                    showAlert("Invalid Value", "Please enter a valid height (between 70 and 230).");
                }
                 else {
                     Float newHeight = Float.parseFloat(height_text);
                    Person newPerson = new Person(oldPerson.getFirstName(), oldPerson.getLastName(), newHeight, oldPerson.getWeight(), oldPerson.getPhone(), oldPerson.getRole(), oldPerson.getPassword());
                    DatabaseConnection.updatePerson(oldPerson, newPerson); // Save to the database
                    showAlert("Success", "Height updated successfully!");
                    // create a new scene

                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid number.");
            }
        });

        backButton.setOnAction(event -> editSpecificField(stage, oldPerson));
    }



    private void editWeight(Stage stage, Person oldPerson) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fffacd;");

        Text title = new Text("Edit Weight");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);

        TextField weightField = new TextField();
        weightField.setPromptText("Enter new weight (in kg)");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");

        saveButton.setStyle("-fx-padding: 10 20; -fx-background-color: #98fb98; -fx-border-color: green;");
        backButton.setStyle("-fx-padding: 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink;");

        layout.getChildren().addAll(title, weightField, saveButton, backButton);

        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);

        saveButton.setOnAction(event -> {
            try {
                String weight_text = weightField.getText();
                if (!weight_text.matches("\\d+(\\.\\d+)?")) {  // Validate weight
                    showAlert("Invalid Weight", "Please enter a valid weight.");
                }else if (Float.parseFloat(weight_text) <= 0 ) {
                    showAlert("Invalid Value", "Weight must be a positive value.");
                }
                else if(Float.parseFloat(weight_text) < 20 || Float.parseFloat(weight_text) > 200){
                    showAlert("Invalid Value", "Please enter a valid weight (between 20 and 200).");
                }
                 else {
                     Float newWeight = Float.parseFloat(weight_text);
                    Person newPerson = new Person(oldPerson.getFirstName(), oldPerson.getLastName(), oldPerson.getHeight(), newWeight, oldPerson.getPhone(), oldPerson.getRole(), oldPerson.getPassword());
                    DatabaseConnection.updatePerson(oldPerson, newPerson); // Save to the database
                    showAlert("Success", "Weight updated successfully!");
                    // create a new scene
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid number.");
            }
        });

        backButton.setOnAction(event -> editSpecificField(stage, oldPerson));
    }


    private void editPassword(Stage stage, Person oldPerson) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fffacd;");

        Text title = new Text("Edit Password");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter new password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm new password");

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");

        saveButton.setStyle("-fx-padding: 10 20; -fx-background-color: #98fb98; -fx-border-color: green;");
        backButton.setStyle("-fx-padding: 10 20; -fx-background-color: #ffcccc; -fx-border-color: pink;");

        layout.getChildren().addAll(title, passwordField, confirmPasswordField, saveButton, backButton);

        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);

        saveButton.setOnAction(event -> {
            String newPassword = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();


            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                showAlert("Error", "Password fields cannot be empty.");
            }else if (newPassword.length() > 8 || !newPassword.matches(".*[0-9].*")) {
                showAlert("Invalid Password", "Password must be at most 8 characters long and contain only digits.");
            }
            else if (!newPassword.equals(confirmPassword)) {
                showAlert("Error", "Passwords do not match.");
            } else {
                Person newPerson = new Person(oldPerson.getFirstName(), oldPerson.getLastName(), oldPerson.getHeight(), oldPerson.getWeight(), oldPerson.getPhone(), oldPerson.getRole(), newPassword);
                DatabaseConnection.updatePerson(oldPerson, newPerson); // Save to the database
                showAlert("Success", "Weight updated successfully!");

            }
        });

        backButton.setOnAction(event -> editSpecificField(stage, oldPerson));
    }




    private void viewAccount(Stage stage, Person person) {
        // Create a VBox for layout
        VBox root = new VBox(30); // Increased spacing between rows
        root.setStyle("-fx-background-color: #f5deb3; -fx-padding: 40;"); // Beige background with padding
        root.setAlignment(Pos.TOP_CENTER);

        // Create a title for the scene
        Text title = new Text("Your Account");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36)); // Bigger font size
        title.setFill(Color.HOTPINK); // Pink color
        title.setTextAlignment(TextAlignment.CENTER);

        // Format the person's details with extra spacing
        String accountDetails = String.format(
                "First Name: %s\n\nLast Name: %s\n\nWeight: %.2f kg\n\nHeight: %.2f m\n\nPhone: %s\n\nRole: %s\n\nPassword: %s",
                person.getFirstName(), person.getLastName(), person.getWeight(),
                person.getHeight(), person.getPhone(), person.getRole(), person.getPassword()
        );

        // Create a text node for the details
        Text details = new Text(accountDetails);
        details.setFont(Font.font("Arial", 20)); // Larger font size for readability
        details.setFill(Color.HOTPINK); // Pink color
        details.setTextAlignment(TextAlignment.CENTER);

        // Add title and details to the VBox
        root.getChildren().addAll(title, details);

        // Create a new scene and set it on the stage
        Scene scene = new Scene(root, 700, 600); // Increased scene size for better layout
        stage.setScene(scene);
    }



    private void viewBMI(Stage stage, Person person) {
        // Create a VBox for layout
        VBox root = new VBox(30); // Increased spacing between elements
        root.setStyle("-fx-background-color: #f5deb3; -fx-padding: 40;"); // Beige background with padding
        root.setAlignment(Pos.TOP_CENTER);

        // Create a title for the scene
        Text title = new Text("Your BMI");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36)); // Bigger font size for the title
        title.setFill(Color.HOTPINK); // Hot pink color for the title
        title.setTextAlignment(TextAlignment.CENTER);

        // Calculate BMI (Weight / (Height * Height))
        float bmi = person.BMI();
        String bmiCategory = person.getRecommendation();

        // Format the BMI details
        String bmiDetails = String.format(
                "BMI: %.2f\n\nWeight: %.2f kg\n\nHeight: %.2f m\n\nCategory: %s",
                bmi, person.getWeight(), person.getHeight(), bmiCategory
        );

        // Create a text node for the BMI details
        Text bmiInfo = new Text(bmiDetails);
        bmiInfo.setFont(Font.font("Arial", 20)); // Font size for BMI details
        bmiInfo.setFill(Color.HOTPINK); // Hot pink color for the text
        bmiInfo.setTextAlignment(TextAlignment.CENTER);

        // Create a VBox to hold the title and BMI details with proper spacing
        VBox layout = new VBox(30); // Spacing of 30 pixels between elements
        layout.setAlignment(Pos.CENTER); // Center align all elements
        layout.setPadding(new Insets(20)); // Padding around the VBox
        layout.setStyle("-fx-background-color: #f5deb3;"); // Beige background
        layout.getChildren().addAll(title, bmiInfo);

        // Create the scene and set it to the stage
        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);
    }



    private void getPersonalizedPlan(Stage stage, Person person) {
        // Create root StackPane with beige background
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #f5deb3;"); // Beige background

        // Create and style the main title "First, tell us your weight goal..."
        Text title = new Text("First, tell us your weight goal...");
        title.setFont(Font.font("Arial", 32));
        title.setFill(Color.web("#C71585"));

        // Create the text showing the ideal weight range and current weight
        float idealMinWeight = person.computeHealthyWeightLowerLimit(person.getHeight());
        float idealMaxWeight = person.computeHealthyWeightHigherLimit(person.getHeight());
        float currentWeight = person.getWeight();

        Text weightInfo = new Text(String.format("The ideal weight for you is between %.1f and %.1f kg. You currently have %.1f kg.",
                idealMinWeight, idealMaxWeight, currentWeight));
        weightInfo.setFont(Font.font("Arial", 14));
        weightInfo.setFill(Color.DARKGREEN);

        // Create a TextField for the user to input their weight goal
        TextField weightGoalField = new TextField();
        weightGoalField.setPromptText("Enter your weight goal...");
        weightGoalField.setStyle("-fx-padding: 8; -fx-font-size: 14px; -fx-pref-width: 250px;");

        // Create small text under the TextField
        Text weightNote = new Text("If you don't want to change your weight, write your current weight.");
        weightNote.setFont(Font.font("Arial", 10));
        weightNote.setFill(Color.DARKGREEN);

        // Create "Get Plan" button
        Button getPlanButton = new Button("Get Plan");
        getPlanButton.setStyle("-fx-font-size: 16; -fx-background-color: pink;");


        // Set action for the "Get Plan" button
        getPlanButton.setOnAction(e -> {
            String weightText = weightGoalField.getText();

            // Check if the input is empty or invalid
            if (weightText.isEmpty()) {
                showAlert("Error", "Please fill in the field!");
            } else if (!weightText.matches("\\d+(\\.\\d+)?")) {  // Validate if it's a valid number
                showAlert("Invalid Weight", "Please enter a valid weight.");
            } else if (Float.parseFloat(weightText) <= 0) {
                showAlert("Invalid Value", "Weight must be a positive value.");
            } else if (Float.parseFloat(weightText) < 20 || Float.parseFloat(weightText) > 200) {
                showAlert("Invalid Value", "Please enter a weight between 20kg and 200kg.");
            } else {
                try {

                    Float weight = Float.parseFloat(weightText);
                    Diet diet = new Diet("D", 1000); // Empty Diet object to be populated
                    ExercisePlan exercise = new ExercisePlan("E", "M", 20); // Empty ExercisePlan object to be populated

                    Float weight_difference;

                    if(person.getWeight() > weight)
                        weight_difference = person.getWeight() - weight;
                    else
                        weight_difference =  weight - person.getWeight();

                    // Call the function to populate the objects
                    DatabaseConnection.getPlanForPerson(person, diet, exercise, weight_difference);

                    // Create a new scene to display the personalized plan
                    StackPane newRoot = new StackPane();
                    newRoot.setStyle("-fx-background-color: #f5deb3;"); // Beige background

                    // Create text components for the details
                    Text difficultyText = new Text("Difficulty level to achieve your goal: " + exercise.getDifficultyLevel());
                    difficultyText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                    difficultyText.setFill(Color.DARKMAGENTA);

                    Text dietText = new Text("Diet plan recommendation:\n" + diet.getDescription() + "\nRecommended Calories: " + diet.getCalories());
                    dietText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                    dietText.setFill(Color.DARKMAGENTA);

                    Text exerciseText = new Text("Exercise plan recommendation:\n" + exercise.getName() + "\nDuration: " + exercise.getDurationInMinutes() + " minutes");
                    exerciseText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                    exerciseText.setFill(Color.DARKMAGENTA);

                    // Organize components in a VBox
                    VBox contentBox = new VBox(20, difficultyText, dietText, exerciseText); // Add spacing
                    contentBox.setAlignment(Pos.CENTER);

                    newRoot.getChildren().add(contentBox);

                    // Create and set the scene
                    Scene newScene = new Scene(newRoot, 600, 500);
                    stage.setScene(newScene); // Switch to the new scene
                } catch (Exception e6) {
                    // Handle any other unexpected errors
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Unexpected Error");
                    alert.setHeaderText("An error occurred");
                    alert.setContentText("Details: " + e6.getMessage());
                    alert.showAndWait();
                }
            }

        });

        // Create a VBox to organize the elements vertically
        VBox layout = new VBox(20);  // 20px spacing between elements
        layout.setAlignment(Pos.CENTER);  // Center-align all elements
        layout.getChildren().addAll(title, weightInfo, weightGoalField, weightNote, getPlanButton);

        // Add layout to the root pane and create the scene
        root.getChildren().add(layout);
        Scene scene = new Scene(root, 600, 500);

        // Set the scene for the stage
        stage.setScene(scene);
    }


    public void editUsersInfo() {
        Stage stage = new Stage(); // Create a new stage for this scene
        StackPane signInRoot = new StackPane();
        signInRoot.setStyle("-fx-background-color: #f5deb3;"); // Same background color

        // Welcome Text
        Text welcomeText = new Text("Insert the user credentials:");
        welcomeText.setFont(Font.font("Arial", 36));
        welcomeText.setFill(Color.web("#C71585")); // Dark pink

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button checkButton = new Button("Check");
        checkButton.setFont(Font.font("Arial", 20));
        checkButton.setStyle("-fx-padding: 10 30 10 30; -fx-background-color: #ffcccc; -fx-border-color: pink; -fx-border-width: 2;");

        VBox formLayout = new VBox(10, welcomeText, firstNameField, lastNameField, passwordField, checkButton);
        formLayout.setAlignment(Pos.CENTER);

        signInRoot.getChildren().add(formLayout);

        Scene signInScene = new Scene(signInRoot, 600, 500);
        stage.setScene(signInScene);
        stage.show();

        checkButton.setOnAction(event -> {
            try {
                DatabaseConnection.connect(); // Establish database connection

                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String password = passwordField.getText();

                if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                    showAlert("Error", "All fields must be filled!");
                    return;
                } else if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
                    showAlert("Invalid Input", "Please enter a valid name (letters only, no spaces or special characters).");
                } else if (firstName.length() > 20 || lastName.length() > 20) { // Limit input length to 20 characters
                    showAlert("Error", "Input must be no longer than 20 characters.");
                } else if (password.length() > 8 || !password.matches(".*[0-9].*")) {
                    showAlert("Invalid Password", "Password must be at most 8 characters long and contain only digits.");
                } else {
                    Person existingPerson = DatabaseConnection.findPersonByNamePasswordAndRole(firstName, lastName, password, "user");

                    if (existingPerson != null) {
                        VBox editWholeLayout = new VBox(20);
                        editWholeLayout.setPadding(new Insets(20));
                        editWholeLayout.setAlignment(Pos.CENTER);
                        editWholeLayout.setStyle("-fx-background-color: #f5deb3;"); // Light yellow background

                        Text title = new Text("Edit Users Account");
                        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                        title.setFill(Color.DARKORANGE);

                        TextField newFirstName = new TextField();
                        newFirstName.setPromptText("Updated First Name");

                        TextField newLastName = new TextField();
                        newLastName.setPromptText("Updated Last Name");

                        TextField newPhone = new TextField();
                        newPhone.setPromptText("Updated Phone Number (XXX-XXX-XXXX)");

                        TextField newHeight = new TextField();
                        newHeight.setPromptText("Updated Height (cm)");

                        TextField newWeight = new TextField();
                        newWeight.setPromptText("Updated Weight (kg)");

                        PasswordField newPassword = new PasswordField();
                        newPassword.setPromptText("Updated Password");

                        PasswordField confirmNewPasswordField = new PasswordField();
                        confirmNewPasswordField.setPromptText("Confirm Updated Password");

                        TextField newRole = new TextField();
                        newRole.setPromptText("Updated Role");

                        Button saveButton = new Button("Save Changes");
                        saveButton.setStyle("-fx-padding: 10 20 10 20; -fx-background-color: #98fb98; -fx-border-color: green; -fx-border-width: 2;");

                        editWholeLayout.getChildren().addAll(title, newFirstName, newLastName, newPhone, newHeight, newWeight, newPassword, confirmNewPasswordField, newRole, saveButton);

                        Scene editWholeScene = new Scene(editWholeLayout, 600, 500);
                        stage.setScene(editWholeScene);

                        saveButton.setOnAction(saveEvent -> {
                            try {
                                String updatedFirstName = newFirstName.getText();
                                String updatedLastName = newLastName.getText();
                                String updatedPhone = newPhone.getText();
                                String updatedHeight = newHeight.getText();
                                String updatedWeight = newWeight.getText();
                                String updatedPassword = newPassword.getText();
                                String confirmPassword = confirmNewPasswordField.getText();
                                String updatedRole = newRole.getText();

                                if (updatedFirstName.isEmpty() || updatedLastName.isEmpty() || updatedPhone.isEmpty() || updatedHeight.isEmpty() || updatedWeight.isEmpty() || updatedPassword.isEmpty() || confirmPassword.isEmpty() || updatedRole.isEmpty()) {
                                    showAlert("Error", "All fields must be filled!");
                                } else if (!updatedFirstName.matches("[a-zA-Z]+") || !updatedLastName.matches("[a-zA-Z]+")) {
                                    showAlert("Invalid Input", "Please enter a valid name (letters only, no spaces or special characters).");
                                } else if (updatedFirstName.length() > 20 || updatedLastName.length() > 20) {
                                    showAlert("Error", "Input must be no longer than 20 characters.");
                                } else if (!updatedPhone.matches("\\d{3}-\\d{3}-\\d{4}")) {
                                    showAlert("Invalid Phone Number", "Please enter a valid phone number (XXX-XXX-XXXX).");
                                } else if (!updatedHeight.matches("\\d+(\\.\\d+)?") || !updatedWeight.matches("\\d+(\\.\\d+)?")) {
                                    showAlert("Invalid Height/Weight", "Height and weight must be positive numeric values.");
                                } else if (!updatedPassword.equals(confirmPassword)) {
                                    showAlert("Password Mismatch", "Passwords do not match!");
                                } else if (updatedPassword.length() > 8 || !updatedPassword.matches(".*[0-9].*")) {
                                    showAlert("Invalid Password", "Password must be at most 8 characters long and contain only digits.");
                                } else if (!updatedRole.equals("admin") && !updatedRole.equals("user")) {
                                    showAlert("Invalid Role", "Please enter a valid role - user/admin.");
                                } else {
                                    Person updatedPerson = new Person(updatedFirstName, updatedLastName, Float.parseFloat(updatedHeight), Float.parseFloat(updatedWeight), updatedPhone, updatedRole, updatedPassword);
                                    DatabaseConnection.updatePerson(existingPerson, updatedPerson);
                                    showAlert("Success", "Account updated successfully!");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                showAlert("Error", "An error occurred while saving changes.");
                            }
                        });
                    } else {
                        showAlert("Error", "Incorrect credentials!");
                    }
                }
            } catch (PersonNotFoundError err) {
                showAlert("Error", "User not found!");
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Unable to connect to the database.");
            }
        });
    }




    private void deleteAccount(Stage stage, String firstName, String lastName, String password, String role) {
        // Create a new StackPane for the new scene
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #f5deb3;"); // Beige background

        // Create a Text element to display the message
        Text message = new Text();
        message.setFont(Font.font("Arial", 20));
        root.getChildren().add(message);

        // Call the deletePerson function and set the message based on the result
        try {
            DatabaseConnection.deletePerson(firstName, lastName, password, role); // Attempt to delete the account
            message.setText("Account deleted successfully!");
        } catch (Exception e) {
            message.setText("The account couldn't be deleted because an error occurred.");
            System.err.println("Error: " + e.getMessage()); // Log the error for debugging
        }

        // Create a new scene and set it on the stage
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);

        // Button configuration
        Button deleteAccountButton = new Button("Delete Account");
        deleteAccountButton.setOnAction(e -> {
            deleteAccount(stage, firstName, lastName, password, role);
        });

    }



    public static void main(String[] args) {
        launch();
    }
}