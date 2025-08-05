#!/bin/bash
set -v

curl -v --anyauth  --user admin:admin   \
    -H "Accept: application/json"       \
    'http://localhost:8000/LATEST/documents?uri=/Users/athom555/expr/marklogic/data/cust1.json'

