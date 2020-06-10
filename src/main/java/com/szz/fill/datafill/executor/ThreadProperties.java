package com.szz.fill.datafill.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author szz
 */
public final class ThreadProperties {

    public static ThreadPoolExecutor pool = new ThreadPoolExecutor(
            10,
            10,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

}
