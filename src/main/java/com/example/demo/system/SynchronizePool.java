package com.example.demo.system;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
	public class SynchronizePool {
	private static final int NUMBER_OF_SYCHRONIZE = 5;
	private static SynchronizePool synchronizePool = null;
		ArrayList<Synchronize> synchronizesPool;
		private SynchronizePool ()
		{
			super ();
			synchronizesPool = new ArrayList<>();
			for (int i = 0 ; i < NUMBER_OF_SYCHRONIZE ; i++)
			{
				synchronizesPool.add(new Synchronize());			
			}
		}
		
		public static SynchronizePool getInstance()
		{
			if (synchronizePool == null)
			{
				synchronizePool = new SynchronizePool();
			}
			return synchronizePool;
		}
		
		public synchronized Synchronize getSynchronize () 
		{
			while (synchronizesPool.isEmpty())
			{
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Synchronize synchronize = synchronizesPool.get(0);
			synchronizesPool.remove(synchronize);
			System.out.println("got the sycronize!");
			return synchronize;
		}
		public synchronized void returnSynchronize(Synchronize synchronize)
		{
			notify();
			synchronizesPool.add(synchronize);
			System.out.println("i returned the syncronize");
		}
	}



