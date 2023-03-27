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
PROJECT_HOME="/home/ubuntu/project"
REPOSITORY="$PROJECT_HOME/deploy"
FRONT_END_APP_BUILD_PATH="$REPOSITORY/frontend"
BACK_END_APP_BUILD_PATH="$REPOSITORY/backend"

function build() {
  cd ${REPOSITORY}
  releaseFrontEndApp;
  shutdownBackEndApp;
  releaseBackEndApp;
}

function releaseFrontEndApp() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< FrontEnd App (재)시작 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"

  cd ${FRONT_END_APP_BUILD_PATH}
  npm install
  npm run deploy:prod
  echo -e "${txtylw} >> [$(date)] FrontEnd App (재)시작 완료${txtrst}"
}

function shutdownBackEndApp() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< BackEnd App 종료 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"
  local appPid=$(pgrep -f $PROJECT_NAME)
  if [[ -n "$appPid" ]]; then
    kill -TERM $appPid
    sleep 5
    echo -e "${txtylw} >> BackEnd App 종료 완료${txtrst}"
  else
    echo -e "${txtylw} >> 구동중인 BackEnd App이 없으므로 종료하지 않습니다.${txtrst}"
  fi
}

function releaseBackEndApp() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< BackEnd App 시작 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"

  cd ${BACK_END_APP_BUILD_PATH}
  # tail -n으로 최신 jar 파일 변수에 저장
  local JAR_NAME=$(ls $BACK_END_APP_BUILD_PATH | grep 'my-pet' | tail -n 1)
  local JAR_PATH=$BACK_END_APP_BUILD_PATH/$JAR_NAME
  echo -e "${txtylw} >> Profile : $PROFILE${txtrst}"
  echo -e "${txtylw} >> JAR : $JAR_NAME${txtrst}"

  nohup java -jar \
    -Dspring.profiles.active=$PROFILE \
    $JAR_PATH 1> $PROJECT_HOME/app.log 2>&1  &
  echo -e "${txtylw} >> [$(date)] BackEnd App 시작 완료${txtrst}"
}

build;
