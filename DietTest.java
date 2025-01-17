import org.example.fitness_app_fx.Diet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DietTest {

    @Test
    public void testValidDiet() {
        Diet diet = new Diet("Vegetarian Diet", 1500);
        assertNotNull(diet);
        assertEquals("Vegetarian Diet", diet.getDescription());
        assertEquals(1500, diet.getCalories());
    }

    @Test
    public void testCaloriesCannotBeNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Diet("Keto Diet", -500); // Negative calories
        });
        assertEquals("Calories cannot be negative", exception.getMessage());
    }

    @Test
    public void testDescriptionCannotBeEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Diet("", 1500); // Empty description
        });
        assertEquals("Diet Description cannot be empty", exception.getMessage());
    }

    @Test
    public void testDescriptionCannotBeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Diet(null, 1500); // Null description
        });
        assertEquals("Diet Description cannot be null", exception.getMessage());
    }


    @Test
    public void testCaloriesZero() {
        Diet diet = new Diet("Intermittent Fasting", 0); // Zero calories
        assertNotNull(diet);
        assertEquals("Intermittent Fasting", diet.getDescription());
        assertEquals(0, diet.getCalories());
    }

    @Test
    public void testLargeCaloriesValue() {
        Diet diet = new Diet("High-Calorie Diet", 10000); // Large calories value
        assertNotNull(diet);
        assertEquals("High-Calorie Diet", diet.getDescription());
        assertEquals(10000, diet.getCalories());
    }
}
