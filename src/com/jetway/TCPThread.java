package com.jetway;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TCPThread extends Thread{
	
	public static InetAddress hostAddress;
	
	//最小的端口号
	public static int MIN_port;
	//最大的端口号
	public static int MAX_port;
	
	//线程总数
	private int threadnum;    
	
	//查询方式：0为ip；1为主机名
	public static int type;

	//ip地址前3位
	public static int ip1;
	//ip地址4~6位
	public static int ip2;
	//ip地址7~9位
	public static int ip3;
	//起始ip地址的最后4位
	public static int ipstart;
	//结束ip地址的最后4位
	public static int ipend;
	//完整的ip地址
	public static String ipAll;

	//扫描的主机名称或ip
	String hostname = "";
	//端口的类别
	String porttype = "0";
		
	/*
	 *构造函数
	 */
	public TCPThread(String name,int threadnum){
		super(name);        
		this.threadnum = threadnum;    
	}    
	
	/*
	 *运行函数
	 */
	public void run() {
		
		//ip地址
		int h = 0;
		//端口号
		int i = 0;
		Socket theTCPsocket;

		//根据ip地址进行扫描
		if(type == 0){
			
			//ip地址循环扫描
			for(h = ipstart; h <=ipend; h++){
				
				//组成完整的ip地址
				ipAll = "" + ip1 + "." + ip2 + "." + ip3 + "." + h;
				hostname = ipAll;
				
				try{
					//在给定主机名的情况下确定主机的 IP 地址
					hostAddress=InetAddress.getByName(ipAll);
				}
				catch(UnknownHostException e){
				}
				
				//不同的端口循环扫描
				for (i = MIN_port+threadnum; i < MAX_port + Integer.parseInt(ThreadScan.maxThread.getText()); i += Integer.parseInt(ThreadScan.maxThread.getText())){

					try{
						theTCPsocket=new Socket(hostAddress,i);
						theTCPsocket.close();
						ThreadScan.Result.append(hostname+":"+i);
						
						//判断端口的类别
						switch(i){
							case 21:
								porttype = "(FTP)";
								break;
							case 23:
								porttype = "(TELNET)";
								break;
							case 25:
								porttype = "(SMTP)";
								break; 
							case 80:
								porttype = "(HTTP)";	
								break;
							case 110:
								porttype = "(POP)";
								break;
							case 139:
								porttype = "(netBIOS)";
								break;
							case 1433:
								porttype = "(SQL Server)";
								break;
							case 3389:
								porttype = "(Terminal Service)";
								break;
							case 443:
								porttype = "(HTTPS)";
								break;
							case 1521:
								porttype = "(Oracle)";
								break;
						}
						
						//端口没有特定类别
						if(porttype.equals("0")){
							ThreadScan.Result.append("\n");
						}
						else{
							ThreadScan.Result.append(":"+porttype+"\n");
						}
					}
					catch (IOException e){
					}
				}
			}
			
			//扫描完成后，显示扫描完成，并将“确定”按钮设置为可用
			if (i==MAX_port+Integer.parseInt(ThreadScan.maxThread.getText())){
				ThreadScan.Result.append("\n"+"扫描完成...");
				
				//将"确定"按钮设置成为可用
				if(!ThreadScan.Submit.isEnabled()){
					ThreadScan.Submit.setEnabled(true);
				}
			}
		}
		
		//按照主机名进行端口扫描
		if(type == 1){

			for (i = MIN_port+threadnum; i < MAX_port+Integer.parseInt(ThreadScan.maxThread.getText()); i += Integer.parseInt(ThreadScan.maxThread.getText())){

				try{
					theTCPsocket=new Socket(hostAddress,i);
					theTCPsocket.close();
					ThreadScan.Result.append(" "+i);
					switch(i){
							case 21:
								porttype = "(FTP)";
								break;
							case 23:
								porttype = "(TELNET)";
								break;
							case 25:
								porttype = "(SMTP)";
								break; 
							case 80:
								porttype = "(HTTP)";	
								break;
							case 110:
								porttype = "(POP)";
								break;
							case 139:
								porttype = "(netBIOS)";
								break;
							case 1433:
								porttype = "(SQL Server)";
								break;
							case 3389:
								porttype = "(Terminal Service)";
								break;
							case 443:
								porttype = "(HTTPS)";
								break;
							case 1521:
								porttype = "(Oracle)";
								break;
						}
						
						//端口没有特定类别
						if(porttype.equals("0")){
							ThreadScan.Result.append("\n");
						}
						else{
							ThreadScan.Result.append(":"+porttype+"\n");
						}
				}
				catch (IOException e){
				}
			}
			
			//扫描完成后，显示扫描完成，并将【确定】按钮设置为可用
			if (i==MAX_port+Integer.parseInt(ThreadScan.maxThread.getText())){
				ThreadScan.Result.append("\n"+"扫描完成...");
				
				//将【确定】按钮设置成为可用
				if(!ThreadScan.Submit.isEnabled()){
					ThreadScan.Submit.setEnabled(true);
				}
			}
		}
	}
}