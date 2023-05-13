## 제어의 역전 (Inversion of Control)

### 팩토리 (Factory)

```java
public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectionMaker connectionMaker = new KakaoConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        // ...
    }
}
```

- 개선된 UserDao를 사용하는 영역의 코드에는 두 가지 책임이 동시에 존재하고 있음
  - UserDao의 기능이 잘 동작하는지 확인하는 책임
  - UserDao가 어떤 ConnectionMaker 구현체를 사용하게 할 것인지 결정하게 하는 책임
- UserDao와 ConnectionMaker 구현체 인스턴스를 만들고,
  만들어진 두 객체들 간에 관계를 맺어주기 위한 기능을 팩토에 클래스로 분리시킬 수 있음

```java
public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }
    
    public AccountDao accountDao() {
        retuen new AccountDao(connectionMaker());
    }

    public ConnectionMaker connectionMaker() {
        return new NaverConnectionMaker();
    }
}

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao userDao = new DaoFactory().userDao();
        // ...
    }
}
```

- 팩토리는 어떤 객체가 어떤 객체를 사용할 것인지를 정의해놓은 영역
  - 애플리케이션을 구성하는 컴포넌트의 구조와 관계를 정의하는 일종의 설계도와 같은 역할을 수행

### 제어권의 이전을 통한 제어관계 역전

- 제어의 역전에서는 특정 객체가 사용할 객체를 선택하거나 생성하는 역할을
  특정한 다른 대상에 위임
- 즉 제어의 역전 하에서는 main()과 같은 엔트리 포인트를 제외하면 모든 객체들은
  제어 권한을 갖는 특별한 객체에 의해 만들어지고 어떻게 사용될 것인지 결정됨
- 개선된 UserDao는
  - UserDao 자신도 DaoFactory에 의해 생성됨
  - 어떤 ConnectionMaker 구현체를 사용할 것인지의 권한이 DaoFactory에게 있음
- 제어의 역전에서는 이처럼 애플리케이션 컴포넌트의
  생성, 관계 설정, 사용, 생명주기 관리 등을 관장하는 존재가 필요

### 스프링에서의 IoC

- 스프링의 핵심은 바로 ApplicationContext
- 클라이언트는 ApplicationContext를 통해 구체적인 팩토리를 알지 못해도 되며,
  일관된 방식으로 원하는 객체를 가져올 수 있음

#### Bean

- 스프링이 제어권을 갖고 직접 만들어 관계를 부여하는 객체

#### BeanFactory

- 스프링의 IoC를 담당하는 핵심 컨테이너
- Bean을 등록하고, 생성하고, 조회해 반환하는 등 관리를 수행

#### ApplicationContext

- BeanFactory를 상속받은 IoC 컨테이너
- BeanFactory의 기능에 더해 스프링이 제공하는 애플리케이션 지원 기능들을 같이 제공
- 생성 정보나 연관관계 정보는 별도로 부여된 Configuration들을 통해 습득

#### Configuration

- ApplicationContext가 IoC를 적용하기 위해 사용하는 메타정보
- IoC 컨테이너에 의해 관리되는 애플리케이션 객체를 생성하고 구성할 때 사용
- 다음과 같이 설정정보를 부여할 수 있음

```java
@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new NaverConnectionMaker();
    }
}
```

## 싱글톤 레지스트리

- 스프링의 ApplicationContext는 여러 번에 걸쳐 Bean을 요청하더라도
  매번 동일한 객체를 반환함
- 즉, ApplicationContext는 싱글톤을 저장하고 관리하는 싱글톤 레지스트리 (Singleton Registry)

### 왜 싱글톤 형태로 Bean을 관리하는가?

- 스프링은 기본적으로 엔터프라이즈 시스템을 위해 고안된 기술
- 수많은 클라이언트에서 요청이 전달될 때마다 각 로직의 수행을 담당할 Bean 객체를 새로 생성해 사용할 경우,
  Java의 Garbage Collector의 관리 범위 이상으로 서버에 부하가 발생할 수 있음
- 따라서 Java 엔터프라이즈 기술의 가장 기본이 되는 Service 객체인 Servlet은
  Servlet 클래스 당 하나의 객체만 생성해둔 뒤,
  사용자의 요청을 담당하는 각 스레드에서 하나의 객체를 공유해 동시에 사용

### 싱글톤 패턴의 문제점을 극복하기 위한 싱글톤 레지스트리

- 스프링은 Java에서의 싱글톤 패턴이 가질 수 있는 문제점들을 극복하기 위해
  직접 싱글톤 형태의 객체를 만들고 관리
- 일반적인 싱글톤 패턴은 다음과 같은 단점이 존재
  - private 생성자를 이용해 생성하기 때문에 상속받을 수 없음
  - 생성되는 방식이 제한적이기 때문에 테스트 시 mocking하기 어려움
  - 서버 환경에서는 클래스 로더의 구성에 따라 하나 이상의 객체가 만들어질 수 있어 싱글톤이 반드시 보장되지 않음
  - static하기 때문에 전역에서 접근해 상태를 변경시킬 수 있음
- 스프링의 싱글톤 레지스트리는 일반적인 싱글톤 패턴 형태로 정의된 class의 객체를 생성하는 것이 아닌
  일반적인 Java class 형태의 객체를 활용하기 때문에 앞서 언급한 문제들에서 자유로울 수 있음

### 싱글톤과 객체의 상태

- 싱글톤은 멀티스레드 환경이라면 여러 스레드가 동시에 접근해서 사용할 수 있기 때문에
  상태정보를 내부에 갖고 있지 않는 Stateless 방식으로 정의되어야 함
  - 각 요청으로부터의 정보나 DB, 서버의 리소스로부터 생성한 정보들은
    파라미터, 지역 변수, 반환값 등을 이용해 관리할 수 있음
    - 메서드는 각 스레드 별로 자신의 값을 저장할 독립적인 공간을 stack에 할당해 사용하기 때문에
      싱글톤 객체에 여러 스레드가 접근하더라도 어떤 덮어씌울 값이 없으므로 안전
  - 단, 싱글톤 객체 자신이 사용하는 다른 싱글톤 Bean을 저장하는 목적으로는
    인스턴스 변수를 사용
    - 이 경우에도 생성 시 한 번 초기화시키고 이후에는 수정시키지 않는 식으로 사용

## 의존관계 주입 (Dependency Injection)

### 의존

- A 객체가 B 객체에 의존하는 경우, B 객체에 기능이 추가되거나 변경될 경우, 혹은 형식이 바뀌었을 때
  그 영향이 A 객체에도 전달될 수 있음
- 의존관계에는 방향성이 존재
  - A 객체가 B 객체에 의존하고 있고 B 객체는 A 객체에 의존하고 있지 않을 경우,
    A 객체는 B 객체의 변화에 영향을 받지만, B 객체는 A 객체의 변화에 영향을 받지 않음

### 의존관계 주입

- 의존관계에는 다음의 두 종류가 존재
  - 모델이나 코드 상에서 class와 interface 정의를 통해 드러나는 의존관계
  - 런타임 시 객체들 간에 형성될 수 있는 의존관계
- 애플리케이션이 실행되었을 때 객체는 연결관계가 형성된 interface의 구현체를 사용하게 되는데,
  이때 대상 객체와 그 객체가 실제로 사용하게 되는 interface의 구현체인 '의존 객체'를 런타임에 연결해주는 작업을 지칭
- 이때 런타임 시점의 의존관계는 컨테이너나 팩토리 같은 제3의 존재가 결정
- 일반적으로 의존관계는 생성자를 통해 주입받음

```java
public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    
    // ...
}
```

- DI 하에서 특정 객체는 자신이 사용할 객체를 선택하거나 생성하는 제어권을 외부로 넘기고,
  자신은 수동적으로 주입받은 객체를 사용하므로 IoC의 개념과 일맥상통
  - 스프링의 IoC는 DI에 초점이 맞춰져 있음
- 의존관계를 주입받기 위해서는 전달되는 객체와 전달받는 객체 모두
  ApplicationContext에 의해 생성되는 Bean 객체여야 함
  - 컨테이너가 전달받는 객체에 다른 객체를 주입하기 위해서는 전달받는 객체에 대해서도
    생성이나 초기와 권한을 갖고 있어야 하기 때문

### 의존관계 주입의 응용

#### 기능 구현의 교환

- 여러 Bean들에 전달되는 구현체 객체를 통해 수행하는 기능을 공통적으로 수정하고자 할 때,
  단순히 전달하는 구현체를 다른 구현체로 바꿔 전달하는 것만으로도 해당 공통적인 기능을 수정할 수 있음
  - 기능 수정을 위해 해당 구현체 객체를 전달받는 Bean의 소스코드를 수정할 필요가 없음
- 다음과 같이 환경에 따라 사용할 ConnectionMaker 구현체를 변경하는 작업이 필요할 경우,
  각기 다른 객체에 대해 런타임에 의존관계를 갖게 하는 식으로 적용할 수 있음

```java
@Bean
public ConnectionMaker connectionMaker() {
    return new LocalDbConnectionMaker();
    }

@Bean
public ConnectionMaker connectionMaker() {
    return new ProductionDbConnectionMaker();
    }
```

#### 부가기능 추가

- 기능의 부가적인 추가가 필요할 시, 두 객체 사이에
  전달받아야 하는 구현체 객체를 interface 필드로 갖는 새로운 객체를 정의한 뒤,
  해당 객체에서 추가적인 기능을 수행한 뒤 전달받아야 하는 구현체 객체를 다시 전달하는 방식으로
  부가적인 기능을 추가할 수 있음
- DB 연결횟수를 카운팅하는 기능을 추가하는 경우의 예시

```java
public class CountingConnectionMaker implements ConnetionMaker {
    int counter = 0;
    private ConnectionMaker connectionMaker;
    
    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter += 1;
        return connectionMaker.makeConnection();
    }
    
    public int counter() {
        return counter;
    }
}
```

```java
@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }
    
    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new NaverConnectionMaker();
    }
}
```
