package Geekbrains;
import java.util.ArrayList;
import java.util.Arrays;

        public class Main {
            public static void main(String[] args) {
            }
        }
        class ArrayTask {
            //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
            public static void arrToSwap(Object[] array, int a, int b) {
                Object tmp = array[a];
                array[a] = array[b];
                array[a] = tmp;
            }
            //2. Написать метод, который преобразует массив в ArrayList;
            public <T> ArrayList convert(T[] array) {
                return new ArrayList<>(Arrays.asList(array));
            }
        }
        // 3. Большая задача:
        //a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
        //b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
        //c. Для хранения фруктов внутри коробки можете использовать ArrayList;
        //d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
        //e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
        //f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
        //g. Не забываем про метод добавления фрукта в коробку.
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
