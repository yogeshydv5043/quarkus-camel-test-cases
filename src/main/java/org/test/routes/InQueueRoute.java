package org.test.routes;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import java.time.LocalDate;


@ApplicationScoped
public class InQueueRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("direct:sendData")
                .routeId("route-1")
                .log(LoggingLevel.INFO, "sending data to queue : ${body}")
                .process(exchange -> {
                    String data = exchange.getIn().getBody(String.class);

                    exchange.getIn().setBody(data);
                })
                .to("amqp:queue:msgQueue");
    }

}
