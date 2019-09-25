#!/usr/bin/env bash

find . -name requirements.lock -exec rm {} \;
find . -name '*.tgz' -exec rm {} \;


