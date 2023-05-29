package object.chapter2;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DiscountPeriodCondition implements DiscountCondition {
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    public static class Builder {
        private DayOfWeek dayOfWeek;
        private LocalTime startTime;
        private LocalTime endTime;

        public Builder dayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public DiscountPeriodCondition build() {
            return new DiscountPeriodCondition(this);
        }
    }

    private DiscountPeriodCondition(Builder builder) {
        this.dayOfWeek = builder.dayOfWeek;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.startTime().getDayOfWeek().equals(dayOfWeek)
            && startTime.compareTo(screening.startTime().toLocalTime()) <= 0
            && endTime.compareTo(screening.startTime().toLocalTime()) >= 0;
    }
}
