package geekbrains;

public class Main {
    static volatile char c = 'A';
    static final Object mon = new Object();

    static class WaitNotifyClass implements Runnable{
        private final char currentLetter;
        private final char nextLetter;

        public static void main(String[] args) {
            System.out.println("Start");
            new Thread(new WaitNotifyClass('A', 'B')).start();
            new Thread(new WaitNotifyClass('B', 'C')).start();
            new Thread(new WaitNotifyClass('C', 'A')).start();

        }

        public WaitNotifyClass(char currentLetter, char nextLetter){
            this.currentLetter = currentLetter;
            this.nextLetter = nextLetter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (mon) {
                    try {
                        while (c != currentLetter)
                            mon.wait();
                        System.out.print(currentLetter);
                        c = nextLetter;
                        mon.notifyAll();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

