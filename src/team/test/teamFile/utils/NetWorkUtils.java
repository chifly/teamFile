package team.test.teamFile.utils;

import team.test.teamFile.constants.HandleConstants;
import team.test.teamFile.constants.ServerConstants;
import team.test.teamFile.pojo.FileItem;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;


/**
 * @author 陈航
 */
public class NetWorkUtils {
    /**
     * 登录方法
     * @param username 用户名
     * @param password 密码
     * @return userId
     */
    public static Long login(String username, String password){
        try (Socket socket = new Socket(ServerConstants.IP, ServerConstants.PORT);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            //发送登录命令
            outputStream.writeInt(ServerConstants.TYPE_LOGIN);
            //发送账号 密码
            outputStream.writeUTF(username);
            outputStream.writeUTF(password);
            //接受用户id，
            return inputStream.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获得服务器端的文件列表
     * @return 一个文件列表集合
     */
    public static List<FileItem> getFileItems(Long userId) {
        try (Socket socket = new Socket(ServerConstants.IP, ServerConstants.PORT);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            //发送查询命令
            outputStream.writeInt(ServerConstants.TYPE_SELECT);
            //发送用户id
            outputStream.writeLong(userId);
            //接受服务器的json
            String json = inputStream.readUTF();
            //解析json
            return FileJson.jsonToList(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件上传
     * @param path 文件路径
     */
    public static void uploadFile(Long userId, String path) {
        try (Socket socket = new Socket(ServerConstants.IP, ServerConstants.PORT);
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            //发送查询命令
            outputStream.writeInt(ServerConstants.TYPE_UPLOAD);
            //创建文件对象
            File file = new File(path);
            //判断文件存在
            if (!file.exists()) {
                outputStream.writeUTF(HandleConstants.FAILURE);
                System.out.println("文件不存在");
                return;
            }
            //发送文件名
            outputStream.writeUTF(file.getName());
            //发送用户id
            outputStream.writeLong(userId);
            //创建文件输入流
            FileInputStream fileInputStream = new FileInputStream(path);
            //读取文件流发送给服务器
            FileUtils.io(fileInputStream, outputStream);
            System.out.println("上传完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     * @param fileId 文件id
     */
    public static void downloadFile(Long fileId) {
        try (Socket socket = new Socket(ServerConstants.IP, ServerConstants.PORT);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            //发送下载命令
            outputStream.writeInt(ServerConstants.TYPE_DOWNLOAD);
            //发送文件id
            outputStream.writeLong(fileId);
            //接受服务器的消息
            String name = inputStream.readUTF();
            if (HandleConstants.FAILURE.equals(name)) {
                System.out.println("没有这个文件");
                return;
            }
            //读出写入本地磁盘
            File file = new File(HandleConstants.DOWN_LOAD);
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdir();
                if (!wasSuccessful) {
                    System.out.println("文件创建失败");
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(HandleConstants.DOWN_LOAD + "\\" + name);
            FileUtils.io(inputStream, fileOutputStream);
            System.out.println("下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件删除
     * @param fileId 文件id
     */
    public static void deleteFile(Long fileId) {
        try (Socket socket = new Socket(ServerConstants.IP, ServerConstants.PORT);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            //发送delete命令
            outputStream.writeInt(ServerConstants.TYPE_DELETE);
            //发送文件id
            outputStream.writeLong(fileId);
            //接受服务器的消息
            String name = inputStream.readUTF();
            if (HandleConstants.FAILURE.equals(name)) {
                System.out.println("没有这个文件");
                return;
            }
            System.out.println("删除完成 ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
