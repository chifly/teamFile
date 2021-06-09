package team.test.teamFile.server;

import team.test.teamFile.constants.HandleConstants;
import team.test.teamFile.constants.ServerConstants;
import team.test.teamFile.dao.Factory.FileFactory;
import team.test.teamFile.dao.Factory.UserFactory;
import team.test.teamFile.dao.FileDao;
import team.test.teamFile.dao.UserDao;
import team.test.teamFile.pojo.FileItem;
import team.test.teamFile.pojo.User;
import team.test.teamFile.utils.FileJson;
import team.test.teamFile.utils.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器
 * @author chh
 */
public class Server {

    FileFactory fileFactory = new FileFactory();
    UserFactory userFactory = new UserFactory();
    private UserDao userDao = (UserDao) userFactory.getInstance();
    private FileDao fileDao = (FileDao) fileFactory.getInstance();


    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private ExecutorService threadPool = Executors.newFixedThreadPool(CPU_COUNT * 2);
    /**
     * 服务器启动方法
     */
    public void start(){
        System.out.println("服务器启动");
        //创建socket
        try (ServerSocket serverSocket = new ServerSocket(ServerConstants.PORT)) {
            //接收客户端的连接
            while (true) {
                Socket socket = serverSocket.accept();
                //使用线程池来启动线程
                threadPool.execute(() -> {
                    //获得IO流 装饰者模式
                    try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                         DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());) {
                        //获得客户端的命令类型
                        int type = inputStream.readInt();
                        switch (type) {
                            case ServerConstants.TYPE_LOGIN:
                                handleLogin(inputStream, outputStream);
                                System.out.printf("客户端%s登录\n", socket.getInetAddress());
                                break;
                            case ServerConstants.TYPE_SELECT:
                                handleListFiles(inputStream, outputStream);
                                System.out.printf("客户端%s查询\n", socket.getInetAddress());
                                break;
                            case ServerConstants.TYPE_UPLOAD:
                                handleUpload(inputStream, outputStream);
                                System.out.printf("客户端%s上传\n", socket.getInetAddress());
                                break;
                            case ServerConstants.TYPE_DOWNLOAD:
                                handleDownload(inputStream, outputStream);
                                System.out.printf("客户端%s下载\n", socket.getInetAddress());
                                break;
                            case ServerConstants.TYPE_DELETE:
                                handleDelete(inputStream, outputStream);
                                System.out.printf("客户端%s删除\n", socket.getInetAddress());
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 处理文件查询
     * @param inputStream
     * @param outputStream
     */
    public synchronized void handleListFiles(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        //接收用户id
        long userId = inputStream.readLong();
        try {
            //获得文件集合
            List<FileItem> fileItemList = fileDao.selectFileItemsByUserId(userId);
            //file to json
            String json = FileJson.listToJson(fileItemList);
            //发送给客户端
            outputStream.writeUTF(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 处理文件的上传
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public synchronized void handleUpload(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        String fileName = inputStream.readUTF();
        if (HandleConstants.FAILURE.equals(fileName)) {
            System.out.println("客户端文件名上传错误");
            return;
        }
        //接受用户id
        long userId = inputStream.readLong();
        //查询用户名
        try {
            User user = userDao.selectUserById(userId);
            File file = new File(HandleConstants.UPLOAD_PATH + "\\" +  user.getUsername());
            if (!file.exists()) {
                file.mkdirs();
            }
            File uploadFile = new File(HandleConstants.UPLOAD_PATH + "\\" +  user.getUsername() + "\\" + fileName);
            //创建文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
            //客户端输入流读取字节写入到本地文件中
            FileUtils.io(inputStream, fileOutputStream);
            //判断该用户文件是否存在在数据库中
            FileItem fileItem = fileDao.selectFileItemByUserIdAndFilename(userId, fileName);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            //fileItem is exists
            if (fileItem == null) {
                fileItem = new FileItem(uploadFile.getName(), uploadFile.getPath(),
                        uploadFile.length(), userId, 0,time);
                fileDao.insertFileItem(fileItem);
            } else {
                fileItem.setLength(uploadFile.length());
                fileItem.setCreate_time(time);
                fileDao.updateFileItem(fileItem);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 处理文件下载
     * @param inputStream
     * @param outputStream
     */
    public synchronized void handleDownload(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        //接受文件名
        Long fileId = inputStream.readLong();
        try {
            FileItem fileItem = fileDao.selectFileItemById(fileId);
            //判断服务器上面是否存在文件
            File file = new File(fileItem.getPath());
            if (file.exists()) {
                //if file exists
                outputStream.writeUTF(file.getName());
                //read local file send client
                FileInputStream fileInputStream = new FileInputStream(file);
                FileUtils.io(fileInputStream, outputStream);
            } else {
                //not exit
                outputStream.writeUTF("error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public synchronized void handleDelete(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        //接受文件名
        Long fileId = inputStream.readLong();
        try {
            FileItem fileItem = fileDao.selectFileItemById(fileId);
            //判断服务器上面是否存在文件
            File file = new File(fileItem.getPath());
            if (file.exists()) {
                //if file exists
                outputStream.writeUTF(file.getName());
                //delete
                fileDao.deleteFileItem(fileId);
                file.delete();
            } else {
                //not exit
                outputStream.writeUTF(HandleConstants.FAILURE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public synchronized void handleLogin(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        String username = inputStream.readUTF();
        String password = inputStream.readUTF();
        //数据库中查询
        try {
            User user = userDao.login(username, password);
            if (user == null) {
                outputStream.writeLong(-1L);
                return;
            }
            outputStream.writeLong(user.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Server fileServer = new Server();
        fileServer.start();
    }
}