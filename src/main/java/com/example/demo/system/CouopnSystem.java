package com.example.demo.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.Facade.AdminFacade;
import com.example.demo.Facade.ClientType;
import com.example.demo.Facade.CompanyFacade;
import com.example.demo.Facade.CouponClientFacade;
import com.example.demo.Facade.CustomerFacade;

public class CouopnSystem {
	@Component
	@Scope("singleton")
	public class CouponSystem {

		@Autowired
		AdminFacade adminFacade;
		@Autowired
		CompanyFacade companyFacade;
		@Autowired
		CustomerFacade customerFacade;


		public CouponClientFacade login(String name ,String password, ClientType clientType){
			switch (clientType) {
			case Admin:
				return adminFacade.login(name, password, clientType);
			case Company:
				return companyFacade.login(name, password, clientType);
			case Customer:
				return customerFacade.login(name, password, clientType);
			}
			return null;
		}
	}

}
