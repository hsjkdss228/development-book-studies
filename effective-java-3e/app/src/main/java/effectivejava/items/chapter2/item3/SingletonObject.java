package effectivejava.items.chapter2.item3;

import java.io.Serial;
import java.io.Serializable;

public class SingletonObject implements Serializable {
    public static final SingletonObject INSTANCE
        = new SingletonObject();

    private SingletonObject() {
        if (INSTANCE != null) {
            throw new IllegalStateException("이미 instance가 생성되어 있습니다.");
        }
    }

    @Serial
    private Object readResolve() {
        return INSTANCE;
    }

    public void doSomething() {

    }
}
