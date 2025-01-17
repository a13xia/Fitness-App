package org.example.fitness_app_fx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person{       // A Class

    private int personID;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
    private String password;

    // height in cm
    protected float height;

    // weight in kg
    protected float weight;

    // Default constructor
    public Person() {
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    // Constructor
    public Person(String firstName, String lastName, float height , float weight , String phone, String role, String password) {
        if (height < 0) {
            throw new IllegalArgumentException("Height cannot be negative");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
        this.phone = phone;
        this.role = role;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    // Function that computes the BMI

    public float BMI() {
        float bmi = 10000 * weight / (height * height);
        return Math.round(bmi * 100) / 100.0f;
    }


    public String getWeightCategory(){
        float bmi = BMI();
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 24.9) {
            return "Normal";
        } else if (bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public String getRecommendation(){
        float bmi = BMI();
        if (bmi < 18.5) {
            return "You are underweight...you should gain some weight!";
        } else if (bmi < 24.9) {
            return "Congratulations, you have a healthy weight!";
        } else if (bmi < 29.9) {
            return "You are overweight...you should lose some weight!";
        } else {
            return "You are obese...you should really consider losing weight!";
        }
    }

//    public String getRecommendation(){
//        float bmi = BMI();
//        if (bmi < 18.5) {
//            return "You are underweight "+ "🫤" + " You should gain weight!";
//        } else if (bmi < 24.9) {
//            return "You have a healthy weight! " + "🥰";
//        } else if (bmi < 29.9) {
//            return "You are overweight " + "🫤" + " You should lose weight!";
//        } else {
//            return "You are obese " + "😞" + " You should really consider losing weight!";
//        }
//    }

    public float computeHealthyWeightLowerLimit(float height){
        return Math.round(18.5f * (height / 100) * (height / 100) * 10) / 10f;
    }
    public float computeHealthyWeightHigherLimit(float height){
        return Math.round(24.9f * (height / 100) * (height / 100) * 10) / 10f;
    }

    public int getRec(float bmi){
        if (bmi < 18.5) {
            return -1;
        } else if (bmi < 24.9) {
            return 0;
        } else if (bmi < 29.9) {
            return 1;
        } else {
            return 2;
        }
    }

    public int compareTo(Person p2){
        float bmi_p1 = this.BMI();
        float bmi_p2 = p2.BMI();

        return Float.compare(bmi_p1, bmi_p2);
    }

    public static List<Person> sortPersonsByBMI(List<Person> persons) {
        persons.sort((p1, p2) -> Float.compare(p1.BMI(), p2.BMI()));
//        Collections.sort(persons);
        return persons;
    }

    public static Map<String, List<Person>> groupPersonsByWeightCategory(List<Person> persons) {

        Map<String, List<Person>> categoryMap = new HashMap<>();

        categoryMap.put("Underweight", new ArrayList<>());
        categoryMap.put("Healthy", new ArrayList<>());
        categoryMap.put("Overweight", new ArrayList<>());
        categoryMap.put("Obese", new ArrayList<>());

        for (Person person : persons) {

            float bmi = person.BMI();
            if (bmi < 18.5) {
                categoryMap.get("Underweight").add(person);
            } else if (bmi < 24.9) {
                categoryMap.get("Healthy").add(person);
            } else if (bmi < 29.9) {
                categoryMap.get("Overweight").add(person);
            } else {
                categoryMap.get("Obese").add(person);
            }

        }
        return categoryMap;
    }


}
