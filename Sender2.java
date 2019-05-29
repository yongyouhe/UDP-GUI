import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
//import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

public class Sender2 {
    public static void main(String[] args) {

        try {
            // 构造数据报包套接字并将其绑定到本地主机上任何可用的端口
            DatagramSocket sendSocket = new DatagramSocket();

            // 确定接收方的IP地址及端口号，地址为本地机器地址
            int port = 808;
            InetAddress ip = InetAddress.getLocalHost();

            // 构建发送方的图形化界面
            JFrame frame = new JFrame("Sender");
            JPanel jpanel1 = new JPanel();
            JPanel jpanel2 = new JPanel();
            JLabel jlabel = new JLabel("请输入发送的消息：");
            JLabel jlabel2 = new JLabel("收到反馈的消息：");
            JTextArea jta1 = new JTextArea(200, 400);
            JTextArea jta2 = new JTextArea(100, 400);
            JButton jbutton = new JButton("发送");
    
            frame.setLayout(null);
            jpanel1.setLayout(null);
            jpanel2.setLayout(null);
    
            jpanel1.setBounds(0, 30, 1000, 300);
            jpanel2.setBounds(0, 390, 1000, 200);
    
            jlabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
            jlabel2.setFont(new Font("微软雅黑", Font.BOLD, 20));
            jlabel.setBounds(0, 0, 1000, 30);
            jlabel2.setBounds(0, 360, 1000, 30);
    
            jta1.setEditable(true);
            jta1.setBounds(0, 0, 1000, 300);
            jta2.setEditable(false);
            jta2.setBounds(0, 0, 1000, 200);
    
            jpanel1.add(jta1);
            jta1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            //jta1.setText("对方发送的消息：" + '\n' + getMessage);
            // jpanel2.add(jlabel2);
            jpanel2.add(jta2);
            jta2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            // jpanel2.add(jbutton, BorderLayout.SOUTH);
            jbutton.setBounds(0, 330, 60, 30);
            jbutton.setFont(new Font("微软雅黑", Font.PLAIN, 10));
            jbutton.setFocusPainted(false);
    
            frame.add(jlabel, BorderLayout.NORTH);
            frame.add(jpanel1);
            frame.add(jlabel2);
            frame.add(jpanel2);
            frame.add(jbutton);
            frame.setBounds(300, 300, 1000, 630);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
    
            ActionListener AL = new ActionListener() {
                //构建发送按钮事件响应
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == jbutton) {
                        String message = jta1.getText();
                        byte[] buf = message.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, ip, port);
                        try {
                            sendSocket.send(sendPacket);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        
                    }
                }
            };

            jbutton.addActionListener(AL);

            //接收反馈信息
            byte[] getBuf = new byte[1024];
            DatagramPacket getPacket = new DatagramPacket(getBuf, getBuf.length);

            try {
                sendSocket.receive(getPacket);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String backMessage = new String(getBuf, 0, getPacket.getLength());
            jta2.setText(backMessage);

            sendSocket.close();
 /**

            // 创建发送类型的数据报：
            DatagramPacket sendPacket = new DatagramPacket(buf, i, ip, port);
            sendSocket.send(sendPacket);

            // 确定接受反馈数据的缓冲存储器
            byte[] getBuf = new byte[1024];

            // 创建接受类型的数据报
            DatagramPacket getPacket = new DatagramPacket(getBuf, getBuf.length);

            // 通过套接字接受数据
            sendSocket.receive(getPacket);
            String backMessage = new String(getBuf, 0, getPacket.getLength());
            System.out.println("接受方返回的消息：" + backMessage);

            sendSocket.close();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}