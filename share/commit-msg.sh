#!/bin/sh

COMMIT_MSG_FILE=$1

# 커밋 메시지 전체 get
COMMIT_MSG=$(cat $COMMIT_MSG_FILE)

# 커밋 메시지중 첫 번째 라인(Header) get
COMMIT_HEADER=`head -n1 ${COMMIT_MSG_FILE}`

byPass() {
  if [[ $COMMIT_HEADER =~ ^(Merge branch) ]] ||
     [[ $COMMIT_HEADER =~ ^(Merge pull request) ]]; then

    echo "🛠[Commit lint] --- 커밋 메시지 확인 중..."
    exit 0
  fi

  if [[ $COMMIT_HEADER =~ ^(initial) ]]; then
    echo "⚙️[Commit lint] --- 커밋 메시지 초기화 중..."
    exit 0
  fi
}

validHeader() {
  if [[ $COMMIT_HEADER == "" ]]; then
    echo "🔴[Commit lint] Error --- 메시지를 작성해주세요."
    exit 1
  fi


  if [[ $COMMIT_HEADER =~ (\.)$ ]]; then
    echo "🔴[Commit lint] Error --- 문장 끝의 마침표('.')를 제거해주세요."
    exit 1

  elif [[ ! $COMMIT_HEADER =~ ^(feat(\(.*\))?!?: ) ]] &&
      [[ ! $COMMIT_HEADER =~ ^(fix(\(.*\))?!?: ) ]] &&
      [[ ! $COMMIT_HEADER =~ ^(docs(\(.*\))?!?: ) ]] &&
      [[ ! $COMMIT_HEADER =~ ^(style(\(.*\))?!?: ) ]] &&
      [[ ! $COMMIT_HEADER =~ ^(refactor(\(.*\))?!?: ) ]] &&
      [[ ! $COMMIT_HEADER =~ ^(test(\(.*\))?!?: ) ]] &&
      [[ ! $COMMIT_HEADER =~ ^(chore(\(.*\))?!?: ) ]]; then

    echo -e "🔴[Commit lint] Error --- Header(첫 번째 라인)의 접두사, 콜론(:), 띄어쓰기 형태를 확인해주세요.\n"

    echo "<type>(scope option): <subject>"
    echo "- feat: 새로운 기능 추가"
    echo "- fix: 버그 수정"
    echo "- docs: readme 등의 문서 내용 수정"
    echo "- style: (로직 수정 없이) 코드 스타일 수정 - 들여쓰기 같은 포맷이나 세미콜론 등"
    echo "- refactor: 코드 리팩토링"
    echo "- test: test와 관련된 코드 추가 및 수정"
    echo "- chore: (코드, 로직 수정 없이) 빌드 파일, 설정 등 변경"
    exit 1

  fi
}

addCommitHeaderEmoji() {
  if [[ $COMMIT_HEADER =~ ^(feat.*) ]]; then
    NEW_COMMIT_HEADER="🚀 $COMMIT_HEADER"
  elif [[ $COMMIT_HEADER =~ ^(fix.*) ]]; then
    NEW_COMMIT_HEADER="🔥 $COMMIT_HEADER"
  elif [[ $COMMIT_HEADER =~ ^(docs.*) ]]; then
    NEW_COMMIT_HEADER="📝 $COMMIT_HEADER"
  elif [[ $COMMIT_HEADER =~ ^(style.*) ]]; then
    NEW_COMMIT_HEADER="🎨 $COMMIT_HEADER"a
  elif [[ $COMMIT_HEADER =~ ^(refactor.*) ]]; then
    NEW_COMMIT_HEADER="🧠 $COMMIT_HEADER"
  elif [[ $COMMIT_HEADER =~ ^(test.*) ]]; then
    NEW_COMMIT_HEADER="🧪 $COMMIT_HEADER"
  elif [[ $COMMIT_HEADER =~ ^(chore.*) ]]; then
    NEW_COMMIT_HEADER="🍎 $COMMIT_HEADER"
  fi

  # 메시지 본문 추출
  COMMIT_BODY=$(echo "$COMMIT_MSG" | sed -n '2,$p')  # 첫 번째 줄은 이미 Header로 처리됐으므로, 2번째 줄부터 본문으로 간주
  NEW_COMMIT_MSG="$NEW_COMMIT_HEADER\n\n$COMMIT_BODY"
  
  # 변환된 내용으로 메시지 덮어쓰기
  echo "$NEW_COMMIT_MSG" > $COMMIT_MSG_FILE
}

byPass
validHeader
addCommitHeaderEmoji

echo "❇️[Commit lint] --- succeed commit!"
exit 0