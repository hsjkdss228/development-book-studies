package object.chapter2;

public class DiscountPercentPolicy extends DiscountPolicy {
    private double percent;

    public static class Builder extends DiscountPolicy.Builder<Builder> {
        private double percent;

        public Builder percent(double percent) {
            this.percent = percent;
            return this;
        }

        @Override
        public DiscountPolicy build() {
            return new DiscountPercentPolicy(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private DiscountPercentPolicy(Builder builder) {
        super(builder);
        percent = builder.percent;
    }

    @Override
    public Money discountAmount(Screening screening) {
        Money movieFee = screening.movieFee();
        return movieFee.times(percent);
    }
}
