package Geekbrains;

import org.junit.Test;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
// Создать класс, который может выполнять «тесты».
// В качестве тестов выступают классы с наборами методов,
// снабженных аннотациями @Test.
// Класс, запускающий тесты, должен иметь статический метод start(Class testClass),
// которому в качестве аргумента передается объект типа Class.
// Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если он присутствует.
// Далее запускаются методы с аннотациями @Test, а по завершении всех тестов – метод с аннотацией @AfterSuite.
// К каждому тесту необходимо добавить приоритеты (int-числа от 1 до 10),
// в соответствии с которыми будет выбираться порядок их выполнения.
// Если приоритет одинаковый, то порядок не имеет значения.
// Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре.
// Если это не так – необходимо бросить RuntimeException при запуске «тестирования».

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        TestClass tc = new TestClass();
        tc.start(TestClassOne.class);
    }
}

class TestClass{
    public static void start(Class testClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        if (!areBeforeAfterAnnotationsCorrect(testClass)) {
            throw new RuntimeException();
        }
        //  Метод newInstance() позволяет создавать экземпляры класса
        //  через объект типа Class и возвращает объект типа Object
        TestClassOne testClassOne = (TestClassOne) testClass.newInstance();
        //  Методы getMethods() и getDeclaredMethods() возвращают массив
        //  объектов типа Method, в которых содержится полная информация о методах класса
        Method[] methods = testClass.getDeclaredMethods();
        ArrayList<Method> testMethods = new ArrayList<>();
        Method first = null;
        Method last = null;
        for (Method method : methods) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                first = method;
            } else if (method.getAnnotation(AfterSuite.class) != null) {
                last = method;
            } else if (method.getAnnotation(Test.class) != null) {
                testMethods.add(method);
            }
        }
        testMethods.sort(Comparator.comparingInt(o -> o.getAnnotation(Priority.class).value()));
       // System.out.println(testMethods);
        if (first != null) {
            testMethods.add(0, first);
        }

        if (last != null) {
            testMethods.add(last);
        }

        try {
            for (Method testMethod : testMethods) {
                if (Modifier.isPrivate(testMethod.getModifiers())) {
                    testMethod.setAccessible(true);
                }
                testMethod.invoke(testClassOne);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
// Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре.
     static boolean areBeforeAfterAnnotationsCorrect(Class testClass) {
        int beforeAnnotationCount = 0;
        int afterAnnotationCount = 0;

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                beforeAnnotationCount++;
            }
            if (method.getAnnotation(AfterSuite.class) != null) {
                afterAnnotationCount++;
            }
        }
        return (beforeAnnotationCount < 2) && (afterAnnotationCount < 2);
    }
}





