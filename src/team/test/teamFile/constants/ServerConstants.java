package team.test.teamFile.constants;

/**
 * 关于服务器的一些常量
 * @author chh
 */
public class ServerConstants {
    /**
     * PORT 端口号
     * IP 服务器IP地址
     * TYPE_LOGIN 客户端的登录消息
     * TYPE_SELECT 客户端的查询消息
     * TYPE_UPLOAD 客户端的上传消息
     * TYPE_DOWNLOAD 客户端的下载消息
     * TYPE_DELETE 客户端的删除消息
     */
    public static final int PORT = 8888;

    public static final String IP = "127.0.0.1";

    public static final int TYPE_LOGIN = 1;

    public static final int TYPE_SELECT = 2;

    public static final int TYPE_UPLOAD = 3;

    public static final int TYPE_DOWNLOAD = 4;

    public static final int TYPE_DELETE = 5;
}
