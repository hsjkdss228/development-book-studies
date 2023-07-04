package effectivejava.items.chapter2.item6;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RomanNumeralCheckMethodExecutionTimeTest {
    private void testExecutionTime(RomanNumeralChecker romanNumeralChecker) {
        int count = 10000;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i += 1) {
            assertThat(romanNumeralChecker
                .isRomanNumeral("MDCCLXXVI"))
                .isTrue();
        }
        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        System.out.println("수행 시간: " + elapsedTime + "ms");
    }

    @Test
    void isRomanNumeral() {
        testExecutionTime(new RomanNumeralCheckerDefault());
    }

    @Test
    void isRomanNumeralWithCachedPattern() {
        testExecutionTime(new RomanNumeralCheckerWithCachedPattern());
    }
}
