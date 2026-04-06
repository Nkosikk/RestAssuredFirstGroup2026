package utils;
import com.github.javafaker.Faker;

public class RandomDataGenerator {

    public static Faker faker = new Faker();

    // Generate a random email address
    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    // Generate a random First Name
    public static String generateFirstName() {
        return faker.name().firstName();
    }

    // Generate a random Last Name
    public static String generateLastName() {
        return faker.name().lastName();
    }

}
