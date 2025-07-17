#!/bin/bash
set -v

curl --anyauth --user admin:admin   \
  -X GET -i \
  'http://localhost:8000/LATEST/documents?uri=/Users/alan/expr/marklogic/data/cust1.json'

