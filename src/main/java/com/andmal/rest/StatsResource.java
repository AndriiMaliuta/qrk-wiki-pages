package com.andmal.rest;

import com.andmal.PageRepo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RequestScoped
@Path("/rest/api/stats/")
public class StatsResource {

    @Inject
    PageRepo pageRepo;

    @GET
    @Path("pages")
    public long getPagesSize() {
        return pageRepo.findAll().count().await().indefinitely();
    }

    @GET
    @Path("spaces")
    public long getSpacesSize() {
        return 0; // todo
    }

}
