# 아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라.

## 싱글턴(singleton)

- 인스턴스를 오직 하나만 생성할 수 있는 클래스
- 일반적으로 stateless하게 사용되는 객체나,
  설계상 유일해야 하는 시스템 컴포넌트를 구성할 때 싱글턴 방식을 도입
- **싱글턴 클래스는 싱글턴 클래스를 사용하는 클라이언트의 테스트 코드를 작성하기 어렵다는 단점이 존재**
  - 싱글턴 인스턴스를 mock으로 대체할 수 없기 때문
  - 타입을 인터페이스로 정의한 뒤, 해당 인터페이스를 구현해서 생성하는
    방식으로 문제를 해결할 수 있음
    (Spring에서 싱글턴으로 Bean을 구성하는 방식이 이에 해당)

## 싱글턴 객체의 구현 방식

- 생성자를 private으로 은닉
- 유일한 인스턴스에 접근할 수 있는 수단을 제공

### public static final 필드를 제공하는 방식

```java
public class SingletonObject {
    public static final SingletonObject INSTANCE = new SingletonObject();
    
    private SingletonObject() {
        // ...
    }
    
    public void doSomething() {
        // ...
    }
}
```

### 정적 팩터리 메서드를 제공하는 방식

```java
public class SingletonObject {
    private static final SingletonObject INSTANCE = new SingletonObject();
    
    private SingletonObject() {
        // ...
    }
    
    public static SingletonObject getInstance() {
        return INSTANCE;
    }
    
    public void doSomething() {
        // ...
    }
}
```

- 이러한 방식들은 public이나 protected 생성자가 존재하지 않으므로
  해당 클래스의 인스턴스가 전체 시스템에서 단 하나임을 보장할 수 있음.

### 원소가 하나인 열거 타입을 선언하는 방식

```java
public enum SingletonEnum {
    INSTANCE;
    
    public void doSomething() {
        // ...
    }
}
```

- enum의 인스턴스는 단일하기 때문에 아래에서 언급할 Reflection API를 이용하거나,
  역직렬화를 통해 싱글턴이 아닌 인스턴스를 생성할 수 있는 문제를
  별도의 예외처리를 위한 소스코드의 작성 없이도 방지할 수 있음.
- 단, 생성하려는 싱글턴이 enum 이외의 클래스를 상속해야 할 경우 해당 방법은 사용할 수 없음.

## 싱글턴 보장 취약점 및 해결방안

### Reflection API를 통한 추가적인 인스턴스 생성 문제

- Reflection API인 AccessibleObject.setAccessible() 메서드를 통해
  private 생성자를 호출하는 방식으로 다른 인스턴스를 생성할 수 있다는 취약점이 존재.
  - 해당 문제를 방지하기 위해서는 생성자를 수정해서
    단 하나의 인스턴스 이외에는 다른 객체가 생성되지 않도록 예외처리를 해줄 필요가 있음.

> cf. Reflection API란?
> - 런타임에 구체적인 클래스 타입을 알지 못해도 해당 클래스의 정보에 접근할 수 있도록 하는 Java API
> - 사용자가 작성한 소스코드는 컴파일 과정에서 바이트 코드로 변환되어 static 영역에 저장되는데,
    >   런타임에 해당 정보들에 직접 접근하는 방식으로 특정 클래스의 정보를 획득
> - 일반적으로는 프레임워크, 라이브러리에서 사용자가 어떤 사용자 정의 클래스를 생성해 사용할지
    >   알 수 없기 때문에 이에 대한 문제를 동적으로 해결하기 위해 사용.
    >   - 애플리케이션 개발 과정에서는 구체적인 클래스를 충분히 알 수 있기 때문에 일반적으로 사용하지 않으며,
          >     JVM 최적화가 어렵고, private 타입의 인스턴스나 변수에도 접근할 수 있기 때문에
          >     객체지향의 이점을 해치는 문제가 있을 수 있으므로 사용하지 않음
> - Reference: https://tecoble.techcourse.co.kr/post/2020-07-16-reflection-api/

```java
private SingletonObject() {
    if (INSTANCE != null) {
        throw new IllegalStateException("이미 instance가 생성되어 있습니다.");
    }
}
```

### 역직렬화를 통한 추가적인 인스턴스 생성 문제

- 싱글턴으로 구성한 클래스의 직렬화 및 역직렬화가 필요한 경우,
  직렬화한 싱글턴 클래스에 대해 역직렬화를 수행했을 때 새로운 인스턴스가 생성되므로
  이 경우에도 싱글턴을 보장할 수 없게 됨
  - 역직렬화 자체가 사실상 생성자를 통해 객체를 생성하는 식으로 동작을 수행하기 때문
- 해당 이슈를 방지하기 위해 직렬화 관련 메서드인 readResolve()를 정의해
  역직렬화 과정에서 수행되는 readObject()를 통해 생성된 인스턴스 결과를 무시하고
  readResolve()로부터 반환되는 싱글턴 인스턴스를 사용하도록 하는 방식을 취할 수 있음
  - 이 경우, **해당 싱글턴 클래스의 모든 필드 변수에는 transient 예약어를 부여**해 직렬화에서 제외시킴

```java
public class SingletonObject {
    // ...

    private Object readResolve() {
        return INSTANCE;
    }
}
```

## Additional Reference
- https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%8B%B1%EA%B8%80%ED%86%A4-%EA%B0%9D%EC%B2%B4-%EA%B9%A8%EB%9C%A8%EB%A6%AC%EB%8A%94-%EB%B0%A9%EB%B2%95-%EC%97%AD%EC%A7%81%EB%A0%AC%ED%99%94-%EB%A6%AC%ED%94%8C%EB%A0%89%EC%85%98#thankYou
