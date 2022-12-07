package com.andmal.rest;

import com.andmal.PageRepo;
import com.andmal.model.CreatePage;
import com.andmal.model.Page;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
@Path("/rest/api/pages")
public class PageResource {
    private final static Logger LOGGER = LoggerFactory.getLogger(PageResource.class);
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