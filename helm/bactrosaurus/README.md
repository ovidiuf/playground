# Overview

A Helm chart that deploys an application which contains a ConfigMap.

Simple template replacement is demonstrated:
* {{ .Chart.Description }}
* {{ .Values.size }}
* {{ .Values.characteristics.shape }}
* {{ .Release.Name }}
* {{ .Capabilities.KubeVersion.Major }}
* {{ .Template.Name }}

Function and pipeline usage is also demonstrated:

* {{ quote .Values.characteristics.shape }}
* {{ .Values.characteristics.shape | upper | quote }}
* {{ default "blue" .Values.color }}


It can be installed as:

````bash

helm install ./bactrosaurus
helm delete <release-name>

````