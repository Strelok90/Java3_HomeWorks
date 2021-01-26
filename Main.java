package main.geekbrains.hw;


import main.geekbrains.hw.testing.TestsHandler;

public class Main {
    public static void main(String[] args) {
        /**
         * Task1
         */
        ClassForTesting classForTesting = new ClassForTesting();
        TestsHandler.start(classForTesting.getClass());

        /**
         * Task2
         */
        System.out.println();
       // ClassExplorer.outClassInfo(String.class);
    }
}