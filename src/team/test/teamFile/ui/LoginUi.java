package team.test.teamFile.ui;


import team.test.teamFile.utils.NetWorkUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 陈航
 */
public class LoginUi {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton BtnLogin;
    private JButton BtnRegist;
    private JPanel panel1;
    private static JFrame LoginFrame;

    public LoginUi() {
        //登录按钮
        BtnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "账号不能为空");
                    return;
                }
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "密码不能为空");
                    return;
                }
                Long userId = NetWorkUtils.login(username, password);
                if (userId == null || userId == -1) {
                    JOptionPane.showMessageDialog(null, "登录失败");
                    return;
                }
                showClientFrom(userId);
                LoginFrame.dispose();
            }
        });
    }

    public void showClientFrom(Long userId){
        JFrame frame = new JFrame("ClientUi");
        frame.setContentPane(new ClientUi(userId).filePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        LoginFrame = new JFrame("LoginUi");
        LoginFrame.setContentPane(new LoginUi().panel1);
        LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginFrame.pack();
        LoginFrame.setVisible(true);
    }
}
