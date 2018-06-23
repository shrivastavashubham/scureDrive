package com.ssp.storage.service;

import org.springframework.web.multipart.MultipartFile;

import com.ssp.storage.domain.File;

public interface IFilesService {

	public boolean addFile(MultipartFile file, String parent, String userName);
	
	public File getFile(String folder, String parentFolder, String userName, String fileName);
}
