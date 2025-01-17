package org.example.fitness_app_fx;

public class PersonNotFoundError extends RuntimeException {
    public PersonNotFoundError(String message) {
        super(message);
    }
}
