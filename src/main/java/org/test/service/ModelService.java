package org.test.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.test.entity.Model;
import org.test.payload.request.ModelRequest;
import org.test.repository.ModelRepository;

import java.util.List;

@ApplicationScoped
public class ModelService {


    @Inject
    private ModelRepository modelRepository;


    @Transactional
    public Model addModel(ModelRequest modelRequest) {
        Model model = new Model();
        model.setName(modelRequest.getName());
        model.setCourse(modelRequest.getCourse());
        modelRepository.persist(model);
        return model;
    }


    public Model findByName(String name) {
        return modelRepository.findByName(name);
    }

    public List<Model> findByCourse(String name) {
        return modelRepository.findByCourse(name);
    }

    public Model findById(Long id) {
        return modelRepository.findById(id);
    }

    @Transactional
    public Model updateModel(Long id, ModelRequest modelRequest) {
        Model model = modelRepository.findById(id);
        model.setName(modelRequest.getName());
        model.setCourse(modelRequest.getCourse());
        modelRepository.persist(model);
        return model;
    }

    public List<Model> getModel() {
        return modelRepository.findAll().list();
    }

    @Transactional
    public void deleteModel(Long id) {
        Model model = modelRepository.findById(id);
        modelRepository.delete(model);
    }

}
