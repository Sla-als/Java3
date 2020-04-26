package Geekbrains;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
    }
}
class ArrayTask {
    //1
    public static void arrToSwap(Object[] array, int a, int b) {
        Object tmp = array[a];
        array[a] = array[b];
        array[a] = tmp;
    }
    //2
    public <T> ArrayList convert(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
}
    // 3
    class Fruit {
        private float weight;

        public Fruit(float weight) {
            this.weight = weight;
        }

        float getWeight() {
            return weight;
        }
    }
    class Apple <T> extends Fruit  {
        public Apple(float weight) {
            super(weight);
        }

    }
    class Orange <T> extends Fruit {
        public Orange(float weight) {
            super(weight);
        }
    }

    class Box <T extends Fruit>  {
        // Складываем  фрукты одного типа
         private ArrayList <T> arrayList;
        // конструктор на один или несколько фрутов
        public Box() {
            this.arrayList = new ArrayList<>();
        }
        public Box(T... fruits) {
            this.arrayList = new ArrayList<>(Arrays.asList(fruits));
        }
        // вес
        public float getWeight() {
            float weight = 0.0f;
            for (T o : arrayList) {
                weight += o.getWeight();
            }
            return weight;
        }
        // сравнить
        public boolean compare(Box <?> box){
            return (this.getWeight()-box.getWeight() < 0.0001);
        }
        // добавить
        public void add(T fruit) {
            arrayList.add(fruit);
        }
        // перемещение
        public void sprinkle(Box<T> another) {
        another.arrayList.addAll(arrayList);
        arrayList.clear();
    }
}


