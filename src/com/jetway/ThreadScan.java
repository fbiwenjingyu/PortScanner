package com.jetway;

import java.net.*;
import java.util.Collections;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 *实现扫描的主体程序
 */
public class ThreadScan{
	
	public static JFrame main=new JFrame("JAVA端口扫描器");
	
	//显示扫描结果
	public static JTextArea Result=new JTextArea("",4,40);
	//滚动条面板
	public static JScrollPane resultPane = new JScrollPane(Result,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	
	//输入主机名文本框
	public static JTextField hostname=new JTextField("localhost",8);
	
	//输入ip地址前3位的输入框
	public static JTextField fromip1=new JTextField("0",3);
	//输入ip地址4~6位的输入框
	public static JTextField fromip2=new JTextField("0",3);
	//输入ip地址7~9位的输入框
	public static JTextField fromip3=new JTextField("0",3);
	//输入起始ip地址最后4位的输入框
	public static JTextField fromip4=new JTextField("0",3);
	//输入目标ip地址最后4位的输入框
	public static JTextField toip=new JTextField("0",3);
	
	//输入最小端口的输入框
	public static JTextField minPort=new JTextField("0",4);
	//输入最大端口的输入框
	public static JTextField maxPort=new JTextField("1000",4);
	//输入最大线程数量的输入框
	public static JTextField maxThread=new JTextField("100",3);
	
	//错误提示框
	public static JDialog DLGError=new JDialog(main,"错误!");
	public static JLabel DLGINFO=new JLabel("");
	
	public static JLabel type=new JLabel("请选择：");
	
	//扫描类型
	public static JRadioButton radioIp = new JRadioButton("IP地址：");
	public static JRadioButton radioHost = new JRadioButton("主机名：",true);
	//单选框组
	public static ButtonGroup group = new ButtonGroup();

	public static JLabel P1=new JLabel("端口范围:");
	public static JLabel P2=new JLabel("~");
	public static JLabel P3=new JLabel("~");
	public static JLabel Pdot1 = new JLabel(".");
	public static JLabel Pdot2 = new JLabel(".");
	public static JLabel Pdot3 = new JLabel(".");
	public static JLabel TNUM=new JLabel("线程数:");
	public static JLabel RST=new JLabel("扫描结果:                                       ");
	//public static JLabel con=new JLabel("                                                                                                              ");

	//定义按钮
	public static JButton OK = new JButton("确定");
	public static JButton Submit = new JButton("开始扫描");
	public static JButton Cancel = new JButton("退出");
	public static JButton saveButton = new JButton("保存扫描结果");

	//菜单栏
	public static JMenuBar myBar = new JMenuBar();
	public static JMenu myMenu = new JMenu("文件(F)");
	public static JMenuItem saveItem = new JMenuItem("保存扫描结果(S)");
	public static JMenuItem exitItem = new JMenuItem("退出(Q)");
	public static JMenu myMenu2 = new JMenu("帮助");
	public static JMenuItem helpItem = new JMenuItem("阅读");
	
	public static Thread[] threads;

	public static void main(String[] args){

		main.setSize(500,400);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		main.setLayout(new GridBagLayout());
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DLGError.setSize(300,100);
		DLGError.setLocationRelativeTo(null);

		//添加“菜单栏”
		myMenu.add(saveItem);
		myMenu.add(exitItem);

		myMenu2.add(helpItem);

		myBar.add(myMenu);
		myBar.add(myMenu2);
		main.setJMenuBar(myBar);

		//设置热键
		myMenu.setMnemonic('F'); 
		saveItem.setMnemonic ('S'); 
		//为“另存为”组件设置快捷键为ctrl+s
		saveItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S,InputEvent.CTRL_MASK)); 
		exitItem.setMnemonic('Q'); 
		exitItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_Q,InputEvent.CTRL_MASK)); 

		//采用表格包型布局
		Container mPanel = main.getContentPane();
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,0,10);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 10;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(type,c);

		group.add(radioIp);
		group.add(radioHost);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(radioIp,c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(fromip1,c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(Pdot1,c);

		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(fromip2,c);

		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(Pdot2,c);

		c.gridx = 5;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(fromip3,c);

		c.gridx = 6;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(Pdot3,c);

		c.gridx = 7;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(fromip4,c);

		c.gridx = 8;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(P2,c);

		c.gridx = 9;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(toip,c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(radioHost,c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(hostname,c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(P1,c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(minPort,c);

		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(P3,c);

		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(maxPort,c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(TNUM,c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(maxThread,c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(Submit,c);
		
		c.gridx = 3;
		c.gridy = 5;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(saveButton,c);

		c.gridx = 6;
		c.gridy = 5;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(Cancel,c);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 10;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(RST,c);

		//设置文本区域可以换行
		Result.setLineWrap(true);
		//设置文本区域不可编辑
		Result.setEditable(false);

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 10;
		c.gridheight = 4;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(resultPane,c);

		Container dPanel = DLGError.getContentPane();
		dPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		dPanel.add(DLGINFO);
		dPanel.add(OK);

		Submit.addActionListener(new SubmitAction());
		Cancel.addActionListener(new CancleAction());
		OK.addActionListener(new OKAction());

		//实现保存功能
		saveItem.addActionListener(new SaveAction());

		//实现退出功能
		exitItem.addActionListener(new java.awt.event.ActionListener() { 
			public void actionPerformed(java.awt.event.ActionEvent e) {    
				System.exit(0);
			}
		});

		//实现帮助功能
		helpItem.addActionListener(new java.awt.event.ActionListener() { 
			public void actionPerformed(java.awt.event.ActionEvent e) {    
				new AboutDialog();
			}
		});

		saveButton.addActionListener(new SaveAction());

		main.setVisible(true);
	}
}

/*
 *实现“取消”功能
 *退出程序
 */
class CancleAction implements ActionListener{

	public void actionPerformed (ActionEvent e){
		System.exit(0);
	}
}

/*
 *实现“确定”功能
 *完成扫描
 */
class SubmitAction implements ActionListener{

	public void actionPerformed (ActionEvent a){

		int minPort;
		int maxPort;
		int maxThread;

		int ip1 = 0;
		int ip2 = 0;
		int ip3 = 0;
		int ipstart = 0;
		int ipend = 0;
		
		String ipaddress = "";
		String hostname = "";

		ThreadScan.Result.setText("");
		//将"确定"按钮设置成为不可用
		if(ThreadScan.Submit.isEnabled()){
			ThreadScan.Submit.setEnabled(false);
		}

		/*
		 *判断搜索的类型
		 *按照ip地址扫描：type = 0
		 *按照主机名称扫描：type = 1
		 */
		if(ThreadScan.radioIp.isSelected()){
			
			TCPThread.type = 0;

			//判断ip的前3位是否为int型
			try{
				ip1=Integer.parseInt(ThreadScan.fromip1.getText());
			}
			catch(NumberFormatException e){
				ThreadScan.DLGINFO.setText("错误的ip!");
				ThreadScan.DLGError.setVisible(true);
				return;
			}

			//判断ip的4~6位是否为int型
			try{
				ip2=Integer.parseInt(ThreadScan.fromip2.getText());
			}
			catch(NumberFormatException e){
				ThreadScan.DLGINFO.setText("错误的ip!");
				ThreadScan.DLGError.setVisible(true);
				return;
			}

			//判断ip的7~9位是否为int型
			try{
				ip3=Integer.parseInt(ThreadScan.fromip3.getText());
			}
			catch(NumberFormatException e){
				ThreadScan.DLGINFO.setText("错误的ip!");
				ThreadScan.DLGError.setVisible(true);
				return;
			}

			//判断起始ip的最后4位是否为int型
			try{
				ipstart=Integer.parseInt(ThreadScan.fromip4.getText());
			}
			catch(NumberFormatException e){
				ThreadScan.DLGINFO.setText("错误的ip!");
				ThreadScan.DLGError.setVisible(true);
				return;
			}
			
			//判断目标ip的最后4位是否为int型
			try{
				ipend=Integer.parseInt(ThreadScan.toip.getText());
			}
			catch(NumberFormatException e){
				ThreadScan.DLGINFO.setText("错误的目标ip!");
				ThreadScan.DLGError.setVisible(true);
				return;
			}
			
			//判断起始ip是否正确
			//判断条件：大于0且小于等于255
			if(ip1<0 || ip1>255||ip2<0 || ip2>255||ip3<0 || ip3>255||ipstart<0 || ipstart>255){
				ThreadScan.DLGINFO.setText("                    ip地址为0-255的整数!                    ");
				ThreadScan.DLGError.setVisible(true);
				return;
			}
			else{
				TCPThread.ip1 = ip1;
				TCPThread.ip2 = ip2;
				TCPThread.ip3 = ip3;
				TCPThread.ipstart = ipstart;
			}

			//判断目标ip是否正确
			//判断条件：大于0且小于等于255
			if(ipend<0 || ipend>255){
				ThreadScan.DLGINFO.setText("                    目标ip地址为0-255的整数!                    ");
				ThreadScan.DLGError.setVisible(true);
				return;
			}
			else{
				TCPThread.ipend = ipend;
			}
			
			ipaddress = "" + ip1  + "."+ ip2 + "." + ip3 +"." + ipstart;
			
			/*
			 *判断ip地址的有效性
			 */
			try{
				TCPThread.hostAddress=InetAddress.getByName(ipaddress);
			}
			catch(UnknownHostException e){
				ThreadScan.DLGINFO.setText("            错误的IP或地址不可达!            ");
				ThreadScan.DLGError.setVisible(true);
				return;
			}
		}
		
		//根据主机名进行端口扫描
		if(ThreadScan.radioHost.isSelected()){
			
			TCPThread.type = 1;
			
			/*
			 *判断主机名称的有效性
			 */
			try{
				TCPThread.hostAddress=InetAddress.getByName(ThreadScan.hostname.getText());
			}
			catch(UnknownHostException e){
				ThreadScan.DLGINFO.setText("            错误的域名或地址不可达!            ");
				ThreadScan.DLGError.setVisible(true);
				return;
			}
		}

		/*
		 *判断端口号的有效性
		 */
		try{
			minPort=Integer.parseInt(ThreadScan.minPort.getText());
			maxPort=Integer.parseInt(ThreadScan.maxPort.getText());
			maxThread=Integer.parseInt(ThreadScan.maxThread.getText());
		}
		catch(NumberFormatException e){
			ThreadScan.DLGINFO.setText("错误的端口号或线程数!端口号和线程数必须为整数!");
			ThreadScan.DLGError.setVisible(true);
			return;
		}
		
		/*
		 *判断最小端口号的有效范围
		 *判断条件：大于0且小于65535，最大端口应大于最小端口
		 */
		if(minPort<0 || minPort>65535 || minPort>maxPort){
			ThreadScan.DLGINFO.setText("最小端口必须是0-65535并且小于最大端口的整数!");
			ThreadScan.DLGError.setVisible(true);
			return;			
		}
		else{
			TCPThread.MIN_port=minPort;
		}

		/*
		 *判断最大端口号的有效范围
		 *判断条件：大于0且小于65535，最大端口应大于最小端口
		 */
		if(maxPort<0 || maxPort>65535 || maxPort<minPort){
			ThreadScan.DLGINFO.setText("最大端口必须是0-65535并且大于最小端口的整数!");
			ThreadScan.DLGError.setVisible(true);
			return;	
		}
		else{
			TCPThread.MAX_port=maxPort;
		}

		/*
		 *判断线程数量的有效范围
		 *判断条件：大于等于1且小于等于200
		 */
		if(maxThread<1 || maxThread>200){
			ThreadScan.DLGINFO.setText("                    线程数为1-200的整数!                    ");
			ThreadScan.DLGError.setVisible(true);
			return;
		}

		ThreadScan.Result.append("线程数 "+ThreadScan.maxThread.getText()+"\n");
		
		//启动线程
		
		ThreadScan.threads = new Thread[maxThread];
		for(int i=0;i<maxThread;i++){
			ThreadScan.threads[i] = new TCPThread("T" + i,i);
			ThreadScan.threads[i].start();
		}
		 
		//等待所有线程结束
		waitForAllThreadFinish(maxThread);
		
	}

	private void waitForAllThreadFinish(int maxThread) {
		new Thread() {

			@Override
			public void run() {
				while(true) {
					boolean allFinish = true;
					for(int i=0;i<maxThread;i++){
						allFinish = allFinish && ThreadScan.threads[i].getState() == Thread.State.TERMINATED;
					}
					if(allFinish) break;
				}
				
				ThreadScan.Result.append("\n"+"扫描完成...");
				
				Collections.sort(TCPThread.ports);
				System.out.println(TCPThread.ports);
				
				//将【确定】按钮设置成为可用
				if(!ThreadScan.Submit.isEnabled()){
					ThreadScan.Submit.setEnabled(true);
				}
			}
			
		}.start();
	}
	
	
}

/*
 *实现错误提示框中的“确定”按钮功能
 */
class OKAction implements ActionListener{

	public void actionPerformed (ActionEvent e){
		ThreadScan.Submit.setEnabled(true);
		ThreadScan.DLGError.dispose();
	}
}

class SaveAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(null);

		// 点击“保存”
		if (returnVal == 0) {
			File saveFile = fc.getSelectedFile();
			try {
				FileWriter writeOut = new FileWriter(saveFile);
				writeOut.write(ThreadScan.Result.getText());
				writeOut.close();
			} catch (IOException ex) {
				System.out.println("保存失败");
			}
		}
		// 点击“取消”
		else
			return;
	}
	
}

