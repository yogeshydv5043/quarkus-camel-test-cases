package org.test.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.test.entity.Model;

import java.util.List;

@ApplicationScoped
public class ModelRepository implements PanacheRepository<Model> {

    public List<Model> findByCourse(String course) {
        return list("course", course);
    }

    public Model findByName(String name) {
        return find("name", name).firstResult();
    }




}
