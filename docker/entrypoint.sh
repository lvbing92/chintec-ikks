#!/bin/sh

if [ -z $@ ]; then
  PROFILE="dev"
else
  PROFILE="$1"
fi

java -jar -Dspring.profiles.active=${PROFILE} /app/app.jar
