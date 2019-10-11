#!/usr/bin/env bash
mvn clean package
docker build -t config:1.20 .