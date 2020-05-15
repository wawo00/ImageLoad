package com.openup.testapplication.learn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.testapplication.learn
 * @ClassName: RandomAccessFileTest
 * @Description: 用于测试RandomAccessFile
 * @Author: Roy
 * @CreateDate: 2020/3/31 17:48
 */

public class RandomAccessFileTest {
    public static final File sFile = new File("D:\\TestApplication\\app\\randomfile.txt");

    public static void main(String[] args) throws IOException {
        randomWirte();
        randomReader();
    }


    public static void randomWirte() {
        try {
            if (sFile.exists()) {
                sFile.delete();
            }
            RandomAccessFile rsfWriter = new RandomAccessFile(sFile, "rw");
            rsfWriter.seek(10000); // 文件系统中文件头内的文件插入指针指向10000这个位置
            printFileLength(rsfWriter);
            rsfWriter.setLength(10000);//10*1000byte  要求分配10kb大小的空间
            System.out.println("oo");
            printFileLength(rsfWriter);
            System.out.println("xx");
            rsfWriter.writeUTF("这是random");
            rsfWriter.seek(5000);
            char[] cbuf = new char[100];
            for(int i=0; i<cbuf.length; i++){
                cbuf[i] = 'a';
                rsfWriter.writeChar(cbuf[i]);
            }


            printFileLength(rsfWriter);	//result:  10026
            byte[] bbuf = new byte[100];
            for (int i = 0; i < bbuf.length; i++) {
                bbuf[i] = 1;
            }
            rsfWriter.seek(1000);
            rsfWriter.writeBytes(new String(bbuf));
            printFileLength(rsfWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void  randomReader() throws IOException {
        RandomAccessFile rsfReader = new RandomAccessFile(sFile, "r");

        rsfReader.seek(10000);
        System.out.println(rsfReader.readUTF());

        rsfReader.seek(5000);
        byte[] bbuf = new byte[200];
        rsfReader.read(bbuf);
        System.out.println(new String(bbuf));

        byte[] bbuf2 = new byte[100];
        rsfReader.seek(1000);
        rsfReader.read(bbuf2, 0, 100);
        for(byte b : bbuf2){
            System.out.print(b);
        }
        byte[] bbuf3 = new byte[12];
        rsfReader.seek(10014);
        rsfReader.read(bbuf3);
        System.out.println(new String(bbuf3));
    }


    private static void printFileLength(RandomAccessFile rsfWriter)
            throws IOException {
        System.out.println("file length: " + rsfWriter.length() + "  file pointer: " + rsfWriter.getFilePointer());
    }

}
