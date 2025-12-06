package com.jhonatapers.aop.executor.chain;

import com.jhonatapers.aop.context.PostInvocationContext;
import com.jhonatapers.aop.executor.PostExecutor;

public final class PostInvocationExecutorChain
        extends ExecutorChain<Object, PostInvocationContext, PostExecutor> {

    public PostInvocationExecutorChain(final PostExecutor executor) {
        super(executor);
    }

    @Override
    protected Object resolve(final PostInvocationContext joinPoint) throws Throwable {
        if (joinPoint.wasSuccessful())
            return joinPoint.getResult();
        else
            throw joinPoint.getThrowable();
    }

}
