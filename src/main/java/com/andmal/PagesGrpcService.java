package com.andmal;

import io.quarkus.grpc.GrpcService;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@GrpcService
public class PagesGrpcService implements PagesGrpc {

//    @Override
//    public Uni<PagesReply> sayHello(PagesRequest request) {
//        return Uni.createFrom().item(request.getId())
//                .map(p -> PagesReply.newBuilder()
//                        .setTitle("Page 1")
//                        .setBody("lorem ...")
//                        .setSpace("space A")
//                        .build());
//    }

    @Override
    public Uni<PageReply> getPage(PagesRequest request) {
        Long id = Long.parseLong(request.getId());
        Uni<PanacheEntityBase> page = Page.findById(id);
        Page.find("id", id);
        return Uni.createFrom().item(page)
                .map(p -> PageReply.newBuilder()
                        .setSpace("SPACE 1")
                        .setBody("aaa")
                        .setTitle("Page 1")
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
