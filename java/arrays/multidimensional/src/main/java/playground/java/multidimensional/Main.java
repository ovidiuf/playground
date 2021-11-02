package playground.java.multidimensional;

public class Main {
    public static void main(String[] args) {
        // m x n matrix with m = 3 rows and n = 4 columns
        int[][] a =  // equivalent with int[][] a = new int[3][4]
                    {
                      {11, 12, 13, 14},
                      {21, 22, 23, 24},
                      {31, 32, 33, 34}
                    };

        System.out.println("number of rows: " + a.length); // will display m = 3
        System.out.println("number of columns: " + a[0].length); // will display n = 4
        System.out.println("a11=" + a[0][0] + " a12=" + a[0][1] + " a13=" + a[0][2] + " a14=" + a[0][3]);
        System.out.println("a21=" + a[1][0] + " a22=" + a[1][1] + " a23=" + a[1][2] + " a24=" + a[1][3]);
        System.out.println("a31=" + a[2][0] + " a32=" + a[2][1] + " a33=" + a[2][2] + " a34=" + a[2][3]);
    }
}
