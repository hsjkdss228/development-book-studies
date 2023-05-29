package object.chapter2;

public class Customer {
    private Long id;

    private CustomerName name;

    public static class Builder {
        private Long id;
        private CustomerName name;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = CustomerName.of(name);
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    private Customer(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }
}
