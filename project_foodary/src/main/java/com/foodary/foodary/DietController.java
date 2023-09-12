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
	
	// dietInsertView.jsp에서 저장하기 눌렀을 때 userfood에 저장하는 메소드
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
	      
	      // 어떠한 음식도 입력하지 않았을 때 아무것도 띄워주지 않기 위해 빈 배열로 선언한다.
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
		logger.info("foodListView() 메소드 실행");
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
			// hashMap 대신 Param을 쓴 이유는 No와 Name의 데이터 타입이 달라서 한 곳에 저장하기 때문이다.
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
		logger.info("userFoodInsert() 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		logger.info("{}", userFoodVO);
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

	// 날짜, 시간, 메모를 넣어주는 메소드
	@RequestMapping("/diet/dietInsert")
	public String dietInsert(MultipartHttpServletRequest request, Model model, DietVO dietVO, UserFoodList userFoodList, UserRegisterVO userRegisterVO) {
		logger.info("dietInsert() 실행");
		HttpSession session = request.getSession();
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		
		String rootUploadDir = "C:\\upload\\diet"; // 업로드 될 파일 경로
		// 사진 파일명에 날짜를 붙여주기 위해 가져온 Date클래스 객체
	    Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
	      
	    MultipartFile multipartFile = request.getFile("fileName");
	    String originalFilename = multipartFile.getOriginalFilename().trim();
	    // 업로드 할 사진이 없을 때
	    if (originalFilename.isEmpty())  {
	    	String picture = "no picture";
	    	dietVO.setDietPicture(picture);
	    	dietVO.setId(userRegisterVO.getId());
	    	logger.info("{}", dietVO);
	    	mapper.insertDiet(dietVO);
	    }
	    // 업로드 할 사진이 있을 때
	    else {
	    	String picture = sdf.format(date) + "_" + originalFilename;
	    	File dir = new File(rootUploadDir, picture);
	    	try {
	    		multipartFile.transferTo(dir);  // 사진이 업로드되는 실질 코드
	    	} catch (Exception e) { }
	    	
	    	dietVO.setDietPicture(picture);
	    	dietVO.setId(userRegisterVO.getId());
	    	logger.info("{}", dietVO);
	    	mapper.insertDiet(dietVO);
	    }
	    
	    // 내가 방금 저장한 음식 목록의 idx를 날짜, 시간, 메모에도 동일하게 맞춰줘야 하기 때문에 
	    // DietVO에 넣어준다.
		DietVO getIdx = mapper.getIdx(0);
		int idx = getIdx.getIdx(); 
		// 같은 날짜와 시간, 메모에 입력한 내용과 그 날 적은 음식의 gup를 동일하게 맞춰준다.
		mapper.setDietGup(idx); 
		mapper.setUserFoodGup(idx); 
		// 처음 식단 쓰기로 들어오면 음식 이름이 아무것도 저장되어 있지 않기 때문에 null을 넣어줘서
		// foodNames = new String[]{}; 로 초기화한다. 
		session.setAttribute("foodNames", null);
		// 식단 보기에서 내가 먹은 음식 목록을 띄우기 위해 gup, 날짜, id를 보내준다.
		model.addAttribute("gup", idx);
		model.addAttribute("dietWriteDate", dietVO.getDietWriteDate());
		model.addAttribute("id", userRegisterVO.getId());
		return "redirect:dietListView";
	}

	// 내가 입력한 음식을 <2023-09-11 식단 목록> '16:52 잔치국수'의 형태로 띄우기 위한 메소드
	@RequestMapping("/diet/dietListView")
	public String dietListView(HttpServletRequest request, Model model) {
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		String dietWriteDate = request.getParameter("dietWriteDate");
		// 디비에서 dietWriteDate가 같은 것만 넘기면 회원가입 때 저장된 user들 중 같은 날짜에 입력한 음식 목록이
		// 전부 불러오게 되므로 id도 같이 넘겨줘야 한다.
		String id = request.getParameter("id");
		logger.info("{}" + id);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		DietList dietList = ctx.getBean("dietList", DietList.class);
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("dietWriteDate", dietWriteDate);
		hmap.put("id", id);
		dietList.setList(mapper.selectDietList(hmap));
		UserFoodList userFoodList = ctx.getBean("userFoodList", UserFoodList.class);
		
		// dietList(한 날짜에 해당하는 시간과 음식 목록)에 들어간 내용 전체를 불러오기 위해 반복문을 돌린다.
		logger.info("{}", dietList.getList());
		for (int i=0; i<dietList.getList().size(); i++) {
			DietVO dietVO = dietList.getList().get(i);
			// 내가 먹은 음식의 전체 목록 중에서 시간에 해당하는(gup가 같은) 음식 목록만 띄운다. 예> '16:52 잔치국수'
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