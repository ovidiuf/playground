{{- define "sub-template.a" }}
labels:
  generator: helm
  date: {{ now | htmlDate }}
  chart: {{ .Chart.Name }}
  size: square
{{- end }}