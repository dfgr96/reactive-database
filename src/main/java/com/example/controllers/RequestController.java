package com.example.controllers;

import com.example.data.RequestService;
import com.example.dto.RequestEntity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/request")
public class RequestController {

    @Inject
    RequestService requestService;

    @Inject
    MySQLPool client;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<RequestEntity> request() {
        return requestService.findAll(client);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getOne(@PathParam("id") Long id) {
        return requestService.findById(client, id)
                .onItem().transform(request -> request != null ? Response.ok(request) : Response.status(Response.Status.NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }


    @POST
    public Uni<Response> create(RequestEntity requestEntity) {
        System.out.println("uid = " + requestEntity.getUid());
        System.out.println("id = " + requestEntity.getId());
        return requestService.save(client,requestEntity)
                .onItem().transform(id -> URI.create("/request/" + id))
                .onItem().transform(uri -> Response.created(uri).build());
    }

    @PUT
    public Uni<Response> update(RequestEntity requestEntity) {
        System.out.println("uid = " + requestEntity.getUid());
        System.out.println("id = " + requestEntity.getId());
        return requestService.update(client,requestEntity)
                .onItem().transform(result -> Response.ok(result).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(Long id) {
        return requestService.delete(client, id)
                .onItem().transform(deleted -> deleted ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

}
