package me.indychkov;

import java.util.*;

public class Pareto {
    private final static int ALT_COUNT = 9;
    private final static int CRIT_COUNT = 5;
    private static double[][] mData = new double[ALT_COUNT][CRIT_COUNT];
    private static int mCounter = 0;
    private static String[][] mComparision = new String[ALT_COUNT][ALT_COUNT];

    private static void init(double... args) {
        for (int i = 0; i < args.length; i++) {
            mData[mCounter][i] = args[i];
        }
        mCounter++;
    }
    private static Set<Integer> paretoValues = new HashSet<Integer>();
    private static String comparisionAlt(double[] alt_a, double[] alt_b, int a, int b) {
        int similar_count = 0;
        String result = Integer.toString(a+1);
        for (int i = 0; i < alt_a.length; i++) {
            if (i == 2) {
                i = 4;
            }
            if (alt_a[i] > alt_b[i]) {
                result = "н";
            } else {
                if (alt_a[i] == alt_b[i]) {
                    similar_count++;
                }
            }
        }
        for (int i = 2; i < 4; i++) {
            if (alt_a[i] < alt_b[i]) {
                result = "н";
            } else {
                if (alt_a[i] == alt_b[i]) {
                    similar_count++;
                }
            }
        }
        if (similar_count == 5) {
            result = "н";
        }
        similar_count = 0;
        if (result.equals("н")) {
            result = Integer.toString(b+1);
            for (int i = 0; i < alt_a.length; i++) {
                if (i == 2) {
                    i = 4;
                }
                if (alt_a[i] < alt_b[i]) {
                    result = "н";
                } else {
                    if (alt_a[i] == alt_b[i]) {
                        similar_count++;
                    }
                }
            }
            for (int i = 2; i < 4; i++) {
                if (alt_a[i] > alt_b[i]) {
                    result = "н";
                } else {
                    if (alt_a[i] == alt_b[i]) {
                        similar_count++;
                    }
                }
            }
            if (similar_count == 5) {
                result = "н";
            }
        }

        return result;
    }

    public static void main(String[] args) {
        init(40, 3.5, 3, 3, 0);//Турция
        init(35, 4.5, 4, 3, 2600);//Египет
        init(80, 4, 5, 4, 2600);//Франция
        init(50, 9, 4, 3, 0);//Тайланд
        init(45, 2, 3, 3, 0);//Россия
        init(70, 13, 3, 3, 0);//Куба
        init(95, 17.5, 4, 5, 5500);//Австралия
        init(50, 5, 4, 4, 2600);//Испания
        init(45, 4, 5, 5, 2600);//Италия
        for (int i = 0; i < mComparision.length; i++) {
            for (int j = i; j < mComparision[0].length; j++) {
                mComparision[i][j] = "X";
            }
        }
        for (int i = 0; i < mComparision.length; i++) {
            for (int j = 0; j < i; j++) {
                mComparision[i][j] = comparisionAlt(mData[i], mData[j], i, j);
            }
        }
        for (int i = 0; i < mComparision.length; i++) {
            for (int j = 0; j < mComparision[0].length; j++) {
                System.out.print(mComparision[i][j] + "\t");
            }
            System.out.println("");
        }

        for (int i = 0; i < mComparision.length; i++) {
            for (int j = 0; j < mComparision[0].length; j++) {
                if(!mComparision[i][j].equals("X") && !mComparision[i][j].equals("н")){
                    for (int k = 0; k < mData[0].length; k++) {
                        paretoValues.add(Integer.valueOf(mComparision[i][j]));
                    }
                }

            }
        }
        for (int paretoIndex:
             paretoValues) {

            for (int i = 0; i < mData[0].length; i++) {
                System.out.print(mData[paretoIndex-1][i]+" ");
            }
            System.out.println("");
        }

        ParetoBorders borders = new ParetoBorders(mData, paretoValues);
        ParetoGraphics paretoGraphics = new ParetoGraphics(mData, paretoValues);
        borders.setBorders(-1, 5, -1, -1, -1, -1, -1, 4, -1, -1);
    }
}

