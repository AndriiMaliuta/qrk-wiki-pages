package com.andmal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

@QuarkusTest
public class HelloGrpcServiceTest {

    @GrpcClient
    PagesGrpc pagesGrpc;

    @Test
    public void testHello() {
        PageReply reply = pagesGrpc
                .getPage(PagesRequest.newBuilder().setId(1111L).build()).await().atMost(Duration.ofSeconds(5));
        assertEquals("Hello", "Hello");
    }

}
