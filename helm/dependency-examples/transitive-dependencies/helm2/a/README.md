The top of the transient dependency graph:

```
 a
 |
 b
 |
 c 
```

Operations:

```bash
helm dependency update ./a
helm install --name a ./a/
helm delete --purge a

```