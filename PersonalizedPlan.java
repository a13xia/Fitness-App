package org.example.fitness_app_fx;

public class PersonalizedPlan{       // Class B  Diet suggestions based on the person's BMI.
    private Person person;
    private Diet[] diet;
    //private String[] dietSuggestions;

    public PersonalizedPlan(Person person) {
        this.person = person;
        //this.dietSuggestions = new String[] {"Balanced Diet", "High Protein Diet", "Low Carb Diet", "High Carb Diet"};
    }

    public void generateDietPlan(){
        person.getRecommendation();
        System.out.println("We recommend you to follow a " + suggestDiet());
    }

    public String  suggestDiet() {
        double bmi = person.BMI();
        if (bmi < 18.5) {
            return "High Protein Diet, High Carb Diet";
        } else if (bmi < 25) {
            return "Balanced Diet";
        } else {
            return "Low Carb Diet, High Protein Diet";
        }
    }



    public boolean hasAchievedGoal(float goal_bmi){
        return person.BMI() <= goal_bmi;
    }

    public static String goalDifficulty(float bmi, float weight_difference) {
        if (bmi < 18.5) {
            if(weight_difference<3)
                return "Very easy";
            else if(weight_difference<4)
                return "Easy";
            else if (weight_difference<7)
                return "Moderate";
            else if (weight_difference<10)
                return "Challenging";
            return "Extreme";
        }
        else if (bmi<24.9){
            if(weight_difference<3)
                return "Very easy";
            else if (weight_difference<5)
                return "Easy";
            else if (weight_difference<8)
                return "Moderate";
            else if (weight_difference<14)
                return "Challenging";
            return "Extreme";
        }
        else if (bmi<29.9){
            if(weight_difference<3)
                return "Very easy";
            else if (weight_difference<7)
                return "Challenging";
            else if (weight_difference<12)
                return "Hard";
            else if (weight_difference<20)
                return "Very hard";
            return "Extreme";
        }
        else{
            if(weight_difference<3)
                return "Easy";
            else if (weight_difference<7)
                return "Challenging";
            else if (weight_difference<11)
                return "Hard";
            else if (weight_difference<20)
                return "Very hard";
            return "Extreme";
        }
    }
}