import org.example.fitness_app_fx.Person;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testPersonConstructor() {
        // Create a Person object with sample data
        Person person = new Person("John", "Doe", 180, 70, "111-222-3333", "user", "1");

        // Assert that the fields are set correctly
        assertEquals("John", person.getFirstName(), "First name should be John");
        assertEquals("Doe", person.getLastName(), "Last name should be Doe");
        assertEquals(180, person.getHeight(), "Height should be 180 cm");
        assertEquals(70, person.getWeight(), "Weight should be 70 kg");
        assertEquals("111-222-3333", person.getPhone(), "Contact should be 111-222-3333");
        assertEquals("user", person.getRole(), "Role should be user");
    }


    @Test
    public void testInvalidHeight() {
        // Test invalid height (negative value)
        assertThrows(IllegalArgumentException.class, () -> {
            new Person("John", "Doe", -180, 70, "111-222-3333", "user", "1");
        }, "Height cannot be negative");
    }

    @Test
    public void testInvalidWeight() {
        // Test invalid weight (negative value)
        assertThrows(IllegalArgumentException.class, () -> {
            new Person("John", "Doe", 180, -70, "111-222-3333", "user", "1");
        }, "Weight cannot be negative");
    }

    @Test
    public void testEmptyFirstName() {
        // Test empty first name
        Person person = new Person("", "Doe", 180, 70, "111-222-3333", "user", "1");
        assertEquals("", person.getFirstName(), "First name should be empty");
    }

    @Test
    public void testEmptyLastName() {
        // Test empty last name
        Person person = new Person("John", "", 180, 70, "111-222-3333", "user", "1");
        assertEquals("", person.getLastName(), "Last name should be empty");
    }
}
