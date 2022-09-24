package com.andmal;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PageRepo implements PanacheRepository<Page> {
    Uni<List<Page>> findByTitle(String title) {
        return find("title", title).list();
    }
}
