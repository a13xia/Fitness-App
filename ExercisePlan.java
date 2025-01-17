package org.example.fitness_app_fx;

public class ExercisePlan{      // CLASS D
    private Person person;
    private String difficultyLevel;
    private String name;
    private int durationInMinutes;


    public ExercisePlan(Person person, String name, String difficultyLevel, int duration) {
        this.person = person;
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.durationInMinutes = duration;
    }

    public ExercisePlan(String name, String difficultyLevel, int duration) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
        if (difficultyLevel == null || difficultyLevel.isEmpty()) {
            throw new IllegalArgumentException("Difficulty level cannot be empty or null");
        }
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.durationInMinutes = duration;
    }

    public ExercisePlan() {

    }

    public String getName() {
        return name;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String goalDifficulty(float bmi, float difference) {
        if(person.BMI()< 18.5) {
            if (person.BMI() > 17.5)
                return "Easy";
            else
                return "Hard";

        }
        else if(person.BMI() == 18.5)
            return "Easy";
        else
        {
            if (person.BMI()<19.5)
                return "Easy";
            else
                return "Hard";
        }
    }


    public void suggestExercise(int ok) {
        // ok - for the new bmi. if ok = -1, means the person wants to be underweight, so it will receive a smaller calorie diet

        if (ok<0) {
            System.out.println("Recommended exercise: Weight training to start building more muscle mass.");
            System.out.println("For example, you could start with walking, running or yoga");
        } else if (ok==0) {
            System.out.println("Recommended exercise: Cardio and strength training to maintain fitness.");
            System.out.println("For example, you could try pilates, hot yoga or HIIT.");
        } else {
            System.out.println("Recommended exercise: Cardio-focused workouts to reduce weight.");
            System.out.println("For example, you could try walking, jogging, zumba or cycling.");
        }
    }

    public int compareTo(ExercisePlan otherPlan) {
        return Integer.compare(this.durationInMinutes, otherPlan.durationInMinutes);
    }

    public static void exercise(int ok){
        // ok - for the new bmi. if ok = -1, means the person wants to be underweight, so it will receive a smaller calorie diet

        ExercisePlan exercisePlan = new ExercisePlan();
        System.out.println("\nLet's see what workouts suit you the best:\n");
        exercisePlan.suggestExercise(ok);
    }


}