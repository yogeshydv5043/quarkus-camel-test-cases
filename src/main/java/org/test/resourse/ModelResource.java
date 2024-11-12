package org.test.resourse;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.test.entity.Model;
import org.test.payload.request.ModelRequest;
import org.test.service.ModelService;

import java.util.List;

@Path("/model")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModelResource {

    @Inject
    private ModelService modelService;

    @POST
    @Path("/")
    public Response addModel(@RequestBody ModelRequest modelRequest) {
        Model model = modelService.addModel(modelRequest);
        return Response.status(Response.Status.CREATED).entity(model).build();
    }


    @GET
    @Path("/{id}")
    public Response getModelById(@PathParam("id") Long id) {
        Model model = modelService.findById(id);
        if (model == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(model).build();
    }

    @GET
    @Path("/")
    public List<Model> getAllModels() {
        return modelService.getModel();
    }

    @GET
    @Path("/name/{name}")
    public Response getModelByName(@PathParam("name") String name) {
        Model model = modelService.findByName(name);
        if (model == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(model).build();
    }

    @GET
    @Path("/course/{course}")
    public List<Model> getModelsByCourse(@PathParam("course") String course) {
        return modelService.findByCourse(course);
    }

    @PUT
    @Path("/{id}")
    public Response updateModel(@PathParam("id") Long id, ModelRequest modelRequest) {
        Model model = modelService.findById(id);
        if (model == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Model updatedModel = modelService.updateModel(id, modelRequest);
        return Response.ok(updatedModel).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteModel(@PathParam("id") Long id) {
        Model model = modelService.findById(id);
        if (model == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        modelService.deleteModel(id);
        return Response.noContent().build();
    }
}
