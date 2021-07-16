package playground.mockito;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
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
    public void containsLine() {
        //
        // implementing a Stub
        //
        SomeClass c = new SomeClass(mockExternalDependency);

        when(mockExternalDependency.containsLine(isA(String.class))).thenReturn(true).thenReturn(false);
        assertTrue(c.lineExists("does not matter"));
        assertFalse(c.lineExists("does not matter"));
    }

    @Test
    public void write_ValidLine() throws Exception {
        SomeClass c = new SomeClass(mockExternalDependency);
        c.write("valid line");
    }

    @Test(expected = IOException.class)
    public void write_LineTooLong() throws Exception {
        SomeClass c = new SomeClass(mockExternalDependency);
        doThrow(IOException.class).when(mockExternalDependency).writeLine("this line is too long .................");
        c.write("this line is too long .................");
    }

    @Test
    public void dumpConfig_UnknownSystem() {
        SomeClass c = new SomeClass(mockExternalDependency);
        assertNull(c.dumpConfig("does not matter", "does not matter"));
    }
}
