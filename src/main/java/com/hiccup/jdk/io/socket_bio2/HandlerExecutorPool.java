package com.hiccup.jdk.io.socket_bio2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerExecutorPool {

	private ExecutorService executor;

	public HandlerExecutorPool(int maxPoolSize, int queueSize){
		// 自定义线程池
		this.executor = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),	//可用处理器数
				maxPoolSize,
				120L,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	public void execute(Runnable task){
		this.executor.execute(task);
	}

}
