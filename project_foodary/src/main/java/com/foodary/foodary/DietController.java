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
import com.foodary.vo.FreeboardVO;
import com.foodary.vo.UserFoodList;
import com.foodary.vo.UserFoodVO;
import com.foodary.vo.UserRegisterVO;

@Controller
public class DietController {
	
	private static final Logger logger = LoggerFactory.getLogger(DietController.class);
	@Autowired
	private SqlSession sqlSession;
	
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
		model.addAttribute("msg", "성공적으로 기록되었습니다.");
		return "redirect:dietListView";
	}
	
	// dietInsertView.jsp에서 저장하기 눌렀을 때 userfood에 저장하는 메소드
	@RequestMapping("/diet/dietInsertView")
	public String dietInsertView(HttpServletRequest request, Model model, UserFoodVO userFoodVO) {
		logger.info("dietInsertView() 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		HttpSession session = request.getSession();
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserFoodList userFoodList = ctx.getBean("userFoodList", UserFoodList.class);
		userFoodList.setList(mapper.userFoodList(0));
		session.setAttribute("userFoodList", userFoodList);
		return "diet/dietInsertView";
	}

	@RequestMapping(value = {"/diet/foodList", "/diet/updateFoodList"})
	public String foodListView(HttpServletRequest request, Model model , DietVO dvo) {
		logger.info("foodListView 메소드 실행");
		logger.info("{}============" ,  dvo);
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		int currentPage = 1;
		int pageSize = 10;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {
		}

		String foodName = request.getParameter("foodName");

		int totalCount = mapper.foodSelectCount();

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		FoodList foodList = ctx.getBean("foodList", FoodList.class);
		foodList.initFoodList(pageSize, totalCount, currentPage);

		if (foodName == null || foodName.trim().length() == 0) {
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
			System.out.println(foodList);
		}

		model.addAttribute("foodList", foodList);
		model.addAttribute("currentPage", currentPage);
	    if(request.getServletPath().equals("/diet/foodList")) {
	    	return "diet/foodListView";
	    }else {
	    	model.addAttribute("gup" , dvo.getGup());
	    	return "diet/updateFoodListView";
	    }
	}

	@RequestMapping(value = {"/diet/userFoodInsert", "/diet/updateUserFoodInsert"})
	public String userFoodInsert(HttpServletRequest request, Model model, UserFoodVO userFoodVO , DietVO dvo) {
		logger.info("===========userFoodInsert 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		logger.info("{}", userFoodVO);
		logger.info("gup를 찾아라!! : ========= {}", dvo);
		HttpSession session = request.getSession();

		String id = (String) session.getAttribute("id");
		logger.info("{}", id);
		
		 if(request.getServletPath().equals("/diet/userFoodInsert")) {
			 	mapper.userFoodInsert(userFoodVO);
		    	return "redirect:dietInsertView";
	    }else if(request.getServletPath().equals("/diet/updateUserFoodInsert")){
	    	System.out.println("=======updateUserFoodInsert");
	    	int gup = dvo.getGup();
	    	System.out.println(gup);
	    	userFoodVO.setGup(gup);
	    	mapper.userFoodInsert(userFoodVO);
	    	return null;
	    } else {
	    	return null;
	    }
	}

	@RequestMapping("/diet/updateUserFood")
	public String updateUserFood(HttpServletRequest request, Model model, UserFoodVO userFoodVO) {
		logger.info("updateUserFood() 실행");
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

	

	// 내가 입력한 음식을 <2023-09-11 식단 목록> '16:52 잔치국수'의 형태로 띄우기 위한 메소드
	@RequestMapping("/diet/dietListView")
	public String dietListView(HttpServletRequest request, Model model) {
		logger.info("dietListView() 실행");
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
			model.addAttribute("msg", "성공적으로 저장되었습니다.");
			return "diet/dietListView";
	}

	@RequestMapping("/diet/dietView")
	public String dietView(HttpServletRequest request, Model model) {
		logger.info("dietView() 실행");
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
	
	@RequestMapping("/diet/dietViewAll")
	   public String dietViewAll(HttpServletRequest request, Model model) {
	      logger.info("dietView 메소드 실행");
	      DietDAO mapper = sqlSession.getMapper(DietDAO.class);
	      String dietWriteDate = request.getParameter("dietWriteDate");
	      String dietWriteTime = request.getParameter("dietWriteTime");      
	      String id = request.getParameter("id");
	      System.out.printf("id: %s, dietWriteDate: %s, dietWriteTime: %s", String.format("%s", id),
	            String.format("%s", dietWriteDate), String.format("%s", dietWriteTime));
//	      logger.info("id: {}, dietWriteDate: {}", id, dietWriteDate);
	      
	      AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	      DietList dietList = ctx.getBean("dietList", DietList.class);
	      UserFoodList userFoodList = ctx.getBean("userFoodList", UserFoodList.class);
	      if(dietWriteTime != null) {
	         HashMap<String, String> hmap = new HashMap<String, String>();
	         hmap.put("dietWriteDate", dietWriteDate);
	         hmap.put("dietWriteTime", dietWriteTime);
	         hmap.put("id", id);
	         DietVO dvo = mapper.selectDiet(hmap);
	         
	         userFoodList.setList(mapper.userFoodListGup(dvo.getGup()));
	         logger.info("userFoodList: {}", userFoodList);
	         model.addAttribute("dvo", dvo);
	         model.addAttribute("userFoodList", userFoodList);
	      }else {
	         HashMap<String, String> hmap = new HashMap<String, String>();
	         hmap.put("dietWriteDate", dietWriteDate);
	         hmap.put("id", id);
	         dietList.setList(mapper.selectGup(hmap));
	         logger.info("selectGup: {}", dietList.getList());
	         
	         ArrayList<DietVO> selectDietList = new ArrayList<DietVO>();
	         ArrayList<UserFoodVO> selectfoodList = new ArrayList<UserFoodVO>();
	         DietVO dvo = new DietVO();
	         
	         for (int i=0; i<dietList.getList().size(); i++) {
	            dvo = dietList.getList().get(i);
	         //   logger.info("dietList.getList().get(" + i + ").getGup(): {}", dietList.getList().get(i).getGup());
	            
	            ArrayList<DietVO> dietGup = mapper.selectDietGup(dvo.getGup());
	            ArrayList<UserFoodVO> userfoodGup = mapper.userFoodListGup(dvo.getGup());
	            logger.info("dietGup: {}", dietGup);
	            logger.info("userfoodGup: {}", userfoodGup);
	            selectDietList.addAll(dietGup);
	            selectfoodList.addAll(userfoodGup);
	         }
	         userFoodList.setList(selectfoodList);
	         logger.info("selectDietList: {}", selectDietList);
	         logger.info("selectfoodList: {}", selectfoodList);
	         logger.info("userFoodList 전체: {}", userFoodList);
	         model.addAttribute("selectDietList", selectDietList);
	         model.addAttribute("userFoodList", userFoodList);
	         model.addAttribute("dietWriteDate", dietWriteDate);
	      }
	      return "diet/dietViewAll";
	   }
	
	// 실제로 내가 적은 음식 목록 수정하는 메소드
	@RequestMapping("/diet/dietUpdateView")
	public String dietUpdate(HttpServletRequest request, Model model, HttpSession session, DietVO dvo) {
		logger.info("============dietUpdateView 이동하는 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		String id = (String) session.getAttribute("id");
		logger.info("id ==== {}", id);
		int gup = Integer.parseInt(request.getParameter("gup"));
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserFoodList userFoodList = ctx.getBean("userFoodList", UserFoodList.class);
		dvo = mapper.updateDeleteFoodDiet(dvo.getGup());
		System.out.println("1. ============" + dvo);
		System.out.println("2 :  " + dvo.getGup());
		userFoodList.setList(mapper.userFoodListGup(dvo.getGup()));
		System.out.println("3. ============" + userFoodList);
		model.addAttribute("userFoodList", userFoodList);
		model.addAttribute("dvo", dvo);
		return "diet/dietUpdateView";
	}
	
	@RequestMapping("/diet/dietUpdateUserFood")
	public String dietUpdateUserFood(HttpServletRequest request, Model model, UserFoodVO userFoodVO, DietVO dvo) {
		System.out.println("1. ======= dietUpdateUserFood");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		int gup = dvo.getGup();
		dvo = mapper.updateDeleteFoodDiet(dvo.getGup());
		System.out.println("######### " + dvo);
		System.out.println("2 " + userFoodVO);
		mapper.updateUserFood(userFoodVO);
		model.addAttribute("dvo", dvo);
		model.addAttribute("gup" , dvo.getGup());
		return "redirect:dietUpdateView";
	}
	
	@RequestMapping("/diet/diteDeleteUserFood")
	public String diteDeleteUserFood(HttpServletRequest request, Model model,  HttpSession session, UserFoodVO userFoodVO, DietVO dvo) {
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		System.out.println("이동. ======= diteDeleteUserFood");
		int idx = Integer.parseInt(request.getParameter("idx"));
		int gup = Integer.parseInt(request.getParameter("gup"));
		System.out.println("idx " + idx);
		System.out.println("gup " + gup);
		mapper.deleteUserFood(idx);
		dvo = mapper.updateDeleteFoodDiet(gup);
		System.out.println("######### " + dvo);
		model.addAttribute("dvo", dvo);
		model.addAttribute("gup" , dvo.getGup());
		return "redirect:dietUpdateView";
	}
	
	@RequestMapping("diet/dietUpdateOK")
	public String dietUpdateOK(HttpServletRequest request, Model model, DietVO dvo) {
		logger.info("============dietUpdateOK 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		logger.info("============" + dvo);
		mapper.updateDiet(dvo);
		int gup = dvo.getGup();
		model.addAttribute("dvo", dvo);
		model.addAttribute("gup", gup);
		return "redirect:dietViewUpdate";
	}
	
	// 내가 쓴 음식을 수정했을 때 수정하기 누르면 수정된 음식과 메모를 띄워주는 메소드
	@RequestMapping("/diet/dietViewUpdate")
	public String dietViewUpdate(HttpServletRequest request, Model model, DietVO dvo) {
		logger.info("dietViewUpdate 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		int gup = Integer.parseInt(request.getParameter("gup"));
		dvo = mapper.updateDeleteFoodDiet(gup);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserFoodList userFoodList = ctx.getBean("userFoodList", UserFoodList.class);
		userFoodList.setList(mapper.userFoodListGup(dvo.getGup()));
		model.addAttribute("dvo", dvo);
		model.addAttribute("userFoodList", userFoodList);
		return "diet/dietView";
	}
	
	@RequestMapping("diet/dietDelete")
	public String dietDelete(HttpServletRequest request, Model model, DietVO dvo) {
		logger.info("============dietDelete 메소드 실행");
		DietDAO mapper = sqlSession.getMapper(DietDAO.class);
		int idx = Integer.parseInt(request.getParameter("idx"));
		String dietWriteDate = request.getParameter("dietWriteDate");
		int gup = Integer.parseInt(request.getParameter("gup"));
		System.out.println("===================== : " + dietWriteDate);
		String id = request.getParameter("id");
		logger.info("#####################" + id + "########################");
		mapper.deleteDietList(idx);
		mapper.deleteDietUserFood(gup);
		model.addAttribute("id", id);
		model.addAttribute("dietWriteDate", dietWriteDate);
		return "redirect:dietListView";
	}
}