package com.jhonatapers.aop.executor.chain;

import com.jhonatapers.aop.context.PostInvocationContext;
import com.jhonatapers.aop.context.PreInvocationContext;
import com.jhonatapers.aop.executor.PreExecutor;

public final class PreInvocationExecutorChain
        extends ExecutorChain<PostInvocationContext, PreInvocationContext, PreExecutor> {

    public PreInvocationExecutorChain(final PreExecutor executor) {
        super(executor);
    }

    @Override
    protected PostInvocationContext resolve(final PreInvocationContext joinPoint) throws Throwable {
        return joinPoint.proceedWithContext();
    }

}
