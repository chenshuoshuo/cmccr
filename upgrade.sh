#!/bin/sh

set -e

echo "开始打包jar"

mvn clean package

mv target/cmccr2-*.jar target/cmccr.jar

echo "打包jar结束"

echo "开始传输jar文件"

rsync -avz --progress -P target/cmccr.jar lqkj@192.168.4.240:/home/lqkj/cmccr -e "ssh -p 1088"

echo "传输jar文件结束"

echo "开始重新服务"

ssh -p 1088 root@192.168.4.240 "service cmccr restart;"

echo "重启服务结束"