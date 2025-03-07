//package com.elite.school.framework.cache;
//
//import lombok.Value;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManagerFactory;
//import java.net.InetSocketAddress;
//import java.security.KeyStore;
//
//@Configuration
//public class MemcachedClientConfig<MemcachedClient> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedClientConfig.class);
//
//    @Value("${cache.server}")
//    private String cacheServer;
//    @Value("${cache.port}")
//    private int cachePort;
//    @Value("${cache.tls:false}")
//    private boolean cacheTls = false;
//
//    @Bean
//    public MemcachedClient memcachedClient() {
//        try {
//            if (cacheTls) {
//                LOGGER.info("Creating Tls MemCache client (abt-common-util): {}", cacheServer + ":" + cachePort);
//                ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
//                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//                tmf.init((KeyStore) null);
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                sslContext.init(null, tmf.getTrustManagers(), null);
//                connectionFactoryBuilder.setSSLContext(sslContext);
//                connectionFactoryBuilder.setSkipTlsHostnameVerification(true);
//                connectionFactoryBuilder.setAuthWaitTime(5000);
//                connectionFactoryBuilder.setFailureMode(FailureMode.Retry);
//                MemcachedClient memcachedClient = new MemcachedClient(connectionFactoryBuilder.build(), AddrUtil.getAddresses(cacheServer + ":" + cachePort));
//                LOGGER.info("Created elasticache client");
//                memcachedClient.set("test-key", 3600, "this is a test value");
//                LOGGER.info("Value set in elasticache client");
//                return memcachedClient;
//            } else {
//                LOGGER.info("Creating Non-Tls MemCache client (abt-common-util): {}", cacheServer + ":" + cachePort);
//                MemcachedClient memcachedClient = new MemcachedClient(new InetSocketAddress(cacheServer, cachePort));
//                memcachedClient.set("test-key", 3600, "this is a test value");
//                LOGGER.info("Created non-tls elasticache client");
//                return memcachedClient;
//            }
//        } catch (Exception ex) {
//            LOGGER.error("Error creating MemCached Client", ex);
//            throw new RuntimeException("Error creating elasticache client", ex);
//        }
//    }
//}
