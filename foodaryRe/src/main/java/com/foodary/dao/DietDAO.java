package com.foodary.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.foodary.foodary.Param;
import com.foodary.vo.DietVO;
import com.foodary.vo.FoodVO;
import com.foodary.vo.UserFoodVO;

public interface DietDAO {

	int foodSelectCount();

	ArrayList<FoodVO> foodSelectList(HashMap<String, Integer> hmap);

	ArrayList<FoodVO> foodSearchList(Param param);

	void userFoodInsert(UserFoodVO userFoodVO);

	void updateUserFood(UserFoodVO userFoodVO);

	void deleteUserFood(int idx);

	void insertDiet(DietVO dietVO);

	DietVO getIdx(int i);

	void setDietGup(int idx);

	void setUserFoodGup(int idx);

	ArrayList<UserFoodVO> userFoodList(int i);

	ArrayList<DietVO> selectDietList(HashMap<String, String> hmap);

	ArrayList<UserFoodVO> userFoodListGup(int gup);

	DietVO selectDiet(HashMap<String, String> hmap);

	

}
