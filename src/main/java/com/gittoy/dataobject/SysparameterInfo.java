package com.gittoy.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * SysparameterInfo
 * Create By lzhao 2018/04/09
 */
@Data
@Entity
public class SysparameterInfo {

	@Id
    private String parametername;

    private Integer status;

    private String describe;
    
}
