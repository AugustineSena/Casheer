package form.internal;

import model.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class FrameLogin extends JInternalFrame  {
    private JTextField jTFName;
    private JPasswordField jPFPass;
    private JButton jBLogin;
    private boolean Login = false;
    private ArrayList<UserData> User = new ArrayList<>();
    public FrameLogin(){
        super("Login", false, true, false, true );
        this.add( MiniForm(), BorderLayout.CENTER ); // add panel
        this.pack();
    }
    public FrameLogin(ArrayList<UserData> User){
        super("Login", false, true, false, true );
        setUser(User);
        this.add( MiniForm(), BorderLayout.CENTER ); // add panel
        this.pack();
    }

    private JPanel MiniForm()
    {

        JPanel jPMiniForm = new JPanel(new GridLayout(3,1,1,1));

        JPanel jPName = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jLName = new JLabel("Name :");
        jTFName = new JTextField(20);
        jPName.add(jLName);
        jPName.add(jTFName);
        jPMiniForm.add(jPName);

        JPanel jPPass = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jLPass = new JLabel("Password :");
        jPFPass = new JPasswordField(20);
        jPFPass.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent evt)
            {
                if(evt.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    jBLogin.doClick();
                }
            }
        });
        jPPass.add(jLPass);
        jPPass.add(jPFPass);
        jPMiniForm.add(jPPass);

        JPanel jPLogin = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jBLogin = new JButton("Login");
        jBLogin.addActionListener((ActionEvent e) -> {
            if(jTFName.getText().equals("")&&jPFPass.getText().equals(""))
            {
                String string = String.format("Username and password cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(jTFName.getText().equals(""))
            {
                String string = String.format("Username cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (jTFName.getText().substring(0, 1).matches("[0-9]"))
            {
                String string = String.format("Username cannot begin with number");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (jTFName.getText().contains(" "))
            {
                String string = String.format("Username must not contain space");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(jPFPass.getText().equals(""))
            {
                String string = String.format("Password cannot empty");
                JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                for(UserData target : getUser())
                {
                    if(jTFName.getText().equals(target.getUsername())&&jPFPass.getText().equals(target.getPassword()))
                    {
                        Login = true;
                        break;
                    }
                    else
                    {
                        Login = false;

                    }
                }
                if(!isLogin())
                {
                    String string = String.format("Username and password are incorrect");
                    JOptionPane.showMessageDialog(null,string,"",JOptionPane.INFORMATION_MESSAGE);
                }
                if(isLogin()) {
                    doWhenSuccess();
                }
            }
        });
        jPLogin.add(jBLogin);
        jPMiniForm.add(jPLogin);
        return jPMiniForm;
    }

    public ArrayList<UserData> getUser() {
        return User;
    }

    public void setUser(ArrayList<UserData> user) {
        User = user;
    }

    public boolean isLogin() {
        return Login;
    }
    public JButton getLoginButton(){
        return jBLogin;
    }
     public void clearPasswordField(){
        jPFPass.setText("");
     }
     public void clearUsernameField(){
        jTFName.setText("");
     }
     public void clearField(){
        clearPasswordField();
        clearUsernameField();
     }
     public void doWhenSuccess(){

    }
}
