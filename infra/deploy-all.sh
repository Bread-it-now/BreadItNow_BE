#!/bin/bash

# //------ Build ------//

ROOT_DIR="$(cd "$(dirname "$0")"/.. && pwd)"

"$ROOT_DIR/gradlew" clean buildAll

if [ $? -ne 0 ]; then
  echo "❌ [BreaditNow] 빌드 중 오류 발생"
  exit 1
fi

echo "✅ [BreaditNow] 전체 빌드 성공"


# //------ Docker ------//

docker network create bin-network

docker-compose -f "$ROOT_DIR/infra/docker-compose.db.yml" up  -d
docker-compose -f "$ROOT_DIR/infra/docker-compose.app.yml" up --build -d

if [ $? -eq 0 ]; then
  echo "✅ [BreaditNow] 모든 컨테이너 실행 성공"
  docker ps --filter "name=breaditnow"
else
  echo "❌ [BreaditNow] Docker 실행 중 문제 발생"
  exit 1
fi
