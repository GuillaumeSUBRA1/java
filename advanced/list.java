package advanced;

import java.util.ArrayList;
import java.util.List;

public class list {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            list.add(i);
        }
        even(list);
        odd(list);
        multiple_of(list, 3);
        multiple_of(list, 4);
        multiple_of(list, 5);
        mean(list);
        sum(list);
    }

    public static void even(List<Integer> l) {
        System.out.println("tous les items pairs de la liste = " + l.stream().filter((i) -> i % 2 == 0).toList());
    }

    public static void odd(List<Integer> l) {
        System.out.println("tous les items impairs de la liste = " + l.stream().filter((i) -> i % 2 == 1).toList());
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
}
