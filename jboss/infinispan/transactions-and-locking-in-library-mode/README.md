# JDG Transactions and Locking in Library Mode

# Overview

An implementation that can be used to experiment with JDG transactions and locking in library mode.
Deploys as a servlet that bootstraps an EmbeddedCacheManager. The JDG dependencies must be previously
deployed as a module and available. Commands can be sent to it using bash shell wrappers available
in the ./bin directory (put, get, lock, etc). The JDG cluster will be bootstraped and form after 
the first invocation.


# Build

```
mvn clean install
```

# Use

````
Usage:

    put|get|lock [options] <node-index> <key> [value]

The node index is 1-based.

By default, all cache operations are executed within a JTA transaction. To disable this behavior
and execute operations in a non-transactional context, use --non-transactional option (see below).

Options:

    -s <sleep-in-seconds> - sleeps after the cache operation. By default, there is no sleep, the
      JTA transaction is committed as soon as the cache operation completes. This could be useful
      to experiment with timeouts, and more importantly, to slow down the system so humans could
      interact with it from command line.

    --non-transactional - executes the cache operation in a non-transactional context. By default
      all cache operations are executed within a JTA transaction.

````

