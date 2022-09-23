package com.andmal;

import io.quarkus.grpc.GrpcService;

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
        return Uni.createFrom().item(request.getId())
                .map(p -> PageReply.newBuilder()
                        .setSpace("space")
                        .setBody("aaa")
                        .setTitle("Page 1")
                        .build());
    }

    @Override
    public Multi<PageReply> getPages(PagesRequest request) {
        return Multi.createFrom().item(request.getId())
                .map(p -> PageReply.newBuilder()
                        .setSpace("space")
                        .setBody("aaa")
                        .setTitle("Page 1")
                        .build());
    }
}
