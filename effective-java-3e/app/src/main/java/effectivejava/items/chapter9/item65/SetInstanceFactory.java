package effectivejava.items.chapter9.item65;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

public class SetInstanceFactory {
    public Set<String> create(String typeName, List<String> inputs)
        throws ClassNotFoundException,
        NoSuchMethodException,
        IllegalAccessException,
        InstantiationException,
        InvocationTargetException,
        ClassCastException {

        // typeName으로 전달된 클래스가 존재하지 않을 경우
        // ClassNotFoundException 예외가 발생한다.
        Class<? extends Set<String>> className
            = (Class<? extends Set<String>>) Class.forName(typeName);

        // 생성자 메서드가 존재하지 않을 경우
        // NoSuchMethodException 예외가 발생한다.
        Constructor<? extends Set<String>> constructor
            = className.getDeclaredConstructor();

        // 생성자에 접근할 수 없는 경우 IllegalAccessException,
        // 클래스를 인스턴스화할 수 없는 경우 InstantiationException,
        // 생성자에서 예외가 발생할 경우 InvocationTargetException 예외가 발생한다.
        // typeName으로 전달된 클래스가 Set을 구현하지 않은 클래스일 경우
        // ClassCastException 예외가 발생한다.
        Set<String> strings = constructor.newInstance();

        strings.addAll(inputs);
        return strings;
    }
}
