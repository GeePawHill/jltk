#!/bin/sh
root=$1
home=$2
scripts=$3

# Create the home/.wtc/KEY if it doesn't exist, and get the key
key=$($scripts/wtc_key_manager.sh $root $home)

# The destination file for the commit message
home_wtc="$home/.wtc"
email=$(git config user.email | cut -f1 -d"@")
branch=$(git rev-parse --abbrev-ref HEAD)
delimiter="_"
extension=".wtc"
log="$home_wtc/$key/$branch$delimiter$email$extension"

# Emit the commit message
$scripts/wtc_commit_log.sh | base64 --wrap=0 >>$log

# Name the final file by "branch_email_timestamp.wtc"
unique=$(date +"%Y%m%d%H%M%S")
filename=$branch$delimiter$email$delimiter$unique$extension

# move the log to the local .wtc
# the caller will quietly add it to the commit
root_wtc="$root/.wtc"
mv $log "$root_wtc/$filename"
