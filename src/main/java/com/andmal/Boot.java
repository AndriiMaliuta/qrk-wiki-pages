package com.andmal;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class Boot {
    private static final Logger LOG = LoggerFactory.getLogger(Boot.class);
    private final PageRepo pageRepo;
    private final PgPool pgClient;

    @Inject
    public Boot(PageRepo pageRepo, PgPool client) {
        this.pageRepo = pageRepo;
        this.pgClient = client;
    }

    void onStart(@Observes StartupEvent event) {
        LOG.info(">>> pages count is:");
//        Page.count().subscribe().with(c -> LOG.info(c.toString()));
        LOG.info(Page.count().await().indefinitely().toString());
//        System.out.println(Page.findById(147L).await().indefinitely());

        // === CREATE pages
        LOG.info(">> creating pages...");

//        createPage(31);

//        createPagesClient(8000, 9000);

        // === GET pages
        getPagesBoot();


        // === create Pages manually

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
//                    page.persist();
//                    return page;
//                })
//                .select().first(3)
//                .onFailure().recoverWithItem(new Page())
//                .subscribe().with(System.out::println);


//        Page.list("select distinct from pages where id < ?", 150L).subscribe().with(i -> System.out.println(i));
//        Page.findAll().list().subscribe().with(System.out::println, Throwable::printStackTrace);
    }

    private void getPagesBoot() {
        this.pgClient
                .query("SELECT * FROM pages")
                .execute()
                .onItem().transformToMulti(
                        rs -> Multi.createFrom().items(() -> StreamSupport.stream(rs.spliterator(), false))
                ).log("... getting page ...")
                .map(this::rowToPage).subscribe().with(
                        item -> System.out.println(item),
                        failure -> System.out.println("Failed with " + failure),
                        () -> System.out.println("Completed"));
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
        pgClient.preparedQuery("insert into pages(id, title, body, space_key, parent_id, author_id, created_at, last_updated) " +
                        "values ($1, $2, $3, $4, $5, $6, $7, $8)")
                .execute(Tuple.tuple(List.of(n, page.title, page.body, page.spaceKey, page.parentId, page.authorId, page.createdAt, page.lastUpdated)))
                .map(RowSet::iterator)
                .subscribe().with(System.out::println);
//        pageRepo.persist(page).subscribe().with(System.out::println);
    }

    @ReactiveTransactional
    private void createPagesClient(int from, int to) {
        Multi.createFrom().generator(() -> from, (n, emitter) -> {
//            if (pageRepo.findAll().stream().filter(p -> p.id == (long) n).collect().)
            String SPACE_KEY = "DEV";
            int next = n + 1;
            if (n <= to) {
                Page page = new Page();
//                page.id = (long) n;
                page.title = String.format("Page %d", n);
                page.body = "lorem.ipsum dolor sit .....";
                page.spaceKey = SPACE_KEY;
                page.parentId = (long) n;
                page.authorId = (long) n;
                page.createdAt = LocalDateTime.now();
                page.lastUpdated = LocalDateTime.now();
//                Uni<Page> savedPage = pageRepo.persist(page);
//                Page.persist(page).subscribe().with(System.out::println);
//                LOG.info(savedPage.toString());
                pgClient.preparedQuery("insert into pages(id, title, body, space_key, parent_id, author_id, created_at, last_updated) " +
                                "values ($1, $2, $3, $4, $5, $6, $7, $8)")
                        .execute(Tuple.tuple(List.of(n, page.title, page.body, page.spaceKey, page.parentId, page.authorId, page.createdAt, page.lastUpdated)))
                        .map(RowSet::iterator)
                        .subscribe().with(System.out::println);
                emitter.emit(next);
            } else {
                emitter.complete();
                LOG.info("[ \uD83D\uDC4C ]");
            }
            return next;
        }).subscribe().with(System.out::println);


    }

    private Page rowToPage(Row row) {
        return Page.of(row.getLong("id"),
                row.getString("title"),
                row.getString("body"),
                row.getString("space_key"),
                row.getLong("author_id"),
                row.getLong("parent_id"),
                row.getLocalDateTime("created_at"),
                row.getLocalDateTime("last_updated"));
    }

}

