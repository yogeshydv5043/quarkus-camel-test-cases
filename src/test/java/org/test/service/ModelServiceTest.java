package org.test.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.entity.Model;
import org.test.payload.request.ModelRequest;
import org.test.repository.ModelRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ModelServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ModelServiceTest.class);
    @Inject
    private ModelService modelService;

    @Inject
    private ModelRepository modelRepository;

    @Test
    @Transactional
    void addModel() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Mayank");
        modelRequest.setCourse("Node");

        Model result = modelService.addModel(modelRequest);
        assertNotNull(result, "The model should not be null");
        assertNotNull(result.getId(), "The model Id should not be null");


        Model persistedModel = modelRepository.findById(result.getId());
        assertNotNull(persistedModel, "The model should not be null");


    }

    @Test
    @Transactional
    void findByName() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Anup");
        modelRequest.setCourse("Java");

        Model model = modelService.addModel(modelRequest);
        assertNotNull(model, "The model should not be null");
        assertNotNull(model.getName(), "The name should not be null");

        Model modelByName = modelService.findByName(model.getName());
        assertNotNull(modelByName, "The model should not be null");
        System.out.println(modelByName);

    }

    @Test
    @Transactional
    void findByCourse() {
        ModelRequest request = new ModelRequest();
        request.setName("Dev");
        request.setCourse("Java");

        Model model = modelService.addModel(request);
        assertNotNull(model);
        assertNotNull(model.getCourse());

        List<Model> list = modelService.findByCourse(request.getCourse());

        assertNotNull(list, "The result list should not be null");
        assertFalse(list.isEmpty(), "The result list should not be empty");
        assertTrue(list.size() > 1, "Expected more than one model in the result");

    }

    @Test
    void findById() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Ram");
        modelRequest.setCourse("Python");

        Model model = modelService.addModel(modelRequest);
        assertNotNull(model, "model should not be null");
        assertNotNull(model.getId(), "model id should not be null");

        Model model1 = modelService.findById(model.getId());

        assertNotNull(model1, "model should not be null");

    }

    @Test
    @Transactional
    void updateModel() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Jhon");
        modelRequest.setCourse("C++");

        assertNotNull(modelRequest, "model should not be null");

        Model model = modelService.addModel(modelRequest);
        assertNotNull(model.getId(), "model Id should not be null");

        ModelRequest updateRequest = new ModelRequest();
        updateRequest.setName("Manoj");
        updateRequest.setCourse("C");

        Model result = modelService.updateModel(model.getId(), updateRequest);

        assertNotNull(result, "Updated model should not be null");
        assertEquals("Manoj", result.getName(), "Name should be updated to 'Manoj'");
        assertEquals("C", result.getCourse(), "Course should be updated to 'C'");

    }

    @Test
    @Transactional
    void getModel() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Jhon");
        modelRequest.setCourse("C");

        Model model = modelService.addModel(modelRequest);
        assertNotNull(model, "model should not be null");

        ModelRequest modelRequest1 = new ModelRequest();
        modelRequest1.setName("Sapna");
        modelRequest1.setCourse("C++");

        Model model1 = modelService.addModel(modelRequest1);
        assertNotNull(model1, "model should not be null");

        List<Model> modelList = modelService.getModel();
        assertNotNull(modelList, "Model list should not be null");
        assertFalse(modelList.isEmpty(), "The result list should not be empty");
        assertTrue(modelList.size() >= 2, "Expected more than one model in the result");

    }

    @Test
    @Transactional
    void deleteModel() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Monty");
        modelRequest.setCourse("Python");

        Model model = modelService.addModel(modelRequest);

        assertNotNull(model.getId(), "model Id should not be null");

        modelService.deleteModel(model.getId());

        Model deletedModel = modelService.findById(model.getId());

        assertNull(deletedModel, "deleteModel should be null");

    }
}