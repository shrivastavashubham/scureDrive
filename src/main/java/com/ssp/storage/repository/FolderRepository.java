package com.ssp.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssp.storage.domain.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long> {

	public Folder findByUserUsernameAndRootTrue(String userName);

	public Folder findByUserUsernameAndFolderName(String userName, String folderName);

	public Folder findByUserUsernameAndFolderNameAndParentFolderName(String userName, String folderName, String parent);
}
