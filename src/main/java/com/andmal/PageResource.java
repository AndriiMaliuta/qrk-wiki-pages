package com.andmal;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Page> createPage(CreatePage createPage) {
        Page page = new Page();
        page.title = createPage.title;
        page.body = createPage.title;
        page.spaceKey = createPage.spaceKey;
        page.parentId = createPage.parentId;
        page.authorId = createPage.authorId;
        page.createdAt = LocalDateTime.now();
        page.lastUpdated = createPage.lastUpdated;
        return pageRepo.persist(page);
    }
}