#!/bin/sh
# emit proper commit entry for log
echo "---"
echo "branch: $(git rev-parse --abbrev-ref HEAD)"
echo "committer: $(git config user.name)"
echo "email: $(git config user.email)"
echo "type: commit"
echo "timestamp: '$(date --iso-8601=seconds)'"
