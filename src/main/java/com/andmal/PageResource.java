package com.andmal;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/rest/api/pages")
public class PageResource {
    @Inject
    PageRepo pageRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PanacheEntityBase>> pages() {
        return Page.listAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Page> getPageById(@PathParam("id") long id) {
        return pageRepo.findById(id);
    }
}