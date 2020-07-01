package Geekbrains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static int CARS_COUNT = 4;
    private static Lock lock =new ReentrantLock();

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        // Все участники должны стартовать одновременно, несмотря на разное время  подготовки. == CyclicBarrier
        //
        // В тоннель не может одновременно заехать больше половины участников (условность). == Semaphore
        //Попробуйте все это синхронизировать.
        //Первый участник, пересекший финишную черту, объявляется победителем (в момент пересечения этой самой черты). == ?
        //Победитель должен быть только один (ситуация с 0 или 2+ победителями недопустима). == ?
        //Когда все завершат гонку, нужно выдать объявление об окончании. == ?
        //Можно корректировать классы (в том числе конструктор машин) и добавлять объекты классов из пакета java.util.concurrent.

        //----------Классы синхронизации----------
        // для training
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
        // для гонка началась
        final CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
        // для тоннелeй
        Semaphore smp = new Semaphore(CARS_COUNT/2);
        // для финиша


        // для гонка закончилась
        final CountDownLatch cdlFin = new CountDownLatch(CARS_COUNT);


        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        //Создается новая трасса (длинна, тунели, дорога)
        Race race = new Race(new Road(60), new Tunnel(smp), new Road(40));
        //Укороченная гонка для тестов
        //Race race = new Race(new Tunnel(smp));
        Car[] cars = new Car[CARS_COUNT];
        // участники ( гонка? рандомная скорость)
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),lock, cdlFin);

        }


        // потоки участников
        // 1 этап: подготовка
           for (int i = 0; i < cars.length; i++) {
               final int w = i;
               new Thread(() -> {
                   // ожидаем всех на подготовке
                   cars[w].training(cb);
                   cdl.countDown();
               }).start();
           }
               // ожидаем всех у линии старта
               cdl.await();
               System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        // 2 этап: основная часть - гонка
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        cdlFin.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

        } // psvm end
    } // main class ends



class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;

    // добавленные поля
    CountDownLatch cdlFin;
    Lock lock;

    //

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, Lock lock, CountDownLatch cdlFin) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;

        this.lock=lock;
        this.cdlFin = cdlFin;
    }

    @Override
    public void run() {
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);

        }
            if (lock.tryLock()) {
                System.out.println("Участник #" + " " + name + " ------------------win------------------");
            }
            // не убираем лок, тк только один поток должен победить
        //lock.unlock();

        cdlFin.countDown();
        }

    public void training(CyclicBarrier cyclicBarrier){
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


abstract class Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }
    public abstract void go(Car c);
}
class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public  void  go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Tunnel extends Stage {
    Semaphore smp;
    public Tunnel(Semaphore smp) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        // Semaphore
        this.smp = smp;
    }

    @Override
    public void  go(Car c) {
        try {
            try {
              if (!smp.tryAcquire()){
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                // Semaphore
                smp.acquire();}
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Semaphore
        smp.release();
    }
}
 class Race {
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }
    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
 }
