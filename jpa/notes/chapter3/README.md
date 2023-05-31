# 03장. 영속성 관리

## EntityManagerFactory

- EntityManager를 생성하는 팩토리 객체
- Persistence 객체에서 팩토리 메서드를 호출해 생성

```java
EntityManagerFactory entityManagerFactory
    = Persistence.createEntityManagerFactory("persistence-unit-name");
```

- 생성 비용이 크므로 일반적으로 하나의 객체만 사용하는 싱글턴 형태로 사용
- 여러 스레드가 동시에 접근해도 안전하므로 스레드 간 공유 가능

## EntityManager

- Entity와 관련된 모든 작업을 처리하는 객체 (저장, 조회, 수정, 삭제)
- EntityManagerFactory에서 팩토리 메서드를 호출해 생성

```java
EntityManager entityManager
    = entityManagerFactory.createEntityManager();
```

- 각 EntityManager는 트랜잭션을 시작할 때 데이터베이스에 연결할 수 있는 커넥션을 획득
- 하나의 EntityManager에는 하나의 스레드만 접근해야 함
  - 여러 스레드가 하나의 EntityManager를 공유할 경우 동시성 문제가 발생할 수 있음

## 영속성 컨텍스트 (Persistence Context)

- Entity를 영구 저장하는 환경

### 영속성 컨텍스트의 특징

1. 식별자 값

- 영속 상태의 Entity는 식별자 값이 존재
  - 식별자 값이 없을 경우 예외 발생

#### flush()

- 트랜잭션이 커밋될 때, entityManager가 영속성 컨텍스트에 저장되어 있는 Entity들을 데이터베이스에 반영해 영속성 컨텍스트와 데이터베이스를 동기화
- 과정
  1. 영속성 컨텍스트에 존재하는 모든 Entity들을 스냅샷과 비교. 수정된 Entity이 존재할 경우 해당 Entity들을 수정하는 쿼리를 생성해 쓰기 지연 SQL 저장소에 등록 (이때 등록, 삭제 쿼리는 이전에 수행되었을 경우 이미 존재하는 상태)
  2. 쓰기 지연 SQL 저장소의 쿼리들을 데이터베이스에 전송
- 메서드 호출 시점
  - 트랜잭션을 커밋하는 경우
  - JPQL 쿼리를 실행하는 경우
    - JPQL 쿼리는 항상 SQL로 변환되어 데이터베이스에서 Entity를 조회하기 때문에, 영속성 컨텍스트에 존재하는 Entity들을 flush()해서 기존의 변경 내용을 데이터베이스에 먼저 반영

#### 1차 캐시

- 영속성 컨텍스트 내부에 존재하는 캐시
  - Map\<Id, Entity\> 형태
    - Id: 대상 Entity에서 @Id 어노테이션이 부여된 식별자 필드
    - Entity: 대상 Entity 인스턴스
- entityManager.find() 계열 메서드 호출 시 먼저 1차 캐시에서 Entity를 찾은 뒤, 1차 캐시에 존재하지 않을 경우 데이터베이스에서 쿼리를 호출해 조회

## Entity의 생명주기

### 비영속

- Entity가 영속성 컨텍스트와 전혀 관계가 없는 상태

### 영속

- Entity가 영속성 컨텍스트에 의해 관리되어 영속성 컨텍스트에 저장된 상태
- persist한 객체와, find() 혹은 JPQL을 사용해 조회한 Entity는 영속성 컨텍스트에 의해 관리됨

### 준영속

- Entity가 영속성 컨텍스트에 의해 관리되던 상태에서 영속성 컨텍스트가 관리하지 않도록 분리된 상태
- 수행 메서드
  - detach()

    ```java
    transaction.begin();

    // ...

    entityManager.persist(entity);
    entityManager.detach(entity);

    // entity는 데이터베이스에 저장되지 않음
    transaction.commit();
    ```

    - 영속성 컨텍스트 내에서 detach 메서드가 수행될 경우, 영속성 컨텍스트 내 1차 캐시와 쓰기 지연 SQL 저장소에 저장되어 있던 해당 Entity와 관련된 모든 정보가 제거됨
  - clear()
    - 영속성 컨텍스트 내 1차 캐시와 쓰기 지연 SQL 저장소의 모든 Entity와 관련된 정보를 제거
  - close()
    - 영속성 컨텍스트를 종료
- 특징
  - 영속성 컨텍스트가 제공하는 기능이 동작하지 않음
  - 비영속 Entity와는 달리 식별자 값을 가지고 있음
  - 지연 로딩을 할 수 없음
    - 프록시 객체의 메서드가 직접 호출되는 시점에 준영속 상태일 경우, 프록시 객체의 필드인 대상 Entity 레퍼런스 필드를 통해 데이터베이스에 접근할 수 없음

#### merge()

- 준영속 상태의 Entity를 영속 상태로 변경
  - 준영속 상태의 Entity 정보를 이용해 새로운 영속 상태의 Entity를 생성해 영속성 컨텍스트의 정보로 등록한 뒤 반환
  
  ```java
    // ...

    entityManager.persist(entity);
    entityManager.detach(entity);

    Entity mergedEntity = entityManager.merge(entity);
    ```

### 삭제

- Entity가 영속성 컨텍스트와 데이터베이스에서 삭제된 상태

## Entity 조회

- Entity 조회 순서

  1. 1차 캐시에 Entity가 존재할 경우
      - 대상 Entity를 반환
  2. 1차 캐시에 대상 Entity가 존재하지 않을 경우, 데이터베이스를 조회해 대상
      - 데이터베이스를 조회해 대상 Entity를 생성
      - 대상 Entity를 1차 캐시에 저장해 영속화한 뒤, 영속 상태의 Entity를 반환

- 같은 EntityManager에서 반환된 같은 id의 Entity 인스턴스들은 모두 동일성을 만족
  - 영속된 Entity의 경우 항상 1차 캐시에서 먼저 동일한 인스턴스를 반환

## Entity 등록

### persist()

- EntityManager를 사용해 대상 Entity를 영속성 컨텍스트에 저장

```java
entityManager.persist(entity);
```

### Entity 등록 순서

```java
EntityManager entityManager = entityManagerFactory.createEntityManager();
EntityTransaction transaction = entityManager.getTransaction();

// 트랜잭션을 시작해 데이터 변경 시점 지정
transaction.begin();

// 각 persist 수행 시 INSERT SQL문을 내부 쿼리 저장소에 삽입
entityManager.persist(entity1);
entityManager.persist(entity2);

// 커밋할 때 내부 쿼리 저장소의 모든 SQL문들을 수행
transaction.commit();
```

### 트랜잭션을 지원하는 쓰기 지원

- 위의 예시처럼, entityManager는 persist를 수행할 때마다 생성 쿼리문을 쓰기 지연 SQL 저장소에 삽입
- 트랜잭션을 커밋하는 순간에 저장소의 모든 쿼리문을 데이터베이스에 한 번에 전송

## Entity 수정

- Entity를 조회한 뒤, 조회한 Entity의 필드 값을 변경하는 것으로 Entity 수정 가능
- 트랜잭션 커밋 시 변경사항을 감지해 데이터베이스에 반영
- 수정 쿼리에서는 대상 Entity의 모든 필드를 업데이트
  - 변경된 필드에는 변경된 값을 전달, 그 외 필드들에는 똑같은 값을 다시 전달
  - 모든 필드를 사용해 업데이트할 경우 수정 쿼리의 형식을 항상 같게 사용할 수 있기 때문에, 애플리케이션 로딩 시점에 수정 쿼리를 미리 생성해두고 재사용할 수 있음

### 변경 감지

- 과정
  1. JPA는 Entity를 영속성 컨텍스트에 보관할 때, 불러오는 최초 상태를 복사해 스냅샷으로 저장
  2. flush()를 수행하는 시점에 Entity들의 상태와 스냅샷을 비교해 변경된 Entity들을 탐지
  3. 변경된 Entity들은 수정 쿼리문을 생성해 쓰기 지연 SQL 저장소에 삽입
  4. 트랜잭션을 커밋
- 변경 감지는 영속성 컨텍스트가 관리하는 영속 상태의 Entity에만 적용됨
  - 영속성 컨텍스트의 관리 대상이 아닌 Entity는 값이 변경되어도 데이터베이스에 반영되지 않음

## Entity 삭제

- 삭제 대상 Entity를 조회한 뒤 삭제

```java
Lecture lecture = entityManager.find(Lecture.class, "lecture1");
entityManager.remove(lecture);
```
