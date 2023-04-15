package effectivejava.items.chapter2.item2;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PizzaTest {
    @Test
    void NewYorkPizzaBuild() {
        NewYorkPizza newYorkPizza = new NewYorkPizza.Builder()
            .size(NewYorkPizza.Size.SMALL)
            .addTopping(Pizza.Topping.SAUSAGE)
            .addTopping(Pizza.Topping.ONION)
            .build();

        assertThat(newYorkPizza).isNotNull();
        assertThat(newYorkPizza.getClass()).isEqualTo(NewYorkPizza.class);
        assertThat(newYorkPizza.size()).isEqualTo(NewYorkPizza.Size.SMALL);
        assertThat(newYorkPizza.toppings()).isEqualTo(Set.of(
            Pizza.Topping.SAUSAGE,
            Pizza.Topping.ONION
        ));
    }

    @Test
    void calzonePizzaBuild() {
        CalzonePizza calzonePizza = new CalzonePizza.Builder()
            .addTopping(Pizza.Topping.HAM)
            .addTopping(Pizza.Topping.MUSHROOM)
            .addTopping(Pizza.Topping.PEPPER)
            .sauceInside()
            .build();

        assertThat(calzonePizza).isNotNull();
        assertThat(calzonePizza.getClass()).isEqualTo(CalzonePizza.class);
        assertThat(calzonePizza.toppings).isEqualTo(Set.of(
            Pizza.Topping.HAM,
            Pizza.Topping.MUSHROOM,
            Pizza.Topping.PEPPER
        ));
        assertThat(calzonePizza.sauceInside()).isTrue();
    }
}
