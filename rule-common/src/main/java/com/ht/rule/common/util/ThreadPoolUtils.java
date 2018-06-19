package com.ht.rule.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


/**
 * @author zhangzhen
 * @date 2018年01月12日
 */
public class ThreadPoolUtils {

	private static ExecutorService executorService ;
	private static Integer cpuNum =0;
	
	private static ForkJoinPool forkJoinPool;
	static{
		cpuNum = Runtime.getRuntime().availableProcessors()*2;
		executorService = Executors.newFixedThreadPool(cpuNum);
		forkJoinPool = new ForkJoinPool();
	}
	
	public static ExecutorService getExecutorService(){
		return executorService;
	}
	
	public static Integer getCpuNum(){
		return cpuNum;
	}
	
	public static ForkJoinPool getForkJoinPool(){
		return forkJoinPool;
	}

	public static void displayAvailableProcessors() {

		Runtime runtime = Runtime.getRuntime();

		int nrOfProcessors = runtime.availableProcessors()*2;

		System.out.println("Number of processors available to the Java Virtual Machine: " + nrOfProcessors);

	}
	public static void main(String[] agrs){
		/**
		 * 问题在于Runtime.getRuntime().availableProcessors()也并非都能返回你所期望的数值。
		 * 比如说，在我的双核1-2-1机器上，它返回的是2，这是对的。不过在我的1-4-2机器 上，也就是一个CPU插槽，4核，每个核2个超线程，这样的话会返回8。
		 * 不过我其实只有4个核，如果代码的瓶颈是在CPU这块的话，我会有7个线程在同时 竞争CPU周期，而不是更合理的4个线程。
		 * 如果我的瓶颈是在内存这的话，那这个测试我可以获得7倍的性能提升。
		 * 不过这还没完！Java Champions上的一个哥们发现了一种情况，他有一台16-4-2的机器
		 * （也就是16个CPU插槽，每个CPU4个核，每核两个超线程，返回的值居然是16！从我的i7 Macbook pro上的结果来看，我觉得应该返回的是1642=128。
		 * 在这台机器上运行java 8的话，它只会将通用的FJ池的并发数设置成15。
		 * 正如 Brian Goetz所指出的，“虚拟机其实不清楚什么是处理器，它只是去请求操作系统返回一个值。
		 * 同样的，操作系统也不知道怎么回事，它是去问的硬件设备。硬件会告诉它一个值，通常来说是硬件线程数。操作系统相信硬件说的，而虚拟机又相信操作系统说的。
		 */
		new ThreadPoolUtils().displayAvailableProcessors();
	}
}
