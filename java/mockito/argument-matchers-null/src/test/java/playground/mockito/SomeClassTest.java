package playground.mockito;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import plagyround.mockito.ExternalDependency;
import plagyround.mockito.Result;
import plagyround.mockito.SomeClass;

public class SomeClassTest {

    private AutoCloseable mocks;

    @Mock
    private ExternalDependency mockExternalDependency;

    @Before
    public void openMocks() {
        mocks = MockitoAnnotations.openMocks(this);
        when(mockExternalDependency.process(nullable(String.class))).thenReturn(new Result("SYNTHETIC"));
    }

    @After
    public void releaseMocks() throws Exception {
        mocks.close();
    }

    @Test
    public void someMethod() {
        SomeClass c = new SomeClass(mockExternalDependency);
        Result r = c.someMethod("test");
        assertEquals("SYNTHETIC", r.s);
    }

    @Test
    public void someMethod_NullArgument() {
        SomeClass c = new SomeClass(mockExternalDependency);
        Result r = c.someMethod(null);
        assertEquals("SYNTHETIC", r.s);
    }
}
