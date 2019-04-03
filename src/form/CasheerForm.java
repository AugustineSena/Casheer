package form;

import form.internal.FrameItemData;
import form.internal.FrameLogin;
import form.internal.FramePrintReceipt;
import form.internal.FrameTransaction;
import model.ItemData;
import model.ReceiptData;
import model.UserData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CasheerForm extends JFrame {

    private final JDesktopPane theDesktop;
    private final FrameLogin frameLogin = new FrameLogin(){
        public void doWhenSuccess(){
            if(frameLogin.isLogin()){
                jBItemData.setEnabled(true);
                jBTransaction.setEnabled(true);
                jMILogin.setText("Logout");
                frameLogin.dispose();
                jBItemData.doClick();
                jBTransaction.doClick();
            }
        }
    };
    private final FrameTransaction frameTransaction = new FrameTransaction();
    private final FrameItemData frameItemData = new FrameItemData();
    private final FramePrintReceipt framePrintReceipt = new FramePrintReceipt();
    private AboutForm aboutform = new AboutForm();
    private ArrayList <ReceiptData> Receipt;
    private ArrayList <UserData> User;
    private ArrayList <ItemData> Item;

    /**
     */
    public CasheerForm(){
        super( "CasheerForm App Free (キャッシャー) - \u00a9 2016 - 2019 by Augustine Sena,Inc" );

        theDesktop = new JDesktopPane(); // create desktop pane
        theDesktop.setBackground(Color.BLACK);
        add( theDesktop );

        InstallUserData();
        InstallItemData();

        setJMenuBar(MenuStrip());//?

        System.out.println("========== Notice Me せんぱい！===========");
        System.out.format("%18s    %-20s\n========================================\n","Username(ユーザ名)","Password(パスワード)");
        User.stream().forEach((me) -> {
            System.out.format("%20s    %-20s\n",me.getUsername(),me.getPassword());
        });
    }
    public void InstallUserData(){
        User = new ArrayList<>();
        User.add(new UserData("admin","1234"));
        User.add(new UserData("AlKachir","antimo=antimaho+jomblo"));//AlKachir means 'Aliran Kathok Chingkrank'
        User.add(new UserData("Lockey","mantaps"));
    }
    public void InstallItemData(){
        Item = new ArrayList<>();
        Item.add(new ItemData(Item.size()+1,"Adobe Creative Pack CS12",2000000));//1
        Item.add(new ItemData(Item.size()+1,"Adobe Master Collection CC2016",2800000));//2
        Item.add(new ItemData(Item.size()+1,"NVidia GTX 980",3900000));//3
        Item.add(new ItemData(Item.size()+1,"NVidia GTX 980i",8200000));//4
        Item.add(new ItemData(Item.size()+1,"NVidia GTX 1028",8900000));//5
        Item.add(new ItemData(Item.size()+1,"Processor Intel i5",2500000));//6
        Item.add(new ItemData(Item.size()+1,"Processor Intel i7",3900000));//7
        Item.add(new ItemData(Item.size()+1,"USB Flashdisk Kingston 8GB",400000));//8
        Item.add(new ItemData(Item.size()+1,"USB Flashdisk Kingston 16GB",600000));//9
        Item.add(new ItemData(Item.size()+1,"USB Flashdisk Kingston 32GB",800000));//10
    }
    //////////////////////////////////////////////MenuStrip///////////////////////////////////////////////
    private JMenuItem jMILogin;
    private JButton jBTransaction;
    private JButton jBItemData;
    Dimension dimin = new Dimension(505, 500);
    Dimension dimin1 = new Dimension(310, 380);
    Dimension dimax1 = new Dimension(400, 400);
    public JMenuBar MenuStrip(){
        frameLogin.setUser(User);
        frameItemData.setItem(Item);
        frameItemData.setSize(dimin);
        frameItemData.setMinimumSize(dimin);

        frameTransaction.setParentDesktop(theDesktop);
        frameTransaction.setItem(frameItemData.getItem());
        frameTransaction.setSize(dimin);
        frameTransaction.setMinimumSize(dimin);

        framePrintReceipt.setReceipt(frameTransaction.getReceipt());
        framePrintReceipt.setSize(dimin1);
        framePrintReceipt.setMinimumSize(dimin1);
        framePrintReceipt.setMaximumSize(dimax1);

        JMenuBar jMBMenuStrip = new JMenuBar();
        JMenu jMFile = new JMenu("File");
        jMILogin = new JMenuItem("Login");
        jMILogin.addActionListener((ActionEvent event) -> {
            // create internal frame
            if(jMILogin.getText().equals("Login")){
                if(!frameLogin.isVisible() ){
                    try{
                        theDesktop.add( frameLogin ); // attach internal frame
                        // show internal frame
                        Dimension desktopSize = theDesktop.getSize();
                        Dimension jInternalFrameSize = frameLogin.getSize();
                        frameLogin.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                                (desktopSize.height- jInternalFrameSize.height)/2);
                        frameLogin.setVisible( true );
                    }catch(IllegalArgumentException e){
                        frameLogin.setVisible(true);
                        frameLogin.toFront();
                        frameLogin.repaint();
                        theDesktop.repaint();
                    }
                }
            }
            if(jMILogin.getText().equals("Logout")){
                jMILogin.setText("Login");
                jBItemData.setEnabled(false);
                jBTransaction.setEnabled(false);
                theDesktop.remove(frameLogin);
                frameTransaction.dispose();
                frameItemData.dispose();
                framePrintReceipt.dispose();
                frameLogin.clearPasswordField();
            }
        });
        JMenuItem jMIExit  = new JMenuItem("Exit");
        jMIExit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        jMFile.add(jMILogin);
        jMFile.add(jMIExit);
        jMBMenuStrip.add(jMFile);
        jBTransaction = new JButton("Transaction");
        jBTransaction.addActionListener((ActionEvent event) -> {
            try{
                theDesktop.add( frameTransaction ); // attach internal frame
                // show internal frame
                Dimension desktopSize = theDesktop.getSize();
                Dimension jInternalFrameSize = frameTransaction.getSize();
                frameTransaction.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                        (desktopSize.height- jInternalFrameSize.height+120)/2);
                frameTransaction.setVisible( true );
            }catch (IllegalArgumentException e){
                frameTransaction.setVisible(true);
                frameTransaction.toFront();
                frameItemData.toBack();
                frameTransaction.repaint();
                theDesktop.repaint();
            }
        });
        jBTransaction.setEnabled(false);
        jMBMenuStrip.add(jBTransaction);
        jBItemData = new JButton("Item Data");
        jBItemData.addActionListener((ActionEvent event) -> {
            try{
                theDesktop.add( frameItemData ); // attach internal frame
                // show internal frame
                Dimension desktopSize = theDesktop.getSize();
                Dimension jInternalFrameSize = frameItemData.getSize();
                frameItemData.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
                        (desktopSize.height - jInternalFrameSize.height+60)/2);
                frameItemData.setVisible( true );
            }catch (IllegalArgumentException e){
                frameItemData.setVisible(true);
                frameItemData.toFront();
                frameTransaction.toBack();
                frameItemData.repaint();
                theDesktop.repaint();
            }
        });
        jBItemData.setEnabled(false);
        jMBMenuStrip.add(jBItemData);
        JButton jBAbout = new JButton("About");
        jBAbout.addActionListener((ActionEvent event) -> {
           aboutform.show();
        });
        jMBMenuStrip.add(jBAbout);
        return jMBMenuStrip;
    }
}
