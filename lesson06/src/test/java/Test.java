
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.lang.reflect.Array;


class TestClass {
    // Проверка: Тестовый массив
    int[] arrayOne={3,5,4,5,4,4,5,67,8,6,5,4,3,56,67};

    int[] arrayTwo={5,5,4,7,5,67,8,6,56,4,3,46,67};

    int[] arrayThree={4,67};


    //2 задание

    int[] array1={7,4,67}; //-
    int[] array2={1,67,8,};//-
    int[] array3={1,4};//+


    // int[] checkedArrayToo={3,5,5,5,67,8,6,5,3,56,67};


     private  ArrayTestClass arrayTestClass;

    @BeforeEach
    public void init() {
        arrayTestClass = new ArrayTestClass();
    }

    @Test
    @Timeout(value = 100)
    public void test1() {
        Assertions.assertEquals("[3, 56, 67]", arrayTestClass.arrayAfterFour(arrayOne));
    }
    @Test
    @Timeout(value = 100)
    public void test2() {

        Assertions.assertEquals("[3, 46, 67]", arrayTestClass.arrayAfterFour(arrayTwo));
    }
    @Test
    @Timeout(value = 100)
    public void test3() {
        Assertions.assertEquals("[67]", arrayTestClass.arrayAfterFour(arrayThree));
    }


    @Test
    public void test4(){
        Assertions.assertTrue(arrayTestClass.arrayOneFour(array1));
     //   Assertions.assertArrayEquals();
    }
    @Test
    public void test5(){
        Assertions.assertTrue(arrayTestClass.arrayOneFour(array2));
     //   Assertions.assertArrayEquals();
    }
    @Test
    public void test6(){
        Assertions.assertTrue(arrayTestClass.arrayOneFour(array3));
     //   Assertions.assertArrayEquals();
    }




    }

