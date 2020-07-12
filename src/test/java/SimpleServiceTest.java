import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Test;

public class SimpleServiceTest {

    @Test
    public void testFixedValueProxy() {
        SimpleService proxy = SimpleService.createFixedValueProxy();
        assertEquals("Hello~ Fixed!", proxy.hello("abc"));
        assertEquals("Hello~ Fixed!", proxy.hello("def"));
        assertEquals("Hello~ Fixed!", proxy.hello(anyString()));
    }

    @Test
    public void testAroundAdviceProxy() {
        SimpleService proxy = SimpleService.createAroundAdviceProxy();
        assertEquals("Hello~ Proxy", proxy.hello("hi"));
        assertEquals("Hello~ Proxy", proxy.hello("hello"));
        assertEquals("Hello~ Proxy", proxy.hello(anyString()));
        assertEquals((Integer) 2, proxy.length("hi"));
        assertEquals((Integer) 5, proxy.length("hello"));
    }
}
