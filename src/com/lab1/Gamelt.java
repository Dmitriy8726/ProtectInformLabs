package com.lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static com.lab1.Criptogr.check;
import static com.lab1.Criptogr.exponentiation;

public class Gamelt {

    public static void generation() throws FileNotFoundException {

        Random random = new Random();
        int temp, temp1;
        int n, m;
        long P, Q;
        do {
            P = random.nextInt(999999999) + 2;
        } while (check(P) == false);
        do {
            Q = random.nextInt(999999999) + 2;
        } while (check(Q) == false);

        long f = (Q - 1) * (P - 1);
        long N = P * Q;

        long d, c;
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
        if (resultEcklid[1] < 0) {
            resultEcklid[1] += f;
        }
        c = resultEcklid[1];
        Scanner in = new Scanner(new File("resource\\graf.txt"));
        temp = in.nextInt();
        if (!(temp > 0 && temp < 1001)) {
            return;
        }
        n = temp;
        temp = in.nextInt();
        if (!(temp > 0 && temp <= (n * n))) {
            return;
        }
        m = temp;

        int[][] G = new int[n][n];
        for (int i = 0; i < m; i++) {
            temp = in.nextInt() - 1;
            temp1 = in.nextInt() - 1;
            G[temp][temp1] = 1;
            G[temp1][temp] = 1;
        }

        ArrayList<Integer> Gam = new ArrayList<>();
        ArrayList<Integer> Gam1 = new ArrayList<>();
        while (in.hasNextInt()) {
            temp = in.nextInt();
            Gam.add(temp);
            Gam1.add(temp);
        }

        System.out.println("N:" + N + " D:" + d );
        System.out.println("G:" );
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(G[i][j] + " ");
            }
            System.out.println();
        }

        int[][] H = G;
        int pp = random.nextInt(n - 1) + 1;
        for (int i = 0; i < n; i++) {
            temp = H[i][0];
            H[i][0] = H[i][pp];
            H[i][pp] = temp;
        }

        for (int i = 0; i < n; i++) {
            temp = H[0][i];
            H[0][i] = H[pp][i];
            H[pp][i] = temp;
        }

        System.out.println("H:" );
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(H[i][j] + " ");
            }
            System.out.println();
        }

        try {
            temp = Gam.get(pp);
            Gam.set(pp, Gam.get(0));
            Gam.set(0, temp);
            Gam.set(Gam.size() - 1, temp);
        } catch (Exception e) {
            return;
        }

        int[][] H1 = H;
        String str;
        int r;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                r = random.nextInt(100000);
                str = r + Integer.toString(H[i][j]);
                H1[i][j] = Integer.valueOf(str);
            }
        }

        System.out.println("H1: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(H1[i][j] + " ");
            }
            System.out.println();
        }

        long[][] F =  new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                F[i][j] = exponentiation(H1[i][j], d, N).longValue();
            }
        }

        System.out.println("F: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(F[i][j] + " ");
            }
            System.out.println();
        }

        //1 вопрос , ответ
        for (int i = 0; i < Gam.size() - 1; i++) {
            System.out.print(((Gam1.get(i) - 1) + 1) + " " + ((Gam1.get(i + 1) - 1) + 1) + " :");
            System.out.print(exponentiation(F[Gam.get(i) - 1][Gam.get(i + 1) - 1], c, N).longValue() + " ");
            System.out.println(exponentiation(H1[Gam.get(i) - 1][Gam.get(i + 1) - 1], d, N));
        }

        long[][] F1 = F;
        System.out.println("F1: ");
        //2 вопрос ответ
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                F1[i][j] = exponentiation(F1[i][j], c, N).longValue();
            }
        }

        for (int i = 0; i < n; i++) {
            temp = (int)F1[0][i];
            F1[0][i] = F1[pp][i];
            F1[pp][i] = temp;
        }

        for (int i = 0; i < n; i++) {
            temp = (int)F1[i][0];
            F1[i][0] = F1[i][pp];
            F1[i][pp] = temp;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (F1[i][j] % 2 == 0) {
                    F1[i][j] = 0;
                } else {
                    F1[i][j] = 1;
                }
                System.out.print(F1[i][j] + " ");
            }
            System.out.println();
        }






    }
}
