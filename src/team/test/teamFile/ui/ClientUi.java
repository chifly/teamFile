package team.test.teamFile.ui;



import team.test.teamFile.pojo.FileItem;
import team.test.teamFile.utils.NetWorkUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author 陈航
 */
public class ClientUi {
    private JTable tb_File;
    private JButton btn_Refresh;
    private JButton btn_Upload;
    private JButton btn_Download;
    private JButton btn_Delete;
    public JPanel filePanel;
    private Long fileId;
    private Long userId;
    public ClientUi(Long userId) {
        this.userId = userId;
        refreshFileList();
        //刷新
        btn_Refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshFileList();
            }
        });
        //上传
        btn_Upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = JOptionPane.showInputDialog("输入文件路径");
                if (path == null || path.length() == 0) {
                    JOptionPane.showMessageDialog(null, "输入文件路径");
                    return;
                }
                NetWorkUtils.uploadFile(userId, path);
                JOptionPane.showMessageDialog(null, "上传完毕");
                refreshFileList();
            }
        });
        //下载
        btn_Download.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileId != null) {
                    NetWorkUtils.downloadFile(fileId);
                    JOptionPane.showMessageDialog(null, "下载完成");
                } else {
                    JOptionPane.showMessageDialog(null, "未选中文件");
                    return;
                }
                fileId = null;
            }
        });
        //删除
        btn_Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileId != null) {
                    NetWorkUtils.deleteFile(fileId);
                    JOptionPane.showMessageDialog(null, "删除完成");
                    refreshFileList();
                } else {
                    JOptionPane.showMessageDialog(null, "未选中文件");
                    return;
                }
                fileId = null;
            }
        });
        //鼠标点击事件
        tb_File.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //获得文件名id
                int row = tb_File.getSelectedRow();
                fileId = Long.valueOf(tb_File.getModel().getValueAt(row, 0).toString());
            }
        });
    }

    public void refreshFileList(){
        //获得文件列表
        List<FileItem> fileItems = NetWorkUtils.getFileItems(userId);
        //将文件集合转化为二维数组
        Object[][] data = new Object[fileItems.size()][3];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = fileItems.get(i).getId();
            data[i][1] = fileItems.get(i).getFilename();
            data[i][2] = fileItems.get(i).getLength();
        }
        //object 显示到表格中
        Object[] colNames = {"编号", "文件名", "长度"};
        tb_File.setModel(new DefaultTableModel(data, colNames));
    }

}
