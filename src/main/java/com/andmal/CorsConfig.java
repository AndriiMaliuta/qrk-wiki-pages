//package com.andmal;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//
//@Provider
//public class CorsConfig implements ContainerResponseFilter {
//
//    private final Logger log = LoggerFactory.getLogger(CorsConfig.class);
//
//    @Override
//    public void filter(final ContainerRequestContext reqContext,
//                       final ContainerResponseContext cres) throws IOException {
//        cres.getHeaders().add("Access-Control-Allow-Origin","http://localhost:3000");
//        cres.getHeaders().add("Access-Control-Allow-Headers", "accept, origin, authorization, content-type, x-requested-with");
//        cres.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
//        cres.getHeaders().add("Access-Control-Max-Age", "1209600");
//    }
//}
