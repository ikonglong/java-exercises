package dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class WhenInvocTargetExcepHappen {

    public static void main(String[] args) {
//        GreetingServiceFace proxyInstance = (GreetingServiceFace) Proxy.newProxyInstance(
//                InvocTargetExcepReproduction.class.getClassLoader(),
//                new Class[]{GreetingServiceFace.class},
//                new DynamicInvocationHandler());
//        try {
//            Method m = GreetingServiceFace.class.getMethod("sayHello");
//            System.out.println(m.invoke(proxyInstance));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(GreetingService.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, callArgs, proxy) -> proxy.invokeSuper(obj, callArgs));
        GreetingService proxy = (GreetingService) enhancer.create();

        try {
            Method m = GreetingServiceFace.class.getMethod("sayHello");
            System.out.println(m.invoke(proxy));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    interface GreetingServiceFace {
        String sayHello();
    }

    public static class DynamicInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            throw new IllegalStateException("Bad state");
            //return "ok";
        }
    }

    static class GreetingService implements GreetingServiceFace {
        @Override
        public String sayHello() {
            throw new IllegalStateException("Bad state");
        }
    }
}


