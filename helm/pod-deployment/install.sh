#!/usr/bin/env bash

#helm install --dry-run --debug httpd $(dirname $0)
helm install httpd $(dirname $0)