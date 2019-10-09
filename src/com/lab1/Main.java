package com.lab1;

import java.io.IOException;
import java.util.Random;

import java.math.BigInteger;

import static com.lab1.Criptogr.*;

import static com.lab1.Cipher.*;

public class Main {

    private static final int MAX_NUMBER = 1000000000;

    public static void main(String[] args) throws IOException {
        Random random = new Random();

        //Првоерка mod в степени
        int a, x, p, y;
        int result;
        BigInteger f, z, temp;
        //1 lab
        /*
        System.out.println("Возведение в степень по модулю:");

        a = random.nextInt(MAX_NUMBER) + 1;
        x = random.nextInt(MAX_NUMBER) + 1;
        p = random.nextInt(MAX_NUMBER) + 1;

        //a = 7;
        //x = 11;
        //p = 9;

        System.out.println(a + " ^ " + x + " mod " + p);

        result = Criptogr.exponentiation(a, x, p).intValue();
        System.out.println("Result = " + result);


        //Проверка Евклида
        System.out.println("Обобщенный Алгоритм Евклида");
        int aEvklid, bEvklid;
        long[] resultEcklid;

        do {
            aEvklid = random.nextInt(MAX_NUMBER) + 1;
            bEvklid = random.nextInt(MAX_NUMBER) + 1;
        } while (aEvklid < bEvklid);


        //aEvklid = 63;
        //bEvklid = 18;
        System.out.println("a = " + aEvklid + " b = " + bEvklid);
        resultEcklid = Criptogr.evklid(aEvklid, bEvklid);
        System.out.println("GCD(a, b) = " + resultEcklid[0] + ", x = " + resultEcklid[1] + ", y =  " + resultEcklid[2]);

        f = BigInteger.valueOf(aEvklid);
        temp = BigInteger.valueOf(resultEcklid[1]);
        f = f.multiply(temp);
        z = BigInteger.valueOf(bEvklid);
        temp = BigInteger.valueOf(resultEcklid[2]);
        z = z.multiply(temp);
        f = f.add(z);
        System.out.println(f);

        System.out.println("Диффи-Хеллмана:");

        def_Hallman();

        System.out.println("Шаг младенца, шаг великана: ");

        y = random.nextInt(p - 1) + 1;
        BigInteger giStResult;
        System.out.println("a = " + a + ", y = " + y + ", p = " + p);
        giStResult = giant_Step(a, y, p);

        System.out.println("x = " + giStResult.toString());
        */
        //2 lab
       shamir();
    }

}