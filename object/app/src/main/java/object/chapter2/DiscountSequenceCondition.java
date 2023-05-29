package object.chapter2;

public class DiscountSequenceCondition implements DiscountCondition {
    private final int sequence;

    public static class Builder {
        private int sequence;

        public Builder sequence(int sequence) {
            this.sequence = sequence;
            return this;
        }

        public DiscountSequenceCondition build() {
            return new DiscountSequenceCondition(this);
        }
    }

    public DiscountSequenceCondition(Builder builder) {
        this.sequence = builder.sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}
