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
FRONT_END_APP_PATH="$PROJECT_HOME/frontend"
BACK_END_APP_PATH="$PROJECT_HOME/backend"

function startAllApps() {
  startFrontEndApp;
  startBackEndApp;
}

function startFrontEndApp() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< FrontEnd App 시작 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"

  cd ${FRONT_END_APP_PATH}
  npm install
  npm run deploy:prod
  echo -e "${txtylw} >> [$(date)] - FrontEnd App 시작 완료${txtrst}"
}

function startBackEndApp() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< BackEnd App 시작 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"

  cd ${BACK_END_APP_PATH}
  # tail -n으로 최신 jar 파일 변수에 저장
  local JAR_NAME=$(ls $BACK_END_APP_PATH | grep 'my-pet' | tail -n 1)
  local JAR_PATH=$BACK_END_APP_PATH/$JAR_NAME
  echo -e "${txtylw} [$(date)] - Profile : $PROFILE${txtrst}"
  echo -e "${txtylw} [$(date)] - JAR : $JAR_NAME${txtrst}"

  nohup java -jar \
    -Dspring.profiles.active=$PROFILE \
    $JAR_PATH 1> $PROJECT_HOME/app.log 2>&1  &
  echo -e "${txtylw} >> [$(date)] BackEnd App 시작 완료${txtrst}"
}

startAllApps;
