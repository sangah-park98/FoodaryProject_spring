package com.foodary.project_foodary;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foodary.dao.UserRegisterDAO;
import com.foodary.vo.UserRegisterVO;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SqlSession sqlSession; 
	
    @RequestMapping("/")
    public String home(HttpServletRequest request, Model model) {
       logger.info("첫 실행 시 푸드어리 메인 페이지 실행");
       return "redirect:foodaryMainPageBefore";
    }
    
    @RequestMapping("/foodaryMainPageBefore")
    public String foodaryMainPageBefore(HttpServletRequest request, Model model) {
    	logger.info("foodaryMainPageBefore() 실행");
    	return "foodaryMainPageBefore";
    }
    
    @RequestMapping("/foodaryMainPageAfter")
    public String foodaryMainPageAfter(HttpServletRequest request, Model model) {
    	logger.info("foodaryMainPageAfter() 실행");
    	return "mainLoginFormAfter";
    }
    
    @RequestMapping("/register")
    public String register(HttpServletRequest request, Model model) {
    	logger.info("register() 실행");
    	return "register"; 
    }
    
    @RequestMapping("/registerOK")
    public String registerOK(HttpServletRequest request, Model model, UserRegisterVO userRegisterVO) {
    	logger.info("registerOK() 실행");
    	UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
    	mapper.insertregister(userRegisterVO);
    	return "redirect:foodaryMainPageBefore";
    }
    
    @RequestMapping("/UserRegisterCheck")
    public String userRegisterCheck(HttpServletRequest request, Model model) {
    	return "foodaryMainPageBefore"; 
    }
    
    @RequestMapping("/idCheck")
    @ResponseBody
    public int idCheck(HttpServletRequest request, Model model) {
    	logger.info("idCheck() 실행");
    	UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
    	String id = request.getParameter("id").trim();
        int result = mapper.idCheck(id);
        if (result == 1) {
            model.addAttribute("msg", "아이디를 입력하고 중복 체크 버튼을 누르세요.");
        } else if (result == 2) {
            model.addAttribute("msg", "사용중인 아이디입니다.");
        } else if (result == 3) {
            model.addAttribute("msg", "사용 가능한 아이디입니다.");
	    }
		return result;
	}

    
    @RequestMapping("/mainLoginForm")
    public String mainLoginForm(HttpServletRequest request, Model model) {
    	logger.info("mainLoginForm() 실행");
    	return "mainLoginForm";
    }
    
    @RequestMapping("/loginOK")
    public String loginOK(HttpServletRequest request, Model model, String id, String password) {
    	logger.info("loginOK() 실행");
    	UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
    	HashMap<String, String> hmap = new HashMap<String, String>();
    	hmap.put("id", id);
    	hmap.put("password", password);
    	UserRegisterVO userRegisterVO = mapper.selectByInfo(hmap);
    	model.addAttribute("rvo", userRegisterVO);
    	return "foodaryMainPageAfter";
    }
    
    @RequestMapping("/logoutOK")
    public String logoutOK(HttpServletRequest request, Model model, String username) {
    	logger.info("logoutOK() 실행");
    	model.addAttribute("msg", username + "님 안녕히 가세요.");
    	return "foodaryMainPageBefore";
    }
    
    @RequestMapping("/myPageInfoUpdate")
    public String myPageInfoUpdate(HttpServletRequest request, Model model, String username, String id, String email, int idx) {
    	logger.info("myPageInfoUpdate() 실행");
        UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("username", username);
        hmap.put("id", id);
        hmap.put("email", email);
        mapper.infoUpdate(hmap);
        UserRegisterVO userRegisterVO = mapper.selectByIdx(idx);
        model.addAttribute("rvo", userRegisterVO);
        model.addAttribute("msg", userRegisterVO.getUsername() + "님 회원 정보 수정 완료!");
    	return "myPage";
    }

    @RequestMapping("/myPageUpdateEnd")
    public String myPageUpdateEnd(HttpServletRequest request, Model model,
    		String height, String currentWeight, String goalWeight,
    		String age, String state, String active, String id, int idx) {
    	logger.info("myPageUpdateEnd() 실행");
    	UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
    	HashMap<String, String> hmap = new HashMap<String, String>();
    	hmap.put("height", height);
    	hmap.put("currentWeight", currentWeight);
    	hmap.put("goalWeight", goalWeight);
    	hmap.put("age", age);
    	hmap.put("state", state);
    	hmap.put("active", active);
    	hmap.put("id", id);
    	mapper.userUpdate(hmap);
    	UserRegisterVO userRegisterVO = mapper.selectByIdx(idx);
        model.addAttribute("rvo", userRegisterVO);
        model.addAttribute("msg", userRegisterVO.getUsername() + "님 회원 정보 수정 완료!");
    	return "myPage";
    }

    @RequestMapping("/myPage")
    public String myPage(HttpServletRequest request, Model model) {
    	logger.info("myPage() 실행");
    	return "myPage";
    }
    
    @RequestMapping("/myPageOK")
    public String myPageOK(HttpServletRequest request, Model model) {
    	logger.info("myPageOK() 실행");
    	UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
    	int idx = Integer.parseInt(request.getParameter("idx"));
    	UserRegisterVO userRegisterVO = mapper.selectByIdx(idx);
    	model.addAttribute("rvo", userRegisterVO);
    	return "myPage";
    }

    @RequestMapping("/passwordUpdate")
    public String passwordUpdate(HttpServletRequest request, Model model, String username, String id, String password) {
    	logger.info("passwordUpdate() 실행");
    	model.addAttribute("username", username);
    	model.addAttribute("id", id);
    	model.addAttribute("password", password);
    	return "passwordUpdate";
    }
    
    @RequestMapping("/newPassword")
    public String newPassword(HttpServletRequest request, Model model) {
    	logger.info("newPassword() 실행");
        UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
        HashMap<String, String> hmap = new HashMap<String, String>();
        String id = request.getParameter("id");
    	String password = request.getParameter("password");
    	String username = request.getParameter("username");
    	String currentpassword = request.getParameter("currentpassword");
    	String newpassword = request.getParameter("newpassword");
    	String newpasswordcheck = request.getParameter("newpasswordcheck");
        
        hmap.put("username", username);
        hmap.put("id", id);
        hmap.put("currentpassword", currentpassword);
        hmap.put("newpassword", newpassword);
        
        if (currentpassword.trim().equals(password.trim())) {
            if (newpassword != null && newpasswordcheck != null && newpassword.equals(newpasswordcheck)) {
            	mapper.newpasswordUpdate(hmap);
                model.addAttribute("msg", username + "님 비밀번호 변경 완료!");
                return "foodaryMainPageBefore";
            } else {
                model.addAttribute("msg", "새 비밀번호가 일치하지 않습니다.");
                return "foodaryMainPageAfter";
            }
        } else {
            model.addAttribute("msg", "비밀번호가 올바르지 않습니다.");
            return "foodaryMainPageAfter";
        }
    }
    
    @RequestMapping("/unregister")
    public String unregister(HttpServletRequest request, Model model, String id, String password) {
    	logger.info("unregister() 실행");
    	model.addAttribute("id", id);
    	model.addAttribute("password", password);
    	return "unregister";
    }
    
    @RequestMapping("/dropInfo")
    public String dropInfo(HttpServletRequest request, Model model, String id) {
    	logger.info("dropInfo() 실행");
    	UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
    	model.addAttribute("id", id);
    	mapper.dropInfo(id);
    	return "foodaryMainPageBefore";
    }
    
    
    
    
}





