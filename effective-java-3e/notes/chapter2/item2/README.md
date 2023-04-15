# 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라.

## 점층적 생성자 패턴

- 정적 팩터리 메서드에도 여전히 일반적인 생성자가 가질 수 있는 문제점이 남아 있음.
- 그것은 바로 객체 생성 시 필요한 매개변수가 많을 때, 적절히 대응하기 어렵다는 데 있음.
- 점층적 생성자 패턴에서는 생성자의 시그니쳐를 구분하는 방식으로
  생성자의 가짓수를 늘려 필요한 데이터를 갖는 인스턴스를 생성

```Java
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    
    public NutritionFacts(int servingSize, int servings) {
        this.servingSize = servingSize;
        this.servings = servings;
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
    }
}
```

- 점층적 생성자 패턴은 적절한 인스턴스를 전달한다면 객체를 안정적으로 생성할 수는 있지만,
  매개변수가 많아지면 많아질수록 작성된 코드를 읽기가 어려움.
- 생성자에 많은 인자를 전달해야 한다면 어떤 인자를 전달했는지 헷갈릴 가능성이 높음.
  타입이 같은 매개변수들이 연달아 있다면 찾기 어려운 버그로 이어질 수 있음.

```Java
NutritionFacts cola = new NutrutionFacts(240, 8, 100);
```

## Java Beans 패턴

- 매개변수가 없는 생성자로 일단 객체를 만들고,
  setter 메서드를 호출해 원하는 매개변수의 값을 설정하는 방식.
- 필요한 데이터가 많아도 점층적 생성자 패턴보다는 읽기 쉬운 코드를 작성할 수 있음.

```Java
public class NutritionFacts {
    // members
    
    public NutritionFacts() {
        
    }
    
    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
```

```Java
NutritionFacts cola = new NutritionFacts();
cola.setServingSize(240);
cola.setServings(8);
cola.setCalories(100);
```

- 그러나 Java Beans 패턴의 심각한 문제점은 원하는 객체 하나를 만들기 위해
  메서드를 여러 개 호출해야 한다는 데 있음.
- 원하는 모든 메서드를 호출해 객체를 완전히 생성하기 전까지는,
  객체는 일관성(Consistency)이 무너진 상태에 놓여있게 됨.
- 이러한 일관성이 깨진 객체는 디버깅이 어려움.
  객체를 생성하는 과정에서 잘못된 버그를 유발하는 코드가 런타임에 버그가 발생하는 영역과
  물리적으로 떨어져 있을 가능성이 높음.
- 이로 인해 Java Beans 패턴에서는 클래스를 불변으로 만들 수 없으며,
  스레드 안정성을 얻기 위한 추가 작업이 필요.

## Builder 패턴

- 점층적 생성자 패턴의 안정성과, Java Beans 패턴의 가독성의 장점을 같이 취한 객체 생성 방식
- Builder 패턴으로 객체를 생성하는 과정
  1. 특정 객체에 정적 멤버 클래스로 정의한 Builder 객체의 생성자를 호출해
     Builder 인스턴스 생성
  2. Builder 객체가 제공하는 setter 메서드를 이용해 객체에 구성해야 할 데이터를
     우선 Builder 인스턴스에 저장
  3. build() 메서드를 호출해 Builder 인스턴스가 가진 데이터를
     객체 생성자에 전달하고, 생성된 객체를 반환해 객체 생성

```Java
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;

    public static class Builder {
        private int servingSize;
        private int servings;
        private int calories;
        
        public Builder() {
            
        }
        
        public Builder servingSize(int servingSize) {
            this.servingSize = servingSize;
            return this;
        }

        public Builder servings(int servings) {
            this.servings = servings;
            return this;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }
        
        public NurtitionFacts build() {
            return new NutritionFacts(this);
        }
    }
    
    // 객체 자체의 생성자는 private!
    private NurtitionFacts(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
    }
}
```

```Java
NutritionFacts cola = new NutritionFacts.Builder()
    .servingSize(240)
    .servings(8)
    .calories(100)
    .build();
```

- Builder의 setter 메서드들은 인스턴스 자기자신을 반환하기 때문에
  메서드 체이닝 형태로 setter 메서드와 build 메서드를 호출할 수 있음.
- 생성 과정에서 매개변수로 전달되는 값의 유효성을 검증하는 로직이 추가될 수 있을 것임.

### Builder 패턴의 단점?

- Builder를 생성하는 데 추가적인 비용이 발생. 성능에 민감한 상황이라면 문제가 될 수도 있음.

## Builder 패턴의 활용

- Builder 패턴은 계층적으로 설계된 클래스들과 함께 쓰는 데 적합
- 추상 클래스를 상속하는 클래스가 존재하는 경우,
  추상 클래스에 추상 빌더를 정의하고 구체(Concrete) 클래스에는 구체 빌더를 정의하는 형식
