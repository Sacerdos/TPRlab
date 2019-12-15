package me.indychkov;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static java.lang.Math.pow;
public class MAI {

    public static void main(String[] args) {
        BufferedReader fileReader = null;
        final String DELIMITER = ";";
        try {
            ArrayList<String[]> stringsFromFile = new ArrayList<String[]>(5);
            Double[][] wagn = new Double[6][5];

            String line = "";
            for (int i = 1; i <= 6; i++) {
                stringsFromFile.clear();
                fileReader = new BufferedReader(new FileReader("src\\main\\java\\me\\indychkov\\data" + i + ".csv"));
                while ((line = fileReader.readLine()) != null) {
                    String[] tokens = line.split(DELIMITER);
                    stringsFromFile.add(tokens);

                }
                Double[][] st = new Double[stringsFromFile.size()][5];
                Double[] formula1 = new Double[stringsFromFile.size()];
                Double[] f4 = new Double[stringsFromFile.size()];
                Double[] f5 = new Double[stringsFromFile.size()];


                for (int i1 = 0; i1 < stringsFromFile.size(); i1++) {
                    for (int j1 = 0; j1 < 5; j1++) {
                        if (stringsFromFile.get(i1)[j1].indexOf("/") != -1) {
                            String[] b = stringsFromFile.get(i1)[j1].split("/");
                            st[i1][j1] = (Double.parseDouble(b[0])) / (Double.parseDouble(b[1]));
                        } else {
                            st[i1][j1] = Double.parseDouble(stringsFromFile.get(i1)[j1]);
                        }
                    }
                }
                System.out.println("Матрица  " + i);
                for (int j = 0; j < stringsFromFile.size(); j++) {
                    for (int k = 0; k < stringsFromFile.get(j).length; k++) {
                        System.out.print(stringsFromFile.get(j)[k] + " \t " + "| ");
                    }
                    System.out.println("");
                }

                System.out.print("Относительная ценность:\n");
                double otnSum = 0;
                for (int v = 0; v < 5; v++) {
                    double temp = pow((st[v][0] * st[v][1] * st[v][2] * st[v][3] * st[v][4]), 0.2);
                    formula1[v] = temp;
                    otnSum += temp;
                    System.out.println(temp + " | ");

                }
                System.out.print("\n\nНормирующий коэффициент\n");
                System.out.println(otnSum);


                System.out.print("\n\nВажность приоритетов\n");
                for (int v = 0; v < 5; v++) {
                    wagn[i - 1][v] = formula1[v] / otnSum;
                    System.out.print(wagn[i - 1][v] + " \t " + "| ");
                }
                System.out.println("");

                System.out.print("\n\nСумма каждого столбца матрицы суждений\n");

                for (int v = 0; v < 5; v++) {
                    f4[v] = ((st[0][v] + st[1][v] + st[2][v] + st[3][v] + st[4][v]));
                    System.out.print(f4[v] + " \t " + "| ");
                }
                System.out.println("");

                System.out.print("\n\nПропорциональность предпочтений\n");

                for (int v = 0; v < 5; v++) {
                    f5[v] = f4[v] * wagn[i - 1][v];
                    System.out.print(f5[v] + " \t " + "| ");
                }
                System.out.println("");

                System.out.print("\n\nСумма пропорциональности предпочтений\n");
                double f6 = f5[0] + f5[1] + f5[2] + f5[3] + f5[4];
                System.out.println(f6);


                System.out.print("\n\nИндекс согласованности\n");
                int n = 5;
                double f7 = (f6 - n) / (n - 1);
                System.out.println(f7);


                System.out.print("\n\nОтношение согласованности\n");
                double SI = 1.12;
                double f8 = f7 / SI;
                System.out.println(f8 + "\n\n");
            }

            Double result[] = new Double[5];
            for (int i = 0; i < 5; i++) {
                result[i] = 0.0;
                for (int j = 0; j < 5; j++) {
                    result[i] += wagn[0][j] * wagn[j + 1][i];
                }
                System.out.print("A" + (i + 1) + " - ");
                System.out.println(result[i]);
            }
            String res = "A1 - " + result[0].toString();
            for (int i = 1; i < 5; i++) {
                if (result[i - 1] < result[i]) {
                    res = "A" + (i + 1);
                }
            }
            System.out.print("\nЛучшая альтернатива - " + res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
