package com.andmal;

import io.quarkus.grpc.GrpcService;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class PagesGrpcService implements PagesGrpc {
    private static final Logger LOG = LoggerFactory.getLogger(PagesGrpcService.class);

    @Override
    public Uni<PageReply> getPage(PagesRequest request) {
        Long id = Long.parseLong(request.getId());
//        Page page = Page.<Page>findById(id).await().indefinitely();
        Uni<Page> pageUni = Page.<Page>findById(id);
//        page.subscribe().with(p -> PageReply.newBuilder()
//                .setSpace(p.spaceKey)
//                .setBody(p.body)
//                .setTitle(p.title)
//                .build(), fail -> fail.printStackTrace());

        return pageUni.map(p -> PageReply.newBuilder()
                .setTitle(p.title)
                .setSpace(p.spaceKey)
                .setBody(p.body)
                .build());
    }

    @Override
    public Multi<PageReply> getPages(PagesRequest request) {
        PageReply pageReply = PageReply.newBuilder().setBody("lorem").setSpace("A").setTitle("A").build();
        return Multi.createFrom().emitter(i -> {
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.emit(pageReply);
            i.complete();
        });
//        return Multi.createFrom().item(request.getId())
//                .map(p -> PageReply.newBuilder()
//                        .setSpace("space")
//                        .setBody("aaa")
//                        .setTitle("Page 1")
//                        .build());
    }
}
