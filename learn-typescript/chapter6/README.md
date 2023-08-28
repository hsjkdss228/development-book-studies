# Chapter 06. 연산자를 사용한 타입 정의

## 유니언 타입

- 특정 속성이나 인자가 유니언 타입으로 선언된 경우, 선언된 타입들 중 한 가지의 타입의 값을 전달받을 수 있음.

```typescript
function logText(text: string | number): void {
  console.log(text.toString());
}

logText('ㅋㅋㅋ');
logText(1231242152345);
```

### 유니언 타입의 장점

- 여러 종류의 타입을 전달받기 위해 `any` 타입을 사용하는 경우, 어떤 타입의 인자가 주어질지 알 수 없기 때문에, 코드 작성 시점에는 `any` 타입으로 주어진 값의 속성이나 API를 코드 작성 시점에 확인할 수 없음.
  - 따라서 속성이나 API를 잘못 입력한 경우에도 컴파일 시점에 에러를 확인하기 어려움.
- 반면 유니언 타입은 정의된 타입들이 동시에 제공하는 속성이나 API를 알 수 있기 때문에 속성이나 API를 잘못 입력한 경우 에러를 확인할 수 있음.

### 유니언 타입 사용 시 주의할 점

- 유니언 타입으로 정의된 값에 접근하는 경우, 정의된 타입들에 공통적으로 존재하는 속성이나 API에만 접근할 수 있음.
  - 특정 타입에만 존재하는 속성이나 API에 접근하는 경우 에러 발생.
- 유니언 타입으로 전달받은 값에서 특정 타입에만 존재하는 속성이나 API에 접근해야 하는 경우, 다음과 같이 해당 값의 타입을 구분지어줘야 함.

```typescript
interface Person {
  name: string;
  age: number;
}

interface Developer {
  name: string;
  skill: string;
}

function introduce(someone: Person | Developer) {
  if ('age' in someone) {
    console.log('나이: ', someone.age);
  }

  if ('skill' in someone) {
    console.log('기술 스택: ', someone.skill);
  }
}
```

- JavaScript의 Primitive 타입에 대해서는 `typeof` 연산자를 사용해 타입을 구분지을 수 있음.

```typescript
function logText(text: string | number) {
  if (typeof text === 'string') {
    console.log(text.toUpperCase());
  }

  if (typeof text === 'number') {
    console.log(text.toLocaleString());
  }
}
```

- 조건문을 통해 타입을 구분지은 이후 시점에서는 해당 값을 유니언 타입이 아닌 **구분지어진 특정 타입으로 인식**.

## 인터섹션 타입

- 특정 속성이나 인자가 인터섹션 타입으로 선언된 경우, 두 타입의 속성을 모두 포함하는 객체가 전달되어야 함.

```typescript
interface Hero {
  name: string;
}

interface Feature {
  ultimate: string;
}

function introduce(someone: Hero & Feature) {
  console.log('이름: ', someone.name);
  console.log('궁극기: ', someone.ultimate);
}

introduce({
  name: '라인하르트',
  ultimate: '대지 분쇄',
});
```

- 전달되는 객체에 인터섹션 타입 객체들에 필요한 모든 속성이 전달되지 않는 경우 에러 발생.

## 어려웠던 점

### 특정 타입 검증 과정의 추상화

- 유니언 타입으로 주어진 **객체**의 타입을 구분짓기 위해 `'age' in someone`이나, `'skill' in someone`와 같은 구문을 작성할 수 있지만, 해당 구문을 통해 'someone에 age 속성이 있으면 Person 타입일 것이고, skill 속성이 있으면 Developer 속성일 것이다'를 *단번에 인지하기*는 쉽지 않을 것으로 생각되었음.
- 따라서 다음과 같이 `typeof`를 이용해서도 타입을 구분지을 수 있을지 확인해봤지만, `typeof` 연산자는 Primitive 타입에 대해서만 검증이 가능하고 직접 정의한 타입에 대해서는 적용할 수 없었음.

```typescript
interface Person {
  name: string;
  age: number;
}

// 다음의 소스코드는 에러 발생
if (typeof person === 'Person') {
  // ...
}
```

- TypeScript에서는 함수의 반환형으로 다음과 같이 `type predicate`를 지정할 수 있으며, 이를 통해 다음과 같이 객체의 타입을 구분짓는 과정을 추상화할 수 있었음.

```typescript
function isPerson(someone: Person | Developer): someone is Person {
  return 'age' in someone;
}

var inu: Person = {
  name: '황인우',
  age: 29,
};

if (isPerson(inu)) {
  // ...
}
```

- 다만 `someone is Person`을 구문에 직접 작성할 수 있으면 함수를 통한 추상화보다도 소스코드의 양을 줄일 수 있을 것이라는 생각이 드는데, 그렇게 할 수 있는 방법은 없을지 의문.

### 펼침 연산자를 이용해 조합한 객체를 인터섹션 타입에 전달하는 경우의 유의점

- 다음과 같이 펼침 연산자로 분해한 객체의 속성을 조합해 생성한 객체를 전달하는 것도 가능.

```typescript
var nameProperty: Hero = { name: '한조' };
var ultimateProperty: Feature = { ultimate: '용의 분노' };

var hanjo: Hero & Feature = {
  ...nameProperty,
  ...ultimateProperty
};

introduce(hanjo);
```

- 펼침 연산자에 전달되는 객체의 이름이 인터섹션 타입에 정의된 객체 타입 이름과 다르더라도, 속성이 동일한 경우에는 사용 가능.

```typescript
interface Major {
  ultimate: string;
};

var nameProperty: Hero = { name: '트레이서' };
var ultimateProperty: Major = { ultimate: '펄스 폭탄' };

var tracer: Hero & Major = {
  ...nameProperty,
  ...ultimateProperty,
};

introduce(tracer);
```

- 단, 다음과 같이 펼침 연산자로 분해하는 객체의 타입이 `object`로 명시되는 경우에는 **사용 불가능**.

```typescript
var nameProperty: object = { name: '리퍼' };
var ultimateProperty: object = { ultimate: '죽음의 꽃' };

// 다음을 실행할 경우 에러 발생
introduce({
  ...nameProperty,
  ...ultimateProperty,
});
```

- TypeScript는 객체 펼침 연산자를 사용할 때 인터페이스나 타입(alias)에 대해서는 객체의 타입 정보를 유지하지만, `object` 타입에 대해서는 **객체의 타입 정보를 유지하지 않음**.
  - 마지막 예시의 `{ ...nameProperty, ...ultimateProperty }`의 경우, `nameProperty`와 `ultimateProperty`가 모두 `object` 타입이므로 펼쳐진 객체들의 타입 정보를 유지하지 않음. 따라서 생성된 객체의 타입이 `{}` 타입으로 추론됨.
  - 이로 인해 TypeScript는 전달되는 객체에 `Hero` 타입과 `Feature` 타입에 존재하는 속성들이 존재하는지 확인할 수 없고, 이로 인해 컴파일 및 실행 시 에러를 검출.

### References

- <https://www.typescriptlang.org/docs/handbook/advanced-types.html#user-defined-type-guards>
- <https://chat.openai.com/share/e3aa3294-6fac-4a8c-adae-21d7f42a76b3>
