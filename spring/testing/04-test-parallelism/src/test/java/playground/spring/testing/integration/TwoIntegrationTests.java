package playground.spring.testing.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import playground.spring.testing.AComponent;
import playground.spring.testing.BComponent;
import playground.spring.testing.SharedSingleton;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        AComponent.class,
        BComponent.class,
        SharedSingleton.class})
public class TwoIntegrationTests {

    @Autowired
    private AComponent aComponent;
    
    @Autowired
    private SharedSingleton sharedSingleton;

    @Test
    public void testOne() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test One (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("blue");
    }

    @Test
    public void testTwo() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Two (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("magenta");
    }

    @Test
    public void testThree() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Three (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("yellow");
    }

    @Test
    public void testFour() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Four (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("red");
    }

    @Test
    public void testFive() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Five (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("white");
    }

    @Test
    public void testSix() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Six (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("black");
    }

    @Test
    public void testSeven() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Seven (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("burgundy");
    }

    @Test
    public void testEight() throws Exception {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Eight (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Egiht is sleeping for 0.5 secs ....");
        System.out.println("###");
        System.out.println("###");

        Thread.sleep(500L);

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("green");
    }

    @Test
    public void testNine() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Nine (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("gray");
    }

    @Test
    public void testTen() {

        System.out.println("###");
        System.out.println("###");
        System.out.println("### Test Ten (2) on thread " + Thread.currentThread() + ", " + sharedSingleton);
        System.out.println("###");
        System.out.println("###");

        assertNotNull(aComponent);
        assertNotNull(aComponent.getDependency());

        sharedSingleton.setColor("pink");
    }


}
