package com.elite.school.framework.cache;

public class MemcachedCache implements Cache {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedCache.class);

    private final String name;
    private final MemcachedClient memcachedClient;
    private final int ttl;
    // cache expiry in minutes in

    public MemcachedCache(String name, MemcachedClient memcachedClient, int ttl) {
        this.name = name;
        this.memcachedClient = memcachedClient;
        this.ttl = ttl;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return memcachedClient;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = memcachedClient.get(generateKey(key));
        LOGGER.trace("Retrieving data from memcached server key = {} ",key);
        return (value != null) ? new SimpleValueWrapper(value) : null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }


    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        LOGGER.trace("Putting data in memcached server key = {}",key);
        memcachedClient.set(generateKey(key), ttl, value); // Set expiration time in seconds (e.g., 1 hour)
    }

    @Override
    public void evict(Object key) {
        memcachedClient.delete(generateKey(key));
    }

    @Override
    public void clear() {
        LOGGER.trace("Clearing data from memcached server");
        memcachedClient.flush();
        // Not supported directly in Memcached. You may implement logic to clear the cache if required.
    }

    private String generateKey(Object key) {
        return name + ":" + key;
    }
}
