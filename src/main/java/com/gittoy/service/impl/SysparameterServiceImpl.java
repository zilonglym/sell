package com.gittoy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gittoy.dataobject.SysparameterInfo;
import com.gittoy.repository.SysparameterRepository;
import com.gittoy.service.SysparameterService;

@Service
public class SysparameterServiceImpl implements SysparameterService {

	@Autowired
	SysparameterRepository sysparameterRepository;
	@Override
	public SysparameterInfo findOne(String paramerername) {
		return sysparameterRepository.findOne(paramerername);
	}

	@Override
	public SysparameterInfo save(SysparameterInfo sysparameterInfo) {
		return sysparameterRepository.save(sysparameterInfo);
	}

}
