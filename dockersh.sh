#!/bin/bash

JOBMS=$1

VERSION=latest

JOBREPO=/data/docker/$1

IMGREPO=xmbaby-test

IMGNAME=$1

DTIME=`date+%Y-%m-%d" "%H":"%M":"%S`

TAG=$2

CONTAINER_NAME=rest-mvc

BRANCH=$3

if[!-d$JOBREPO];then

    echo-e"jar目录不存在，将创建jar目录\n"

mkdir-p$JOBREPO

cp/var/lib/jenkins/workspace/$JOBMS_$BRANCH/target/$JOBMS.jar$JOBREPO/

echo-e"jar已经复制到待创建镜像目录中，等待构建docker镜像\n"

else

    echo-e"时间$DTIME，开始构建docker镜像\n"

fi

echo-e"构建docker镜像前,删除之前的容器、镜像."

IMAGE_ID=`docker images |grep$JOBMS|awk'{print $3}'`

echo"Image镜像ID：$IMAGE_ID \n"

CONTAINER_ID=`docker ps-a|grep$CONTAINER_NAME|awk'{print $NF}'`

echo"Container容器：$CONTAINER_ID \n"

if[-z$CONTAINER_ID];then

    echo-e"不存在容器，跳过清理容器阶段\n"

else

    dockerrm-f$CONTAINER_ID|true

    echo-e"$CONTAINER_ID 旧容器删除成功!"

docker rmi-f$IMAGE_ID|true

    echo-e"$IMAGE_ID 旧镜像删除成功!"

fi

echo-e"时间:$DTIME，正式开始构建docker镜像"

docker build-t$JOBMS:$TAG.

if[$?-ne0];then

    echo-e"时间:$DTIME，$JOBMS 镜像构建失败，请检查Dockerfile !"

exit

else

    echo-e"时间$DTIME，开始运行Docker容器."

#docker run --name $CONTAINER_NAME -v $JOBREPO:$JOBREPO -d -p 8081:8081 b2b-partner-img/$JOBMS

docker run--name$CONTAINER_NAME-d-p8080:8080$JOBMS

sleep 5

IMAGEID_NEW=`docker images |grep"$JOBMS"|awk'{print $3}'`

echo"新镜像ID：$IMAGEID_NEW"

CONTAINERID_NEW=`docker images |grep"$JOBMS"|awk'{print $1}'`

echo"新容器ID：$CONTAINERID_NEW"

fi
