# 아이템 4. 인스턴스화를 막으려거든 private 생성자를 사용하라.

- static 필드 값이나 static 메서드만이 존재하는 클래스는 일반적으로 인스턴스를 생성해 사용하지 않음. 그러나 생성자를 명시하지 않는 경우 컴파일러는 자동으로 기본 생성자를 정의하기 때문에, 사용자가 임의로 인스턴스를 생성하거나 해당 클래스를 상속해 확장한 다른 클래스를 생성할 수 있는 문제가 발생할 수 있음.
- 이를 막기 위해 다음과 같이 private 생성자만을 정의하는 방식으로 클래스의 인스턴스화를 방지할 수 있음.

```java
public class Factory {
    private Factory() {
        
    }
    
    public static ObjectType of() {
        // ...
    }
    
    // ...
}
```

```java
public class Model {
    public void process() {
        // 인스턴스화할 수 없다.
        Factory factory = new Factory();
    }
}
```

- private 생성자만을 정의함으로써 상속을 방지할 수 있는 효과도 있음. 하위 클래스의 생성자는 상위 클래스의 생성자를 호출해야 하는데, 상위 클래스의 생성자의 접근 제어자가 private이기 때문에 하위 클래스에서 상위 클래스의 생성자에 접근할 수 없음.
