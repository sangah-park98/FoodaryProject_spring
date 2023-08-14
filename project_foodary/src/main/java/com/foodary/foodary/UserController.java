package com.foodary.foodary;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SqlSession sqlSession; 
	
    @RequestMapping("/register/register")
    public String register(HttpServletRequest request, Model model) {
    	logger.info("register() 실행");
    	return "register/register"; 
    }
    
    @RequestMapping("/register/registerOK")
    public String registerOK(HttpServletRequest request, Model model, UserRegisterVO userRegisterVO) {
    	logger.info("registerOK() 실행");
    	UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
    	mapper.insertregister(userRegisterVO);
    	model.addAttribute("msg", "회원가입에 성공했습니다.");
    	return "redirect:/main/foodaryMainPageBefore";
    }
    
    @RequestMapping("/UserRegisterCheck")
    public String userRegisterCheck(HttpServletRequest request, Model model) {
    	return "main/foodaryMainPageBefore"; 
    }
    
    @RequestMapping("/idCheck")
    @ResponseBody
    public String idCheck(HttpServletRequest request, Model model) {
       System.out.println("MemberController의 IdCheck()");
       UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
       String id = request.getParameter("id").trim();
       System.out.println(id);
       int result = 0;
       if(id == "") {
          result = 2;
       } else {
          result = mapper.idCheck(id);
       }
       System.out.println("result: " + result);
       return result + "";
     }

    @RequestMapping("/main/mainLoginForm")
    public String mainLoginForm(HttpServletRequest request, Model model) {
    	logger.info("mainLoginForm() 실행");
    	return "main/mainLoginForm";
    }
    
    @RequestMapping("/loginOK")
    public String loginOK(HttpServletRequest request, Model model, String id, String password) {
        logger.info("loginOK() 실행");
        UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);

        if (id == null || id.isEmpty()) {
            model.addAttribute("msg", "아이디를 입력해 주세요.");
            return "main/foodaryMainPageBefore";
        } else if (password == null || password.isEmpty()) {
            model.addAttribute("msg", "비밀번호를 입력해 주세요.");
            return "main/foodaryMainPageBefore";
        }
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("id", id);
        hmap.put("password", password);
        UserRegisterVO userRegisterVO = mapper.selectByInfo(hmap);

        if (userRegisterVO != null) {
            model.addAttribute("rvo", userRegisterVO);
            return "main/foodaryMainPageAfter";
        } else {
            model.addAttribute("msg", "아이디 또는 비밀번호를 잘못 입력했습니다.\\n입력하신 내용을 다시 확인해주세요.");
            return "main/foodaryMainPageBefore";
        }
    }
    
    @RequestMapping("/register/logoutOK")
    public String logoutOK(HttpServletRequest request, Model model, String username) {
    	logger.info("logoutOK() 실행");
    	model.addAttribute("msg", username + "님 안녕히 가세요.");
    	return "main/foodaryMainPageBefore";
    }
    
    @RequestMapping("/register/myPageInfoUpdate")
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
    	return "register/myPage";
    }

    @RequestMapping("/register/myPageUpdateEnd")
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
    	return "register/myPage";
    }

    @RequestMapping("/register/myPage")
    public String myPage(HttpServletRequest request, Model model) {
    	logger.info("myPage() 실행");
    	return "register/myPage";
    }
    
    @RequestMapping("/register/myPageOK")
    public String myPageOK(HttpServletRequest request, HttpSession session, Model model) {
        logger.info("myPageOK() 실행");
        UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
        int idx = Integer.parseInt(request.getParameter("idx"));
        UserRegisterVO userRegisterVO = mapper.selectByIdx(idx);
        logger.info("{}", userRegisterVO);
        session.setAttribute("rvo", userRegisterVO);
        return "register/myPage";
    }
    
    @RequestMapping("/register/passwordUpdate")
    public String passwordUpdate(HttpServletRequest request, Model model, String username, String id, String password) {
    	logger.info("passwordUpdate() 실행");
    	model.addAttribute("username", username);
    	model.addAttribute("id", id);
    	model.addAttribute("password", password);
    	return "register/passwordUpdate";
    }
    
    @RequestMapping("/register/newPassword")
    public String newPassword(HttpServletRequest request, Model model, HttpSession session) {
        logger.info("newPassword() 실행");
        UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
        UserRegisterVO userRegisterVO = (UserRegisterVO) session.getAttribute("rvo");
        
        String username = request.getParameter("username");
        HashMap<String, String> hmap = new HashMap<String, String>();
        String id = request.getParameter("id");
        String password = request.getParameter("password");
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
                return "main/foodaryMainPageBefore";
            } else {
                model.addAttribute("msg", "새 비밀번호가 일치하지 않습니다.");
                session.setAttribute("rvo", userRegisterVO);
                return "register/myPage";
            }
        } else {
            model.addAttribute("msg", "비밀번호가 올바르지 않습니다.");
            session.setAttribute("rvo", userRegisterVO);
            return "register/myPage"; 
        }
    }
    
    @RequestMapping("/register/unregister")
    public String unregister(HttpServletRequest request, Model model, String id, String password) {
    	logger.info("unregister() 실행");
    	model.addAttribute("id", id);
    	model.addAttribute("password", password);
    	return "register/unregister";
    }
    
    @RequestMapping("/register/dropInfo")
    public String dropInfo(HttpServletRequest request, Model model, String id) {
    	logger.info("dropInfo() 실행");
    	UserRegisterDAO mapper = sqlSession.getMapper(UserRegisterDAO.class);
    	model.addAttribute("id", id);
    	mapper.dropInfo(id);
    	return "main/foodaryMainPageBefore";
    }

}





