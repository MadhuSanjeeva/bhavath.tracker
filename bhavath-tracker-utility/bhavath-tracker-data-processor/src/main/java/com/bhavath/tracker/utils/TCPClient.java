package com.bhavath.tracker.utils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient
{

	public static void main(String[] args)
	{
        final int BUFFER_SIZE = 1024;
        String hmpPacket = "$,OTSI,16:58:23,19/02/15,8991737107813176881,192.168.12.22,214074200044176,HMP,OTSI,FV1.00,353635080728062,61.50,12.52,2.00,000500,003000,0010,10,*";
        String healthPacket = "$,OTSI,16:58:23,19/02/15,8991737107813176881,192.168.12.22,214074200044176,HCHKR,10101,OTSI,FV1.00,353635080728305,1,17.343433333,N,77.55656566,E,1,16:58:23,19/02/15,263.19,35.6,31,404,10,d6d6,1,1,3.5,100000,id,*" ;
        String activationPacket = "$,OTSI,16:58:23,19/02/15,8991737107813176881,192.168.12.22,214074200044176,ACTVR,101010,OTSI,FV1.00,12345678901234,1,17.343433333,N,77.55656566,E,1,16:58:23,19/02/15,263.19,35.6,31,404,10,d6d6,1,1,3.5,100000,id,*" ;
        String newEpbPacket = "$,EPB,EMR,353635080728062,NM,060319122325,A,1725.1747,N,07824.0597,E,627.7,0.0,000000,170.00,AP07X7097,+918985102073,AB,*";
        String newLMPPacket = "$,OTSI,18:03:18,07/05/2019,89911300000178357199,100.82.250.11,404070761225586000,LMP,OTSI,FV1.01,OI,01,L,353635080728427,AP38AA0001,1,000000,000000,000000.0000,N,0000000.0000,E,00.0,000.00,00,000.00,0.0,0.0,\"Idea\",1,1,12.1168,100.00,0,O,21,404,07,270C,F347,#MONI: Idea BSIC:17 RxQual:0 LAC:270C Id:F347 ARFCN:751 PWR:-68dbm TA:-1OK,1111,00,000058,AABB,14.98.164.17,+917288806661, 0. 0, 0. 0,VN,*";
        String nrbPacket = "$,NRP,L,353635080728062,NM,060319122325,A,1725.1747,N,07824.0597,E,627.7,0.0,000000,170.00,AP07X7097,FV1.01,AB,*";
        try (Socket socket = new Socket("14.98.164.17", 45567);

        InputStream fileInputStream = new ByteArrayInputStream(newLMPPacket.getBytes());
        OutputStream socketOutputStream = socket.getOutputStream();)
        {

            long startTime = System.currentTimeMillis();
            byte[] buffer = new byte[BUFFER_SIZE];
            int read = 0 ;
            int readTotal = 0;
            while ((read = fileInputStream.read(buffer)) != -1)
            {
                socketOutputStream.write(buffer, 0, read);
                readTotal += read;
            }
            long endTime = System.currentTimeMillis();
           // System.out.println(readTotal + " bytes written in " + (endTime - startTime) + " ms. " + ss);
            readTotal = 0;
            socketOutputStream.flush();
            socketOutputStream.close();
        }
        catch (Exception e)
        {
        	e.printStackTrace();

        }
        finally
        {
        }
    }
}
