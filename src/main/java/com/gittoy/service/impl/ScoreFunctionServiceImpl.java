package com.gittoy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gittoy.dataobject.ScoreFunction;
import com.gittoy.repository.ScoreFunctionRepository;
import com.gittoy.service.ScoreFunctionService;

@Service
public class ScoreFunctionServiceImpl implements ScoreFunctionService{

	@Autowired
	ScoreFunctionRepository scoreFunctionRepository;
	@Override
	public ScoreFunction save(ScoreFunction scoreFunctionInfo) {
		return scoreFunctionRepository.save(scoreFunctionInfo);
	}
	
	/**
     * 查询积分功能开关
     *
     * @param pageable
     */
	@Override
	public Page<ScoreFunction> findList(Pageable pageable) {
		Page<ScoreFunction> scoreFunctionInfo = scoreFunctionRepository.findAll(pageable);
		return new PageImpl<>(scoreFunctionInfo.getContent(), pageable, scoreFunctionInfo.getTotalElements());
	}

	@Override
	public ScoreFunction findOne(String sellerName) {
		return scoreFunctionRepository.findOne(sellerName);
	}

	@Override
	public List<ScoreFunction> findAll() {
		return scoreFunctionRepository.findAll();
	}


}
