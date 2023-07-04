# 아이템 6. 불필요한 객체 생성을 피하라.

- 똑같은 기능의 객체는 매번 생성하기보다는 하나의 객체를 재사용하는 편이 나을 때가 많음
- 특히 불변 객체라면 더더욱.

## String 인스턴스

```java
String string = "Hello";
```

- 해당 소스코드는 새로운 String 인스턴스를 생성하지 않고 하나의 String 인스턴스를 사용함
- 추가적으로 해당 방식은, 같은 JVM 내에서 위와 똑같은 문자열 리터럴을 사용하는 모든 소스코드에서 같은 객체를 재사용함을 보장

## 생성 비용이 비싼 객체의 재사용

- 주어진 문자열이 유효한 로마숫자인지 정규표현식을 활용해 확인하는 예시

```java
public class RomanNumeralChecker {
    public static boolean isRomanNumeral(String string) {
        return string.matches("^(?=[MDCLXVI])M*(C[MD]|D?C*)(X[CL]|L?X*)(I[XV]|V?I*)$");
    }
}
```

- String.matches()는 자주 반복적으로 호출해 사용할 경우 성능에 좋지 않은 영향을 끼칠 수 있음.
  - 해당 메서드는 내부적으로 Pattern 객체 인스턴스를 생성.
  - Pattern 객체 인스턴스는 생성 시 주어진 정규표현식에 해당하는 유한 상태 머신(Finite State Machine)을 생성하기 때문에 인스턴스 생성 비용이 높은데, 해당 인스턴스는 호출된 String.matches() 메서드 내에서만 한 번 쓰이고 다시 쓰이지 않기 때문에 매번 Pattern 객체 인스턴스를 생성해야 함.
- 해당 문제는 해당 정규표현식을 표현하는 Pattern 객체 인스턴스를 클래스 초기화 시 미리 생성해두고, isRomanNumeral() 메서드를 호출할 때마다 해당 인스턴스를 재사용하는 방식으로 해결할 수 있음.

```java
public class RomanNumeralChecker {
    private static final Pattern ROMAN = Pattern.compile(
        "^(?=[MDCLXVI])M*(C[MD]|D?C*)(X[CL]|L?X*)(I[XV]|V?I*)$"
    );
    
    public static boolean isRomanNumeral(String string) {
        return ROMAN.matcher(string).matches();
    }
}
```

## 오토박싱(Auto Boxing)

- 연산 과정에서 Primitive 타입과 Wrapper 타입을 섞어 쓰는 경우, 오토박싱을 통해 자동적으로 상호 변환됨
- 연산의 횟수가 많을 경우, 오토박싱 과정에서 Wrapper 타입 인스턴스가 연산 횟수만큼 생성되면서 성능이 악화될 수 있음
  - 의도치 않은 오토박싱이 연산에 포함되지 않도록 유의해야 함!

```java
private long sum() {
    Long sum = 0L;
    for (long i = 0; i < Integer.MAX_VALUE; i += 1) {
        sum += i;
    }
    return sum;
}
```

## '객체 생성은 비용이 비싸니 피해야 한다'는 것이 아니다!

- JVM이 별다른 작업을 수행하지 않는 작은 객체를 생성하거나 회수하는 비용은 그렇게까지 크지 않음
- 애플리케이션의 명확성, 간결성, 기능을 고려한다면 필요한 객체는 필요에 맞게 추가로 생성하는 것이 좋다.
