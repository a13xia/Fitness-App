package org.example.fitness_app_fx;
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:C:/Users/Alexia/fitness.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static Person findPersonByNamePasswordAndRole(String firstName, String lastName, String password, String role) {
        String sql = "SELECT * FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ? AND Role = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, password);
            pstmt.setString(4, role);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstNameFromDb = rs.getString("FirstName");
                String lastNameFromDb = rs.getString("LastName");
                float weight = rs.getFloat("Weight");
                float height = rs.getFloat("Height");
                String phone = rs.getString("Phone");
                String roleFromDb = rs.getString("Role");
                String passwordFromDb = rs.getString("Password"); // You should retrieve the password as well

                return new Person(firstNameFromDb, lastNameFromDb, height, weight, phone, roleFromDb, passwordFromDb); // Return with role and password
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }




    public static void insertPerson(Person person) {
        String sql = "INSERT INTO Person(FirstName, LastName, Weight, Height, Phone, Role, Password) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String bmiSql = "INSERT INTO BMI(PersonID, BMIValue, BMICategory) VALUES(?, ?, ?)";
        String exercisePlanSql = "INSERT INTO ExercisePlan(PersonID, BMICategory, ExerciseDetails, DifficultyLevel, DurationInMinutes) VALUES(?, ?, ?, ?, ?)";
        String dietPlanSql = "INSERT INTO DietPlan(PersonID, BMICategory, DietDetails, Calories) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL)) {

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, person.getFirstName());
                pstmt.setString(2, person.getLastName());
                pstmt.setDouble(3, person.getWeight());
                pstmt.setDouble(4, person.getHeight());
                pstmt.setString(5, person.getPhone());
                pstmt.setString(6, person.getRole()); // Set role
                pstmt.setString(7, person.getPassword()); // Set password
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                int personId = -1;
                if (generatedKeys.next()) {
                    personId = generatedKeys.getInt(1);
                }

                if (personId != -1) {
                    float bmi = person.BMI();
                    String weightCategory = person.getWeightCategory();

                    try (PreparedStatement bmiStmt = conn.prepareStatement(bmiSql)) {
                        bmiStmt.setInt(1, personId);
                        bmiStmt.setFloat(2, bmi);
                        bmiStmt.setString(3, weightCategory);
                        bmiStmt.executeUpdate();
                    }

                    // Insert Exercise Plan data based on BMI Category
                    String exerciseDetails = getExerciseDescription(weightCategory);
                    String difficultyLevel = "Medium";
                    try (PreparedStatement exerciseStmt = conn.prepareStatement(exercisePlanSql)) {
                        exerciseStmt.setInt(1, personId);
                        exerciseStmt.setString(2, weightCategory);
                        exerciseStmt.setString(3, exerciseDetails);
                        exerciseStmt.setString(4, difficultyLevel);
                        exerciseStmt.setInt(5, 30);
                        exerciseStmt.executeUpdate();
                    }

                    // Insert Diet Plan data based on BMI Category
                    String dietDetails = getDietDescription(weightCategory);
                    int calories = getCaloriesForDiet(weightCategory);
                    try (PreparedStatement dietStmt = conn.prepareStatement(dietPlanSql)) {
                        dietStmt.setInt(1, personId);
                        dietStmt.setString(2, weightCategory);
                        dietStmt.setString(3, dietDetails);
                        dietStmt.setInt(4, calories);
                        dietStmt.executeUpdate();
                    }


                    conn.commit();
                    System.out.println("Person and related data added to the database.");
                } else {
                    System.out.println("Error: PersonID generation failed.");
                }
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error inserting person data: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }




    public static void updatePerson(Person oldPerson, Person newPerson) {
        String getIdSql = "SELECT PersonID FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ?";
        String updatePersonSql = "UPDATE Person SET FirstName = ?, LastName = ?, Weight = ?, Height = ?, Phone = ?, Role = ?, Password = ? WHERE PersonID = ?";
        String bmiSql = "UPDATE BMI SET BMIValue = ?, BMICategory = ? WHERE PersonID = ?";
        String exercisePlanSql = "UPDATE ExercisePlan SET BMICategory = ?, ExerciseDetails = ?, DifficultyLevel = ?, DurationInMinutes = ? WHERE PersonID = ?";
        String dietPlanSql = "UPDATE DietPlan SET BMICategory = ?, DietDetails = ?, Calories = ? WHERE PersonID = ?";

        try (Connection conn = DriverManager.getConnection(URL)) {

            // Enable foreign key constraints
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            conn.setAutoCommit(false);

            try {
                // Step 1: Get the PersonID based on FirstName, LastName, and Password
                int personID;
                try (PreparedStatement getIdStmt = conn.prepareStatement(getIdSql)) {
                    getIdStmt.setString(1, oldPerson.getFirstName());
                    getIdStmt.setString(2, oldPerson.getLastName());
                    getIdStmt.setString(3, oldPerson.getPassword());

                    try (ResultSet rs = getIdStmt.executeQuery()) {
                        if (rs.next()) {
                            personID = rs.getInt("PersonID");
                        } else {
                            System.out.println("Person not found with the given details.");
                            return;
                        }
                    }
                }

                // Step 2: Update the Person table
                try (PreparedStatement updatePersonStmt = conn.prepareStatement(updatePersonSql)) {
                    updatePersonStmt.setString(1, newPerson.getFirstName());
                    updatePersonStmt.setString(2, newPerson.getLastName());
                    updatePersonStmt.setDouble(3, newPerson.getWeight());
                    updatePersonStmt.setDouble(4, newPerson.getHeight());
                    updatePersonStmt.setString(5, newPerson.getPhone());
                    updatePersonStmt.setString(6, newPerson.getRole());
                    updatePersonStmt.setString(7, newPerson.getPassword());
                    updatePersonStmt.setInt(8, personID); // Use PersonID to update
                    updatePersonStmt.executeUpdate();
                }

                // Step 3: Update BMI
                float bmi = newPerson.BMI();
                String weightCategory = newPerson.getWeightCategory();
                try (PreparedStatement bmiStmt = conn.prepareStatement(bmiSql)) {
                    bmiStmt.setFloat(1, bmi);
                    bmiStmt.setString(2, weightCategory);
                    bmiStmt.setInt(3, personID); // Use PersonID to update BMI
                    bmiStmt.executeUpdate();
                }

                // Step 4: Update Exercise Plan
                String exerciseDetails = getExerciseDescription(weightCategory);
                String difficultyLevel = "Medium";
                try (PreparedStatement exerciseStmt = conn.prepareStatement(exercisePlanSql)) {
                    exerciseStmt.setString(1, weightCategory);
                    exerciseStmt.setString(2, exerciseDetails);
                    exerciseStmt.setString(3, difficultyLevel);
                    exerciseStmt.setInt(4, 30); // Duration in minutes (example value)
                    exerciseStmt.setInt(5, personID); // Use PersonID to update ExercisePlan
                    exerciseStmt.executeUpdate();
                }

                // Step 5: Update Diet Plan
                String dietDetails = getDietDescription(weightCategory);
                int calories = getCaloriesForDiet(weightCategory);
                try (PreparedStatement dietStmt = conn.prepareStatement(dietPlanSql)) {
                    dietStmt.setString(1, weightCategory);
                    dietStmt.setString(2, dietDetails);
                    dietStmt.setInt(3, calories);
                    dietStmt.setInt(4, personID); // Use PersonID to update DietPlan
                    dietStmt.executeUpdate();
                }

                conn.commit();
                System.out.println("Person and related data updated successfully.");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error updating person data: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }





    public static void deletePerson(String firstName, String lastName, String password, String role) {
        String personSql = "DELETE FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ? AND Role = ?";
        String bmiSql = "DELETE FROM BMI WHERE PersonID = (SELECT PersonID FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ? AND Role = ?)";
        String exercisePlanSql = "DELETE FROM ExercisePlan WHERE PersonID = (SELECT PersonID FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ? AND Role = ?)";
        String dietPlanSql = "DELETE FROM DietPlan WHERE PersonID = (SELECT PersonID FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ? AND Role = ?)";

        try (Connection conn = DriverManager.getConnection(URL)) {

            // Enable foreign key constraints
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            conn.setAutoCommit(false);

            try (PreparedStatement personStmt = conn.prepareStatement(personSql);
                 PreparedStatement bmiStmt = conn.prepareStatement(bmiSql);
                 PreparedStatement exerciseStmt = conn.prepareStatement(exercisePlanSql);
                 PreparedStatement dietStmt = conn.prepareStatement(dietPlanSql)) {

                personStmt.setString(1, firstName);
                personStmt.setString(2, lastName);
                personStmt.setString(3, password);
                personStmt.setString(4, role);

                bmiStmt.setString(1, firstName);
                bmiStmt.setString(2, lastName);
                bmiStmt.setString(3, password);
                bmiStmt.setString(4, role);

                exerciseStmt.setString(1, firstName);
                exerciseStmt.setString(2, lastName);
                exerciseStmt.setString(3, password);
                exerciseStmt.setString(4, role);

                dietStmt.setString(1, firstName);
                dietStmt.setString(2, lastName);
                dietStmt.setString(3, password);
                dietStmt.setString(4, role);

                bmiStmt.executeUpdate();
                exerciseStmt.executeUpdate();
                dietStmt.executeUpdate();

                int rowsDeleted = personStmt.executeUpdate();

                if (rowsDeleted > 0) {
                    conn.commit();
                    System.out.println("Person and associated data deleted successfully.");
                } else {
                    System.out.println("No person found with the given name.");
                }
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Error deleting person data: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }


    public static void getPlanForPerson(Person person, Diet diet, ExercisePlan exercise, float weightDifference) {
        String updateDifficultySql = "UPDATE ExercisePlan SET DifficultyLevel = ? WHERE PersonID = "
                + "(SELECT PersonID FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ?)";
        String dietPlanSql = "SELECT DietDetails, Calories FROM DietPlan WHERE PersonID = "
                + "(SELECT PersonID FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ?)";
        String exercisePlanSql = "SELECT ExerciseDetails, DifficultyLevel, DurationInMinutes FROM ExercisePlan WHERE PersonID = "
                + "(SELECT PersonID FROM Person WHERE FirstName = ? AND LastName = ? AND Password = ?)";

        try (Connection conn = DriverManager.getConnection(URL)) {

            // Enable foreign key constraints
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            conn.setAutoCommit(false);

            // Update DifficultyLevel in the ExercisePlan table
            String difficultyLevel = PersonalizedPlan.goalDifficulty(person.BMI(), weightDifference);
            try (PreparedStatement updateStmt = conn.prepareStatement(updateDifficultySql)) {
                updateStmt.setString(1, difficultyLevel);
                updateStmt.setString(2, person.getFirstName());
                updateStmt.setString(3, person.getLastName());
                updateStmt.setString(4, person.getPassword());
                updateStmt.executeUpdate();
            }

            // Retrieve and populate Diet object
            try (PreparedStatement dietStmt = conn.prepareStatement(dietPlanSql)) {
                dietStmt.setString(1, person.getFirstName());
                dietStmt.setString(2, person.getLastName());
                dietStmt.setString(3, person.getPassword());
                try (ResultSet dietRs = dietStmt.executeQuery()) {
                    if (dietRs.next()) {
                        String description = dietRs.getString("DietDetails");
                        int calories = dietRs.getInt("Calories");

                        // Populate the Diet object
                        diet.setDescription(description);
                        diet.setCalories(calories);
                    }
                }
            }

            // Retrieve and populate ExercisePlan object
            try (PreparedStatement exerciseStmt = conn.prepareStatement(exercisePlanSql)) {
                exerciseStmt.setString(1, person.getFirstName());
                exerciseStmt.setString(2, person.getLastName());
                exerciseStmt.setString(3, person.getPassword());
                try (ResultSet exerciseRs = exerciseStmt.executeQuery()) {
                    if (exerciseRs.next()) {
                        String exerciseDetails = exerciseRs.getString("ExerciseDetails");
                        String updatedDifficultyLevel = exerciseRs.getString("DifficultyLevel");
                        int duration = exerciseRs.getInt("DurationInMinutes");

                        // Populate the ExercisePlan object
                        exercise.setName(exerciseDetails);
                        exercise.setDifficultyLevel(updatedDifficultyLevel);
                        exercise.setDurationInMinutes(duration);
                    }
                }
            }

            conn.commit();

        } catch (SQLException e) {
            System.out.println("Database operation error: " + e.getMessage());
        }
    }












    private static void insertExercisePlan(Connection conn, int personId, String weightCategory) throws SQLException {
        String exerciseSql = "INSERT INTO ExercisePlan(PersonID, WeightCategory, ExerciseDescription) VALUES(?, ?, ?)";
        String exerciseDescription = getExerciseDescription(weightCategory);

        try (PreparedStatement stmt = conn.prepareStatement(exerciseSql)) {
            stmt.setInt(1, personId);
            stmt.setString(2, weightCategory);
            stmt.setString(3, exerciseDescription);
            stmt.executeUpdate();
        }
    }


    private static void insertDietPlan(Connection conn, int personId, String weightCategory) throws SQLException {
        String dietSql = "INSERT INTO DietPlan(PersonID, WeightCategory, DietDescription) VALUES(?, ?, ?)";
        String dietDescription = getDietDescription(weightCategory);

        try (PreparedStatement stmt = conn.prepareStatement(dietSql)) {
            stmt.setInt(1, personId);
            stmt.setString(2, weightCategory);
            stmt.setString(3, dietDescription);
            stmt.executeUpdate();
        }
    }




    // exercises from exercisePlan, written here in another manner
    // used for inserting and updating
    private static String getExerciseDescription(String weightCategory) {

        if(weightCategory.equals("Underweight"))
            return "walking, running or yoga";
        else if(weightCategory.equals("Normal"))
            return "pilates, hot yoga or HIIT";
        else
            return "walking, jogging, zumba or cycling";
    }

    // diets, written here in another manner
    // used for inserting and updating
    private static String getDietDescription(String weightCategory) {

        if(weightCategory.equals("Underweight"))
            return "Intermittent Fasting. Eating within specific time windows, such as 16:8 or 18:6";
        else if(weightCategory.equals("Normal"))
            return "High-Protein. Protein-rich diet supporting muscle growth, around 1800 calories";
        else
            return "Mediterranean. Balanced diet with carbohydrates, meat, fruits and vegetables";
    }

    private static int getCaloriesForDiet(String weightCategory) {

        if(weightCategory.equals("Underweight"))
            return 1500;
        else if(weightCategory.equals("Normal"))
            return 2300;
        else
            return 3000;
    }





    private static void updateExercisePlan(Connection conn, int personId, String weightCategory) throws SQLException {
        String exerciseSql = "UPDATE ExercisePlan SET ExerciseDescription = ? WHERE PersonID = ? AND WeightCategory = ?";
        String exerciseDescription = getExerciseDescription(weightCategory);

        try (PreparedStatement stmt = conn.prepareStatement(exerciseSql)) {
            stmt.setString(1, exerciseDescription);
            stmt.setInt(2, personId);
            stmt.setString(3, weightCategory);
            stmt.executeUpdate();
        }
    }

    private static void updateDietPlan(Connection conn, int personId, String weightCategory) throws SQLException {
        String dietSql = "UPDATE DietPlan SET DietDescription = ? WHERE PersonID = ? AND WeightCategory = ?";
        String dietDescription = getDietDescription(weightCategory);

        try (PreparedStatement stmt = conn.prepareStatement(dietSql)) {
            stmt.setString(1, dietDescription);
            stmt.setInt(2, personId);
            stmt.setString(3, weightCategory);
            stmt.executeUpdate();
        }
    }


}
