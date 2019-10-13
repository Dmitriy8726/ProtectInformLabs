package com.lab1;

import javax.imageio.ImageIO;
import javax.sound.midi.SysexMessage;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.Random;



import static com.lab1.Criptogr.*;

public class Cipher {


    public Cipher() throws IOException {
    }

    public static void shamir() throws IOException {
        File Dir = new File("resource\\Shamir");
        if (!Dir.exists()) {
            try{
                Dir.mkdir();
            }
            catch(SecurityException se){

            }
        }
        File keytxt = new File("resource\\Shamir", "key.txt");
        File x1_dat = new File("resource\\Shamir", "x1.dat");
        File x2_dat = new File("resource\\Shamir", "x2.dat");
        File x3_dat = new File("resource\\Shamir", "x3.dat");
        FileWriter key_file, file_x1, file_x2, file_x3;
        key_file = new FileWriter(keytxt, false);
        file_x1 = new FileWriter(x1_dat, false);
        file_x2 = new FileWriter(x2_dat, false);
        file_x3 = new FileWriter(x3_dat, false);
        Random random = new Random();
        int p;
        int Ca, Da, Cb, Db;
        int x1, x2, x3, x4;
        int number;
        long[] resultEcklid;
        do {
            p = random.nextInt(999999998) + 2;
        } while (check(p) == false);
        System.out.println("p = " + p);
        //Алиса
        Ca = random.nextInt(999999999) + 1;
        resultEcklid = Criptogr.evklid(Ca, p - 1);
        while(resultEcklid[0] != 1) {
            Ca--;
            resultEcklid = Criptogr.evklid(Ca, p - 1);
        }

        if (resultEcklid[1] < 0) {
            resultEcklid[1] += (p - 1);
        }
        Da = (int)resultEcklid[1];


        //Боб
        Cb = random.nextInt(999999999) + 1;
        resultEcklid = Criptogr.evklid(Cb, p - 1);
        while(resultEcklid[0] != 1) {
            Cb--;
            resultEcklid = Criptogr.evklid(Cb, p - 1);
        }

        if (resultEcklid[1] < 0) {
            resultEcklid[1] += (p - 1);
        }
        Db = (int)resultEcklid[1];

        //Запись ключей в файл
        key_Entry(Ca, Da, Cb, Db,key_file);

        FileInputStream fin = new FileInputStream("resource\\200-200-pixels.png");
        //FileInputStream fin = new FileInputStream("resource\\test.txt");
        number = fin.available();
        byte[] buffer = new byte[number];
        fin.read(buffer, 0, buffer.length);
        int [] bytes = new int[number];
        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer[i] & 255;
        }
        for(int i = 0; i < bytes.length; i++) {
            x1 = Criptogr.exponentiation(bytes[i], Ca, p).intValue();
            key_Entry(x1, file_x1);
            x2 = Criptogr.exponentiation(x1, Cb, p).intValue();
            key_Entry(x2, file_x2);
            x3 = Criptogr.exponentiation(x2, Da, p).intValue();
            key_Entry(x3, file_x3);
            x4 = Criptogr.exponentiation(x3, Db, p).intValue();
            buffer[i] = (byte)x4;

            //Алиса
            Ca = random.nextInt(999999999) + 1;
            resultEcklid = Criptogr.evklid(Ca, p - 1);
            while(resultEcklid[0] != 1) {
                Ca--;
                resultEcklid = Criptogr.evklid(Ca, p - 1);
            }

            if (resultEcklid[1] < 0) {
                resultEcklid[1] += (p - 1);
            }
            Da = (int)resultEcklid[1];

            //Боб
            Cb = random.nextInt(999999999) + 1;
            resultEcklid = Criptogr.evklid(Cb, p - 1);
            while(resultEcklid[0] != 1) {
                Cb--;
                resultEcklid = Criptogr.evklid(Cb, p - 1);
            }

            if (resultEcklid[1] < 0) {
                resultEcklid[1] += (p - 1);
            }
            Db = (int)resultEcklid[1];

            //Запись ключей в файл
            key_Entry(Ca, Da, Cb, Db, key_file);
        }

        key_file.close();
        file_x1.close();
        file_x2.close();
        file_x3.close();
        FileOutputStream  fos = new FileOutputStream ("resource\\Shamir\\200-200-pixels1.png");
        //FileOutputStream  fos = new FileOutputStream ("resource\\Shamir\\test1.txt");
        fos.write(buffer, 0, buffer.length);
    }

    public static void el_Gamal() throws IOException {
        File Dir = new File("resource\\El-Gamal");
        if (!Dir.exists()) {
            try{
                Dir.mkdir();
            }
            catch(SecurityException se){

            }
        }
        File keytxt = new File("resource\\El-Gamal", "key.txt");
        File ab_dat = new File("resource\\El-Gamal", "ab.dat");
        FileWriter key_file, ab_file;
        key_file = new FileWriter(keytxt, false);
        ab_file = new FileWriter(ab_dat, false);

        Random random = new Random();
        int p, g, q, x, y, k;
        int number;
        long a, b;

        while(true) {
            do {
                p = random.nextInt(999999998) + 2;
            } while (check(p) == false);
            q = (p - 1) / 2;
            if (check(q) == true) {
                break;
            }
        }


        do {
            g = random.nextInt(p - 3) + 2;
        } while (exponentiation(g, q, p).intValue() == 1);

        x = random.nextInt(p - 2) + 2;
        y = exponentiation(g, x, p).intValue();

        FileInputStream fin = new FileInputStream("resource\\200-200-pixels.png");
        //FileInputStream fin = new FileInputStream("resource\\test.txt");
        number = fin.available();
        byte[] buffer = new byte[number];
        fin.read(buffer, 0, buffer.length);
        long [] bytes = new long[number];
        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer[i] & 255;
        }

        for(int i = 0; i < bytes.length; i++) {
            k = random.nextInt(p - 3) + 2;
            a = exponentiation(g, k, p).intValue();
            b = exponentiation(bytes[i] * exponentiation(y, k, p).intValue(), 1, p).intValue();
            key_Entry(a, b, ab_file);
            bytes[i] = exponentiation(b * exponentiation(a, p - 1 - x, p).intValue(),1, p).intValue();
            buffer[i] = (byte)bytes[i];
            key_Entry(k, key_file);
        }

        key_file.close();
        FileOutputStream  fos = new FileOutputStream ("resource\\El-Gamal\\200-200-pixels1.png");
        //FileOutputStream  fos = new FileOutputStream ("resource\\El-Gamal\\test1.txt");
        fos.write(buffer, 0, buffer.length);
    }

    public static void rsa() throws IOException {
        File Dir = new File("resource\\RSA");
        if (!Dir.exists()) {
            try{
                Dir.mkdir();
            }
            catch(SecurityException se){

            }
        }
        File e_dat = new File("resource\\RSA", "e.dat");
        FileWriter file_e;
        file_e = new FileWriter(e_dat, false);
        Random random = new Random();
        int number;
        long P, Q;
        do {
            P = random.nextInt(999999999) + 2;
        } while (check(P) == false);
        do {
            Q = random.nextInt(999999999) + 2;
        } while (check(Q) == false);

        long f = (Q - 1) * (P - 1);
        long N = P * Q;

        long d, c, e;
        long[] resultEcklid;

        while(true) {
            if (f < 1000000000) {
                d = random.nextInt((int)f);
            } else {
                d = random.nextInt(1000000000);
            }
            resultEcklid = Criptogr.evklid(d, f);
            if (resultEcklid[0] == 1) {
                break;
            }
        }

        FileInputStream fin = new FileInputStream("resource\\200-200-pixels.png");
        //FileInputStream fin = new FileInputStream("resource\\test.txt");
        number = fin.available();
        byte[] buffer = new byte[number];
        fin.read(buffer, 0, buffer.length);
        int [] bytes = new int[number];
        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer[i] & 255;
        }

        if (resultEcklid[1] < 0) {
            resultEcklid[1] += f;
        }
        c = resultEcklid[1];
        System.out.println(c);
        for(int i = 0; i < bytes.length; i++) {
            e = exponentiation(bytes[i], d, N).longValue();
            key_Entry(e, file_e);
            bytes[i] = exponentiation(e, c, N).intValue();
            buffer[i] = (byte)bytes[i];
        }
        FileOutputStream  fos = new FileOutputStream ("resource\\RSA\\200-200-pixels1.png");
        //FileOutputStream  fos = new FileOutputStream ("resource\\RSA\\test1.txt");
        fos.write(buffer, 0, buffer.length);
    }

    public static void vernam() throws IOException {
        File Dir = new File("resource\\Vernam");
        if (!Dir.exists()) {
            try{
                Dir.mkdir();
            }
            catch(SecurityException se){

            }
        }
        File keytxt = new File("resource\\Vernam", "key.txt");
        File e_dat = new File("resource\\Vernam", "e.dat");
        FileWriter key_file, file_e;
        key_file = new FileWriter(keytxt, false);
        file_e = new FileWriter(e_dat, false);

        Random random = new Random();
        int k;
        int number;

        FileInputStream fin = new FileInputStream("resource\\200-200-pixels.png");
        //FileInputStream fin = new FileInputStream("resource\\test.txt");
        number = fin.available();
        byte[] buffer = new byte[number];
        fin.read(buffer, 0, buffer.length);
        int [] bytes = new int[number];
        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer[i] & 255;
        }

        for(int i = 0; i < bytes.length; i++) {
            k = random.nextInt(256);
            key_Entry(k, key_file);
            bytes[i] = bytes[i] ^ k ; // e = m (+) k
            key_Entry(bytes[i], file_e);
            bytes[i] = bytes[i] ^ k; // m = e (+) k
            buffer[i] = (byte)bytes[i];
        }

        key_file.close();
        FileOutputStream  fos = new FileOutputStream ("resource\\Vernam\\200-200-pixels1.png");
        //FileOutputStream  fos = new FileOutputStream ("resource\\Vernam\\test1.txt");
        fos.write(buffer, 0, buffer.length);
    }

    public static void key_Entry(int Ca, int Da, int Cb, int Db, FileWriter key_file) throws IOException {

        Integer C;
        C = Ca;
        key_file.write(C.toString());
        key_file.append(' ');
        C = Da;
        key_file.write(C.toString());
        key_file.append(' ');
        C = Cb;
        key_file.write(C.toString());
        key_file.append(' ');
        C = Db;
        key_file.write(C.toString());
        key_file.append('\n');
    }

    public static void key_Entry(long k1, long k2, FileWriter key_file) throws IOException {
        Long K = k1;
        key_file.write(K.toString());
        key_file.append(' ');
        K = k2;
        key_file.write(K.toString());
        key_file.append('\n');
    }

    public static void key_Entry(long k, FileWriter key_file) throws IOException {
        Long K = k;
        key_file.write(K.toString());
        key_file.append('\n');
    }


}
