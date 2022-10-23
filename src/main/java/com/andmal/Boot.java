package com.andmal;

import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class Boot {
    private static final Logger LOG = LoggerFactory.getLogger(Boot.class);

    void onStart(@Observes StartupEvent event) {
        LOG.info(">>> pages count is:");

//        Page.count().subscribe().with(c -> LOG.info(c.toString()));
        LOG.info(Page.count().await().indefinitely().toString());
        System.out.println(Page.findById(147L).await().indefinitely());

//        Page.list("select distinct from pages where id < ?", 150L).subscribe().with(i -> System.out.println(i));
//        Page.findAll().list().subscribe().with(System.out::println, Throwable::printStackTrace);
    }
}
