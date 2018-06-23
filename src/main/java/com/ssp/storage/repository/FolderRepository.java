package com.ssp.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssp.storage.domain.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long> {

	public Folder findByUserIdUsernameAndRootTrue(String userName);

	public Folder findByUserIdUsernameAndFolderName(String userName, String folderName);

	public Folder findByUserIdUsernameAndFolderNameAndParentFolderName(String userName, String folderName, String parent);
}
