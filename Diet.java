package org.example.fitness_app_fx;

import java.util.Comparator;
import java.util.List;

public class Diet{ //class C

    private String dietName;
    private String description;
    private int calories;

    public Diet(String dietName, String description, int calories) {
        this.dietName = dietName;
        this.description = description;
        this.calories = calories;
    }

    public Diet(String description, int calories) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty or null");
        }
        if (calories < 0) {
            throw new IllegalArgumentException("Calories cannot be negative");
        }

        this.description = description;
        this.calories = calories;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<Diet> sortByCalories(List<Diet> diets) {
        diets.sort(Comparator.comparingInt(Diet::getCalories));
        return diets;
    }

    // Diets for underweight people

    public static String smallDiet(){
        return "Vegan. Plant-based diet avoiding animal products with approximately 1400 calories";
    }
    public static String verySmallDiet(){
        return "Low-Carb. Emphasizes low carbohydrates and moderate protein, around 1300-1600 calories, for weight management and blood sugar control";
    }
    public static String extremelySmallDiet(){
        return "Intermittent Fasting. Eating within specific time windows, such as 16:8 or 18:6, with a calorie range of 1200-1800 per day\n";
    }


    // Diets for normal weight people

    public static String normalDiet(){
        return "Keto. Low carb and high fat diet with approximately 1500 calories";
    }
    public static String veryNormalDiet(){
        return "High-Protein. Protein-rich diet supporting muscle growth, around 1800 calories, suitable for weight gain and lean muscle development";
    }
    public static String smallNormalDiet(){
        return "Flexitarian. Primarily plant-based with occasional meat, offering 1600-2000 calories, focused on balanced nutrition";
    }


    // Diets for overweight people

    public static String bigDiet(){
        return "Paleo. Focuses on whole foods like meat and vegetables, with approximately 1600 calories";
    }
    public static String bigBigDiet(){
        return "Lacto Diet. A dairy-based diet emphasizing milk, cheese, yogurt, and other lactose-containing foods, with approximately 1800-2000 calories";
    }
    public static String hugeDiet(){
        return "Mediterranean. Balanced diet with carbohydrates, meat, fruits and vegetables with approximately 2500 calories";
    }
    public static String veryBigDiet(){
        // for obese people
        return "DASH (Dietary Approaches to Stop Hypertension). Low-sodium diet with high fruit and vegetable intake, around 2000 calories for heart health\n";
    }



    public static void diet(int ok, float weight_difference, int ct){
        System.out.println("\nLet's see what diets are best for you:\n");
        // ok - for the new bmi. if ok = -1, means the person wants to be underweight, so it will receive a smaller calorie diet
        // ct - to say if you want to lose/gain weight; ct=0-no, ct= -1-lose, ct=1-gain

        if(ct == -1){
            if (ok == -1) {
                System.out.println(Diet.verySmallDiet() + " would suit your weight goal very well!");
                System.out.println("But also, " + Diet.smallDiet() + "would be good to try!");
            }
            else if (ok == 0) {
                System.out.println(Diet.smallDiet() + " would suit your weight goal very well!");
                System.out.println("But also, " + Diet.smallNormalDiet() + "would be good to try!");
            }
            else if (ok == 1) {
                System.out.println(Diet.normalDiet() + " would suit your weight goal very well!");
                System.out.println("But also, " + Diet.extremelySmallDiet() + "would be good to try!");
            }
            else{
                System.out.println(Diet.extremelySmallDiet()+" would suit your weight goal very well!");
                System.out.println("But also, " + Diet.veryBigDiet() + "would be good to try!");
            }
        }


        else if (ct == 0){
            if (ok == -1) {
                System.out.println(Diet.smallDiet() + " would suit your weight goal very well!");
                System.out.println("But also, " + Diet.smallNormalDiet() + "would be good to try!");
            }
            else if (ok == 0) {
                System.out.println(Diet.normalDiet() + " would suit your weight goal very well!");
                System.out.println("But also, " + Diet.smallNormalDiet() + "would be good to try!");
            }
            else if (ok == 1) {
                System.out.println(Diet.bigDiet() + " would suit your weight goal very well!");
                System.out.println("But also, " + Diet.bigBigDiet() + "would be good to try!");
            }
            else{
                System.out.println(Diet.hugeDiet()+" would suit your weight goal very well!");
                System.out.println("But also, " + Diet.veryBigDiet() + "would be good to try!");
            }
        }


        else if (ct==1){
            if (weight_difference < 7)
            {
                if (ok == -1) {
                    System.out.println(Diet.smallDiet() + " would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.verySmallDiet() + "would be good to try!");
                }
                else if (ok == 0) {
                    System.out.println(Diet.normalDiet() + " would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.smallNormalDiet() + "would be good to try!");
                }
                else if (ok == 1) {
                    System.out.println(Diet.bigDiet() + " would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.bigBigDiet() + "would be good to try!");
                }
                else{
                    System.out.println(Diet.hugeDiet()+" would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.bigBigDiet() + "would be good to try!");
                }
            }else if (weight_difference<15) {
                if (ok == -1) {
                    System.out.println(Diet.bigDiet() + " would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.smallNormalDiet() + "would be good to try!");
                }
                else if (ok == 0) {
                    System.out.println(Diet.veryNormalDiet() + " would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.bigDiet() + "would be good to try!");
                }
                else{
                    System.out.println(Diet.bigBigDiet() + " would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.hugeDiet() + "would be good to try!");
                }
            }
            else{
                if (ok == -1) {
                    System.out.println(Diet.bigDiet() + " would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.veryNormalDiet() + "would be good to try!");
                }
                else {
                    System.out.println(Diet.hugeDiet() + " would suit your weight goal very well!");
                    System.out.println("But also, " + Diet.bigBigDiet() + "would be good to try!");
                }
            }


        }


    }


}
