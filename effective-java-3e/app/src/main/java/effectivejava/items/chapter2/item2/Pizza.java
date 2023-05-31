package effectivejava.items.chapter2.item2;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public abstract class Pizza {
    public enum Topping {
        HAM,
        MUSHROOM,
        ONION,
        PEPPER,
        SAUSAGE
    }

    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(topping);
            return self();
        }

        /**
         * 각 하위 클래스의 Builder가 정의하는 build 메서드는
         * 해당하는 구체 하위 클래스를 반환하도록 선언.
         * 예를 들어 NewYorkPizza.Builder는 NewYorkPizza를,
         * CalzonePizza.Builder는 CalzonePizza를 반환.
         * 이를 통해 형변환에 신경쓰지 않아도 Builder를 사용할 수 있음 (Covariant return typing)
         */
        abstract Pizza build();

        /**
         * 각 하위 클래스에서 해당 메서드를 재정의할 때 this를 반환하도록 함
         */
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }

    public Set<Topping> toppings() {
        return new HashSet<>(toppings);
    }
}
