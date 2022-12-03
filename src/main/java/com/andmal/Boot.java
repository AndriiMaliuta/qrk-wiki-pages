package com.andmal;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.time.LocalDateTime;

@ApplicationScoped
public class Boot {
    private static final Logger LOG = LoggerFactory.getLogger(Boot.class);
    @Inject PageRepo pageRepo;

    void onStart(@Observes StartupEvent event) {
        LOG.info(">>> pages count is:");
//        Page.count().subscribe().with(c -> LOG.info(c.toString()));
        LOG.info(Page.count().await().indefinitely().toString());
//        System.out.println(Page.findById(147L).await().indefinitely());

        // === CREATE pages
        LOG.info(">> creating pages...");

        Multi.createFrom().items(3, 4, 5)
                .onItem().transform(n -> {
                    Page page = new Page();
                    page.title = String.format("Page %d", n);
                    page.body = "lorem...";
                    page.spaceKey = "DEV";
                    page.parentId = (long) n;
                    page.authorId = (long) n;
                    page.createdAt = LocalDateTime.now();
                    page.lastUpdated = LocalDateTime.now();
                    page.persist();
                    return page;
                })
                .select().first(3)
                .onFailure().recoverWithItem(new Page())
                .subscribe().with(System.out::println);

//        Multi.createFrom().items(3, 4, 5)
//                .onItem().transform(n -> {
//                    Page page = new Page();
//                    page.title = String.format("Page %d", n);
//                    page.body = "lorem...";
//                    page.spaceKey = "DEV";
//                    page.parentId = (long) n;
//                    page.authorId = (long) n;
//                    page.createdAt = LocalDateTime.now();
//                    page.lastUpdated = LocalDateTime.now();
//                    return page;
//                })
//                .select().first(3)
//                .onFailure().recoverWithItem(new Page())
//                .subscribe().with(System.out::println);

//        Page.list("select distinct from pages where id < ?", 150L).subscribe().with(i -> System.out.println(i));
        Page.findAll().list().subscribe().with(System.out::println, Throwable::printStackTrace);
    }

    @ReactiveTransactional
    private void createPage(int n) {
        Page page = new Page();
        page.title = String.format("Page %d", n);
        page.body = "lorem...";
        page.spaceKey = "DEV";
        page.parentId = (long) n;
        page.authorId = (long) n;
        page.createdAt = LocalDateTime.now();
        page.lastUpdated = LocalDateTime.now();
        pageRepo.persist(page).await().indefinitely();
    }

    @ReactiveTransactional
    private void createPages() {

        Multi.createFrom().generator(() -> 1, (n, emitter) -> {
            int next = n + 1;
            if (n <= 10) {
                Page page = new Page();
//                page.id = (long) n;
                page.title = String.format("Page %d", n);
                page.body = "lorem...";
                page.spaceKey = "DEV";
                page.parentId = (long) n;
                page.authorId = (long) n;
                page.createdAt = LocalDateTime.now();
                page.lastUpdated = LocalDateTime.now();
                Uni<Page> savedPage = pageRepo.persist(page);
//                Page.persist(page).subscribe().with(System.out::println);
//                LOG.info(savedPage.toString());
                emitter.emit(next);
            } else {
                emitter.complete();
            }
            return next;
        }).subscribe().with(System.out::println);

//        Multi.createFrom().items((e) -> {
//            for (int i = 2; i < 10; i++) {
//
//            }
//        }).subscribe();
    }
}
