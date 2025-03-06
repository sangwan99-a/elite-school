package com.elite.school.framework.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class MemcachedConfig implements CachingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedConfig.class);

    @Autowired
    private MemcachedClient memcachedClient;

    /*@Bean
    @Override
    public CacheManager cacheManager() {
        return new MemcachedCacheManager(memcachedClient());
    }*/

    @Bean
    @Override
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        Map<String, Cache> caches = new HashMap<>();
        caches.putIfAbsent("abt_master_data_v3", new MemcachedCache("abt_master_data_v3", memcachedClient, CommonConstant.cacheExpiryInMinutes * 60)); // TTL of 30 minutes
        caches.putIfAbsent("deny_list", new MemcachedCache("deny_list", memcachedClient, 30)); // TTL of 30 seconds
        caches.putIfAbsent("username_userid_cache", new MemcachedCache("username_userid_cache", memcachedClient, 30 * 60)); // TTL of 30 minutes
        caches.putIfAbsent("operator_username_userid_cache", new MemcachedCache("operator_username_userid_cache", memcachedClient, 30 * 60)); // TTL of 30 minutes
        cacheManager.setCaches(caches.values());
        LOGGER.info("Created SimpleCacheManager successfully");
        return cacheManager;
    }
}