package com.mediot.ygb.mrqs.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class CompressUtil {

    public static byte[] writeCompressObject(Object object){

        byte[] data = null;
        try{
            //建立字节数组输出流
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            GZIPOutputStream gzout = new GZIPOutputStream(bao);
            ObjectOutputStream out = new ObjectOutputStream(gzout);
            out.writeObject(object);
            out.flush();
            out.close();
            gzout.close();

            data = bao.toByteArray();
            bao.close();
        }catch(IOException e){
            System.err.println(e);
        }
        return data;
    }

    public static Object readCompressObject(byte[] data){
        Object object = null;
        try{
            //建立字节数组输入流
            ByteArrayInputStream i = new ByteArrayInputStream(data);
            //建立gzip解压输入流
            GZIPInputStream gzin=new GZIPInputStream(i);
            //建立对象序列化输入流
            ObjectInputStream in = new ObjectInputStream(gzin);
            //按制定类型还原对象
            object = in.readObject();
        }catch(ClassNotFoundException e){
            System.err.println(e.getMessage());
        }catch (IOException ex) {
            System.err.println(ex);
        }

        return object;
    }

}
