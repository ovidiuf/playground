
```bash
helm dependencies update ./postgresql-dynamic-dependency
helm install --name blue ./postgresql-dynamic-dependency
helm delete --purge blue
```