#!/bin/sh
nohup sh start.sh > nohup.out 2>&1 &
tail -f nohup.out ../logs/*