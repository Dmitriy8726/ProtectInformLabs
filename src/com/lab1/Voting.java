package com.lab1;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Voting {

    public static void generathion() throws IOException, NoSuchAlgorithmException {
        Random random = new Random();
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        long p;
        long q;
        do {
            do {
                p = (long)(random.nextInt(16384) + 16384);
            } while(!Criptogr.check(p));

            do {
                q = (long)(random.nextInt(16384) + 16384);
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

        int rnd;
        int answer;
        byte[] n = new byte[2];
        long r;
        //Алиса
        rnd = random.nextInt(128) + 128;
        BigInteger temp = BigInteger.valueOf(rnd);
        answer = 3;
        n[0] = temp.byteValue();
        temp = BigInteger.valueOf(answer);
        n[1] = temp.byteValue();
        do {
            r = (random.nextInt(999999999) + 1);
            resultEcklid = Criptogr.evklid(r, N);
        } while (resultEcklid[0] != 1);

        byte[] digest = m.digest(n);
        int[] bytes = new int[digest.length];


        for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = digest[i] & 255;
        }

        long h_, s_, s, tt, r_;
        for(int i = 0; i < bytes.length; ++i) {
            h_ = Criptogr.exponentiation(bytes[i] * Criptogr.exponentiation(r, d, N).longValue(), 1 , N).longValue();
            s_ = Criptogr.exponentiation(h_, c, N).longValue();
            resultEcklid = Criptogr.evklid(r, N);
            if (resultEcklid[1] < 0L) {
                resultEcklid[1] += N;
            }
            r_ = resultEcklid[1];
            s = Criptogr.exponentiation(Criptogr.exponentiation(s_ ,1, N).longValue() * Criptogr.exponentiation(r_, 1, N).longValue(), 1, N).longValue();
            tt = Criptogr.exponentiation(s, d, N).longValue();
            if (bytes[i] != tt) {
                System.out.println("Error1");
                return;
            }


        }



    }
}
