import org.example.fitness_app_fx.ExercisePlan;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExercisePlanTest {

    @Test
    public void testValidExercisePlan() {
        ExercisePlan plan = new ExercisePlan("Morning Workout", "Intermediate", 45);
        assertNotNull(plan);
        assertEquals("Morning Workout", plan.getName());
        assertEquals("Intermediate", plan.getDifficultyLevel());
        assertEquals(45, plan.getDurationInMinutes());
    }

    @Test
    public void testNameCannotBeEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ExercisePlan("", "Medium", 30); // Empty name
        });
        assertEquals("Name cannot be empty or null", exception.getMessage());
    }

    @Test
    public void testNameCannotBeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ExercisePlan(null, "Easy", 60); // Null name
        });
        assertEquals("Name cannot be empty or null", exception.getMessage());
    }

    @Test
    public void testDifficultyLevelCannotBeEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ExercisePlan("Yoga", "", 30); // Empty difficulty level
        });
        assertEquals("Difficulty level cannot be empty or null", exception.getMessage());
    }

    @Test
    public void testDifficultyLevelCannotBeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ExercisePlan("HIIT", null, 30); // Null difficulty level
        });
        assertEquals("Difficulty level cannot be empty or null", exception.getMessage());
    }

    @Test
    public void testDurationCannotBeNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ExercisePlan("Running", "Hard", -30); // Negative duration
        });
        assertEquals("Duration cannot be negative", exception.getMessage());
    }

    @Test
    public void testDurationZero() {
        ExercisePlan plan = new ExercisePlan("Stretching", "Easy", 0); // Zero duration
        assertNotNull(plan);
        assertEquals("Stretching", plan.getName());
        assertEquals("Easy", plan.getDifficultyLevel());
        assertEquals(0, plan.getDurationInMinutes());
    }

    @Test
    public void testLargeDurationValue() {
        ExercisePlan plan = new ExercisePlan("Marathon Training", "Very hard", 180); // Large duration
        assertNotNull(plan);
        assertEquals("Marathon Training", plan.getName());
        assertEquals("Very hard", plan.getDifficultyLevel());
        assertEquals(180, plan.getDurationInMinutes());
    }
}
