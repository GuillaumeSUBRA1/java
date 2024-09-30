package advanced;

public class fibonacci {
    public static void main(String[] args) {
        fib(30);
    }

    public static void fib(int max) {
        int x = 0;
        int y = 1;
        int fib = 0;

        System.out.print(x + " ");
        System.out.print(y + " ");

        for (int i = 2; i < max; i++) {
            fib = x + y;
            System.out.print(fib + " ");

            x = y;
            y = fib;
        }
    }
}
