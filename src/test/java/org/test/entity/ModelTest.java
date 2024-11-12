package org.test.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModelTest {

    @Order(1)
    @Test
    void getId() {
        Model model = new Model();
        model.setId(1L);

        assertEquals(1L, model.getId(), "ID should be 1");
    }

    @Order(2)
    @Test
    void getName() {
        Model model = new Model();
        model.setName("John Doe");

        assertEquals("John Doe", model.getName(), "Name should be John Doe");
    }

    @Order(3)
    @Test
    void getCourse() {
        Model model = new Model();
        model.setCourse("Java");

        assertEquals("Java", model.getCourse(), "Course should be Java");
    }

    @Order(4)
    @Test
    void setId() {
        Model model = new Model();
        model.setId(1L);

        assertEquals(1L, model.getId(), "ID should be 1");
    }

    @Order(5)
    @Test
    void setName() {
        Model model = new Model();
        model.setName("John Doe");

        assertEquals("John Doe", model.getName(), "Name should be John Doe");
    }

    @Order(6)
    @Test
    void setCourse() {
        Model model = new Model();
        model.setCourse("Java");

        assertEquals("Java", model.getCourse(), "Course should be Java");
    }
}