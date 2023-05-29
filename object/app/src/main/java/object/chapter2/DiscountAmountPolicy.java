package object.chapter2;

public class DiscountAmountPolicy extends DiscountPolicy {
    private Money discountAmount;

    public static class Builder extends DiscountPolicy.Builder<Builder> {
        private Money discountAmount;

        public Builder discountAmount(Money discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        @Override
        public DiscountPolicy build() {
            return new DiscountAmountPolicy(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private DiscountAmountPolicy(Builder builder) {
        super(builder);
        discountAmount = builder.discountAmount;
    }

    @Override
    public Money discountAmount(Screening screening) {
        return discountAmount;
    }
}
