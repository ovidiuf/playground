package playground.mockito;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import plagyround.mockito.ExternalDependency;
import plagyround.mockito.SomeClass;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SomeClassTest {

    private ExternalDependency mockExternalDependency;

    @Before
    public void setUp() {
        mockExternalDependency = mock(ExternalDependency.class);
    }

    @Test
    public void read() throws Exception {
        SomeClass c = new SomeClass(mockExternalDependency);
        when(mockExternalDependency.readNextLine()).thenReturn("test-value");
        assertEquals("test-value", c.read());
    }
}
