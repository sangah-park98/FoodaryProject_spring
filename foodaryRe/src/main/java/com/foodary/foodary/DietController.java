package com.foodary.foodary;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import com.foodary.vo.DietList;
import com.foodary.vo.DietVO;
import com.foodary.vo.FoodList;
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

	@RequestMapping("/diet/userFoodInsert")
	public String userFoodInsert(HttpServletRequest request, Model model, UserFoodVO userFoodVO) {
		logger.info("userFoodInsert 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		logger.info("{}", userFoodVO);
		HttpSession session = request.getSession();
		
		String id = (String) session.getAttribute("id");
		logger.info("{}", id);
		
		mapper.userFoodInsert(userFoodVO);
	
		return "redirect:dietInsertView";
	}

	@RequestMapping("/diet/updateUserFood")
	public String updateUserFood(HttpServletRequest request, Model model, UserFoodVO userFoodVO) {
		logger.info("updateUserFood 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		logger.info("{}", userFoodVO);
		
		mapper.updateUserFood(userFoodVO);
		
		return "redirect:dietInsertView";
	}

	@RequestMapping("/diet/deleteUserFood")
	public String deleteUserFood(HttpServletRequest request, Model model, UserFoodVO userFoodVO) {
		logger.info("updateUserFood 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		logger.info("{}", userFoodVO);
		
		mapper.deleteUserFood(userFoodVO.getIdx());
	
		return "redirect:dietInsertView";
	}

	@RequestMapping("/diet/dietInsert")
	public String dietInsert(MultipartHttpServletRequest request, Model model, DietVO dietVO, UserFoodList userFoodList, UserRegisterVO userRegisterVO) {
		logger.info("========== dietInsert 메소드 실행 ===========");
		HttpSession session = request.getSession();
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		
		String rootUploadDir = "C:\\upload\\diet"; // 업로드 될 파일 경로
		// 사진 파일명에 날짜를 붙여주기 위해 가져온 Date클래스 객체
	    Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
	      
	    MultipartFile multipartFile = request.getFile("fileName");
	    String originalFilename = multipartFile.getOriginalFilename().trim();
	    if (originalFilename.isEmpty())  {
	    	String picture = "no picture";
	    	dietVO.setDietPicture(picture);
	    	dietVO.setId(userRegisterVO.getId());
	    	logger.info("{}", dietVO);
	    	mapper.insertDiet(dietVO);
	    }
	    else {
	    	String picture = sdf.format(date) + "_" + originalFilename;
	    	File dir = new File(rootUploadDir, picture);
	    	try {
	    		multipartFile.transferTo(dir);  // 업로드해주는 코드
	    	} catch (Exception e) { }
	    	dietVO.setDietPicture(picture);
	    	dietVO.setId(userRegisterVO.getId());
	    	logger.info("{}", dietVO);
	    	mapper.insertDiet(dietVO);
	    }
	    
		DietVO getIdx = mapper.getIdx(0);
		int idx = getIdx.getIdx();
		mapper.setDietGup(idx);
		mapper.setUserFoodGup(idx);
		session.setAttribute("foodNames", null);
		model.addAttribute("gup", idx);
		model.addAttribute("dietWriteDate", dietVO.getDietWriteDate());
		model.addAttribute("id", userRegisterVO.getId());
		return "redirect:dietListView";
	}

	
	@RequestMapping("/diet/dietListView")
	public String dietListView(HttpServletRequest request, Model model) {
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		String dietWriteDate = request.getParameter("dietWriteDate");
		String id = request.getParameter("id");
		logger.info("#####################" + id + "########################");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		DietList dietList = ctx.getBean("dietList", DietList.class);
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("dietWriteDate", dietWriteDate);
		hmap.put("id", id);
		dietList.setList(mapper.selectDietList(hmap));
		UserFoodList userFoodList = ctx.getBean("userFoodList", UserFoodList.class);
		
		logger.info("{}", dietList.getList());
		for (int i=0; i<dietList.getList().size(); i++) {
			DietVO dietVO = dietList.getList().get(i);
			ArrayList<UserFoodVO> userFoodGup = mapper.userFoodListGup(dietVO.getGup());
		    
		    userFoodList.getList().addAll(userFoodGup);
		}
		
		logger.info("{}", userFoodList);
		model.addAttribute("dietList", dietList);
		model.addAttribute("userFoodList", userFoodList);
		model.addAttribute("dietWriteDate", dietWriteDate);
		
		return "diet/dietListView";
	}

	@RequestMapping("/diet/dietView")
	public String dietView(HttpServletRequest request, Model model) {
		logger.info("fdietListView 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		String dietWriteDate = request.getParameter("dietWriteDate");
		String dietWriteTime = request.getParameter("dietWriteTime");
		String id = request.getParameter("id");
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("dietWriteDate", dietWriteDate);
		hmap.put("dietWriteTime", dietWriteTime);
		hmap.put("id", id);
		DietVO dvo = mapper.selectDiet(hmap);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserFoodList userFoodList = ctx.getBean("userFoodList", UserFoodList.class);
		
		userFoodList.setList(mapper.userFoodListGup(dvo.getGup()));
		
		model.addAttribute("dvo", dvo);
		model.addAttribute("userFoodList", userFoodList);
		
		return "diet/dietView";
	}
	
}