package basics.list;

import java.util.ArrayList;
import java.util.List;

public class list {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            list.add(i);
        }
        size(list);
        first(list);
        last(list);
        even(list);
        odd(list);
        multiple_of(list, 3);
        multiple_of(list, 4);
        multiple_of(list, 5);
        mean(list);
        sum(list);
    }

    public static void size(List<Integer> l) {
        System.out.println("taille de la liste = " + l.size());
    }

    public static void first(List<Integer> l) {
        System.out.println("premier item de la liste = " + l.getFirst());
    }

    public static void last(List<Integer> l) {
        System.out.println("dernier item de la liste = " + l.getLast());
    }

    public static void even(List<Integer> l) {
        List<Integer> pairs = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            if (i % 2 == 0) {
                pairs.add(i);
            }
        }
        System.out.println("tous les items pairs de la liste = " + pairs);
    }

    public static void odd(List<Integer> l) {
        List<Integer> impairs = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            if (i % 2 == 1) {
                impairs.add(i);
            }
        }
        System.out.println("tous les items impairs de la liste = " + impairs);
    }

    public static void multiple_of(List<Integer> l, int x) {
        List<Integer> multiple = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            if (i % x == 0) {
                multiple.add(i);
            }
        }
        System.out.println("tous les items multiples de " + x + " de la liste = " + multiple);
    }

    public static void mean(List<Integer> l) {
        int mean = 0;
        for (int i = 0; i < l.size(); i++) {
            mean += l.get(i);
        }
        System.out.println("moyenne de la liste = " + mean / l.size());
    }

    public static void sum(List<Integer> l) {
        int s = 0;
        for (int i = 0; i < l.size(); i++) {
            s += l.get(i);
        }
        System.out.println("somme des items de la liste = " + s);
    }
}
