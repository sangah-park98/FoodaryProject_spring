package com.foodary.dao;

import java.util.HashMap;

import com.foodary.vo.UserRegisterVO;

public interface UserRegisterDAO {

//	FreeboardService 클래스에서 호출되는 mapper와 메인글이 저장된 객체를 넘겨받고 freeboard.xml 파일의
//	insert sql 명령을 실행하는 메소드
	void insertregister(UserRegisterVO rvo);

//	UserRegisterService에서 넘어온 idx를 처리하는 dao 메소드	
	UserRegisterVO selectByIdx(int idx);
	
//	회원 정보 한 건을 저장하는 메소드
	UserRegisterVO selectByInfo(HashMap<String, String> hmap);
	
//	키, 현재 체중, 목표 체중, 나이를 수정하는 메소드
	void userUpdate(HashMap<String, String> hmap);
	
//	회원 탈퇴하는 메소드
	void dropInfo(String id);
	
//	이름, 이메일을 수정하는 메소드
	void infoUpdate(HashMap<String, String> hmap);
	
//	비밀번호 변경하는 메소드
	void newpasswordUpdate(HashMap<String, String> hmap);

	int idCheck(String id);



}




