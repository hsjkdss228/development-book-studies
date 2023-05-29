package object.chapter2;

public class NoneDiscountPolicy extends DiscountPolicy {
    public static class Builder extends DiscountPolicy.Builder<Builder> {
        @Override
        public DiscountPolicy build() {
            return new NoneDiscountPolicy(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private NoneDiscountPolicy(Builder builder) {
        super(builder);
    }

    @Override
    public Money discountAmount(Screening screening) {
        return Money.ZERO;
    }
}
