package com.gittoy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gittoy.dataobject.SysparameterInfo;

public interface SysparameterRepository extends JpaRepository<SysparameterInfo, String>{
	List<SysparameterInfo> findByParametername(String parametername);
}
