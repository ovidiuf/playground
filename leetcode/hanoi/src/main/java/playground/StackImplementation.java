package playground;

import java.util.Stack;

public class StackImplementation {

   private static Stack<Integer[]> stack = new Stack<>();

   public static void execute(int n, int source, int destination) {

      //
      // Not finished yet
      //

      int invocationLocation = 0;

      stack.push(new Integer[] {n, source, destination, invocationLocation});

      while(!stack.isEmpty()) {

         Integer[] args = stack.pop();
         n = args[0];
         source = args[1];
         destination = args[2];
         invocationLocation = args[3];
      }
   }

   public static void move(int n, int source, int destination) {

      if (n == 1) {

         System.out.println("move disk from " + source + " to " + destination);

         return;
      }

      move(n - 1, source, 3 - source - destination);

      System.out.println("move disk from " + source + " to " + destination);

      move(n - 1, 3 - source - destination, destination);
   }
}
