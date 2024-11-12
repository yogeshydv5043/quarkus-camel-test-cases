package org.test.routes;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class OutQueueTest {

    private static final Logger log = LoggerFactory.getLogger(OutQueueTest.class);
    @Inject
    private CamelContext camelContext;

    @Inject
    private ProducerTemplate producerTemplate;

    @BeforeEach
    public void setup() throws Exception {
        // Set up a route that sends to a mock endpoint
        camelContext.addRoutes(new OutQueue() {
            @Override
            public void configure() throws Exception {
                from("amqp:queue:sendDataRoute")
                        .log("sending data to queue : ${body}")
                        .to("mock:log");
            }
        });
    }

    @Test
    public void receivedToQueue() throws Exception {

        String SendData = "Hello World";
        // Set up the mock endpoint to expect a single message with the expected data
        MockEndpoint mockQueue = camelContext.getEndpoint("mock:log", MockEndpoint.class);
        mockQueue.expectedMessageCount(1);
        mockQueue.expectedBodiesReceived(SendData);

        // Send test data to the "amqp:queue:sendDataRoute" endpoint
        producerTemplate.sendBody("amqp:queue:sendDataRoute", SendData);
        System.out.println("Sending Data : " + SendData);

        // Assert that the mock endpoint received the message as expected
        mockQueue.assertIsSatisfied();

        // Verify that the actual message content matches the expected data
        String actualBody = mockQueue.getExchanges().get(0).getIn().getBody(String.class);
        System.out.println("Received Data : " + actualBody);
        assertEquals(SendData, actualBody, "The processed data should match the expected format");
    }
}