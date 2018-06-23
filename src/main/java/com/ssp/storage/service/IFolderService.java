package com.ssp.storage.service;

import com.ssp.storage.domain.Folder;

public interface IFolderService {

	public Folder getRootFolder(String userName);

	public Folder addFolder(String userName, String folderName, String parent);
	
	public Folder getFolder(String userName, String folderName, String parent);
}
