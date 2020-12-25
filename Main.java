package ru.geekbrains;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Integer[] intArr = new Integer[2];

        intArr[0] = 1;
        intArr[1] = 2;

        System.out.println(Arrays.deepToString(intArr));
        swap(intArr, 0, 1);
        System.out.println(Arrays.deepToString(intArr));

        toArrayList(intArr);

        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Apple apple3 = new Apple();

        Orange orange1 = new Orange();
        Orange orange2 = new Orange();

        Box<Apple> box1 = new Box<Apple>(apple1,apple2,apple3);
        Box<Orange> box2 = new Box<Orange>(orange1,orange2);
        box1.remove(apple2);
        box2.add(orange2);

        System.out.println(box1.compare(box2));
        if(box1.compare(box2)) {
            System.out.println("Коробки равны по весу");
        } else System.out.println("Коробки не равны по весу");

        Box<Apple> box3 = new Box<Apple>();
        box1.transfer(box3);
    }

    public static void swap(Object[] arr, int index1, int index2) {
        Object tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    public static <T> ArrayList<T> toArrayList(T[] arr) {
        return new ArrayList<T>(Arrays.asList(arr));
    }
}