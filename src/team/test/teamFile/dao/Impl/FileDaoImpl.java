package team.test.teamFile.dao.Impl;

import team.test.teamFile.dao.FileDao;
import team.test.teamFile.pojo.FileItem;
import team.test.teamFile.utils.JdbcUtils;

import java.util.List;

/**
 * @author chh
 */
public class FileDaoImpl implements FileDao {
    @Override
    public List<FileItem> selectFileItemsByUserId(Long userId){
        return JdbcUtils.query(FileItem.class, "select * from t_file where user_id = ?", userId);
    }

    @Override
    public FileItem selectFileItemByUserIdAndFilename(Long userId, String filename){
        List<FileItem> list = JdbcUtils.query(FileItem.class, "select * from t_file where user_id = ? and filename = ?", userId, filename);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public FileItem selectFileItemById(Long id){
        List<FileItem> list = JdbcUtils.query(FileItem.class, "select * from t_file where id = ?", id);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void insertFileItem(FileItem fileItem){
        JdbcUtils.update("insert into t_file(filename, path, length, user_id, open, create_time) values (?,?,?,?,?,?)",
                fileItem.getFilename(), fileItem.getPath(), fileItem.getLength(), fileItem.getUser_id(),
                fileItem.getOpen(), fileItem.getCreate_time());
    }

    @Override
    public void updateFileItem(FileItem fileItem){
        JdbcUtils.update("update t_file set filename=?, path=?, length=?, user_id=?, open=?, create_time=? where id=?",
                fileItem.getFilename(), fileItem.getPath(), fileItem.getLength(), fileItem.getUser_id(), fileItem.getOpen(), fileItem.getCreate_time(),
                fileItem.getId());

    }

    @Override
    public void deleteFileItem(Long id){
        JdbcUtils.update("delete from t_file where id = ?", id);
    }
}
