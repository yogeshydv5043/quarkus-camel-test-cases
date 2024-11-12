package org.test.routes;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class InQueueRouteTest {

    @Inject
    CamelContext camelContext;

    @Inject
    ProducerTemplate producerTemplate;

    private String expectedData;

    @BeforeEach
    public void setup() throws Exception {
        // Set up a route that sends to a mock endpoint
        camelContext.addRoutes(new InQueueRoute() {
            @Override
            public void configure() throws Exception {
                from("direct:sendDataRoute1")
                        .log("sending data to queue : ${body}")
                        .process(exchange -> {
                            String data = exchange.getIn().getBody(String.class);
                            String file = data + LocalDate.now();
                            exchange.getIn().setBody(file);
                        })
                        .to("mock:amqp:queue:msgQueue");

            }
        });

        // Expected message content
        expectedData = "Test Data" + LocalDate.now();
    }


    @Test
    public void testDataSentToQueue() throws Exception {
        // Set up the mock endpoint to expect a single message with the expected data
        MockEndpoint mockQueue = camelContext.getEndpoint("mock:amqp:queue:msgQueue", MockEndpoint.class);
        mockQueue.expectedMessageCount(1);
        mockQueue.expectedBodiesReceived(expectedData);

        // Send test data to the "direct:sendData" endpoint
        producerTemplate.sendBody("direct:sendDataRoute1", "Test Data");

        // Assert that the mock endpoint received the message as expected
        mockQueue.assertIsSatisfied();

        // Verify that the actual message content matches the expected data
        String actualBody = mockQueue.getExchanges().get(0).getIn().getBody(String.class);
        assertEquals(expectedData, actualBody, "The processed data should match the expected format");
    }
}
