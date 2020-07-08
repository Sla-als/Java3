package Geekbrains;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BeforeSuite {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterSuite {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
 @interface Priority {
    int value();

}

public class TestClassOne {
    @BeforeSuite
    @Priority(1)
    public void test0(){
        System.out.println("method BeforeSuite worked");
    }
    @Test
    @Priority(2)
    public void test1(){
        System.out.println("method 1 worked");
    }
    @Test
    @Priority(3)
    public void test2(){
        System.out.println("method 2 worked");
    }
    @Test
    @Priority(4)
    public void test3(){
        System.out.println("method 3 worked");
    }
    @Test
    @Priority(5)
    public void test9(){
        System.out.println("method 4 worked");
    }
    @AfterSuite
    @Priority(10)
    public void testLast(){
        System.out.println("method AfterSuite worked");
    }
}


