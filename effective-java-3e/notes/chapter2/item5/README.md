# 아이템 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.

## 의존 객체 주입

- 하나 이상의 자원에 의존하는 어떤 클래스가 사용하는 자원에 따라 동작이 달라지는 경우, 정적 유틸리티 클래스나 싱글턴 방식으로 구현하는 것은 적절하지 않음.

```java
public class StaticSpellChecker {
    private static final Lexicon dictionary = new KoreanDictionary();
    
    private StaticSpellChecker() {
        
    }
    
    public static boolean isValid(String word) {
        // ...
    }

    public static List<String> suggestions(String typo) {
        // ...
    }
}
```

```java
public class SingletonSpellChecker {
    private final Lexicon dictionary = new EnglishDictionary();
    
    private SingletonSpellChecker() {
        
    }
    
    public static SingletonSpellChecker INSTANCE = new SingletonSpellChecker();
    
    public boolean isValid(String word) {
        // ...
    }

    public List<String> suggestions(String typo) {
        // ...
    }
}
```

- 해당 방식으로는 단 하나의 Lexicon 구현체만을 사용할 수밖에 없음.
- 이 구조를 유지하면서 여러 종류의 Lexicon 구현체를 사용하도록 하기 위해서는 Lexicon 타입 필드 변수에 부여된 final 키워드를 제거한 뒤, setter를 이용해 다른 Lexicon 구현체를 주입하는 방식을 따라야 하지만, 해당 방식은 오류를 발생시키기 쉽고 Multi-thread 환경에서는 사용할 수 없다는 문제점이 있음.
- 해당 문제는 **해당 클래스의 인스턴스를 생성할 때, 생성자에 필요한 자원을 넘겨주는 방식**으로 해결할 수 있음

```java
public class SpellChecker {
    private final Lexicon dictionary;
    
    public SpellChecker(Lexicon dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) {
      // ...
    }
  
    public List<String> suggestions(String typo) {
      // ...
    } 
}
```

- 의존 객체를 주입하는 방식은 다음과 같은 장점이 있음
  - 생성자로 Lexicon의 어떤 구현체던지 전달받을 수 있음
  - 전달받은 자원의 불변이 보장되므로, 해당 자원을 여러 클라이언트가 사용하려는 경우에도 안전하게 공유할 수 있음

## 의존 객체 생성 팩터리 주입

- 주입받아야 하는 의존 객체를 생성하는 팩터리를 전달받는 방식을 따를 수도 있음

```java
public class Mosaic {
    private final Tile tile;
    
    private Mosaic(Tile tile) {
        this.tile = tile;
    }
    
    public static Mosaic create(Supplier<? extends Tile> tileFactory) {
        return new Mosaic(tileFactory.get());
    }
}
```

- 해당 방식에서는 한정적 와일드카드 타입을 통해 팩터리의 타입 매개변수 종류를 제한
- 해당 클래스의 생성을 위한 팩터리 메서드를 호출할 때, 명시된 타입의 하위 타입을 생성하는 팩터리를 전달할 수 있음

## 의존 객체 주입 프레임워크

- 의존 객체 주입은 유연성과 테스트 용이성을 개선해주는 이점이 있지만, 애플리케이션 내 의존성이 대단히 많아지는 경우 관리가 어려워질 수 있음
- Spring과 같은 의존 객체 주입 프레임워크를 사용해 의존 객체를 적절하게 주입할 수 있음
