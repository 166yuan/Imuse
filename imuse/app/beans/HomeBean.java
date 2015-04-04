package beans;

import models.HomePage;



public class HomeBean {

	private String backgroundImage;
	
	public static HomeBean build(HomePage home){
		HomeBean hBean=new HomeBean();
		hBean.backgroundImage=home.backgroundImage;
		return hBean;
	}
}
