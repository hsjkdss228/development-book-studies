package effectivejava.items.chapter9.item65;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SetInstanceFactoryTest {
    private SetInstanceFactory setInstanceFactory;

    @BeforeEach
    void setUp() {
        setInstanceFactory = new SetInstanceFactory();
    }

    /**
     * 전달된 String 컬렉션이 HashSet에 삽입되어 있는지만 검증한다.
     */
    @Test
    void createWithHashSet() throws ClassNotFoundException,
        InvocationTargetException,
        IllegalAccessException,
        InstantiationException,
        NoSuchMethodException,
        ClassCastException {

        List<String> inputs = List.of("Eevee", "Pikachu", "Chikorita");
        Set<String> strings = setInstanceFactory.create("java.util.HashSet", inputs);
        assertThat(strings).isNotNull();
        assertThat(strings).contains("Chikorita");
        assertThat(strings).contains("Eevee");
        assertThat(strings).contains("Pikachu");
    }

    /**
     * 전달된 String 컬렉션이 TreeSet에 오름차순으로 정렬되어 삽입되어 있는지 검증한다.
     */
    @Test
    void createWithTreeSet() throws ClassNotFoundException,
        InvocationTargetException,
        IllegalAccessException,
        InstantiationException,
        NoSuchMethodException,
        ClassCastException {

        List<String> inputs = List.of("Eevee", "Pikachu", "Chikorita");
        Set<String> strings = setInstanceFactory.create("java.util.TreeSet", inputs);
        assertThat(strings).isNotNull();

        List<String> stringsConvertedToList = strings.stream()
            .toList();
        List<String> outputsOrderedByAsc = List.of("Chikorita", "Eevee", "Pikachu");
        for (int index = 0; index < outputsOrderedByAsc.size(); index += 1) {
            assertThat(stringsConvertedToList.get(index))
                .isEqualTo(outputsOrderedByAsc.get(index));
        }
    }

    @Test
    void throwClassNotFoundException() {
        assertThrows(ClassNotFoundException.class, () -> {
            setInstanceFactory.create("NonExistentSet", List.of());
        });
    }

    @Test
    void throwClassCastException() {
        assertThrows(ClassCastException.class, () -> {
            setInstanceFactory.create("java.util.ArrayList", List.of());
        });
    }
}
