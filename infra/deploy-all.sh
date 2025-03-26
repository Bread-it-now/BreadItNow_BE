#!/bin/bash

# //------ Build ------//
ROOT_DIR="$(cd "$(dirname "$0")"/.. && pwd)"
cd "$ROOT_DIR"

./gradlew clean buildAll

if [ $? -ne 0 ]; then
  echo "❌ [BreaditNow] 빌드 중 오류 발생"
  exit 1
fi

echo "✅ [BreaditNow] 전체 빌드 성공"

# //------ ENV 파일 복사 ------//

if [ ! -f "$ROOT_DIR/infra/.env" ]; then
  cp "$ROOT_DIR/.env" "$ROOT_DIR/infra/.env"
  echo "📦 [BreaditNow] .env 파일을 infra/ 디렉토리로 복사 완료"
else
  echo "⚠️ [BreaditNow] infra/.env 파일이 이미 존재합니다. 복사하지 않음"
fi

# //------ Docker ------//

docker network inspect bin-network >/dev/null 2>&1 || docker network create bin-network

echo "🌐 [BreaditNow] 네트워크 연결 준비 완료"

docker-compose -f "infra/docker-compose.db.yml" up  -d
docker-compose -f "infra/docker-compose.app.yml" up --build -d

if [ $? -eq 0 ]; then
  echo "✅ [BreaditNow] 모든 컨테이너 실행 성공"
  docker ps --filter "name=breaditnow"
else
  echo "❌ [BreaditNow] Docker 실행 중 문제 발생"
  exit 1
fi
