#!/bin/bash
PROJECT_NAME="github-action-practice"
JAR_PATH="/home/ubuntu/deploy/$PROJECT_NAME/build/libs/*.jar"
DEPLOY_PATH="/home/ubuntu/deploy/$PROJECT_NAME/"
DEPLOY_LOG_PATH="/home/ubuntu/deploy/$PROJECT_NAME/deploy.log"
BUILD_JAR=$(ls $JAR_PATH)
JAR_NAME=$(basename $BUILD_JAR)

echo "===== deploy started : $(date +%c) =====" >> $DEPLOY_LOG_PATH

echo "> build filename: $JAR_NAME" >> $DEPLOY_LOG_PATH
echo "> copy build file " >> $DEPLOY_LOG_PATH
cp $BUILD_JAR $DEPLOY_PATH

echo "> check running application's pid" >> $DEPLOY_LOG_PATH
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> application is not running." >> $DEPLOY_LOG_PATH
else
  echo "> A running application exists. " >> $DEPLOY_LOG_PATH
  echo "> Kill the running application." >> $DEPLOY_LOG_PATH
  echo "> kill -9 $CURRENT_PID" >> $DEPLOY_LOG_PATH
  kill -9 $CURRENT_PID
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> deploy new jar $DEPLOY_JAR" >> $DEPLOY_LOG_PATH

nohup java -jar -Dspring.profiles.active=prod $DEPLOY_JAR > nohup.out 2>&1 &

sleep 3

echo "> deploy ended. : $(date +%c)" >> $DEPLOY_LOG_PATH
