package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        for (Method method : Address.class.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Inspect.class)) {
                    try {
                        method.invoke(address);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }

                    String name = method.getName();
                    String returnType = method.getReturnType().getSimpleName();
                    System.out.println(String.format("Method %s returns a value of type %s", name, returnType));
                }
        }
        // END
    }
}
