package com.foodary.foodary;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodary.dao.NaverLoginBO;
import com.foodary.dao.UserDAO;
import com.foodary.vo.UserRegisterVO;
import com.github.scribejava.core.model.OAuth2AccessToken;

@Controller
public class LoginController {
   
   private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
   private NaverLoginBO naverLoginBO;
   private String apiResult = null;
   @Autowired
	private SqlSession sqlSession; 
   
   // NaverLoginBO
   @Autowired
   public void setNaverLoginBO(NaverLoginBO naverLoginBO) {
      this.naverLoginBO = naverLoginBO;
   }
   
  // 로그인 성공시 콜백 
  @RequestMapping("/callback")
  public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session, UserRegisterVO rvo) throws Exception {
    logger.info("callback() 실행");
    UserDAO mapper = sqlSession.getMapper(UserDAO.class);
    // 로그인 사용자 정보를 얻어온다.
    // logger.info("apiResult: {}", apiResult);
    
    OAuth2AccessToken oauthToken;
    oauthToken = naverLoginBO.getAccessToken(session, code, state);
    // 로그인 사용자 정보를 읽어온다.
    apiResult = naverLoginBO.getUserProfile(oauthToken);
    
    // String 형식인 로그인 사용자 정보를 json 형태로 바꾼다.
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObj;
    jsonObj = (JSONObject) jsonParser.parse(apiResult);
     
     // 데이터 파싱
     JSONObject response_obj = (JSONObject) jsonObj.get("response");
     logger.info("response_obj: {}", response_obj);
     // top 레벨 단계 데이터 파싱 결과에서 회원가입 시 넣어줄 항목들을 넣어준다.
     String username = (String) response_obj.get("name");
     logger.info("username: {}", username);
     String id = (String) response_obj.get("id");
     logger.info("id: {}", id);
     String email = (String) response_obj.get("email");
     logger.info("email: {}", email);
     String gender = (String) response_obj.get("gender");
     if (gender.equals("F")) {
    	 gender = "여성";
     } else {
    	 gender = "남성";
     }
     logger.info("gender: {}", gender);
     String birthyear = (String) response_obj.get("birthyear");
     Date date = new Date();
     int year = date.getYear()+1900;
     logger.info("date: {}", year);
     String age = year - Integer.parseInt(birthyear) + "";
     logger.info("age: {}", age);
     
     // 네이버 로그인 API를 통해 얻어온 이름, 아이디, 이메일, 성별, 나이를 rvo 디비에 넣어준다.
     rvo.setUsername(username);
     rvo.setId(id);
     rvo.setEmail(email);
     rvo.setGender(gender);
     rvo.setAge(age);  
     // 디비에 save() 메소드를 실행해서 저장해준다.
     mapper.save(rvo);
     
     // 세션에 사용자 정보 등록
     session.setAttribute("islogin_r", "Y");
     //  session.setAttribute("signIn", apiResult);
     //  session.setAttribute("id", id); 
     //  session.setAttribute("email", email);
     session.setAttribute("username", username);
     return "main/foodaryMainPageAfter";
  }
  
  @RequestMapping("/logout")
  public String logout(HttpServletRequest request, Model model, HttpSession session) {
	  session.invalidate();
	  return "main/foodaryMainPageAfter";
  }
}