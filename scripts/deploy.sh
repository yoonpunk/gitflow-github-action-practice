#!/usr/bin/env bash
source ~/.bash_profile 

PROJECT_NAME="github-action-practice"
JAR_PATH="/home/ubuntu/deploy/$PROJECT_NAME/build/libs/*.jar"
DEPLOY_PATH="/home/ubuntu/deploy/$PROJECT_NAME/"
DEPLOY_LOG_PATH="/home/ubuntu/deploy/$PROJECT_NAME/deploy.log"
DEPLOY_ERR_LOG_PATH="/home/ubuntu/deploy/$PROJECT_NAME/deploy_err.log"
BUILD_JAR=$(ls $JAR_PATH)
JAR_NAME=$(basename $BUILD_JAR)

cd $JAR_PATH
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
echo ">pwd $(pwd)" >> $DEPLOY_LOG_PATH
echo ">java home ${JAVA_HOME}" >> $DEPLOY_LOG_PATH
echo ">java version $(/usr/bin/java -version)" >> $DEPLOY_LOG_PATH
echo ">DB_IP ${DB_IP}" >> $DEPLOY_LOG_PATH
echo ">DB_PORT ${DB_PORT}" >> $DEPLOY_LOG_PATH
echo ">DB_NAME ${DB_NAME}" >> $DEPLOY_LOG_PATH
echo ">DB_USER ${DB_USER}" >> $DEPLOY_LOG_PATH
echo ">DB_PORT ${DB_PORT}" >> $DEPLOY_LOG_PATH

nohup java -jar -Dspring.profiles.active=prod $DEPLOY_JAR -DDB_IP=${DB_IP} -DDB_PORT=${DB_PORT} -DDB_NAME=${DB_NAME} -DDB_USER=${DB_USER} -DDB_PW=${DB_PORT}  &

sleep 3

echo "> deploy ended. : $(date +%c)" >> $DEPLOY_LOG_PATH
