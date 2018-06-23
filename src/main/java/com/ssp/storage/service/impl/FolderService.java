package com.ssp.storage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssp.storage.domain.Folder;
import com.ssp.storage.repository.FolderRepository;
import com.ssp.storage.service.IFolderService;

@Service
public class FolderService implements IFolderService {

	@Autowired
	FolderRepository folderRepository;

	@Override
	public Folder getRootFolder(String userName) {

		return folderRepository.findByUserIdUsernameAndRootTrue(userName);
	}

	@Override
	public Folder addFolder(String userName, String folderName, String parent) {
		Folder parentFolder = folderRepository.findByUserIdUsernameAndFolderName(userName, parent);
		Folder folder = new Folder();
		folder.setFolderName(folderName);
		folder.setParent(parentFolder);
		folder.setRoot(false);
		folder.setUserId(parentFolder.getUserId());
		List<Folder> children = parentFolder.getChildren();
		children.add(folder);
		parentFolder.setChildren(children);
		folderRepository.save(parentFolder);
		return folder;
	}

	@Override
	public Folder getFolder(String userName, String folderName, String parent) {

		return folderRepository.findByUserIdUsernameAndFolderNameAndParentFolderName(userName, folderName, parent);
	}

}
