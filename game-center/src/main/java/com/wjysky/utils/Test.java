package com.wjysky.utils;

import com.alibaba.fastjson.JSON;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Test extends JFrame{
    private PrintWriter printWriter;
    Socket socket;
    private JTextField jTextField = new JTextField();
    private JTextArea jTextArea = new JTextArea();
    Container container;
    /**
     * 创建的Tcp客户端程序
     */

    public Test (String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = this.getContentPane();
        final JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBorder(new BevelBorder(BevelBorder.RAISED));    //显示边框
        getContentPane().add(jScrollPane,BorderLayout.CENTER);
        jScrollPane.setViewportView(jTextArea);
        container.add(jTextField,"South");			//将文本框放在窗体下面
        jTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printWriter.println(jTextField.getText());		//将文本框的信息写入流（为下面的输出流写入信息做准备）
                jTextArea.append(jTextField.getText() + "\n");
                jTextArea.setSelectionEnd(jTextArea.getText().length());
                jTextField.setText(null);
            }
        });
    }

    private void connect() {
        jTextArea.append("尝试连接中...\n");
        try {
            socket = new Socket("127.0.0.1",9999);
            printWriter = new PrintWriter(socket.getOutputStream(),true);   //将printwriter中的信息流写入到套接字的输出流传送给服务端
            jTextArea.setText("已完成连接\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        Test myTcpClient = new Test("向服务器发送数据");
//        myTcpClient.setSize(500,200);
//        myTcpClient.setVisible(true);
//        myTcpClient.connect();

//        Map<String, Object> map = new HashMap<>();
//        map.put("systemType", 60);
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> data = new HashMap<>();
//        data.put("businessLabelVar", "{$real_amount_chinese}");
//        data.put("testReferenceValue", "中国联合网络通信有限公司");
//        data.put("businessRequire", "Y");
//        data.put("type", 1);
//        data.put("businessLabelType", "0");
//        data.put("businessLabelCHname", "666");
//        list.add(data);
//        map.put("businessLabelList", list);
//        String str = JSON.toJSONString(map);
//        System.out.println(str);
//        System.out.println("20230513".substring(0, 6));
//        List<String> list = new ArrayList<>();
//        list.add(null);
//        System.out.println(list.get(0) == "_n1111".split("_")[0]);
//        System.out.println(JSON.toJSONString(list));
        System.out.println(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
    }
}


