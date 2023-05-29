package object.chapter2;

import java.util.Arrays;
import java.util.List;

public abstract class DiscountPolicy {
    private List<DiscountCondition> discountConditions;

    abstract static class Builder<T extends Builder<T>> {
        private List<DiscountCondition> discountConditions;

        public T discountConditions(DiscountCondition... conditions) {
            this.discountConditions = Arrays.asList(conditions);
            return self();
        }

        abstract DiscountPolicy build();

        protected abstract T self();
    }

    DiscountPolicy(Builder<?> builder) {
        this.discountConditions = builder.discountConditions;
    }

    /**
     * 할인 여부와 요금 계산에 필요한 전체적인 흐름을 정의
     */
    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition discountCondition : discountConditions) {
            if (discountCondition.isSatisfiedBy(screening)) {
                return discountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    /**
     * 실제로 요금을 계산하는 로직은 해당 추상 메서드에 위임.
     * 해당 메서드는 DiscountPolicy를 상속받은 자식 클래스에서 오버라이딩한 메서드를 실행
     */
    abstract public Money discountAmount(Screening screening);
}
