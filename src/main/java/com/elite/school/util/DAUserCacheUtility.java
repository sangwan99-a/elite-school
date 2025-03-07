//package com.elite.school.util;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DAUserCacheUtility {
//    private static final Logger LOGGER = LoggerFactory.getLogger(DAUserCacheUtility.class);
//
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MemcachedClient memcachedClient;
//
//    private static final Integer DA_User_Cache_TTL_IN_MINUTES = 57;
//
//    public void putDAUserInCache(String key, Object value) {
//
//        if (key != null && value != null) {
//            LOGGER.info("Putting DA User data in memcached server key = {}", key);
//            memcachedClient.set(key, DA_User_Cache_TTL_IN_MINUTES * 60, value);
//            LOGGER.info("DA User data successfully saved in memcached server value {}", value);
//        } else {
//            throw new RuntimeException("Please enter valid key and value");
//        }
//
//
//    }
//
//    public CustomDAUserInfo getDAUserInfoFromCache(String key) {
//        if (key != null) {
//            Object cacheResult = memcachedClient.get(key);
//            if (cacheResult != null) {
//                LOGGER.info("Retrieving DA User data from memcached server key = {} ", key);
//                return objectMapper.convertValue(cacheResult, new TypeReference<>() {
//                });
//            }
//        } else {
//            return null;
//        }
//        return null;
//    }
//}
