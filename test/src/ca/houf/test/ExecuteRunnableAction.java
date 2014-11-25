package ca.houf.test;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

public class ExecuteRunnableAction implements Action
{
    public static Action executeRunnable()
    {
        return new ExecuteRunnableAction();
    }

    @Override
    public void describeTo(final Description description)
    {
        description.appendText("invokes passed Runnable");
    }

    @Override
    public Object invoke(final Invocation invocation) throws Throwable
    {
        if(invocation.getParameterCount() < 1 || !(invocation.getParameter(0) instanceof Runnable))
            throw new IllegalArgumentException("Expected first argument of type Runnable");

        ((Runnable) invocation.getParameter(0)).run();

        return null;
    }
}
