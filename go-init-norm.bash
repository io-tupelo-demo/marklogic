#!/bin/bash
set -v

docker \
     run -d -it -p 8000:8000 -p 8001:8001 -p 8002:8002 \
     -e MARKLOGIC_INIT=true \
     -e MARKLOGIC_ADMIN_USERNAME='admin' \
     -e MARKLOGIC_ADMIN_PASSWORD='admin' \
     progressofficial/marklogic-db

