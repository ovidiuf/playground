package playground.fibonacci;

public class Main {
    public static void main(String[] args) {
        for (int i = 2; i < 20; i ++) {
            System.out.println(i + ":");
            System.out.println("  " + fib(i));
            System.out.println("  " + fib_dp(i));
        }
    }

    public static long fib(long n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static long fib_dp(int n) {
        long[] a = new long[n + 1];
        a[0] = 0;
        a[1] = 1;
        for(int i = 2; i <= n; i ++) {
            a[i] = a[i-1] + a[i-2];
        }
        return a[n];
    }
}
