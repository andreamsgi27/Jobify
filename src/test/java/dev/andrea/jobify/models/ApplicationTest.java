package dev.andrea.jobify.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApplicationTest {

    private Application application;
    private User user;
    private JobType jobType;

    @BeforeEach
    void setUp() {
        user = new User(1L, "john_doe", "password123", "john@example.com");
        jobType = new JobType(1L, "Full-time");
        application = new Application(1L, user, "Company A", "Software Developer", "New York", 
        "Java, Spring", jobType, 60000, "http://joblink.com", "Great opportunity");
    }

    @Test
    void testApplicationCreation() {
        assertNotNull(application);
        assertEquals(1L, application.getApplicationId());
        assertEquals("Company A", application.getCompany());
        assertEquals("Software Developer", application.getPosition());
        assertEquals("New York", application.getLocation());
        assertEquals("Java, Spring", application.getRequirements());
        assertNotNull(application.getJobType());
        assertEquals(60000, application.getSalary());
        assertEquals("http://joblink.com", application.getLink());
        assertEquals("Great opportunity", application.getNotes());
    }

    @Test
    void testApplicationWithUserAndJobType() {
        assertNotNull(application.getUser());
        assertEquals("john_doe", application.getUser().getUsername());
        assertNotNull(application.getJobType());
    }
    
    @Test
    void testDefaultConstructor() {
        Application defaultApplication = new Application();
        assertNull(defaultApplication.getApplicationId());
        assertNull(defaultApplication.getCompany());
        assertNull(defaultApplication.getPosition());
        assertNull(defaultApplication.getLocation());
        assertNull(defaultApplication.getRequirements());
        assertNull(defaultApplication.getJobType());
        assertEquals(0, defaultApplication.getSalary());
        assertNull(defaultApplication.getLink());
        assertNull(defaultApplication.getNotes());
    }
}