package com.gittoy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gittoy.dataobject.ScoreFunction;

public interface ScoreFunctionService {

	ScoreFunction save(ScoreFunction scoreFunctionInfo);
	
	Page<ScoreFunction> findList(Pageable pageable);
	
	ScoreFunction findOne(String sellerName);

	List<ScoreFunction> findAll();

}
