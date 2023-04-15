package effectivejava.items.chapter2.item2;

public class NewYorkPizza extends Pizza {
    public enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private Size size;

        public Builder() {

        }

        public Builder size(Size size) {
            this.size = size;
            return this;
        }

        @Override
        public NewYorkPizza build() {
            return new NewYorkPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public NewYorkPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

    public Size size() {
        return size;
    }
}
