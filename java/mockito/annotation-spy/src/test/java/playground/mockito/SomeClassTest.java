package playground.mockito;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.Mockito;
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
    public void a() {

        SomeClass c = new SomeClass(mockExternalDependency);
        c.a();
        verify(mockExternalDependency).aSupport();
        verify(mockExternalDependency, times(1)).aSupport();
        verify(mockExternalDependency, times(0)).bSupport();
    }
}
