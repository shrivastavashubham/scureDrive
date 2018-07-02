package com.ssp.storage.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssp.storage.Beans.FileBean;
import com.ssp.storage.domain.File;
import com.ssp.storage.domain.Folder;
import com.ssp.storage.repository.FileRepository;
import com.ssp.storage.repository.FolderRepository;
import com.ssp.storage.repository.UserSecurityQuestionRepository;
import com.ssp.storage.service.IFilesService;

@Service
public class FilesService implements IFilesService {

	@Autowired
	FolderRepository folderRepository;

	@Autowired
	FileRepository fileRepository;

	@Autowired
	UserSecurityQuestionRepository userSecurityQuestionRepository;

	@Override
	public boolean addFile(MultipartFile file, String parent, String userName) {
		Folder parentFolder = folderRepository.findByUserUsernameAndFolderName(userName, parent);
		byte[] bytes = null;
		try {
			bytes = file.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File Dfile = new File();
		Dfile.setFileName(file.getOriginalFilename());
		Dfile.setFile(bytes);
		Dfile.setExtension(file.getOriginalFilename().lastIndexOf(".") != -1
				? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
				: null);
		Dfile.setFolder(parentFolder);
		List<File> childFiles = parentFolder.getFiles();
		childFiles.add(Dfile);
		if (Objects.nonNull(folderRepository.save(parentFolder)))
			return true;
		return false;
	}

	@Override
	public FileBean getFile(String folder, String parentFolder, String userName, String fileName, String tracePath) {
		Folder parentFolderFile = folderRepository.findByUserUsernameAndFolderNameAndParentFolderName(userName, folder,
				parentFolder);
		File file = fileRepository.findByFileNameAndFolderId(fileName, parentFolderFile.getId());
		FileBean fileBean = new FileBean();
		if (Objects.nonNull(file.getAbsolutePath())) {
			if (file.getAbsolutePath().equals(tracePath)) {
				fileBean.setFile(file);
			}
			return fileBean;
		} else {
			fileBean.setListOfQuestions(userSecurityQuestionRepository.findAllByUserUsername(userName).stream()
					.map(a -> a.getQuestion()).collect(Collectors.toList()));
			return fileBean;
		}

	}

}
