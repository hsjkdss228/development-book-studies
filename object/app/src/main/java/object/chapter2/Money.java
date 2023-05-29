package object.chapter2;

import java.math.BigDecimal;

/**
 * 의미를 좀 더 명시적이고 분명하게 표현할 수 있다면 객체를 사용해서 해당 개념을 구현하는 것을 권장.
 * 비록 해당 개념이 하나의 필드 변수만을 포함하더라도, 개념을 명시적으로 표현하는 것이
 * 전체적인 설계의 명확성과 유연성을 높이는 길!
 */
public class Money {
    public static final Money ZERO = Money.wons(0);

    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money wons(double amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public Money plus(Money money) {
        return new Money(amount.add(money.amount));
    }

    public Money minus(Money money) {
        return new Money(amount.subtract(money.amount));
    }

    public Money times(double percent) {
        return new Money(amount.multiply(BigDecimal.valueOf(percent)));
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }
}
