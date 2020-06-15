package com.netty.server.invokedata;

import lombok.Data;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/31
 */
@Data
public class MethodInvokeMeta {

    private Class<?> interfaceClass;
    private Class<?> returnType;
    private String methodName;
    private Object[] args;
    private Class<?>[] parameterTypes;
}
