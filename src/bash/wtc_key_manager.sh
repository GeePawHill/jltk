#!/bin/sh
root_wtc="$1/.wtc"
home_wtc="$2/.wtc"
wtc_key_file="$root_wtc/wtc.key"
mkdir -p $root_wtc
mkdir -p $home_wtc
if [ ! -f $wtc_key_file ]; then
  wtc_key=$(date +"%Y%m%d%H%M%S")
  echo $wtc_key >$wtc_key_file
fi
wtc_key=$(cat $wtc_key_file)
if [ ! -d $home_wtc ]; then
    mkdir -p $home_wtc
fi
if [ ! -d $home_wtc/$wtc_key ]; then
  mkdir -p "$home_wtc/$wtc_key"
  touch "$home_wtc/$wtc_key/wtc.log"
fi
cat $wtc_key_file
