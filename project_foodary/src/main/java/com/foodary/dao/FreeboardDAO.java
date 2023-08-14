package com.foodary.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

import com.foodary.vo.FreeboardCommentVO;
import com.foodary.vo.FreeboardVO;

public interface FreeboardDAO {

	int freeboardSelectCount();

	ArrayList<FreeboardVO> freeboardSelectList(HashMap<String, Integer> hmap);

	ArrayList<FreeboardVO> freeboardSelectNotice();

	int commentCount(int idx);

	void freeboardInsert(FreeboardVO freeboardVO);

	void freeboardIncrement(int idx);

	FreeboardVO freeboardSelectByIdx(int idx);

	ArrayList<FreeboardCommentVO> selectCommentList(int idx);

	void freeboardDelete(int idx);

	void freeboardUpdate(FreeboardVO freeboardVO);

	void insertComment(FreeboardCommentVO freeboardCommentVO);

	void deleteComment(FreeboardCommentVO freeboardCommentVO);

	void updateComment(FreeboardCommentVO freeboardCommentVO);

	

	
		
	
	
}
