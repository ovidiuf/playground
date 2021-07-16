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
        when(mockExternalDependency.process(anyString(), anyLong())).thenAnswer((Answer<Result>) invocation -> {
            String arg0 = invocation.getArgument(0);
            long arg1 = invocation.getArgument(1);
            if ("red".equals(arg0)) {
                return new Result("RED", arg1);
            }
            return new Result(arg0, arg1);
        });
    }

    @After
    public void releaseMocks() throws Exception {
        mocks.close();
    }

    @Test
    public void someMethod() {
        SomeClass c = new SomeClass(mockExternalDependency);
        Result r = c.someMethod("test", 10L);
        assertEquals("test", r.s);
        assertEquals(10L, r.l);
    }

    @Test
    public void someMethod_RedArgument() {
        SomeClass c = new SomeClass(mockExternalDependency);
        Result r = c.someMethod("red", 11L);
        assertEquals("RED", r.s);
        assertEquals(11L, r.l);
    }
}
