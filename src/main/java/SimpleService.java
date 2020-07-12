import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;

public class SimpleService {

    public enum Method {
        HELLO("hello"),
        LENGTH("length");
        public final String str;
        Method(String str) {
            this.str = str;
        }

        public static Method find(String str) {
            for (Method method : Method.values()) {
                if (method.str.equals(str)) {
                    return method;
                }
            }
            throw new IllegalArgumentException("cannot find " + str);
        }
    }

    public String hello(String name) {
        return "Hello~ " + name;
    }
    public Integer length(String name) {
        return name.length();
    }

    /**
     * {@code FixedValue}
     * {@link Enhancer} callback that simply returns the value to return
     * from the proxied method. No information about what method
     * is being called is available to the callback, and the type of
     * the returned object must be compatible with the return type of
     * the proxied method.
     * ...
     */
    public static SimpleService createFixedValueProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SimpleService.class);
        enhancer.setCallback((FixedValue) () -> "Hello~ Fixed!");

        return (SimpleService) enhancer.create();
    }

    /**
     * General-purpose {@link Enhancer} callback which provides for "around advice".
     */
    public static SimpleService createAroundAdviceProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SimpleService.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getDeclaringClass() == Object.class) {
                throw new Exception("declaring class is not a SimpleService");
            }
            switch (Method.find(method.getName())) {
                case HELLO:
                    return "Hello~ Proxy";
                case LENGTH:
                    return proxy.invokeSuper(obj, args);
                default:
                    throw new IllegalArgumentException("cannot find "+ method.getName());
            }
        });
        return (SimpleService) enhancer.create();
    }
}