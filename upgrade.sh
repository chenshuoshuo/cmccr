#!/bin/sh

set -e

echo "开始打包jar"

mvn clean package

mv target/cmccr2-*.jar target/cmccr.jar

echo "打包jar结束"

echo "开始传输jar文件"

rsync -avz --progress -P target/cmccr.jar lqkj2@192.168.4.241:/home/lqkj2/cmccr -e "ssh -p 22"

echo "传输jar文件结束"

echo "开始重新服务"

ssh -p 22 root@192.168.4.241 "service cmccr restart;"

echo "重启服务结束"