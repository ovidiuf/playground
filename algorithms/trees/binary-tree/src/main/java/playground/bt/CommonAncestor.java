package playground.bt;

import java.util.HashSet;
import java.util.Set;

public class CommonAncestor {

    public void run() throws Exception {
        N r = Trees.load("./data/test2.txt");
        N n1 = r.forId(6);
        N n2 = r.forId(13);
        System.out.println("common ancestor of " + n1 + " and " + n2 + " is " + commonAncestor(n1, n2));
    }

    public N commonAncestor(N n1, N n2) {
        Set<N> ancestors = new HashSet<>();
        N crt1 = n1;
        N crt2 = n2;
        while(true) {
            // advance one parent at a step and check intersection
            if (crt1 != null) {
                if (ancestors.contains(crt1)) {
                    return crt1;
                } else {
                    ancestors.add(crt1);
                    crt1 = crt1.p;
                }
            }
            if (crt2 != null) {
                if (ancestors.contains(crt2)) {
                    return crt2;
                } else {
                    ancestors.add(crt2);
                    crt2 = crt2.p;
                }
            }
         }
    }
}
