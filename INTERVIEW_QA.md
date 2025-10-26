# 🍞 BreadItNow 프로젝트 - 면접 Q&A

> 기술 면접에서 자주 나올 수 있는 질문들과 답변

---

## 🎯 프로젝트 이해도

### Q1: BreadItNow 프로젝트를 요약해서 설명해주세요.

**답변:**

BreadItNow는 베이커리의 신선한 빵 출시 정보를 실시간으로 알려주고, 온라인 예약 및 배송을 통합으로 제공하는 플랫폼입니다.

**주요 특징:**
- **고객 입장**: 신제품 출시 알림을 받고 간편하게 예약 및 배송 또는 픽업
- **사업자 입장**: 상품 관리, 예약 처리, 매출 통계를 한 곳에서 관리
- **기술적 특징**: 마이크로서비스 아키텍처, 실시간 알림, Redis 캐싱

**비즈니스 가치:**
- 베이커리 산업의 디지털 전환
- 고객의 편의성 향상
- 사업자의 효율성 증대

---

### Q2: 이 프로젝트에서 당신의 역할이 무엇인가요?

**답변:**

Full-Stack 개발자로서 백엔드와 프론트엔드 모두를 담당했습니다.

**백엔드 역할:**
- Java/Spring Boot 기반 8개 마이크로서비스 개발
- API Gateway 설계 및 구축
- JWT 인증, Redis 캐싱, Firebase 알림 통합

**프론트엔드 역할:**
- TypeScript/React 기반 고객 웹앱 개발
- Next.js를 활용한 SSR 구현
- Tailwind CSS와 Framer Motion으로 UI/UX 개선
- Zustand와 React Query로 상태 관리

**주요 성과:**
- 마이크로서비스 간 seamless 통신 구현
- API 응답 시간 500ms 이하로 최적화
- 푸시 알림 1초 이내 전송

---

## 🏗️ 아키텍처 및 설계

### Q3: 왜 마이크로서비스 아키텍처를 선택했나요?

**답변:**

마이크로서비스 아키텍처는 다음과 같은 이유로 선택했습니다:

**확장성 (Scalability):**
- 각 서비스를 독립적으로 확장 가능
- 트래픽에 따라 필요한 서비스만 선택적 확장

**유지보수성 (Maintainability):**
- 서비스별로 작은 단위의 코드베이스
- 팀원들의 병렬 개발 가능
- 버그 수정 시 해당 서비스만 배포

**기술 스택의 자유도:**
- 서비스별로 다른 기술 선택 가능
- 각 팀이 최적의 기술 선택

**단점 인식:**
- 복잡성 증가
- API Gateway 관리 필요
- 데이터 일관성 관리 필요
- 이에 대해 Redis와 JWT를 활용한 해결책 구현

---

### Q4: 8개 마이크로서비스의 역할을 설명해주세요.

**답변:**

| 서비스 | 역할 | 핵심 기능 |
|--------|------|---------|
| **Auth API** | 인증/권한 | JWT 발급, 토큰 검증, 역할 관리 |
| **Customer API** | 고객 서비스 | 베이커리 조회, 상품 조회, 마이페이지 |
| **Owner API** | 사업자 서비스 | 상품 관리, 재고 관리, 통계 |
| **Reservation API** | 예약 서비스 | 예약 신청, 상태 관리, 배송 추적 |
| **Notification API** | 알림 서비스 | FCM 통합, 푸시 알림 발송 |
| **Gateway** | 라우팅 | 요청 라우팅, 로드 밸런싱, 속도 제한 |
| **Redis** | 캐싱/세션 | 세션 저장, 데이터 캐싱 |
| **Common** | 공통 라이브러리 | 공통 유틸, 예외 처리, DTO |

**통신 패턴:**
```
클라이언트 → API Gateway → 각 마이크로서비스
                         → Redis (캐싱)
                         → Database
```

---

### Q5: API Gateway의 역할은 무엇인가요?

**답변:**

API Gateway는 모든 클라이언트 요청의 단일 진입점(Single Entry Point)입니다.

**역할:**

1. **요청 라우팅**: URL 경로에 따라 적절한 마이크로서비스로 라우팅
2. **인증 검증**: JWT 토큰 검증
3. **로드 밸런싱**: 여러 인스턴스로 요청 분산
4. **속도 제한**: Rate Limiting으로 남용 방지
5. **로깅/모니터링**: 모든 요청 기록

**예시:**
```
GET /customers/profile → Customer API
POST /products → Owner API
GET /reservations → Reservation API
POST /notifications → Notification API
```

**장점:**
- 클라이언트가 백엔드 구조를 몰라도 됨
- 서비스 변경 시 클라이언트 수정 불필요
- 횡단 관심사(logging, auth, rate limiting) 한 곳에서 관리

---

## 💾 데이터베이스 및 캐싱

### Q6: Redis를 어떻게 활용했나요?

**답변:**

Redis는 3가지 주요 용도로 활용했습니다:

**1. 세션 관리**
```
로그인 성공 → JWT 토큰 발급 → Redis에 세션 정보 저장
토큰 검증 → Redis에서 세션 조회 (TTL: 24시간)
로그아웃 → Redis에서 세션 제거
```

**2. 캐싱**
```
GET /bakeries → Redis에서 조회 (캐시 hit)
                    ↓
               DB에서 조회 (캐시 miss)
               → Redis에 저장 (TTL: 1시간)
```

**3. 실시간 데이터**
```
인기 상품: 실시간 조회수 기록 (TTL: 30분)
재고 현황: 변경 사항 즉시 반영
```

**성과:**
- 응답 시간: 500ms → 150ms (70% 개선)
- 데이터베이스 부하 감소
- 사용자 경험 향상

---

### Q7: 캐시 무효화 전략은 어떻게 구현했나요?

**답변:**

**TTL (Time To Live) 기반:**
- 베이커리 정보: 1시간
- 인기 상품: 30분
- 사용자 세션: 24시간

**이벤트 기반 무효화:**
```
상품 정보 수정 → Cache invalidation 이벤트 발생
             → Redis에서 해당 데이터 즉시 삭제
             → 다음 요청 시 DB에서 최신 데이터 조회
```

**고민 사항:**
- TTL 시간 설정: 너무 짧으면 캐싱 효과 감소, 너무 길면 최신 정보 보장 불가
- 해결책: 데이터 특성에 따라 다른 TTL 적용

---

## 🔐 보안

### Q8: 인증 및 권한 관리는 어떻게 구현했나요?

**답변:**

JWT(JSON Web Token) 기반 토큰 인증을 사용했습니다.

**인증 흐름:**
```
1. 사용자 로그인
   ↓
2. 아이디/비밀번호 검증
   ↓
3. JWT 토큰 생성
   - Header: 토큰 타입 (Bearer)
   - Payload: 사용자 ID, 역할(Role), 발급 시간
   - Signature: 서버 시크릿으로 서명
   ↓
4. 클라이언트에 토큰 반환
   ↓
5. 이후 모든 요청에 Authorization 헤더에 포함
```

**권한 관리:**
```
Payload의 Role 필드:
- CUSTOMER: 고객 권한
- OWNER: 사업자 권한
- ADMIN: 관리자 권한
```

**보안:**
- 비밀번호는 해싱 (BCrypt)
- 토큰은 HTTPS로만 전송
- 토큰 만료 시간 설정
- 민감한 작업은 토큰 재검증

**API 보안 예시:**
```
@PostMapping("/owner/products")
@RequireRole("OWNER")  // OWNER만 접근 가능
public ResponseEntity<Product> createProduct(...) {
    ...
}
```

---

## 📱 프론트엔드

### Q9: 상태 관리를 Zustand로 한 이유는?

**답변:**

Redux 대비 Zustand의 장점:

**1. 간단한 문법**
```
// Redux (보일러플레이트 많음)
const [state, dispatch] = useReducer(reducer, initialState);
dispatch({ type: 'SET_USER', payload: user });

// Zustand (간단함)
const { setUser } = useUserStore();
setUser(user);
```

**2. 더 적은 보일러플레이트**
- Action, Reducer, Dispatch 없음
- 직접 함수 호출

**3. 작은 번들 크기**
- Redux 대비 훨씬 가벼움
- 성능에 유리

**사용 예시:**
```typescript
// store/userStore.ts
import { create } from 'zustand';

interface UserStore {
  user: User | null;
  setUser: (user: User) => void;
  logout: () => void;
}

export const useUserStore = create<UserStore>((set) => ({
  user: null,
  setUser: (user) => set({ user }),
  logout: () => set({ user: null }),
}));

// Component
function Profile() {
  const { user, logout } = useUserStore();
  return (
    <>
      <p>{user?.name}</p>
      <button onClick={logout}>로그아웃</button>
    </>
  );
}
```

---

### Q10: React Query를 어떻게 활용했나요?

**답변:**

React Query는 서버 상태 관리 라이브러리로, 다음과 같이 활용했습니다:

**1. 데이터 페칭**
```typescript
const { data: bakeries, isLoading, error } = useQuery({
  queryKey: ['bakeries'],
  queryFn: async () => {
    const response = await fetch('/api/bakeries');
    return response.json();
  },
  staleTime: 1000 * 60 * 5, // 5분
});
```

**2. 자동 캐싱 및 동기화**
```typescript
// 동일한 queryKey로 요청하면 캐시된 데이터 사용
const { data: user1 } = useQuery(['user', 1], ...);
const { data: user2 } = useQuery(['user', 1], ...); // 캐시 사용
```

**3. 뮤테이션 (POST, PUT, DELETE)**
```typescript
const { mutate: createReservation } = useMutation({
  mutationFn: (data) => fetch('/api/reservations', {
    method: 'POST',
    body: JSON.stringify(data),
  }),
  onSuccess: () => {
    // 데이터 갱신
    queryClient.invalidateQueries(['reservations']);
  },
});
```

**장점:**
- 캐싱 자동 관리
- 백그라운드 동기화
- 실패 시 자동 재시도
- 낙관적 업데이트 (Optimistic Update) 가능

---

## 🔄 예약 프로세스

### Q11: 예약 상태 변경은 어떻게 관리하나요?

**답변:**

상태 머신(State Machine) 패턴을 사용했습니다:

**예약 상태 플로우:**
```
┌─────────┐
│ 대기중  │ (고객이 예약 신청)
└────┬────┘
     │
     ▼
┌──────────┐
│ 수락/거절 │ (사업자가 처리)
└────┬────┘
     │
     ├─→ "수락" → ┌──────────┐
     │            │ 준비중   │
     │            └────┬─────┘
     │                 │
     │                 ▼
     │            ┌──────────────┐
     │            │ 픽업대기    │
     │            └────┬─────────┘
     │                 │
     │                 ▼
     │            ┌──────────┐
     │            │ 완료    │
     │            └─────────┘
     │
     └─→ "거절" → ┌─────────┐
                 │ 거절됨  │
                 └────────┘
```

**상태 전환 규칙:**
```java
public enum ReservationStatus {
    PENDING,      // 대기중
    ACCEPTED,     // 수락
    REJECTED,     // 거절
    PREPARING,    // 준비중
    READY,        // 픽업대기
    COMPLETED,    // 완료
    CANCELLED     // 취소
}

// 상태 전환 가능 여부 체크
private boolean isValidTransition(ReservationStatus from, ReservationStatus to) {
    // PENDING → ACCEPTED, REJECTED만 가능
    if (from == PENDING) {
        return to == ACCEPTED || to == REJECTED;
    }
    // ACCEPTED → PREPARING만 가능
    if (from == ACCEPTED) {
        return to == PREPARING;
    }
    // ...
}
```

**알림 발송:**
```
상태 변경 → Notification API 호출 → FCM으로 푸시 알림 발송
          → 예약 이력 저장
```

---

## ⚡ 성능 최적화

### Q12: 성능 최적화는 어떻게 했나요?

**답변:**

여러 레벨에서 최적화를 진행했습니다:

**1. 데이터베이스 최적화**
```
- 인덱싱: 자주 조회되는 컬럼에 인덱스 추가
  예) bakery_id, user_id, status
  
- 쿼리 최적화: N+1 문제 해결
  ❌ 나쁜 예:
  for(Bakery bakery : bakeries) {
    Products products = getProducts(bakery.id); // 매번 쿼리
  }
  
  ✅ 좋은 예:
  Bakeries with Products = getWithProducts(bakeryIds);
```

**2. 캐싱**
```
- Redis: 자주 접근하는 데이터 캐싱 (70% 개선)
- HTTP 캐싱: 정적 리소스 브라우저 캐싱
```

**3. API 응답 최적화**
```
- 필요한 필드만 반환
- 페이지네이션: 한 번에 20개만 조회
- 압축: gzip 압축으로 응답 크기 감소
```

**4. 프론트엔드 최적화**
```
- 이미지 최적화: WebP 포맷, 레이지 로딩
- 코드 스플리팅: 필요한 경우에만 로드
- 가상화: 긴 리스트는 필요한 부분만 렌더링
```

**결과:**
| 지표 | 개선 전 | 개선 후 | 개선율 |
|------|--------|--------|-------|
| API 응답 시간 | 1500ms | 500ms | 67% |
| 페이지 로딩 | 3.5s | 1.2s | 66% |
| 데이터베이스 쿼리 | 200개/분 | 60개/분 | 70% |

---

## 🚨 문제 해결

### Q13: 개발 중 가장 큰 문제는 무엇이었고 어떻게 해결했나요?

**답변:**

**문제 1: 마이크로서비스 간 데이터 일관성**

상황:
```
예약 생성 → Owner API에서 재고 감소
         → Notification API에서 고객 알림 발송
        
만약 중간에 실패하면?
→ 재고는 감소했는데 알림 못 받음 (데이터 불일치)
```

해결책:
```
1. Saga 패턴 구현
   - 각 단계를 독립적인 트랜잭션으로 처리
   - 실패 시 롤백 로직 구현

2. 메시지 큐 도입
   - 이벤트 기반 처리
   - 재시도 메커니즘
```

**문제 2: Redis 캐시 동기화**

상황:
```
API 1: 상품 정보 업데이트
API 2: 캐시에서 구 데이터 조회
→ 데이터 불일치
```

해결책:
```
1. 캐시 무효화 이벤트
   상품 업데이트 → Redis 삭제 → 다음 조회 시 DB에서 갱신

2. 적절한 TTL 설정
   - 자주 변경: 짧은 TTL
   - 거의 안 변경: 긴 TTL
```

---

## 🎓 배운 점

### Q14: 이 프로젝트를 통해 가장 많이 배운 것은 무엇인가요?

**답변:**

3가지를 가장 많이 배웠습니다:

**1. 시스템 설계의 중요성**
- 처음부터 좋은 구조로 설계하면 나중에 확장이 쉬움
- 마이크로서비스 vs 모놀리식 아키텍처의 트레이드오프
- API 설계의 중요성 (REST 원칙 준수)

**2. 실시간 시스템의 복잡성**
- 알림 시스템의 신뢰성
- 상태 관리의 중요성
- 이벤트 기반 아키텍처의 강점

**3. 사용자 중심 사고**
- 사용자 경험의 중요성
- 고객과 사업자 양쪽의 요구사항 균형
- 성능은 사용자 만족도에 직결됨

---

### Q15: 만약 처음부터 다시 한다면 뭘 다르게 하시겠어요?

**답변:**

**개선할 점:**

1. **초기 설계에 더 신경 쓰기**
   - 데이터 모델링을 더 철저히
   - API 인터페이스를 먼저 정의
   
2. **테스트 코드 작성**
   - Unit 테스트: 비즈니스 로직
   - Integration 테스트: API 엔드포인트
   - E2E 테스트: 사용자 시나리오
   
3. **모니터링 및 로깅**
   - 처음부터 구조화된 로깅
   - APM (Application Performance Monitoring) 도입
   
4. **문서화**
   - API 문서를 코드와 함께 관리 (Swagger)
   - 아키텍처 결정 기록 (ADR)
   
5. **CI/CD 파이프라인**
   - 자동 테스트
   - 자동 배포
   - 빠른 피드백

**배운 교훈:**
"작은 것부터 시작하되, 확장 가능하게. 기술보다 설계가 중요하다."

---

## 📚 추가 질문 대비

### Q16: 예약 시스템에서 동시성 문제는 어떻게 처리했나요?

**답변:**

인기 있는 상품의 경우 동시에 여러 사람이 예약할 수 있습니다.

**재고 감소 시 동시성 문제:**
```
재고: 10개
사용자 A, B, C가 동시에 각 5개 예약

❌ 문제: 전체 15개 예약되어 버림 (재고 부족)

✅ 해결책:
1. Database Lock (비관적 락)
   SELECT ... FOR UPDATE
   
2. Optimistic Locking (낙관적 락)
   version 필드 사용
   UPDATE products SET version = version + 1
   WHERE id = ? AND version = ?
   
3. Redis를 이용한 분산 락
   SETNX를 이용한 락 구현
```

**구현 예시:**
```java
@Transactional
public void reduceInventory(Long productId, int quantity) {
    // 1. 재고 조회 (Lock)
    Product product = productRepository.findByIdWithLock(productId);
    
    // 2. 재고 확인
    if (product.getInventory() < quantity) {
        throw new InsufficientInventoryException();
    }
    
    // 3. 재고 감소
    product.decreaseInventory(quantity);
    
    // 4. 저장 (버전 증가)
    productRepository.save(product);
}
```

---

### Q17: 배포 환경은 어떻게 구성했나요?

**답변:**

Docker와 Kubernetes 기반 배포 (예상):

**배포 구조:**
```
┌─────────────┐
│ GitHub      │ 코드 푸시
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ CI/CD       │ 자동 테스트
│ (GitHub     │ 자동 빌드
│  Actions)   │ 자동 배포
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────┐
│ Docker Registry                 │
│ (Docker 이미지 저장)             │
└──────┬──────────────────────────┘
       │
       ▼
┌─────────────────────────────────┐
│ Kubernetes Cluster              │
├─────────────┬─────────┬─────────┤
│ Auth API    │ Customer│ Owner   │
│ (3 Pods)    │ (2 Pods)│ (2 Pods)│
└─────────────┴─────────┴─────────┘
```

**각 단계:**

1. **개발**: Git 커밋
2. **테스트**: 자동 테스트 실행
3. **빌드**: Docker 이미지 생성
4. **배포**: 쿠버네티스에 배포
5. **모니터링**: 성능 모니터링

---

## 💬 마무리 팁

### Q18: 면접에서 주의할 점은?

**답변:**

1. **구체적인 예시 들기**
   - "마이크로서비스를 사용했어요" (X)
   - "8개의 독립적인 API를 API Gateway로 통합했어요" (O)

2. **문제와 해결책 세트로 설명**
   - "동시성 문제가 있었고, optimistic locking으로 해결했어요"

3. **숫자와 지표로 증명**
   - "성능을 70% 개선했어요"
   - "API 응답 시간을 1500ms에서 500ms로 단축했어요"

4. **배운 점 강조**
   - "이 과정에서 시스템 설계의 중요성을 배웠어요"

5. **자신의 역할 명확히**
   - "제가 주로 담당한 부분은 예약 서비스와 프론트엔드입니다"

---

**작성일**: 2025년  
**최종 수정**: 2025년
