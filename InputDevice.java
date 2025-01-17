package org.example.fitness_app_fx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class InputDevice {
    private InputStream inputStream;
    private Scanner scanner;

    public InputDevice(InputStream inputStream){
        this.scanner = new Scanner(inputStream);
        this.inputStream = inputStream;
    }


    public String readName() {
        while (true) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            } else if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Name can only contain letters and spaces. Please enter a valid name.");
            } else {
                return name;
            }
        }
    }



    public String readFirstName() {
        while (true) {
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine().trim();
            if (firstName.isEmpty()) {
                System.out.println("First name cannot be empty. Please enter a valid first name.");
            } else if (!firstName.matches("[a-zA-Z]+")) {
                System.out.println("First name can only contain letters. Please enter a valid first name.");
            } else {
                return firstName;
            }
        }
    }


    public String readLastName() {
        while (true) {
            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine().trim();
            if (lastName.isEmpty()) {
                System.out.println("Last name cannot be empty. Please enter a valid last name.");
            } else if (!lastName.matches("[a-zA-Z]+")) {
                System.out.println("Last name can only contain letters. Please enter a valid last name.");
            } else {
                return lastName;
            }
        }
    }



    public float readHeight() {
        while (true) {
            System.out.print("Enter your height (in cm): ");
            if (scanner.hasNextFloat()) {
                float height = scanner.nextFloat();
                if (height > 50 && height < 250) {
                    return height;
                } else {
                    System.out.println("Invalid height. Please enter a height between 50 cm and 250 cm.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for height.");
                scanner.next(); // consumes the invalid input
            }
        }
    }



    public float readWeight() {
        while (true) {
            System.out.print("Enter your weight (in kg): ");
            if (scanner.hasNextFloat()) {
                float weight = scanner.nextFloat();
                if (weight > 30 && weight < 250) {
                    return weight;
                } else {
                    System.out.println("Invalid weight. Please enter a weight between 30 kg and 250 kg.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for weight.");
                scanner.next(); // consumes the invalid input
            }
        }
    }


    public String readPhoneNumber() {
        while (true) {
            System.out.print("Please enter your phone number (format: 123-456-7890): ");
            if (scanner.hasNext()) {
                String phoneNumber = scanner.next();
                if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")) {
                    return phoneNumber;
                } else {
                    System.out.println("Invalid phone number format. Please use the format: 123-456-7890.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid phone number.");
                scanner.next(); // consumes the invalid input
            }
        }
    }



    public static String getType(){
        return "random";
    }

    public static Integer nextInt(){
        Random random = new Random();
        int i = random.nextInt(100) +1;
        return i;
    }



    public  int[] getNumbers(int n){
        int[] array = new int[n];
        for (int i=0;i<n;i++){
            array[i] = nextInt();
        }
        return array;

    }

    public String getLine(){
        return "The quick brown fox jumps over the lazy dog";
    }




    // first approach

    public String readData() throws IOException {
        StringBuilder data = new StringBuilder();
        int byteData;

        // Read data byte by byte from the InputStream until the end (-1)
        while ((byteData = inputStream.read()) != -1) {
            data.append((char) byteData);
        }

        return data.toString();
    }


    // second approach

    public String readLine(){
        if (scanner.hasNextLine()){
            return scanner.nextLine();
        }
        else
            return null;
    }

    public String readWord(){
        if (scanner.hasNext())
            return scanner.next();
        else
            return null;
    }



    public int readInt(){
        if (scanner.hasNextInt())
            return scanner.nextInt();
        else
            throw new IllegalStateException("Next input is not an integer");
    }

    public float readFloat() {
        scanner.nextLine();
        while (true) {
            System.out.print("Enter a float: ");
            String input = scanner.nextLine(); // Read the entire line
            try {
                return Float.parseFloat(input); // Try to parse it as a float
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid float."); // Handle invalid input
            }
        }
    }


    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }

}
