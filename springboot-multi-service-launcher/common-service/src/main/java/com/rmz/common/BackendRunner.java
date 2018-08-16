package com.rmz.common;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class BackendRunner {
    private ConfigurableApplicationContext appContext;
    private final Class<?>[] backendClasses;

    private Object monitor = new Object();
    private boolean shouldWait;

    protected BackendRunner(final Class<?>... backendClasses) {
        this.backendClasses = backendClasses;
    }

    public void run() {
        if(appContext != null) {
            throw new IllegalStateException("AppContext must be null to run this backend");
        }
        runBackendInThread();
        waitUntilBackendIsStarted();
    }

    private void waitUntilBackendIsStarted() {
        try {
            synchronized (monitor) {
                if(shouldWait) {
                    monitor.wait();
                }
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    private void runBackendInThread() {
        final Thread runnerThread = new BackendRunnerThread();
        shouldWait = true;
        runnerThread.setContextClassLoader(backendClasses[0].getClassLoader());
        runnerThread.start();
    }

    public void stop() {
        SpringApplication.exit(appContext);
        appContext = null;
    }

    private class BackendRunnerThread extends Thread {
        @Override
        public void run() {
            appContext = SpringApplication.run(backendClasses, new String[]{});
            synchronized (monitor) {
                shouldWait = false;
                monitor.notify();
            }
        }
    }
}

