package com.andmal;

import io.quarkus.grpc.GrpcService;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@GrpcService
public class PagesGrpcService implements PagesGrpc {
    private static final Logger LOG = LoggerFactory.getLogger(PagesGrpcService.class);
    @Inject
    PageRepo pageRepo;
    @Override
    public Uni<PageReply> getPage(PagesRequest request) {
        LOG.info(">> performing gRPC GET_PAGE request");
        Long id = request.getId();
        LOG.info(">> ID is " + id);
//        Page page = Page.<Page>findById(id).await().indefinitely();
        Uni<Page> pageUni = Page.<Page>findById(id);

        return pageUni.map(p -> PageReply.newBuilder()
                .setId(p.id)
                .setTitle(p.title)
                .setBody(p.body)
                .setSpace(p.spaceKey)
                .setAuthorId(p.authorId)
                .setParentId(p.parentId)
                .setCreatedAt(p.createdAt.toString())
                .setLastUpdated(p.lastUpdated.toString())
                .build());
    }

    @Override
    public Multi<PageReply> getPages(PagesRequest request) {
//        return Multi.createFrom().emitter(i -> {
//            i.emit(pageReply);
//            i.emit(pageReply);
//            i.emit(pageReply);
//            i.emit(pageReply);
//            i.emit(pageReply);
//            i.complete();
//        });

//        return Page.<Page>listAll().toMulti()
//                .map(pe -> PageReply.newBuilder()
//                        .setTitle()
//                        .buildPartial());
    return pageRepo.getAll()
            .map(p -> PageReply.newBuilder()
                    .setId(p.id)
                    .setTitle(p.title)
                    .setBody(p.body)
                    .setSpace(p.spaceKey)
                    .setAuthorId(p.authorId)
                    .setParentId(p.parentId)
                    .setCreatedAt(p.createdAt.toString())
                    .setLastUpdated(p.lastUpdated.toString())
                    .buildPartial());
    }
}
