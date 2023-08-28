# Chapter 05. 인터페이스

## TypeScript를 런타임에서 실행시키기 위한 사전 설정

- typescript, ts-node 패키지를 전역으로 설치하고 실행

```bash
npm i -g typescript
npm i -g ts-node
ts-node 파일명.ts
```

## 옵션 속성 (Optional Property)

특정 인터페이스에 맞는 객체를 전달할 때, 옵션 속성으로 지정된 속성은 선택적으로 넘길 수 있다.

```typescript
interface Person {
  name?: string;
  age: number;
}

function logAge(someone: Person): void {
  console.log('age: ', someone.age);
}

var inu = { age: 29 };
logAge(inu);
```

## 인터페이스 상속

인터페이스는 `extends` 키워드를 통해 다른 인터페이스를 상속받을(확장할) 수 있다.

```typescript
interface Person {
  name: string;
  age: number;
}

interface Developer extends Person {
  skill: string;
}

var inu = {
  name: '황인우',
  age: 29,
  skill: 'Supabase',
};
```

### 인터페이스 상속 시 유의사항

하위 인터페이스는 상위 인터페이스에서 정의한 속성의 타입을 재정의하지 않아야 한다.

```typescript
interface Person {
  name: string;
  age: number;
}

// 다음의 정의는 에러를 표출:
// 'Athlete' 인터페이스가 'Person' 인터페이스를 잘못 확장합니다.
// 'name' 속성의 형식이 호환되지 않습니다.
// 'number' 형식은 'string' 형식에 할당할 수 없습니다.
interface Athlete extends Person {
  name: number;
}
```

## 인터페이스를 이용한 인덱싱 타입 정의

### 배열 인덱싱 타입 정의

- 배열을 다음과 같이 interface로 정의할 수 있다. 이때 배열의 인덱스에는 number 타입만 부여할 수 있다.
  - index의 타입을 number가 아닌 타입으로 부여하는 경우, 에러를 표출.

```typescript
interface StringArray {
  [index: number]: string;
  // [index: string]: string;
}

var companies: StringArray = ['닌텐도', '펄어비스', '크래프톤'];
```

### 객체 인덱싱 타입 정의

- 객체의 경우, 인덱싱 타입을 정의할 수 있다.

```typescript
interface SalaryMap {
  [level: string]: number;
}

var salary: SalaryMap = {
  junior: 100,
  middle: 200,
  senior: 500,
}
```

- 해당 interface의 요소에는 다음과 같이 접근할 수 있다.

```typescript
console.log('junior\'s salary: ', salary.junior);
console.log('senior\'s salary: ', salary['senior']);
```

### 인덱스 시그니처

- 위와 같이 객체의 정확한 속성 이름을 명시하지 않고, 속성 이름의 타입과 속성 값의 타입을 정의하는 문법을 **인덱스 시그니처**(Index Signature)라 지칭.

### 인덱스 시그니처는 언제 사용하는 게 좋을까?

- 객체에 특정 타입의 속성이 계속해서 추가될 가능성이 있을 때는 인덱스 시그니처를 사용하는 것이 효과적.
- 객체의 속성 이름과 개수를 구체적으로 알 수 있는 경우에는 속성 이름과 속성 값 타입을 구체적으로 명시하는 것이 효과적.
  - 코드를 작성할 때 해당 인터페이스로부터 어떤 속성이 제공되는지 자동완성을 통해 확인할 수 있음.
  - 반면 인덱스 시그니처는 구체적으로 어떤 속성이 제공될 것인지 알 수 없음.
- 물론 다음과 같이 속성을 구체적으로 명시하는 것과 인덱스 시그니처를 혼용하는 것도 가능.

```typescript
interface User {
  id: string;
  name: string;
  [property: string]: string;
}

var inu: User = {
  id: 'hsjkdss228',
  name: '황인우',
  address: '광진구',
  skill: 'Supabase',
}

var seungjun: User = {
  id: 'seungjun',
  name: '노승준',
}
```

## 의문점

- 인터페이스를 확장할 때 상위 인터페이스의 타입을 보장하지 않을 경우, 에러 메시지를 표출하기는 하지만 스크립트의 실행 자체는 정상적으로 이루어진다.
- 속성과 인덱스 시그니처가 혼용될 때 이들은 모두 같은 타입이어야 한다. 다른 타입이 혼용되는 경우에는 에러가 발생해 스크립트를 실행할 수 없다.
