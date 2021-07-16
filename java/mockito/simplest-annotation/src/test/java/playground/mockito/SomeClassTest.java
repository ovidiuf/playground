package playground.mockito;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import plagyround.mockito.ExternalDependency;
import plagyround.mockito.SomeClass;

import java.io.IOException;

public class SomeClassTest {

    private AutoCloseable mocks;

    @Mock
    private ExternalDependency mockExternalDependency;

    @Before
    public void openMocks() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void releaseMocks() throws Exception {
        mocks.close();
    }

    @Test
    public void read() throws Exception {
        //
        // implementing a Stub
        //
        SomeClass c = new SomeClass(mockExternalDependency);
        when(mockExternalDependency.readNextLine()).thenReturn("synthetic-test-value");
        assertEquals("synthetic-test-value", c.read());
    }

    @Test(expected = IOException.class)
    public void read_Failure() throws Exception {
        //
        // implementing a Stub
        //
        SomeClass c = new SomeClass(mockExternalDependency);
        when(mockExternalDependency.readNextLine()).thenThrow(IOException.class);
        c.read();
    }

    @Test
    public void containsLine() throws Exception {
        //
        // implementing a Stub
        //
        SomeClass c = new SomeClass(mockExternalDependency);

        when(mockExternalDependency.containsLine(isA(String.class))).thenReturn(true).thenReturn(false);
        assertTrue(c.lineExists("does not matter"));
        assertFalse(c.lineExists("does not matter"));
    }
}
