package com.example.demo.system;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.example.demo.DBDAO.CouponDBDAO;



public class ExpiredCouponThread implements Runnable {
	
	private CouponDBDAO couponDBDAO;
	
	public ExpiredCouponThread(ApplicationContext ctx) {
		super ();
	this.couponDBDAO = ctx.getBean(CouponDBDAO.class);
	}
	
	public void run()
	{
		while(true)
		{
			this.couponDBDAO.deleteExpiredCoupons(new Date());
		try {
			Thread.sleep(1000*60*60*24);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}

}

