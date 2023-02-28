package com.equitas.Controller;


import com.equitas.Model.ResponseWrapper;
import com.equitas.Model.User;
import com.equitas.Service.UserService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/mob/v1")
public class UserController {


    @Inject
    private UserService service;

    @GET
    @Path("/user")
    public Uni<ResponseWrapper<List<User>>> getUsers() {
        return service.getUsers().map(userDetails ->
                new ResponseWrapper<>(true, userDetails));
    }

}
