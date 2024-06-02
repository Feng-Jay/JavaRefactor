public class FAKECLASS{
    public boolean hasSameMethod(Invocation candidate) {        
        //not using method.equals() for 1 good reason:
        //sometimes java generates forwarding methods when generics are in play see JavaGenericsForwardingMethodsTest
        Method m1 = invocation.getMethod();
        Method m2 = candidate.getMethod();
        
        if (m1.getName() != null && m1.getName().equals(m2.getName())) {
        	/* Avoid unnecessary cloning */
        	Class[] params1 = m1.getParameterTypes();
        	Class[] params2 = m2.getParameterTypes();
        	if (params1.length == params2.length) {
        	    for (int i = 0; i < params1.length; i++) {
        		if (params1[i] != params2[i])
        		    return false;
        	    }
        	    return true;
        	}
        }
        return false;
    }
}