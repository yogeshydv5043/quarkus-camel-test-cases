package org.test.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class OutQueue extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("amqp:queue:msgQueue")
                .log(LoggingLevel.INFO, "Received queue  Data  : ${body}");
    }
}
