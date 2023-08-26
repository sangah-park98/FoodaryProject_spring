package com.foodary.vo;

import java.util.ArrayList;

public class UserFoodList {
	
	private ArrayList<UserFoodVO> list = new ArrayList<UserFoodVO>();
	
	public UserFoodList() { }

	public UserFoodList(ArrayList<UserFoodVO> list) {
		this.list = list;
	}

	public ArrayList<UserFoodVO> getList() {
		return list;
	}

	public void setList(ArrayList<UserFoodVO> list) {
		this.list = list;
	}
	
	

}
