package com.andmal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pages")
public class PageResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String pages() {
        return "Pages here";
    }
}