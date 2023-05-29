package object.chapter2;

public class Reservation {
    private Customer customer;

    private Screening screening;

    private Money totalFee;

    private int audienceCount;

    public static class Builder {
        private Customer customer;
        private Screening screening;
        private Money totalFee;
        private int audienceCount;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder screening(Screening screening) {
            this.screening = screening;
            return this;
        }

        public Builder totalFee(Money totalFee) {
            this.totalFee = totalFee;
            return this;
        }

        public Builder audienceCount(int audienceCount) {
            this.audienceCount = audienceCount;
            return this;
        }

        public Reservation build() {
            return new Reservation(this);
        }
    }

    private Reservation(Builder builder) {
        this.customer = builder.customer;
        this.screening = builder.screening;
        this.totalFee = builder.totalFee;
        this.audienceCount = builder.audienceCount;
    }
}
