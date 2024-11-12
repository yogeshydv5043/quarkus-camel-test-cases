package org.test.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.test.entity.Model;
import org.test.payload.request.ModelRequest;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ModelRepositoryTest {

    @Inject
    private ModelRepository modelRepository;

    @Test
    @Transactional
    void findByCourse() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Mohan");
        modelRequest.setCourse("Pythan");

        Model model = new Model();
        // model.setId(8L);
        model.setName(modelRequest.getName());
        model.setCourse(modelRequest.getCourse());

        modelRepository.persist(model);

        modelRepository.findByCourse(model.getCourse());
        assertNotNull(model.getCourse(), "Model course should not be null after persisting");

    }

    @Test
    @Transactional
    void findByName() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Jhon");
        modelRequest.setCourse("C++");

        Model model = new Model();
        // model.setId(8L);
        model.setName(modelRequest.getName());
        model.setCourse(modelRequest.getCourse());
        modelRepository.persist(model);

        modelRepository.findByName(model.getName());
        assertNotNull(model.getName(), "Model Name should not be null after persisting");

    }

    @Test
    void findById() {
        Model model = modelRepository.findById(1L);
        assertNotNull(model.getId(), "Model Id should not be null or existing");
    }

    @Test
    @Transactional
    void createModel() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Pooja");
        modelRequest.setCourse("Node");

        Model model = new Model();
        model.setName(modelRequest.getName());
        model.setCourse(modelRequest.getCourse());
        modelRepository.persist(model);

        assertNotNull(model.getId(), "Model should not be null after persisting");
    }

    @Test
    @Transactional
    void deleteModel() {
        Model model = new Model();
        model.setName("John Mark");
        model.setCourse("Java");

        modelRepository.persist(model);
        assertNotNull(model.getId(), "Model ID should not be null after persisting");

        modelRepository.deleteById(model.getId());

        Model deletedModel = modelRepository.findById(model.getId());
        assertNull(deletedModel, "Deleted model should not be found");

    }

    @Test
    @Transactional
    void getAllModels() {

        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Pooja");
        modelRequest.setCourse("Node");

        Model model = new Model();
        model.setName(modelRequest.getName());
        model.setCourse(modelRequest.getCourse());
        modelRepository.persist(model);

        Model model1 = new Model();
        model1.setName("Rohit");
        model1.setCourse("Java");
        modelRepository.persist(model1);

        assertTrue(modelRepository.findAll().list().size() > 1, "Should find more than one model");

    }

    @Test
    @Transactional
    void updateModel() {
        Model model = new Model();
        model.setName("John");
        model.setCourse("Java");

        modelRepository.persist(model);

        model.setName("John Smith");
        modelRepository.persist(model);

        // Verify update
        Model updatedModel = modelRepository.findById(model.getId());
        assertNotNull(updatedModel, "Updated model should be found");
        assertEquals("John Smith", updatedModel.getName(), "Name should be updated to John Smith");

    }


}