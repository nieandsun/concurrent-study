package com.nrsc.ch1.base.safeend;

/**
 *类说明：阻塞方法中抛出InterruptedException异常后，如果需要继续中断，需要手动再中断一次
 */
public class HasInterrputException {
	
	private static class UseThread extends Thread{
		
		public UseThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			while(!isInterrupted()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName()
							+" in InterruptedException interrupt flag is "
							+isInterrupted());
					//感知到线程中断后，可以先进行资源释放，然后再手动调用一下interrupt()方法进行真正的线程中断
					interrupt();
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()
						+ " I am extends Thread.");
			}
			System.out.println(Thread.currentThread().getName()
					+" interrupt flag is "+isInterrupted());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread endThread = new UseThread("HasInterrputEx");
		endThread.start();
		Thread.sleep(500);
		endThread.interrupt();
	}

}
