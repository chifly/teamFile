package team.test.teamFile.dao;



import team.test.teamFile.pojo.FileItem;

import java.sql.SQLException;
import java.util.List;


/**
 * 文件数据访问接口
 * @author chh
 */
public interface FileDao {
    /**
     * 通过id查询用户文件
     * @param userId 用户id
     * @return File Items
     * @throws SQLException sql异常
     */
    List<FileItem> selectFileItemsByUserId(Long userId) throws SQLException;

    /**
     * 通过用户id和文件名查询文件
     * @param userId 用户id
     * @param filename 文件名
     * @return FileItem
     * @throws SQLException sql异常
     */
    FileItem selectFileItemByUserIdAndFilename(Long userId, String filename) throws SQLException;

    /**
     * 通过文件id查询文件
     * @param id 用户的文件id
     * @return FileItem
     * @throws SQLException sql异常
     */
    FileItem selectFileItemById(Long id) throws SQLException;

    /**
     * 插入文件
     * @param fileItem 用户的文件
     * @throws SQLException sql异常
     */
    void insertFileItem(FileItem fileItem) throws SQLException;

    /**
     * 更新文件
     * @param fileItem 用户的文件
     * @throws SQLException sql异常
     */
    void updateFileItem(FileItem fileItem) throws SQLException;

    /**
     * 删除文件
     * @param id 用户的文件id
     * @throws SQLException sql异常
     */
    void deleteFileItem(Long id) throws SQLException;
}
