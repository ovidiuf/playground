package playground.b;

import playground.a.A;

/**
 * @author ofeodorov@uplift.com
 * @since 05/03/2019
 */
public class B {

    @Override
    public String toString() {

        return "B(" + new A() +")";
    }
}
