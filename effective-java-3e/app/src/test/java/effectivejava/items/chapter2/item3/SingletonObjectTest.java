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

class SingletonObjectTest {
    @Test
    void equals() {
        SingletonObject instance1
            = SingletonObject.INSTANCE;
        SingletonObject instance2
            = SingletonObject.INSTANCE;
        assertThat(instance1).isEqualTo(instance2);
    }

    /**
     * 해당 테스트 수행 시 Singleton 클래스의 생성자 내에서 IllegalStateException 예외 발생.
     * 이때 해당 예외는 생성자 내에서 발생하므로 발생하는 것으로 발생한 것으로 나타나는 예외는
     * java.lang.reflect.InvocationTargetException이게 됨.
     * 해당 InvocationTargetException의 getCause() 메서드를 호출해
     * 실제로 발생한 예외가 어떤 예외였는지 확인할 수 있음.
     */
    @Test
    void preventCreationUsingReflectionApi() {
        assertThrows(
            IllegalStateException.class, () -> {
                try {
                    createSingletonUsingReflectionApi();
                } catch (InvocationTargetException exception) {
                    throw exception.getCause();
                }
            });
    }

    private void createSingletonUsingReflectionApi()
        throws NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {

        Class<?> instance = SingletonObject.class;
        Constructor<?> constructor = instance.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void equalsWithDeserializedInstance() throws IOException, ClassNotFoundException {
        SingletonObject instance1 = SingletonObject.INSTANCE;

        String filename = "temp";

        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ObjectOutputStream outputStream = new ObjectOutputStream(bufferedOutputStream);
        outputStream.writeObject(instance1);
        outputStream.close();

        FileInputStream fileInputStream = new FileInputStream(filename);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream inputStream = new ObjectInputStream(bufferedInputStream);
        SingletonObject instance2 = (SingletonObject) inputStream.readObject();
        inputStream.close();

        assertThat(instance1).isEqualTo(instance2);
    }
}
