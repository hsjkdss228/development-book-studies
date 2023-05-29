package object.chapter2;

import java.time.Duration;

public class Movie {
    private String title;

    private Duration runningTime;

    private Money fee;

    private DiscountPolicy discountPolicy;

    public static class Builder {
        private String title;
        private Duration runningTime;
        private Money fee;
        private DiscountPolicy discountPolicy;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder runningTime(Duration runningTime) {
            this.runningTime = runningTime;
            return this;
        }

        public Builder fee(Money fee) {
            this.fee = fee;
            return this;
        }

        public Builder discountPolicy(DiscountPolicy discountPolicy) {
            this.discountPolicy = discountPolicy;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    private Movie(Builder builder) {
        this.title = builder.title;
        this.runningTime = builder.runningTime;
        this.fee = builder.fee;
        this.discountPolicy = builder.discountPolicy;
    }

    public Money fee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) {
        Money discountAmount = discountPolicy.calculateDiscountAmount(screening);
        return fee.minus(discountAmount);
    }

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
