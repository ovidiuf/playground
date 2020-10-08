{{- define "sub-template-root-dict-manipulation.generic" }}
apiVersion: v1
kind: ConfigMap
metadata:
  name: {{ .SubTemplateArgs.Name }}
  labels:
    release: {{ .Release.Name }}
{{- end }}