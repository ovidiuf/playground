package com.novaordis.playground.infinispan;

import org.infinispan.Cache;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public interface CacheAccess
{
    /**
     * @throws IllegalArgumentException - no cache with the given name found
     */
    Set<Object> getKeys(String cacheName) throws IllegalArgumentException;

    /**
     * @throws IllegalArgumentException - no cache with the given name found
     */
    int size(String cacheName) throws IllegalArgumentException;

    /**
     * May return null.
     *
     * @throws IllegalArgumentException - no cache with the given name found
     */
    Object get(String cacheName, Object key) throws IllegalArgumentException;

    /**
     * @throws IllegalArgumentException - no cache with the given name found
     */
    void put(String cacheName, Object key, Object value) throws IllegalArgumentException;

    List<Future<Long>> submitDistributable(String cacheName,
                                           Callable<Long> callable,
                                           String... keys) throws IllegalArgumentException;

    Cache getCache(String cacheName);

}
