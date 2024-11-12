package org.test.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.rest.RestParamType;


@ApplicationScoped
public class RouteResourceContext extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Global error handling for exceptions
        onException(Exception.class)
                .handled(true)
                .log("An unexpected error occurred: ${exception.message}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .setBody(constant("An unexpected error occurred. Please try again later."));

        // Specific error handling for HTTP-related exceptions
        onException(HttpOperationFailedException.class)
                .handled(true)
                .log("HTTP operation failed: ${exception.message}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setBody(constant("Bad request. Please check your input."));

        // REST configuration
        restConfiguration()
                .component("netty-http")
                .host("localhost")
                .port(9903);

        // Define REST API routes
        rest("/api/v1")
                .post("/sendData")
                .to("direct:sendData");

    }
}
