package team.test.teamFile.ui;



import team.test.teamFile.pojo.FileItem;
import team.test.teamFile.utils.NetWorkUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 主界面，通过LoginUi启动
 * @author chh
 */
public class ClientUi {
    private JTable tbFile;
    private JButton btnRefresh;
    private JButton btnUpload;
    private JButton btnDownload;
    private JButton btnDelete;
    public JPanel filePanel;
    private Long fileId;
    private Long userId;
    public ClientUi(Long userId) {
        this.userId = userId;
        refreshFileList();
        //刷新
        btnRefresh.addActionListener(e -> refreshFileList());
        //上传
        btnUpload.addActionListener(e -> {
            String path = JOptionPane.showInputDialog("输入文件路径");
            if (path == null || path.length() == 0) {
                JOptionPane.showMessageDialog(null, "输入文件路径");
                return;
            }
            NetWorkUtils.uploadFile(userId, path);
            JOptionPane.showMessageDialog(null, "上传完毕");
            refreshFileList();
        });
        //下载
        btnDownload.addActionListener(e -> {
            if (fileId != null) {
                NetWorkUtils.downloadFile(fileId);
                JOptionPane.showMessageDialog(null, "下载完成");
            } else {
                JOptionPane.showMessageDialog(null, "未选中文件");
                return;
            }
            fileId = null;
        });
        //删除
        btnDelete.addActionListener(e -> {
            if (fileId != null) {
                NetWorkUtils.deleteFile(fileId);
                JOptionPane.showMessageDialog(null, "删除完成");
                refreshFileList();
            } else {
                JOptionPane.showMessageDialog(null, "未选中文件");
                return;
            }
            fileId = null;
        });
        //鼠标点击事件
        tbFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //获得文件名id
                int row = tbFile.getSelectedRow();
                fileId = Long.valueOf(tbFile.getModel().getValueAt(row, 0).toString());
            }
        });
    }

    /**
     * 获得文件列表
     */
    public void refreshFileList(){
        //获得文件列表
        List<FileItem> fileItems = NetWorkUtils.getFileItems(userId);
        //将文件集合转化为二维数组
        if (fileItems == null) {
            return;
        }
        Object[][] data = new Object[fileItems.size()][3];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = fileItems.get(i).getId();
            data[i][1] = fileItems.get(i).getFilename();
            data[i][2] = fileItems.get(i).getLength();
        }
        //object 显示到表格中
        Object[] colNames = {"编号", "文件名", "长度"};
        tbFile.setModel(new DefaultTableModel(data, colNames));
    }

}
