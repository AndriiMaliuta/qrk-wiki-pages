package com.andmal;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Path("/misc/")
public class MiscControllers {
    @Inject
    PageRepo pageRepo;

    @Path("stats")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getStats() throws IOException {
        String fileData = Files.readString(java.nio.file.Path.of("src/main/resources/META-INF/resources/stats.html"));
        return fileData;
    }
}
