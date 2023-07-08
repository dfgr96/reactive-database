package com.example.controllers;

import com.example.services.ReadJson;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;

@Path("/api")
public class ApiPracticeController {

    @Inject
    ReadJson readJson;

    @GET
    @Path("/first-get/{workOut}")
    public Object invokeFirstGet(@PathParam("workOut")String workOut) throws IOException, ParseException {
        return readJson.getWorkOut(workOut);
    }

}
