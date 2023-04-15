package effectivejava.items.chapter2.item2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NutritionFactsTest {
    @Test
    void build() {
        NutritionFacts cola = new NutritionFacts.Builder()
            .servingSize(100)
            .servings(8)
            .calories(100)
            .sodium(35)
            .carbohydrate(27)
            .build();

        assertThat(cola).isNotNull();
        assertThat(cola.servingSize()).isEqualTo(100);
        assertThat(cola.servings()).isEqualTo(8);
        assertThat(cola.calories()).isEqualTo(100);
        assertThat(cola.sodium()).isEqualTo(35);
        assertThat(cola.carbohydrate()).isEqualTo(27);
    }
}
