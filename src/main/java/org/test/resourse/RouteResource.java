package org.test.resourse;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;

@Path("/route")
public class RouteResource {

    @Inject
    private ProducerTemplate producerTemplate;

    @Path("/")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response sendToQueue(String data) {
        String result = producerTemplate.requestBody("direct:sendData", data, String.class);
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
