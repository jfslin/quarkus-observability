package org.acme;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
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
        int[] arr = {10};

        Gauge.builder("xvalue", arr, a -> arr[0])
                .baseUnit("X")
                .description("Some random x")
                .tag("my_key", "x")
                .register(registry);
    }

    private final LongCounter counter;

    public GreetingResource(Meter meter) {
        counter = meter.counterBuilder("hello-metrics")
                .setDescription("hello-metrics")
                .setUnit("invocations")
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        counter.add(1);
        log.info("hello-metrics");

        return "Hello from Quarkus REST";
    }
}
