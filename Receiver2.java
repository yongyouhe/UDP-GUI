import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;



class Receiver2 {
    public static void main(String[] args) {

        try {
            // 构建接收方的IP和端口号，IP地址为本地机器地址
            InetAddress ip = InetAddress.getLocalHost();
            int port = 808;

            // 创建接收方的套接字,并制定端口号和IP地址
            DatagramSocket getSocket = new DatagramSocket(port, ip);

            byte[] buf = new byte[2048];
            DatagramPacket getPacket = new DatagramPacket(buf, buf.length);
            getSocket.receive(getPacket);

            // 解析发送方传递的消息
            String getMessage = new String(buf, 0, getPacket.getLength());
            System.out.println("对方发送的消息：" + '\n' + getMessage);

            // 得到发送方的IP和端口号
            InetAddress sendIP = getPacket.getAddress();
            int sendPort = getPacket.getPort();
            System.out.println("对方的IP地址是：" + sendIP.getHostAddress());
            System.out.println("对方的端口号是：" + sendPort);

            SocketAddress sendAddress = getPacket.getSocketAddress();

            EventQueue.invokeLater(() -> { // 构建接收方的图形化界面
                JFrame frame = new JFrame("Receiver");
                // frame.setTitle("Receiver");
                JPanel jpanel1 = new JPanel();
                JPanel jpanel2 = new JPanel();
                JLabel jlabel = new JLabel("发送者IP：" + sendIP.getHostAddress() + "   端口号：" + sendPort);
                JLabel jlabel2 = new JLabel("请输入回复内容");
                JTextArea jta1 = new JTextArea(200, 400);
                JTextArea jta2 = new JTextArea(100, 400);
                JButton jbutton = new JButton("发送");

                frame.setLayout(null);
                jpanel1.setLayout(null);
                jpanel2.setLayout(null);

                jpanel1.setBounds(0, 30, 1000, 300);
                jpanel2.setBounds(0, 360, 1000, 200);

                jlabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
                jlabel2.setFont(new Font("微软雅黑", Font.BOLD, 20));
                jlabel.setBounds(0, 0, 1000, 30);
                jlabel2.setBounds(0, 330, 1000, 30);

                jta1.setEditable(false);
                jta1.setBounds(0, 0, 1000, 300);
                jta2.setEditable(true);
                jta2.setBounds(0, 0, 1000, 200);

                jpanel1.add(jta1);
                jta1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                jta1.setText("对方发送的消息：" + '\n' + getMessage);
                // jpanel2.add(jlabel2);
                jpanel2.add(jta2);
                jta2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                // jpanel2.add(jbutton, BorderLayout.SOUTH);
                jbutton.setBounds(0, 560, 60, 30);
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

                    @Override
                    public void actionPerformed(ActionEvent e) {//通过按钮发送反馈消息
                        if (e.getSource() == jbutton) {
                            String feedback = jta2.getText();
                            byte[] backBuf = feedback.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(backBuf, backBuf.length, sendAddress);
                            try {
                                getSocket.send(sendPacket);
                                getSocket.close();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    }
                };
                jbutton.addActionListener(AL);

            });

/**
            // 通过数据报得到发送方的套接字地址
            SocketAddress sendAddress = getPacket.getSocketAddress();

            // 反馈发送方的消息内容
            String feedback = "接收方说：已收到！";
            byte[] backBuf = feedback.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(backBuf, backBuf.length, sendAddress);
            getSocket.send(sendPacket);
*/
            //getSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}