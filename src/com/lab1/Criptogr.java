package com.lab1;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Criptogr {
    public static BigInteger exponentiation(long a, long x, long p)
    {
        BigInteger  result = BigInteger.valueOf(1);
        BigInteger c;
        BigInteger pp = BigInteger.valueOf(p);
        long  bit;
        c = BigInteger.valueOf(a % p);
        bit = x & 1;
        if (bit == 1) {
            result = result.multiply(c);
        }
        x = x >> 1;
        while (x != 0) {
            c = c.multiply(c);
            c = c.mod(pp);
            bit = x & 1;
            if (bit == 1) {
                result = result.multiply(c);
            }
            x = x >> 1;
        }
        result = result.mod(pp);

        return result;
    }

    public static long[] evklid(long a, long b)
    {
        long[] U = {a, 1, 0};
        long[] V = {b, 0, 1};
        long[] T = new long[3];
        long q;

        while (V[0] != 0) {
            q = U[0] / V[0];
            T[0] = U[0] % V[0];
            T[1] = U[1] - q * V[1];
            T[2] = U[2] - q * V[2];
            System.arraycopy(V, 0, U, 0, U.length);
            System.arraycopy(T, 0, V, 0, V.length);
        }

        return U;
    }

    public static int def_Hallman()
    {
        Random random = new Random();
        int p, q, g;
        int Y1, Y2;
        int Z1, Z2;
        int X1, X2;
        while(true) {
            do {
                p = random.nextInt(999999999) + 2;
            } while (!check(p));
            q = (p - 1) / 2;
            if (check(q)) {
                break;
            }
        }

        do {
            g = random.nextInt(p - 3) + 2;
        } while (exponentiation(g, q, p).intValue() == 1);




        //p = 11;
        //g = 9;
        //q = 5;

        System.out.print("p = " + p);
        System.out.print(", q = " + q);
        System.out.println(", g = " + g);

        X1 = random.nextInt(p) + 1;
        //X1 = 8;
        X2 = random.nextInt(p ) + 1;
        //X2 = 7;
        System.out.println("X1 = " + X1 + " , X2 = " + X2);

        Y1 = exponentiation(g, X1, p).intValue();
        Y2 = exponentiation(g, X2, p).intValue();
        System.out.println("Y1 = " + Y1 + " , Y2 = " + Y2);

        Z1 = exponentiation(Y2, X1, p).intValue();
        Z2 = exponentiation(Y1, X2, p).intValue();
        System.out.println("Z1 = " + Z1 + " , Z2 = " + Z2);

        return 0;
    }

    public static BigInteger giant_Step(int a, int y, int p)
    {
        //if (y >= p) {
         //   return -1;
        //}
        //int x1 = 0, x2 = 0; //x1 - j, x2 - i
        //int m = (int)Math.sqrt(p) + 1;
        //int k = m;

        BigInteger x1, x2;
        BigInteger m, k;

        x1 = BigInteger.valueOf(0);
        x2 = BigInteger.valueOf(0);

        m = BigInteger.valueOf((int)Math.sqrt(p) + 1);
        k = m;


        BigInteger[] A = new BigInteger[m.intValue()];
        BigInteger[] B = new BigInteger[m.intValue()];
        for (int i = 0; i < m.intValue(); i++) {
            A[i] = exponentiation(y * (exponentiation(a, i, p)).intValue(), 1, p);
            B[i] = exponentiation(a,(1 + i) * m.intValue(), p);
        }

        HashMap <BigInteger,Integer> hashMap = new HashMap();
        for (int i = 0; i < m.intValue(); i++) {
            hashMap.put(A[i], i);
        }
        for (int j = 0; j < k.intValue(); j++) {
            if (hashMap.get(B[j]) != null) {
                x1 = BigInteger.valueOf(hashMap.get(B[j])) ;
                x2 = BigInteger.valueOf(j);
                break;
            }


        }

        BigInteger temp = BigInteger.valueOf(1);
        x2 = x2.add(temp);

        return x2.multiply(m).subtract(x1);

    }

    public static boolean check(long p)
    {
        int b = (int)Math.pow(p, 0.5);
        for (int i = 2; i <= b; ++i) {
            if ((p % i) == 0) {
                return false;
            }
        }

        return true;
    }
}
