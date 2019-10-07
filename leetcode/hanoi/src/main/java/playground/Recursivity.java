package playground;

public class Recursivity {

   public static void move(int n, int source, int destination) {

      if (n == 1) {

         System.out.println("move disk from " + source + " to " + destination);

         return;
      }

      int spare = 3 - source - destination;

      Recursivity.move(n - 1, source, spare);

      System.out.println("move disk from " + source + " to " + destination);

      Recursivity.move(n - 1, spare, destination);
   }

   //
   // recursivity implemented with a StackImplementation
   //

}
