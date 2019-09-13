# Pod Deployment

Framework to experiment with overriding array element values. 

See values.yaml and the "colors" array.

To test:

```bash
helm install --name colors --debug --dry-run ./array-element-variable-override
```

```bash
helm install --name colors --set=complexColors[0].name=fuchsia --debug --dry-run ./array-element-variable-override
```
