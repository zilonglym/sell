package com.gittoy.service;


import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UploadService {
	
	 /* 上传文件 
     * @param file  文件 
     * @param folder    文件夹名称 
     * @param request 
     * @return 
     * @throws IOException 
     */  
    public boolean uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {  
        String fileName = file.getOriginalFilename();  
        try {
			FileCopyUtils.copy(file.getBytes(), new File("/opt/data/images/"+fileName));
		} catch (Exception e) {
			log.error("上传图片失败,/opt/data/images/"+fileName+", {}"+e);
			return false;
		}  
        return true;  
    }
}
