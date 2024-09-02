package org.acme;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@Path("/hello")
public class GreetingResource {
    private static final Logger log = Logger.getLogger(GreetingResource.class);

    @Inject
    MeterRegistry registry;

    @PostConstruct
    public void start() {
        Gauge.builder("xvalue", arr, a -> arr[0])
                .baseUnit("X")
                .description("Some random x")
                .tag("my_key", "x")
                .register(registry);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }
}
