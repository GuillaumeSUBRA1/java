package basics.condition;

public class if_elseif_else {
    public static void main(String[] args) {
        int x = 2;
        if (x == 1) {
            System.out.println(x);
        } else if (x < 1) {
            System.out.println(0);
        } else {
            System.out.println(x++);
        }
    }
}