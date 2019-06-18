package com.web.pcdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.pcdp.entity.FileSystem;

@Repository
public interface FileSystemRepository extends JpaRepository<FileSystem, String> {
	 //查询数据库中最大的ID值
    @Query(value = "SELECT MAX(file_id) FROM filesystem", nativeQuery = true)
    String findMaxID();
	
	@Query(value = "SELECT * FROM filesystem WHERE file_id = (SELECT parent_id FROM filesystem WHERE file_id = ?); ", nativeQuery = true)
	FileSystem getParent(String id);
	
	@Query(value = "SELECT * FROM filesystem WHERE file_id = ?; ", nativeQuery = true)
	FileSystem getByID(String id);
	
	@Query(value = "SELECT * FROM filesystem WHERE parent_id = ? ORDER BY type DESC,name;", nativeQuery = true)
	List<FileSystem> getChildren(String id);
	
	@Modifying
    @Transactional
    @Query(value = "INSERT INTO filesystem VALUES(?,?,?,?,?)", nativeQuery = true)
    void insertFile(@Param("file_id") String file_id,
                    @Param("name") String name,
                    @Param("type") String type,
                    @Param("parent_id") String parent_id,
                    @Param("note") String note);
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM filesystem WHERE file_id = ?", nativeQuery = true)
    void deleteFile(@Param("file_id") String file_id);
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE filesystem SET name = ?, note = ? WHERE file_id = ?;", nativeQuery = true)
    void alterFile(@Param("name")String name
    		,@Param("note") String note
    		,@Param("file_id") String file_id);
}
