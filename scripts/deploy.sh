#! /bin/bash
txtrst='\033[1;37m' # White
txtred='\033[1;31m' # Red
txtylw='\033[1;33m' # Yellow
txtpur='\033[1;35m' # Purple
txtgrn='\033[1;32m' # Green
txtgra='\033[1;30m' # Gray
RET_TRUE=1
RET_FALSE=0

EXECUTION_PATH=$(pwd)
SHELL_SCRIPT_PATH=$(dirname $0)

PROJECT_NAME="my-pet"
PROFILE="dev"
REPOSITORY="/home/ubuntu/project/deploy"
APP_BUILD_PATH="$REPOSITORY/build/libs"

function build() {
  cd ${REPOSITORY}
  shutdownApplication;
  releaseApplication;
}

function shutdownApplication() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< Application 종료 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"
  local appPid=$(pgrep -f $PROJECT_NAME)
  if [[ -n "$appPid" ]]; then
    kill -TERM $appPid
    sleep 5
    echo -e "${txtylw} >> 종료 완료${txtrst}"
  else
    echo -e "${txtylw} >> 구동중인 Application이 없으므로 종료하지 않습니다.${txtrst}"
  fi
}

function releaseApplication() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< Application 시작 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"
  # tail -n으로 최신 jar 파일 변수에 저장
  local JAR_NAME=$(ls $APP_BUILD_PATH | grep 'my-pet' | tail -n 1)
  local JAR_PATH=$APP_BUILD_PATH/$JAR_NAME
  echo -e "${txtylw} >> Profile : $PROFILE${txtrst}"
  echo -e "${txtylw} >> JAR : $jarName${txtrst}"

  nohup java -jar \
    -Dspring.profiles.active=$PROFILE \
    $JAR_PATH 1> app.log 2>&1  &
  echo -e "${txtylw} >> [$(date)] Application 시작 완료${txtrst}"
}

build;
