package com.equitas.Service;

import com.equitas.Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    Vertx vertx;
    WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.create(vertx, new WebClientOptions()
                .setDefaultHost("localhost")
                .setDefaultPort(8090));
    }

    @GET
    public Uni<List<User>> getUsers() {
        ObjectMapper mapper = new ObjectMapper();
        return webClient.get("/Equitas/Banking/getUsers")
                .send()
                .onItem()
                .transform(resp -> {
                    String body = resp.bodyAsString();
                    try {
                        return mapper.readValue(body,
                                TypeFactory.defaultInstance().constructCollectionType(List.class,
                                        User.class));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

    }
}
