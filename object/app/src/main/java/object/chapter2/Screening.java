package object.chapter2;

import java.time.LocalDateTime;

public class Screening {
    private Movie movie;

    private int sequence;

    private LocalDateTime whenScreened;

    public static class Builder {
        private Movie movie;
        private int sequence;
        private LocalDateTime whenScreened;

        public Builder movie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public Builder sequence(int sequence) {
            this.sequence = sequence;
            return this;
        }

        public Builder whenScreened(LocalDateTime whenScreened) {
            this.whenScreened = whenScreened;
            return this;
        }

        public Screening build() {
            return new Screening(this);
        }
    }

    private Screening(Builder builder) {
        this.movie = builder.movie;
        this.sequence = builder.sequence;
        this.whenScreened = builder.whenScreened;
    }

    public LocalDateTime startTime() {
        return whenScreened;
    }

    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    public Money movieFee() {
        return movie.fee();
    }

    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation.Builder()
            .customer(customer)
            .screening(this)
            .totalFee(calculateFee(audienceCount))
            .audienceCount(audienceCount)
            .build();
    }

    private Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }
}
