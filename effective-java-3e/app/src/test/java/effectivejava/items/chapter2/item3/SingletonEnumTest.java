package effectivejava.items.chapter2.item3;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingletonEnumTest {
    @Test
    void equals() {
        SingletonEnum instance1
            = SingletonEnum.INSTANCE;
        SingletonEnum instance2
            = SingletonEnum.INSTANCE;
        assertThat(instance1).isEqualTo(instance2);
    }

    /**
     * enum의 별도의 인스턴스를 임의로 생성하려 할 시 IllegalArgumentException 예외 발생
     */
    @Test
    void preventCreationUsingReflectionApi() {
        assertThrows(
            IllegalArgumentException.class,
            this::createSingletonUsingReflectionApi
        );
    }

    private void createSingletonUsingReflectionApi()
        throws InvocationTargetException,
        InstantiationException,
        IllegalAccessException {

        Class<?> instance = SingletonEnum.class;
        Constructor<?>[] constructors = instance.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }
    }

    @Test
    void equalsWithDeserializedInstance() throws IOException, ClassNotFoundException {
        SingletonEnum instance1 = SingletonEnum.INSTANCE;

        String filename = "temp";

        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ObjectOutputStream outputStream = new ObjectOutputStream(bufferedOutputStream);
        outputStream.writeObject(instance1);
        outputStream.close();

        FileInputStream fileInputStream = new FileInputStream(filename);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream inputStream = new ObjectInputStream(bufferedInputStream);
        SingletonEnum instance2 = (SingletonEnum) inputStream.readObject();
        inputStream.close();

        assertThat(instance1).isEqualTo(instance2);
    }
}
