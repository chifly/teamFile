package team.test.teamFile.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import team.test.teamFile.pojo.FileItem;

import java.util.List;

/**
 * 文件格式与json格式互相转化
 * @author chh
 */
public class FileJson {
    /**
     * 文件转化为json格式
     * @param fileItems
     * @return String
     */
    public static String listToJson(List<FileItem> fileItems) {
        return new Gson().toJson(fileItems);
    }

    /**
     * json数据转化为文件
     * @param json
     * @return
     */
    public static List<FileItem> jsonToList(String json) {
        return new Gson().fromJson(json, new TypeToken<List<FileItem>>(){}.getType());
    }


}
