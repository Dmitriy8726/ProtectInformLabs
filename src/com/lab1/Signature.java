package com.lab1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Signature {
    public Signature() {
    }

    public static void el_Gamal_signature() throws IOException, NoSuchAlgorithmException {
        File Dir = new File("resource\\El_Gamal_signature");
        if (!Dir.exists()) {
            try {
                Dir.mkdir();
            } catch (SecurityException var33) {
            }
        }

        File signaturetxt = new File("resource\\El_Gamal_signature", "signature.txt");
        FileWriter signature_file = new FileWriter(signaturetxt, false);
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        Random random = new Random();

        long p;
        long q;
        do {
            do {
                p = (long)(random.nextInt(999999998) + 2);
            } while(!Criptogr.check(p));

            q = (p - 1L) / 2L;
        } while(!Criptogr.check(q));

        long g;
        do {
            g = (long)(random.nextInt((int)p - 3) + 2);
        } while(Criptogr.exponentiation(g, q, p).intValue() == 1);

        long x = (long)(random.nextInt((int)p - 3) + 2);
        long y = Criptogr.exponentiation(g, x, p).longValue();
        FileInputStream fin = new FileInputStream("resource\\200-200-pixels.png");
        byte[] buffer = new byte[fin.available()];
        fin.read(buffer, 0, buffer.length);
        byte[] digest = m.digest(buffer);
        int[] bytes = new int[digest.length];

        int i;
        for(i = 0; i < bytes.length; ++i) {
            bytes[i] = digest[i] & 255;
        }

        long k;
        long[] resultEcklid;
        for(i = 0; i < bytes.length; ++i) {
            do {
                k = (long) (random.nextInt((int) p - 3) + 2);
                resultEcklid = Criptogr.evklid(k, p - 1L);
                if (resultEcklid[1] < 0L) {
                    resultEcklid[1] += p - 1L;
                }
            } while (resultEcklid[0] != 1);

            long r = (long)Criptogr.exponentiation(g, k, p).intValue();
            long u = Criptogr.exponentiation((long)bytes[i] - x * r, 1L, p - 1L).longValue();

            k = resultEcklid[1];
            long s = Criptogr.exponentiation(u * k, 1L, p - 1L).longValue();
            signature_Entry(r, s, signature_file);
            long l1 = Criptogr.exponentiation(Criptogr.exponentiation(y, r, p).longValue() * Criptogr.exponentiation(r, s, p).longValue(), 1L, p).longValue();
            long l2 = Criptogr.exponentiation(Criptogr.exponentiation(g, (long)bytes[i], p).longValue(), 1L, p).longValue();
            if (l1 != l2) {
                System.out.println("Error 1");
                break;
            }
        }

        signature_file.close();

    }

    public static void rsa_Signature() throws IOException, NoSuchAlgorithmException {
        File Dir = new File("resource\\RSA_signature");
        if (!Dir.exists()) {
            try {
                Dir.mkdir();
            } catch (SecurityException var28) {
            }
        }

        File signaturetxt = new File("resource\\RSA_signature", "signature.txt");
        FileWriter signature_file = new FileWriter(signaturetxt, false);
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        Random random = new Random();

        long p;
        long q;
        do {
            do {
                p = (long)(random.nextInt(12501) + 32500);
            } while(!Criptogr.check(p));

            do {
                q = (long)(random.nextInt(12501) + 32500);
            } while(!Criptogr.check(q));
        } while(p == q);

        long N = p * q;
        long f = (p - 1L) * (q - 1L);
        long d = (long)(random.nextInt(999999999) + 1);

        long[] resultEcklid;
        for(resultEcklid = Criptogr.evklid(d, f); resultEcklid[0] != 1L; resultEcklid = Criptogr.evklid(d, f)) {
            --d;
        }

        if (resultEcklid[1] < 0L) {
            resultEcklid[1] += f;
        }

        long c = resultEcklid[1];
        FileInputStream fin = new FileInputStream("resource\\200-200-pixels.png");
        int number = fin.available();
        byte[] buffer = new byte[number];
        fin.read(buffer, 0, buffer.length);
        byte[] digest = m.digest(buffer);
        int[] bytes = new int[digest.length];

        int i;
        for(i = 0; i < bytes.length; ++i) {
            bytes[i] = digest[i] & 255;
        }

        for(i = 0; i < bytes.length; ++i) {
            long s = Criptogr.exponentiation((long)bytes[i], c, N).longValue();
            signature_Entry(s, signature_file);
            long e = Criptogr.exponentiation(s, d, N).longValue();
            if (e != (long)bytes[i]) {
                System.out.println("Error 2");
            }
        }

        signature_file.close();
    }

    public static void gost_Signature() throws IOException, NoSuchAlgorithmException {
        File Dir = new File("resource\\GOST_signature");
        if (!Dir.exists()) {
            try {
                Dir.mkdir();
            } catch (SecurityException var10) {
            }
        }

        File signaturetxt = new File("resource\\GOST_signature", "signature.txt");
        FileWriter signature_file = new FileWriter(signaturetxt, false);
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        Random random = new Random();
        long p = 0L;
        long q, g, a, b = 0L, x, y, r, s, k, u1, u2, v, h;
        do {
            q = (random.nextInt(32768) + 32768);
        } while(!Criptogr.check(q));

        for(int i = 16384; i < 32768; i += 2) {
            p = q * (long)i + 1L;
            if (Criptogr.check(p)) {
                b = i;
                //System.out.println(p + " " + i + " " + q);
                break;
            }
        }
        do {
            g = (random.nextInt((int)p - 1));
            a = Criptogr.exponentiation(g, b, p).longValue();
        } while(a <= 1L);

        x = (random.nextInt((int)q - 1)) + 1;
        y = Criptogr.exponentiation(a, x, p).longValue();

        FileInputStream fin = new FileInputStream("resource\\200-200-pixels.png");
        int number = fin.available();
        byte[] buffer = new byte[number];
        fin.read(buffer, 0, buffer.length);
        byte[] digest = m.digest(buffer);
        int[] bytes = new int[digest.length];

        int i;
        for(i = 0; i < bytes.length; ++i) {
            bytes[i] = digest[i] & 255;
        }

        for(i = 0; i < bytes.length; ++i) {

            do {
                k = (random.nextInt((int) q - 1)) + 1;
                r = Criptogr.exponentiation(Criptogr.exponentiation(a, k, p).longValue(), 1, q).longValue();
                s = Criptogr.exponentiation(k * bytes[i] + x * r, 1, q).longValue();
            } while (r == 0L || s == 0L);
            signature_Entry(r, s, signature_file);
            if (r >= q || s >= q) {
                System.out.println("Error 3");
                break;
            }

            h = Criptogr.exponentiation(bytes[i], q - 2, q).longValue();
            u1 = Criptogr.exponentiation(s * h, 1, q).longValue();
            u2 = Criptogr.exponentiation((q - r) * h, 1, q).longValue();

            v = Criptogr.exponentiation(Criptogr.exponentiation(a, u1, p).longValue() * Criptogr.exponentiation(y, u2, p).longValue(), 1, p).longValue();
            v = Criptogr.exponentiation(v, 1, q).longValue();
            if (v != r) {
                System.out.println("Error 4");
                break;
            }
        }

        signature_file.close();
    }

    public static void signature_Entry(long k, FileWriter key_file) throws IOException {
        Long K = k;
        key_file.write(K.toString());
        key_file.append('\n');
    }

    public static void  signature_Entry(long k1, long k2, FileWriter key_file) throws IOException {
        Long K = k1;
        key_file.write(K.toString());
        key_file.append(' ');
        K = k2;
        key_file.write(K.toString());
        key_file.append('\n');
    }
}
