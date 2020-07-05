import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTestClass {
    public String arrayAfterFour(int[] array) {
        List<Integer> listOfInt = new ArrayList<Integer>();
        int[] newArrayAfterFour = new int[0];
        boolean checkFour = false;
        // проходим по всем элементам массива
        for (int i = 0; i < array.length; i++) {
            // если есть четверки
            if (array[i] == 4) {
                // то создаем новый array
                newArrayAfterFour = new int[array.length - (i + 1)];
                for (int j = 0, a = i; j < newArrayAfterFour.length; a++, j++) {
                    // и заполняем его тем что справа от четверки
                    newArrayAfterFour[j] = array[a + 1];
                }
                // если хоть раз зашли в условие, то флажок true
                checkFour = true;
            }
            // и так проходим по всему массиву, создавая заново, newArrayAfterFour, хотя это наверное затратно
        }
        if (!checkFour) {
            //если в массиве нет 4, то флажок false и сюда не заходим
            throw new RuntimeException("нет 4");
        }
        // для тестов удобнее чтобы метод возвратил строку
//        System.out.println(Arrays.equals(arrayTestClass.arrayAfterFour(arrayOne),expectedArrayOne));
        String joinedString = Arrays.toString(newArrayAfterFour);
        return joinedString;
    }

    //    Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы, то
//    метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    // корректно ли делать статик переменные в даном случае?
    static boolean checkOne = false;
    static boolean checkTwo = false;
    public static  boolean arrayOneFour(int[] array) {

      boolean checkFinal = (checkOne && checkTwo);
        for (int i = 0; i <array.length; i++)  {
            if (array[i]==1){
                checkOne = true;
            }
            if (array[i]==4){
                checkTwo=true;
            }
        }
        return checkFinal;
    }

}

