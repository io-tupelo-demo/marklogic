#!/bin/bash
set -v

mlcp/bin/mlcp.sh import   \
 -host localhost          \
 -port 8000               \
 -username admin          \
 -password admin          \
 -input_file_path   ./data-834

