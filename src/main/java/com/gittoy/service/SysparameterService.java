package com.gittoy.service;

import com.gittoy.dataobject.SysparameterInfo;

public interface SysparameterService {
	SysparameterInfo findOne(String paramerername);

    SysparameterInfo save(SysparameterInfo sysparameterInfo);
}
