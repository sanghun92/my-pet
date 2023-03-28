#! /bin/bash
txtrst='\033[1;37m' # White
txtred='\033[1;31m' # Red
txtylw='\033[1;33m' # Yellow
txtpur='\033[1;35m' # Purple
txtgrn='\033[1;32m' # Green
txtgra='\033[1;30m' # Gray

PROJECT_NAME="my-pet"

function shutdownAllApps() {
  shutdownFrontEndApp;
  shutdownBackEndApp;
}

function shutdownFrontEndApp() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< FrontEnd App 종료 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"

  pm2 stop MyPetFrontApp
  echo -e "${txtylw} [$(date)] - FrontEnd App 종료 완료${txtrst}"
}

function shutdownBackEndApp() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< BackEnd App 종료 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"
  local appPid=$(pgrep -f $PROJECT_NAME)
  if [[ -n "$appPid" ]]; then
    kill -TERM $appPid
    sleep 5
    echo -e "${txtylw} [$(date)] - BackEnd App 종료 완료${txtrst}"
  else
    echo -e "${txtylw} [$(date)] - 구동중인 BackEnd App이 없으므로 종료하지 않습니다.${txtrst}"
  fi
}

shutdownAllApps;
