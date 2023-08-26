package com.foodary.foodary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodary.dao.DietDAO;
import com.foodary.dao.FreeboardDAO;
import com.foodary.vo.FoodList;
import com.foodary.vo.FreeboardCommentList;
import com.foodary.vo.FreeboardCommentVO;
import com.foodary.vo.FreeboardList;
import com.foodary.vo.FreeboardVO;
import com.foodary.vo.UserFoodList;
import com.foodary.vo.UserFoodVO;
import com.foodary.vo.UserRegisterVO;

@Controller
public class DietController {

	private static final Logger logger = LoggerFactory.getLogger(DietController.class);
	@Autowired
	private SqlSession sqlSession; 
	
	@RequestMapping("/diet/dietInsertView")
	public String dietInsertView(HttpServletRequest request, Model model, UserFoodVO userFoodVO) {
		logger.info("dietInsertView() 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		HttpSession session = request.getSession();
	      String[] foodNames;
	      String[] kcals;
	      String[] carbss;
	      String[] proteins;
	      String[] fats;
	       foodNames = request.getParameterValues("foodName");
	       kcals = request.getParameterValues("kcal");
	       carbss = request.getParameterValues("carbs");
	       proteins = request.getParameterValues("protein");
	       fats = request.getParameterValues("fat");

	       if (foodNames == null) {
	           foodNames = new String[]{};
	           kcals = new String[]{};
	           carbss = new String[]{};
	           proteins = new String[]{};
	           fats = new String[]{};
	      } else {
	    	  session.setAttribute("foodNames", foodNames);
	    	  session.setAttribute("kcals", kcals);
	    	  session.setAttribute("carbss", carbss);
	    	  session.setAttribute("proteins", proteins);
	    	  session.setAttribute("fats", fats);
	      }
	      
	       AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	       UserFoodList userFoodList = ctx.getBean("userFoodList", UserFoodList.class);
	       userFoodList.setList(mapper.userFoodList(0));
	       
	       session.setAttribute("userFoodList", userFoodList);
		return "diet/dietInsertView";
	}

	@RequestMapping("/diet/foodList")
	public String foodListView(HttpServletRequest request, Model model) {
		logger.info("foodListView 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		int currentPage = 1;
		int pageSize = 10;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) { }
		
		String foodName = request.getParameter("foodName");
		
		int totalCount = mapper.foodSelectCount();
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		FoodList foodList = ctx.getBean("foodList", FoodList.class);
		foodList.initFoodList(pageSize, totalCount, currentPage);
		
		  if(foodName == null || foodName.trim().length() == 0) {
			HashMap<String, Integer> hmap = new HashMap<String, Integer>();
			hmap.put("startNo", foodList.getStartNo());
			hmap.put("endNo", foodList.getEndNo());
			foodList.setList(mapper.foodSelectList(hmap));
		  } else {
	        Param param = new Param();
	        param.setStartNo(foodList.getStartNo());
	        param.setEndNo(foodList.getEndNo());
	        param.setFoodName(foodName);
	        System.out.println(param.getFoodName());
	        foodList.setList(mapper.foodSearchList(param));
		  }	  
		  
		model.addAttribute("foodList", foodList);
		model.addAttribute("currentPage", currentPage);
		
		return "diet/foodListView";
	}
	
}





