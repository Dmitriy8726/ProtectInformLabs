package com.lab1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import static com.lab1.Criptogr.check;

public class Pocker {

    private static final int MAX_KARDS = 52;

    public static void Grafh(HashMap<Integer, int[]> map, int n, long[] K) throws IOException {
        JFrame myWindow = new JFrame("Pocker");
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel picLabel1, picLabel2, picLabel3 ;
        BufferedImage myPicture;
        int card1, card2;
        String str;
        for (int i = 1; i <= n; i++) {
            picLabel1 = new JLabel("Player " + i);
            card1 = map.get(i)[0];
            card2 = map.get(i)[1];
            myPicture = ImageIO.read(new File("resource\\cards\\" + K[card1] + ".png"));
            picLabel2 = new JLabel(new ImageIcon(myPicture));
            myPicture = ImageIO.read(new File("resource\\cards\\" + K[card2] + ".png"));
            picLabel3 = new JLabel(new ImageIcon(myPicture));
            panel.add(picLabel1);
            panel.add(picLabel2);
            panel.add(picLabel3);
            myWindow.setContentPane(panel);
        }

        picLabel1 = new JLabel("Table");
        panel.add(picLabel1);
        for (int i = n * 2; i < n * 2 + 5; i++) {
            myPicture = ImageIO.read(new File("resource\\cards\\" + K[i] + ".png"));
            picLabel1 = new JLabel(new ImageIcon(myPicture));
            panel.add(picLabel1);
            myWindow.setContentPane(panel);
        }

        picLabel1 = new JLabel("Other");
        panel.add(picLabel1);
        for (int i = n * 2 + 5; i < MAX_KARDS; i++) {
            myPicture = ImageIO.read(new File("resource\\cards\\" + K[i] + ".png"));
            picLabel1 = new JLabel(new ImageIcon(myPicture));
            panel.add(picLabel1);
            myWindow.setContentPane(panel);
        }
        myWindow.setSize(1000, 500);
    }

    public static void generathion() throws IOException {
        int n = 2;
        long[] K = new long [MAX_KARDS];
        K[0] = 2;
        long p, q;
        long [] C = new long [n];
        long [] D = new long [n];
        long[] resultEcklid;
        Random random = new Random();
        while(true) {
            do {
                p = random.nextInt(999999998) + 2;
            } while (check(p) == false);
            q = (p - 1) / 2;
            if (check(q) == true) {
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            C[i] = random.nextInt((int)p - 2) + 1;
            resultEcklid = Criptogr.evklid(C[i], p - 1);
            while(resultEcklid[0] != 1) {
                C[i]--;
                resultEcklid = Criptogr.evklid(C[i], p - 1);
            }

            if (resultEcklid[1] < 0) {
                resultEcklid[1] += (p - 1);
            }
            D[i] = (int)resultEcklid[1];
        }

        for (int i = 1; i < MAX_KARDS; i++) {
            K[i] = K[i - 1] + 1;
        }

        for (int i = 0; i < n; i++) {
            K = shuffle(K, C[i], p);
        }

        HashMap<Integer, int[]> map = new HashMap<>();

        int t = 0;
        for (int i = 1; i < n + 1; i++) {
            map.put(i, new int[]{t, t + 1});
            t += 2;
        }


        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <=n; j++) {
                if (j != i) {
                    K[map.get(i)[0]] = Criptogr.exponentiation(K[map.get(i)[0]], D[j - 1], p).longValue();
                    K[map.get(i)[1]] = Criptogr.exponentiation(K[map.get(i)[1]], D[j - 1], p).longValue();
                }
            }
            K[map.get(i)[0]] = Criptogr.exponentiation(K[map.get(i)[0]], D[i - 1], p).longValue();
            K[map.get(i)[1]] = Criptogr.exponentiation(K[map.get(i)[1]], D[i - 1], p).longValue();
        }

        for (int i = n * 2; i < MAX_KARDS; i++) {
            for (int j = 1; j <= n; j++) {
                K[i] = Criptogr.exponentiation(K[i], D[j - 1], p).longValue();
            }
        }

        Grafh(map, n, K);

    }

    public static long[] shuffle(long[] K, long C, long P) {
        Random random = new Random();
        int j;
        long temp;
        for (int i = 0; i < MAX_KARDS; i++) {
            j = random.nextInt(MAX_KARDS);
            temp = K[i];
            K[i] = K[j];
            K[j] = temp;
        }
        for (int i = 0; i < MAX_KARDS; i++) {
            K[i] = Criptogr.exponentiation(K[i], C, P).longValue();
        }
        return K;
    }
}
