package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;

public class LgtmTestBase {
    @ConfigProperty(name = "grafana.endpoint")
    String endpoint; // NOTE -- injected Grafana endpoint!

    @Test
    public void testTracing() {
        String response = RestAssured.get("/api/poke?f=100").body().asString();
        System.out.println(response);
        GrafanaClient client = new GrafanaClient(endpoint, "admin", "admin");
        Awaitility.await().atMost(61, TimeUnit.SECONDS).until(
                client::user,
                u -> "admin".equals(u.login));
        Awaitility.await().atMost(61, TimeUnit.SECONDS).until(
                () -> client.query("xvalue_X"),
                result -> !result.data.result.isEmpty());
    }

}