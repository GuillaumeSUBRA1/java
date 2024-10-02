package advanced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class list {
    public static void main(String[] args) {
        System.out.println("##### liste de 20 nombres entiers #####\n");
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            integers.add(i);
        }
        even(integers);
        odd(integers);
        min(integers);
        max(integers);
        multiple_of(integers, 3);
        multiple_of(integers, 4);
        multiple_of(integers, 5);
        mean(integers);
        sum(integers);
        reverse(integers);
        separateInTwo(integers);
        System.out.println("##### liste de 20 nombres entiers #####\n");
    }

    public static void even(List<Integer> l) {
        System.out.println("tous les items pairs de la liste = " + l.stream().filter((i) -> i % 2 == 0).toList());
    }

    public static void odd(List<Integer> l) {
        System.out.println("tous les items impairs de la liste = " + l.stream().filter((i) -> i % 2 == 1).toList());
    }

    public static void min(List<Integer> l) {
        System.out.println("plus petit item de la liste = " + Collections.min(l));
    }

    public static void max(List<Integer> l) {
        System.out.println("plus grand item de la liste = " + Collections.max(l));
    }

    public static void multiple_of(List<Integer> l, int x) {
        System.out.println(
                "tous les items multiples de " + x + " de la liste = " + l.stream().filter((i) -> i % x == 0).toList());
    }

    public static void mean(List<Integer> l) {
        System.out.println("moyenne de la liste = " + l.stream().mapToDouble(a -> a).average().getAsDouble());
    }

    public static void sum(List<Integer> l) {
        System.out.println("somme des items de la liste = " + l.stream().mapToInt(i -> i).sum());
    }

    public static <T> void reverse(List<T> l) {
        System.out.println("liste inversée = " + l.reversed());
    }

    public static <T> void separateInTwo(List<T> l) {
        System.out.println("liste moitié 1 = " + l.subList(0, l.size() / 2) + " | liste moitié 2 = "
                + l.subList(l.size() / 2, l.size()));
    }
}
