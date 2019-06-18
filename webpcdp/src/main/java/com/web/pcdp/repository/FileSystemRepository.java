package com.web.pcdp.repository;

import com.web.pcdp.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface FileSystemRepository extends JpaRepository<FileSystem, String> {
	@Query(value = "SELECT * FROM filesystem WHERE file_id = (SELECT parent_id FROM filesystem WHERE file_id = ?); ", nativeQuery = true)
	FileSystem getParent(String id);
	
	@Query(value = "SELECT * FROM filesystem WHERE file_id = ?; ", nativeQuery = true)
	FileSystem getByID(String id);
	
	@Query(value = "SELECT * FROM filesystem WHERE parent_id = ? ORDER BY type DESC,name;", nativeQuery = true)
	List<FileSystem> getChildren(String id);
	
	
}
