package com.jhonatapers.aop.context;

public interface PreInvocationContext extends InvocationContext {

    PostInvocationContext proceedWithContext();

}
