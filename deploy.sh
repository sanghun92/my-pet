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

BRANCH=$1
PROFILE=$2
REPOSITORY="/home/ubuntu/project/my-pet"
BUILD_PATH="${REPOSITORY}/my-pet-api/build/libs"

function usage() {
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< 스크립트 🧐 >>${txtrst}"
  echo -e ""
  echo -e "${txtgrn}$0 branch${txtred}{ main | develop } ${txtgrn}profile${txtred}{ dev | local | test }"
  echo -e "${txtylw}=======================================${txtrst}"
}

function build() {
  cd ${REPOSITORY}
  check_df;
  pull;
  makeJar;
  shutdownApplication;
  releaseApplication;
}

function check_df() {
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< 저장소 업데이트 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtylw} >> 브랜치 비교 대상 : ${txtred}$BRANCH${txtrst}"
  git fetch

  master_branch=$(git rev-parse $BRANCH)
  remote_branch=$(git rev-parse origin/$BRANCH)

  if [[ $master_branch == $remote_branch ]]; then
    echo -e "${txtylw} >> [$(date)] 변경 된 내용이 없습니다 😫${txtrst}"
    exit 0
  fi
}

function pull() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< Pull Request 🏃♂ >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"
  git pull origin $BRANCH
}

function makeJar() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< Application 빌드 🧐 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"
  ./gradlew clean build
}

function shutdownApplication() {
  echo -e ""
  echo -e "${txtylw}=======================================${txtrst}"
  echo -e "${txtgrn}<< Application 종료 >>${txtrst}"
  echo -e "${txtylw}=======================================${txtrst}"
  local appPid=$(pgrep -f my-pet)
  if [[ -n "$appPid" ]]
  then
    kill -TERM $appPid
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
  local jarName=$(ls $BUILD_PATH | grep 'my-pet' | tail -n 1)
  echo -e "${txtylw} >> Profile : $PROFILE${txtrst}"
  echo -e "${txtylw} >> JAR : $jarName${txtrst}"

  nohup java -jar \
    -Dspring.profiles.active=$PROFILE \
    $BUILD_PATH/$jarName 1> app.log 2>&1  &
  echo -e "${txtylw} >> [$(date)] Application 시작 완료${txtrst}"
}
if [[ $# -ne 2 ]]
then
  usage;
else
  build;
fi
exit;
