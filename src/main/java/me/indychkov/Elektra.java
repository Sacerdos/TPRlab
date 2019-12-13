package me.indychkov;

import java.util.HashSet;
import java.util.Set;

public class Elektra {
    private final static int ALT_COUNT = 9;
    private final static int CRIT_COUNT = 5;
    private static double[][] mData = new double[ALT_COUNT][CRIT_COUNT];
    private static double[] weight = {5, 2, 4, 5, 3};
    private static int mCounter = 0;
    private static String[][] mComparision = new String[ALT_COUNT][ALT_COUNT];

    private static void init(double... args) {
        for (int i = 0; i < args.length; i++) {
            mData[mCounter][i] = args[i];
        }
        mCounter++;
    }

    private static String compareElektra(double[] alt_a, double[] alt_b, boolean toRestrict, double bound) {
        double[] el = new double[5];
        el[0] = alt_a[0] < alt_b[0] ? 1 : (alt_a[0] > alt_b[0] ? -1 : 0);
        el[1] = alt_a[1] < alt_b[1] ? 1 : (alt_a[1] > alt_b[1] ? -1 : 0);
        el[2] = alt_a[2] > alt_b[2] ? 1 : (alt_a[2] < alt_b[2] ? -1 : 0);
        el[3] = alt_a[3] > alt_b[3] ? 1 : (alt_a[3] < alt_b[3] ? -1 : 0);
        el[4] = alt_a[4] < alt_b[4] ? 1 : (alt_a[4] > alt_b[4] ? -1 : 0);
        double resP = 0;
        double resN = 0;
        /*for (int i = 0; i < el.length; i++) {
            System.out.print(el[i]+" ");
        }*/
        //System.out.println("");
        for (int i = 0; i < el.length; i++) {
            if (el[i] < 0) {
                //System.out.println(i + " ---  " + weight[i]*el[i]);
                resN += weight[i] * Math.abs(el[i]);
            } else {
                //System.out.println(i + " +++  " + weight[i]*el[i]);
                resP+=weight[i]*el[i];
            }
        }
        //      System.out.println(resP + "   " + resN);
        if(resP/resN<1){
            return "-";
        } else{
            return String.valueOf(resP/resN>=bound?resP/resN:"-");
        }
    }

    public static void main(String[] args) {
        init(5,4,4,10,3);//Турция
        init(5,4,8,10,6);//Египет
        init(15,4,12,10,6);//Франция
        init(10,6,12,5,3);//Тайланд
        init(5,2,8,5,3);//Россия
        init(10,6,4,5,3);//Куба
        init(15,6,8,15,6);//Австралия
        init(10,4,8,10,6);//Испания
        init(5,4,12,15,6);//Италия
        for (int i = 0; i < mComparision.length; i++) {
            for (int j = 0; j < mComparision[0].length; j++) {
                if(i==j){
                    mComparision[i][j]="X";
                } else{
                    //System.out.println((i+1) + " AND  " + (j+1));
                    mComparision[i][j]=compareElektra(mData[i], mData[j], false, 0);
                }
            }
        }
        for (int i = 0; i < mComparision.length; i++) {
            for (int j = 0; j < mComparision[0].length; j++) {
                System.out.print(mComparision[i][j] + "\t\t\t");
            }
            System.out.println("");
        }
        System.out.println("\n\n\n");
        for (int i = 0; i < mComparision.length; i++) {
            for (int j = 0; j < mComparision[0].length; j++) {
                if(i==j){
                    mComparision[i][j]="X";
                } else{
                    //System.out.println((i+1) + "   " + (j+1));
                    mComparision[i][j]=compareElektra(mData[i], mData[j], true, 2);
                }
            }
        }
        for (int i = 0; i < mComparision.length; i++) {
            for (int j = 0; j < mComparision[0].length; j++) {
                System.out.print(mComparision[i][j] + "\t\t\t");
            }
            System.out.println("");
        }
    }
}
