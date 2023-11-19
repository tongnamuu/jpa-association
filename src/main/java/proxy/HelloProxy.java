package proxy;

import net.sf.cglib.proxy.Enhancer;

public class HelloProxy {
    public static HelloTarget create() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new ToUpperCaeInterceptor());
        Object o = enhancer.create();
        return (HelloTarget) o;
    }
}
