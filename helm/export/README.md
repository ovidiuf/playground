# references.yaml import-values example

The "a" chart is the main chart, and "b" is a dependency.

```bash

helm delete --purge et
helm dependencies update a; helm install --name et ./a

```
