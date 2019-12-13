package me.indychkov;

import java.util.ArrayList;
import java.util.Set;

public class ParetoBorders {
    private ArrayList<Integer> paretoIndexes = new ArrayList<>();
    private double[][] mData;

    public ParetoBorders(double[][] data, Set<Integer> paretoAlt) {
        paretoIndexes.addAll(paretoAlt);
        mData = new double[paretoIndexes.size()][data[0].length];
        int a = 0;
        for (int i = 0; i < data.length + 1; i++) {
            if (paretoIndexes.contains(i)) {
                for (int j = 0; j < data[0].length; j++) {
                    mData[a][j] = data[i - 1][j];
                }
                a++;
            }
        }
    }

    public void setBorders(double a1, double b1, double c1, double d1, double e1,
                           double a2, double b2, double c2, double d2, double e2) {

        ArrayList<double[]> result = new ArrayList<>();

        for (int i = 0; i < mData.length; i++) {
            int k=0;
            System.out.println();
            if(((a1 != -1 && mData[i][0] <= a1) || a1 == -1)){
                k++;
                if(((a2 != -1 && mData[i][0] >= a2) || a2 == -1)){
                    k++;
                    if(((b1 != -1 && mData[i][1] <= b1) || b1 == -1)){
                        k++;
                        if(((b2 != -1 && mData[i][1] >= b2) || b2 == -1)){
                            k++;
                            if(((c1 != -1 && mData[i][2] <= c1) || c1 == -1)){
                                k++;
                                if(((c2 != -1 && mData[i][2] >= c2) || c2 == -1)){
                                    k++;
                                    if(((d1 != -1 && mData[i][3] <= d1) || d1 == -1)){
                                        k++;
                                        if(((d2 != -1 && mData[i][3] >= d2) || d2 == -1)){
                                            k++;
                                            if(((e1 != -1 && mData[i][4] <= e1) || e1 == -1)){
                                                k++;
                                                if(((e2 != -1 && mData[i][4] >= e2) || e2 == -1)){
                                                    k++;
                                                    result.add(mData[i]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (result == null) {
            System.out.println("Not found");
        } else{
            for (int i = 0; i < result.size(); i++) {
                for (int j = 0; j < result.get(i).length; j++) {
                    System.out.print(result.get(i)[j]+" ");
                }
                System.out.println("");
            }
        }
    }
}
