package lesson5.geekbrains;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private final Race race;
    private final int speed;
    private final String name;
    private final CountDownLatch prepareLatch;
    private final CountDownLatch startLatch;
    private final CountDownLatch finishLatch;
    private final Semaphore tunnelSemaphore;
    private final Semaphore winSemaphore;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public void acquireTunnelSemaphore() throws InterruptedException{
        this.tunnelSemaphore.acquire();
    }
    public void releaseTunnelSemaphore(){
        this.tunnelSemaphore.release();
    }
    public Car(Race race, int speed, CountDownLatch prepareLatch, CountDownLatch startLatch, CountDownLatch finishLatch,
               Semaphore tunnelSemaphore, Semaphore winSemaphore) {
        this.race = race;
        this.speed = speed;
        this.prepareLatch = prepareLatch;
        this.startLatch = startLatch;
        this.finishLatch = finishLatch;
        this.tunnelSemaphore = tunnelSemaphore;
        this.winSemaphore = winSemaphore;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            prepareLatch.countDown();
            startLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        try {
            finishLatch.countDown();
            winSemaphore.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(this.name + " - WIN");
    }
}