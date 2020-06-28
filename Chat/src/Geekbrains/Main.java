package Geekbrains;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }

    public static class WaitNotifyClass {
        private final Object mon = new Object();
        private volatile char currentLetter = 'A';

        public static void main(String[] args) {
            WaitNotifyClass w = new WaitNotifyClass();

            Thread t1 = new Thread(() -> {
                w.printA();
            });

            Thread t2 = new Thread(() -> {
                w.printB();
            });

            // Новый поток для метода .printC()
            Thread t3 = new Thread(() -> {
                w.printC();
            });

            t1.start();
            t2.start();
            t3.start();

        }

        public void printA(){
            synchronized (mon){
                try {
                    for (int i = 0; i < 5; i++) {
                        while (currentLetter != 'A'){
                            mon.wait();
                        }
                        System.out.print("A");
                        currentLetter = 'B';
                        // all тк потоков больше чем 2
                        mon.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void printB() {
            synchronized (mon) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (currentLetter != 'B') {
                            mon.wait();
                        }
                        System.out.print("B");
                        currentLetter = 'C';
                        // all тк потоков больше чем 2
                        mon.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        public void printC() {
            // Синхпрнизация по монитору
            synchronized (mon) {
                try {

                    for (int i = 0; i < 5; i++) {

                        while (currentLetter != 'C') {
                            mon.wait();
                        }
                        System.out.print("C");
                        currentLetter = 'A';
                        // all тк потоков больще чем 2
                        mon.notifyAll();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
