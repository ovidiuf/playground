{{- define "sub-template.generic-config-map-2" }}
apiVersion: v1
kind: ConfigMap
metadata:
  name: {{ .name }}
data:
  color: {{ .color }}
{{- end }}