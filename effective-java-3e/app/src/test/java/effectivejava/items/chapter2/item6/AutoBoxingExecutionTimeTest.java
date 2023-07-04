package effectivejava.items.chapter2.item6;

import org.junit.jupiter.api.Test;

public class AutoBoxingExecutionTimeTest {
    @Test
    void withAutoBoxing() {
        long startTime = System.currentTimeMillis();
        Long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i += 1) {
            sum += i;
        }
        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        System.out.println("합계: " + sum);
        System.out.println("수행 시간: " + elapsedTime + "ms");
    }

    @Test
    void withoutAutoBoxing() {
        long startTime = System.currentTimeMillis();
        long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i += 1) {
            sum += i;
        }
        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        System.out.println("합계: " + sum);
        System.out.println("수행 시간: " + elapsedTime + "ms");
    }
}
