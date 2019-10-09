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

    private static FileWriter key_file;

    static {
        try {
            key_file = new FileWriter("resource\\Shamir\\key.txt", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int shamir() throws IOException {
        Random random = new Random();
        int p;
        int Ca, Da, Cb, Db;
        int x1, x2, x3, x4;
        int number;
        long[] resultEcklid;
        do {
            p = random.nextInt(999999999) + 2;
        } while (check(p) == false);
        System.out.println("p = " + p);
        //Алиса
        Ca = random.nextInt(1000000000) + 1;
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
        Cb = random.nextInt(1000000000) + 1;
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
        key_Entry(Ca, Da, Cb, Db);

        FileInputStream fin = new FileInputStream("C:\\4kurs\\ProtectInformLabs\\resource\\200-200-pixels.png");
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
            x2 = Criptogr.exponentiation(x1, Cb, p).intValue();
            x3 = Criptogr.exponentiation(x2, Da, p).intValue();
            x4 = Criptogr.exponentiation(x3, Db, p).intValue();
            buffer[i] = (byte)x4;

            //Алиса
            Ca = random.nextInt(1000000000) + 1;
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
            Cb = random.nextInt(1000000000) + 1;
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
            key_Entry(Ca, Da, Cb, Db);
        }

        key_file.flush();
        FileOutputStream  fos = new FileOutputStream ("resource\\Shamir\\200-200-pixels1.png");
        //FileOutputStream  fos = new FileOutputStream ("resource\\Shamir\\test1.txt");
        fos.write(buffer, 0, buffer.length);

        return 0;
    }


    public static void key_Entry(int Ca, int Da, int Cb, int Db) throws IOException {

        key_file.write(Ca);
        key_file.append(' ');
        key_file.write(Da);
        key_file.append(' ');
        key_file.write(Cb);
        key_file.append(' ');
        key_file.write(Db);
        key_file.append('\n');
    }
}
