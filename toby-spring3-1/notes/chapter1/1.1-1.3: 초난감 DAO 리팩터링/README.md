# 1장. 오브젝트와 의존관계

## 관심사의 분리

```java
public class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost/springbook", "spring", "book"
        );
        PreparedStatement preparedStatement = connection
            .prepareStatement("INSERT INTO USERS(id, name, password)" +
                    "values(?, ?, ?)");
        // ...
    }

    public void get(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost/springbook", "spring", "book"
        );
        PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT *" +
                "FROM USERS" +
                "WHERE id = ?");
        // ...
    }
}
```

- 해당 DAO 클래스에는 어떤 하나의 관심사가 코드의 여러 부분에 흩어져 있어 다른 관심사와 얽혀 있음
- 이로 인해 변화에 대응하기 취약한 구조인데,
  - 특정 관심사에 대한 변경이 일어날 때, 코드의 여러 부분을 직접 찾아다니며 변경해야 함
  - 특정 관심사에 대한 변경이 다른 관심사에도 영향을 끼쳐 연쇄적인 수정을 해야 할 수 있음

### 분리와 확장 

- 프로그래밍에서 변경, 발전, 확장성에 대비하기 위해서는 어떻게 해야 하는가?
  - 변화가 일어날 떄, 변화하는 폭을 최소한으로 줄일 수 있어야 함
- 일반적으로 변화는 어떤 집중된 한 가지 관심에 대해 일어나지만, 그에 따른 작업은 한 곳에 집중되지 않는 경우가 많음.
  - 하나의 영역에는 하나의 관심사만 집중되게 해야 할 것임
  - 하나의 영역에 뭉쳐있는 여러 종류의 관심사를 적절하게 구분하고 분리하는 작업이 필요

### 중복된 코드의 메서드 추출

- 특정한 관심사항이 담긴 동작을 여러 메서드에서 공통적으로 수행하는 경우, 해당 관심사항을 별도의 메서드로 분리할 수 있음

```java
public class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection
            .prepareStatement("INSERT INTO USERS(id, name, password)" +
                    "values(?, ?, ?)");
        // ...
    }

    public void get(User user) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT *" +
                "FROM USERS" +
                "WHERE id = ?");
        // ...
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost/springbook", "spring", "book"
        );
    }
}
```

### 상속을 통한 확장

- 사용하는 주체에 따라 getConnection()을 다른 방식으로 적용하고 싶은 경우,
  UserDao 클래스를 abstract 클래스로 변환하고, 해당 클래스를 상속받는 클래스에서
  getConnection()을 원하는 방식대로 구현하도록 하는 방식을 취할 수 있음

```java
public abstract class UserDao {
    private abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
```

```java
public class NaverUserDao extends UserDao {
    @Override
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        // Naver의 DB Connection 생성 방식
    }
}

public class KakaoUserDao extends UserDao {
    @Override
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        // Kakao의 DB Connection 생성 방식
    }
}
```

- 수정된 UserDao는 템플릿 메서드 패턴과, 팩토리 메서드 패턴을 따르고 있음

> - 템플릿 매서드 패턴 (Template Method Pattern)
>   - 상위 클래스에 (슈퍼클래스) 기본적인 로직의 흐름을 만들고,
>     기능의 일부를 추상 메서드나 오버라이딩이 가능한 protected 메서드로 만든 뒤
>     상속받는 클래스에서 (서브클래스) 해당 메서드를 필요에 맞게 구현해서 사용하도록 하는 디자인 패턴
> - 팩토리 메서드 패턴 (Factory Method Pattern)
>   - 서브클래스에서 구체적인 객체 생성 방법을 결정하게 하는 것
>   - 슈퍼클래스는 서브클래스에서 구현할 메서드를 호출해서 필요한 타입의 객체를 가져와 사용.
>   - 이때 객체는 interface 타입으로 반환받음

### interface를 통한 확장

- 상속을 통한 확장에는 단점이 존재
  - 단순히 한 가지 목적으로만 상속 구조를 형성한다면
    (여기서는 Connection 객체를 가져오는 방법을 분리하기 위한 목적으로만),
    추후 다른 목적으로 UserDao에 상속을 적용하기는 어려워질 수 있음
  - 서브클래스는 슈퍼클래스의 기능을 직접 사용할 수 있기 때문에
    슈퍼클래스 내부가 변경될 경우 모든 서브클래스가 수정되어야 할 수도 있고,
    이로 인해 서로 다른 관심사가 여전히 긴밀하게 결합될 수 있는 문제가 있음
  - 서브클래스에서 정의한 메서드를 다른 클래스에는 적용할 수 없음

#### 클래스의 분리

- 다른 관심사에 대한 영역을 완전히 독립적인 클래스로 만들어 분리할 수 있음

```java
public class ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost/springbook", "spring", "book"
        );
    }
}

public class UserDao {
    private ConnectionMaker connectionMaker;
    
    public UserDao() {
        connectionMaker = new ConnectionMaker();
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        // ...
    }

    public void get(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        // ...
    }
}
```

- 그러나 이러한 클래스 분리는 아직 한계가 존재
  - ConnectionMaker에서 제공하는 메서드에 종속적이기 때문에
    makeConnection()이 바뀐다면, 이를 이용하는 add()와 get() 역시 수정되어야 함
  - UserDao가 ConnectionMaker의 클래스를 구체적으로 알고 있어야 함.
    해당 구조에서는 다른 ConnectionMaker를 받아 사용할 수 없음

#### interface의 도입

- 클래스 분리 과정에서 한계가 발생한 이유는 UserDao가 바뀔 수 있는 정보인
  ConnectionMaker 클래스라는 '단일한 클래스'에 대해 너무 구체적으로 알고 있는 것으로부터 기인
- 해당 문제는 클래스 간에 추상화된 통로를 두는 것으로 해결할 수 있음
- interface 자체에는 기능만 정의되어 있고, 구체적인 구현 방법은 나타나있지 않음
- interface는 자신을 구현한 구현체 클래스에 대한 정보를 모두 감추기 때문에,
  해당 interface를 사용하는 쪽에서 interface를 구현한 구현체를 몰라도 사용할 수 있음.

```java
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
```

```java
public class NaverConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // Naver의 DB Connection 생성 방식
    }
}

public class KakaoConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // Kakao의 DB Connection 생성 방식
    }
}
```

```java
public class UserDao {
    private ConnectionMaker connectionMaker;
    
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        // ...
    }

    public void get(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        // ...
    }
}
```

- 객체 사이의 관계는 특정 객체 내에서 직접 생성자가 호출되어 형성되는 방식뿐만 아니라
  위의 소스코드처럼 객체 외부에서 만들어진 객체를 전달받는 방법도 있음
- 해당 소스코드에서 UserDao는 ConnectionMaker 인터페이스와만 관계가 맺어져 있기 때문에,
  더 이상 UserDao 자신이 어떤 구체적인 ConnectionMaker를 사용해야 할지 결정하지 않아도 되게 되었음
  - UserDao는 이제 SQL 생성과 실행에만 집중할 수 있게 되었음
- 해당 UserDao가 어떤 ConnectionMaker 구현 클래스를 전달받아 사용할지는
  UserDao의 인스턴스를 사용하는 쪽에서 결정해야 함

## 원칙과 패턴

### 개방 폐쇄 원칙 (Open-closed Principle)

> 클래스나 모듈은 확장에는 열려 있어야 하고, 변경에는 닫혀 있어야 한다.

- 개선된 UserDao는
  - 확장에 열려 있음
    - ConnectionMaker의 메서드 시그니쳐에 맞는 구현체를 새롭게 만들어내는 것으로
      UserDao에 전혀 영향을 주지 않고도 DB에 연결하는 방식을 얼마든지 확장할 수 있음
  - 변경에 닫혀 있음
    - UserDao의 핵심 기능을 구현한 소스코드는 확장에 따른 변화에 영향받지 않고 유지될 수 있음
      - 아무리 다양한 종류의 ConnectionMaker 구현체가 존재하고 각기 다른 구현체를 전달받더라도,
        UserDao의 핵심 기능은 ConnectionMaker의 동작에 상관 없이 자신의 기능을 유지

### 높은 응집도와 낮은 결합도

#### 높은 응집도

> 하나의 모듈, 클래스가 하나의 책임 또는 관심사에 집중되어 있는 것

- 개선 이전의 UserDao는 DB 커넥션을 연결하는 책임과 데이터 엑세스에 대한 책임이라는 여러 책임이 집중되어 있으나,
  개선 이후에는 데이터 엑세스에 대한 책임에만 집중하고 있음

#### 낮은 결합도

> 하나의 객체가 변경이 일어날 때, 관계를 맺고 있는 다른 객체의 변화를 요구하는 정도가 낮은 것

- 개선된 UserDao는 ConnectionMaker의 구현체가 바뀌어도 UserDao의 코드가 변경될 필요가 없음
- 개선된 UserDao는 UserDao 인스턴스에서 사용할 ConnectionMaker 구현체가 달라지더라도,
  사용할 구현체를 결정하는 책임이 UserDao를 사용하는 쪽에 있기 때문에
  마찬가지로 UserDao의 코드가 변경될 필요가 없음 

### 전략 패턴 (Strategy Pattern)

- 자신의 기능 맥락에서 (Context)
  - 여기서는 UserDao
- 필요에 따라 변경이 필요한 알고리즘은 interface를 통해 통째로 외부로 분리시키고
  - 여기서 지칭하는 알고리즘이란: 독립적인 책임으로 분리가 가능한 기능
  - 여기서는 DB 연결 방식을 정의한 interface인 ConnectionMaker
- 이를 구현한 구체적인 알고리즘 class를 필요에 따라 바꿔 사용할 수 있게 하는 디자인 패턴

## 그래서 스프링이란?

- 스프링이란 지금까지 설명한 객체지향적 설계 원칙과 디자인 패턴에 나타난 장점들을
  자연스럽게 개발자들이 활용할 수 있게 해주는 프레임워크.
