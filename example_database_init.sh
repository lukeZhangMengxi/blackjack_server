#!/bin/bash

[ -z $(docker images -q my_psql) ] && \
    docker build -t my_psql db || \
    echo "Docker image my_psql already exits!"

[ -z $(docker ps -f 'name=my_psql_instance' --format '{{.Names}}') ] && \
    docker run --rm -d -p 5432:5432 --name my_psql_instance my_psql || \
    echo "Docker container my_psql_instance already running!"

PGPASSWORD=root psql -h localhost -p 5432 -d appdb -U root -a -f db/psql_schema.sql
