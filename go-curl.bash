#!/bin/bash
set -v

curl -v --anyauth --user admin:admin   \
    -H "Accept: application/json"   \
    'http://localhost:8000/LATEST/search?q=comvex.com'

