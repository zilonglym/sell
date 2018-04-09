package com.gittoy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gittoy.dataobject.ScoreFunction;

public interface ScoreFunctionRepository extends JpaRepository<ScoreFunction, String> {
	List<ScoreFunction> findBySellerNameIn(String sellerName);
}
