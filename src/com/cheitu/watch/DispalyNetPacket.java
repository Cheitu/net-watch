package com.cheitu.watch;

import java.io.UnsupportedEncodingException;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
 
public class DispalyNetPacket {    
   public static final String mac1="00-16-3E-00-4D-79";
   public static final String mac2="00-16-3E-00-36-84";

   public static void main(String args[]){    
       try{    
     final  NetworkInterface[] devices = JpcapCaptor.getDeviceList();    
        for(int i=0;i<devices.length;i++){    
            NetworkInterface nc=devices[i]; 
        if(mac1.equals(bytesToHexString(nc.mac_address))||mac2.equals(bytesToHexString(nc.mac_address))){
        JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 2000, true, 20);    
        startCapThread(jpcap);    
        System.out.println("开始抓取第"+i+"个卡口上的数据");    
        }
        }    
        }catch(Exception ef){    
            ef.printStackTrace();    
            System.out.println("启动失败:  "+ef);    
        }    
    
   }    
   
   public static String bytesToHexString(byte[] src){   
       StringBuilder stringBuilder = new StringBuilder("");   
       if (src == null || src.length <= 0) {   
           return null;   
       }   
       for (int i = 0; i < src.length; i++) {   
           int v = src[i] & 0xFF;   
           String hv = Integer.toHexString(v);   
           if (hv.length() < 2) {   
               stringBuilder.append(0);   
           }   
           stringBuilder.append(hv);
           if (i < src.length-1) {
           	stringBuilder.append("-");
           }
       }   
       return stringBuilder.toString().toUpperCase();   
   } 
   public static void startCapThread(final JpcapCaptor jpcap ){    
       JpcapCaptor jp=jpcap;    
       java.lang.Runnable rnner=new Runnable(){    
           public void run(){    
               jpcap.loopPacket(-1, new TestPacketReceiver());    
           }    
       };    
       new Thread(rnner).start(); 
   }        
}    
    
  
class TestPacketReceiver  implements PacketReceiver {    
        
      public void receivePacket(Packet packet) {    
        if(packet instanceof jpcap.packet.TCPPacket){    
            TCPPacket p=(TCPPacket)packet;    
            String s="TCPPacket:| dst_ip "+p.dst_ip+":"+p.dst_port    
                     +"|src_ip "+p.src_ip+":"+p.src_port    
                     +" |len: "+p.len; 
           
            if((p.dst_port == 8080 || p.src_port == 8080)&&p.data.length>0){
            	try {
					System.out.println(new String(p.data,"utf-8").charAt(0));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}  
            
            }
        }    
        
       
  }    
    
}  