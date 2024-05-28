#!/bin/bash

echo " ┌─ 🔔 ᴍᴀɴᴜᴀʟ ───────────────────────────┐ "
echo " │   작업이 완료되면 /build/lib에서      │ "
echo " │   빌드된 jar 파일을 확인한 뒤,        │ "
echo " │   원격 서버에 업로드하고 실행하세요.  │ "
echo " └────────────────────── 🐥 ᴡʀɪᴛᴇʀ. ᴋᴇʙ ─┘ "
echo 
echo "[1] 개발 환경 jar 생성 (profile: dev)"
echo "[2] 제품 환경 jar 생성 (profile: prod)"
echo
read -p "> 번호를 입력하세요 : " input

if [ $input -eq "1" ] ; then
  ./gradlew clean bootjar -P profile=dev

elif [ $input -eq "2" ] ; then
  ./gradlew clean bootjar -P profile=prod

else
  echo "⚠️ 1부터 2까지의 숫자만 입력 가능합니다."
fi

