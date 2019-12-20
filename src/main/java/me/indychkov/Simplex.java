package me.indychkov;


import java.io.*;
import java.util.Arrays;

public class Simplex {
    private static int mScannedRows = -1;
    private static double[][] simplexTable;
    private static double[][] simplexTableForDual;
    private static String[][] xSimplexTable;
    private static String[] mBounds;
    private static int sumBounds;

    public static void main(String[] args) throws FileNotFoundException {
        scanFunctions();
        System.out.println("Введенные данные в виде таблицы:");
        printTable(simplexTable, xSimplexTable);
        System.out.println("Приступаем к алгоритму:");
        simplexMethod();
        ReverseMethod();
    }

    private static void scanFunctions() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    new File("src/main/java/me/indychkov/simplexEntry.txt")));
            String line = reader.readLine();
            String mFunction = line;
            while (line != null) {
                line = reader.readLine();
                mScannedRows++;
            }
            mBounds = new String[mScannedRows];
            reader.close();
            reader = new BufferedReader(new FileReader(
                    new File("src/main/java/me/indychkov/simplexEntry.txt")));
            line = reader.readLine();
            int boundCounter = 0;
            while (line != null && boundCounter < mScannedRows) {
                line = reader.readLine();
                mBounds[boundCounter++] = line;
            }
            System.out.println("Функция: " + mFunction);
            System.out.println("Ограничения: ");
            for (String mBound : mBounds) {
                System.out.println(mBound + " ");
            }
            int xInLineCounter = 0;
            int maxXCounter = 0;
            for (int i = 0; i < mScannedRows; i++) {
                for (int j = 0; j < 10; j++) {
                    if (mBounds[i].contains("x" + j)) {
                        xInLineCounter++;
                    }
                }
                maxXCounter = Math.max(xInLineCounter, maxXCounter);
                xInLineCounter = 0;
            }
            int rowCount = mScannedRows + 1;
            int columnCount = maxXCounter + 1;
            simplexTable = new double[rowCount][columnCount];
            simplexTableForDual = new double[rowCount][columnCount];
            xSimplexTable = new String[rowCount][columnCount];
            for (int i = 0; i < mBounds.length; i++) {
                int symbolPosition = 0;
                for (int j = 0; j < mBounds[i].length(); j++) {
                    if (mBounds[i].charAt(j) == '-' ||
                            mBounds[i].charAt(j) == '+') {
                        symbolPosition = j;
                    }
                    if (mBounds[i].charAt(j) == 'x') {
                        String temp = mBounds[i].substring(symbolPosition, j);
                        if (temp.equals("") || temp.equals("+") || temp.equals("-")) {
                            temp = "1";
                        }
                        simplexTable[i][Integer.parseInt(String.valueOf(mBounds[i].charAt(j + 1))) - 1] = Integer.parseInt(temp);
                        simplexTableForDual[i][Integer.parseInt(String.valueOf(mBounds[i].charAt(j + 1))) - 1] = Integer.parseInt(temp);
                    }
                    if (mBounds[i].charAt(j) == '=') {
                        simplexTable[i][simplexTable[i].length - 1] = Integer.parseInt(mBounds[i].substring(j + 1));
                        simplexTableForDual[i][simplexTableForDual[i].length - 1] = Integer.parseInt(mBounds[i].substring(j + 1));
                    }
                }
            }
            for (int i = 0; i < mBounds.length; i++) {
                if (mBounds[i].contains("<=")) {
                    mBounds[i] = mBounds[i].substring(0, mBounds[i].indexOf("<")) +
                            "+x" + (maxXCounter + i + 1) +
                            mBounds[i].substring(mBounds[i].indexOf("<") + 1);
                } else {
                    mBounds[i] = mBounds[i].substring(0, mBounds[i].indexOf(">")) +
                            "-x" + (maxXCounter + i + 1) +
                            mBounds[i].substring(mBounds[i].indexOf(">") + 1);
                }
                sumBounds = maxXCounter;
            }
            System.out.println("\nВ каноничном виде: ");
            for (int i = 0; i < mBounds.length; i++) {
                System.out.println(mBounds[i]);
            }
            System.out.println();
            for (int i = 0, symbolPosition = 0, counter = 0; i < mFunction.length(); i++) {
                if (mFunction.charAt(i) == '-' || mFunction.charAt(i) == '+' || mFunction.charAt(i) == '=') {
                    symbolPosition = i;
                }
                if (mFunction.charAt(i) == 'x' && mFunction.charAt(i - 1) != '(') {
                    simplexTable[simplexTable.length - 1][counter] = -1.0 * Integer.parseInt(mFunction.substring(symbolPosition + 1, i));
                    simplexTableForDual[simplexTableForDual.length - 1][counter] = -1.0 * Integer.parseInt(mFunction.substring(symbolPosition + 1, i));
                    counter++;
                }
            }
            for (int i = 0, xCounter = 1; i < xSimplexTable.length; i++) {
                for (int j = 0; j < xSimplexTable[0].length; j++) {
                    if (j == 0 && i == 0) xSimplexTable[i][j] = " ";
                    else if (i == 0) {
                        xSimplexTable[i][j] = (-1 * (int) simplexTableForDual[simplexTableForDual.length - 1][j - 1]) + "x" + xCounter;
                        xSimplexTable[i][j] = (-1 * (int) simplexTableForDual[simplexTableForDual.length - 1][j - 1]) + "x" + xCounter;
                        xCounter++;
                    } else {
                        if (j == 0) {
                            xSimplexTable[i][j] = "x" + xCounter;
                            xCounter++;
                        }
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void printTable(double[][] simplexTable, String[][] xSimplexTable) {
        for (int i = 0; i < xSimplexTable.length; i++) {
            for (int j = 0; j < xSimplexTable[i].length + 1; j++) {

                if (i == 0 || j == 0) {
                    if (i == 0 && j == xSimplexTable[i].length) {
                        System.out.format("%10s|", "A0");
                    } else {
                        System.out.format("%10s|", xSimplexTable[i][j]);
                    }
                } else {
                    System.out.format("%10.2f|", simplexTable[i - 1][j - 1]);
                }
            }
            System.out.println();
        }
        System.out.format("%10s|", "f");
        for (double arg :
                simplexTable[simplexTable.length - 1]) {
            System.out.format("%10.2f|", arg);
        }
        System.out.println();
    }

    private static void simplexMethod() {
        boolean isMinus = false;
        for (double arg :
                simplexTable[simplexTable.length - 1]) {
            if (arg < 0) isMinus = true;
        }
        while (isMinus) {
            printTable(simplexTable, xSimplexTable);
            int allowColumn = 0;
            double minElementColumn = Double.MAX_VALUE;
            for (int i = 0; i < simplexTable[0].length; i++) {
                if (simplexTable[simplexTable.length - 1][i] < minElementColumn) {
                    minElementColumn = simplexTable[simplexTable.length - 1][i];
                    allowColumn = i;
                }
            }
            double minCoefficientResult = Double.MAX_VALUE;
            int allowRow = 0;
            for (int i = 0; i < simplexTable.length - 1; i++) {
                if (simplexTable[i][allowColumn] > 0) {
                    double temp = simplexTable[i][simplexTable[0].length - 1] / simplexTable[i][allowColumn];
                    if (temp < minCoefficientResult) {
                        minCoefficientResult = temp;
                        allowRow = i;
                    }
                }
            }
            double allowElement = simplexTable[allowRow][allowColumn];
            System.out.format("Не все относительные оценки неотрицательны\nРазрешающая строка: %d\nРазрешающий столбец:  %d\nРазрешающий элемент:  %5.2f\n",
                    (allowRow + 1), (allowColumn + 1), allowElement);
            double[][] tempSimplexTable = new double[simplexTable.length][simplexTable[0].length];
            String temp = xSimplexTable[allowRow + 1][0];
            xSimplexTable[allowRow + 1][0] = xSimplexTable[0][allowColumn + 1];
            xSimplexTable[0][allowColumn + 1] = temp;
            tempSimplexTable[allowRow][allowColumn] = 1 / allowElement;
            for (int i = 0; i < tempSimplexTable[0].length; i++) {
                if (i != allowColumn) {
                    tempSimplexTable[allowRow][i] = simplexTable[allowRow][i] / allowElement;
                }

            }
            for (int i = 0; i < tempSimplexTable.length; i++) {
                if (i != allowRow) {
                    tempSimplexTable[i][allowColumn] = -1.0 * (simplexTable[i][allowColumn] / allowElement);
                }
            }
            for (int i = 0; i < tempSimplexTable.length; i++) {
                for (int j = 0; j < tempSimplexTable[0].length; j++) {
                    if (i != allowRow && j != allowColumn) {
                        tempSimplexTable[i][j] = (simplexTable[i][j] * allowElement - simplexTable[i][allowColumn] * simplexTable[allowRow][j]) / allowElement;
                    }
                }
            }
            for (int i = 0; i < tempSimplexTable.length; i++) {
                for (int j = 0; j < tempSimplexTable[0].length; j++) {
                    simplexTable[i][j] = tempSimplexTable[i][j];
                }
            }
            isMinus = false;
            for (double arg :
                    simplexTable[simplexTable.length - 1]) {
                if (arg < 0) {
                    isMinus = true;
                    break;
                }
            }
        }
        printTable(simplexTable, xSimplexTable);
        System.out.format("Все относительные оценки неотрицательны -> получен ответ %7.2f",
                simplexTable[simplexTable.length - 1][simplexTable[simplexTable.length - 1].length - 1]);
    }

    private static void ReverseMethod() {
        System.out.println("\nПервая теорема двойственност\n");
        String[] xBase = new String[xSimplexTable.length - 1];
        int[] splitByXCounter = new int[xSimplexTable.length - 1];
        for (int i = 1; i < xSimplexTable.length; i++) {
            String[] splitByX = xSimplexTable[i][0].split("x");
            xBase[i - 1] = "x" + splitByX[1];
            splitByXCounter[i - 1] = Integer.parseInt(splitByX[1]);
        }
        printTable(simplexTable, xSimplexTable);
        printTable(simplexTableForDual, xSimplexTable);
        System.out.print("Базисными переменными являются: ");
        for (int i = 0; i < xBase.length; i++) {
            if (i != xBase.length - 1) {
                System.out.print(xBase[i] + ", ");
            } else {
                System.out.print(xBase[i]);
            }
        }
        System.out.println();
        double[][] matrixD = new double[mBounds.length][xBase.length];
        //splitByXCounter=new int[]{4,5,6};
        for (int splitCount = 0; splitCount < splitByXCounter.length; splitCount++) {
            if (splitByXCounter[splitCount] <= sumBounds) {
                for (int i = 0; i < matrixD[splitCount].length; i++) {
                    matrixD[i][splitCount] = simplexTableForDual[i][splitByXCounter[splitCount] - 1];
                }
            } else {
                for (int i = 0; i < matrixD[splitCount].length; i++) {
                    if (i + 1 == splitByXCounter[splitCount] - sumBounds) {
                        matrixD[splitCount][i] = 1;
                    }
                }
            }
        }
        printMatrix(matrixD);
        double[][] reverseD = invert(matrixD);
        System.out.println("Обратная матрица:");
        printMatrix(reverseD);
        double[] vectorBase = new double[xBase.length];

        for (int i = 1; i < xSimplexTable.length; i++) {
            String[] temp = xSimplexTable[i][0].split("x");
            if (temp[0].equals("")) {
                vectorBase[i - 1] = 0;
            } else {
                vectorBase[i - 1] = Float.parseFloat(temp[0]);
            }
        }

        System.out.println("\nВектор коэффициентов при базисных переменных ");
        System.out.println(Arrays.toString(vectorBase));

        double[] yVec = multipleVectorMatrix(vectorBase, reverseD);
        System.out.print("y: ");
        System.out.println(Arrays.toString(yVec));
        double[] bVec = new double[mBounds.length];
        for (int i = 0; i < mBounds.length; i++) {
            String[] temp = mBounds[i].split("=");
            bVec[i] = Double.parseDouble(temp[1]);
        }

        System.out.println(Arrays.toString(bVec));
        double min = multipleVecs(bVec, yVec);
        System.out.print("Минимальное значение целевой функции двойственной задачи\nmin = (y, b) = " + min);
        System.out.print("\nОтвет = ");
        System.out.printf("%.2f", simplexTable[simplexTable.length - 1][simplexTable[0].length - 1]);
    }

    static double[] multipleVectorMatrix(double[] vector, double[][] matrix) {
        double[] resultVec = new double[matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            double sum = 0;
            for (int j = 0; j < vector.length; j++) {
                sum += vector[j] * matrix[j][i];
            }
            resultVec[i] = sum;
        }
        return resultVec;
    }

    static double multipleVecs(double[] vec1, double[] vec2) {
        double res = 0;
        for (int i = 0; i < vec1.length; i++) {
            res += vec1[i] * vec2[i];
        }
        return res;
    }

    private static double[][] invert(double[][] matrixD) {
        double temp;
        int size = matrixD.length;
        double[][] E = new double[size][size];


        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                E[i][j] = 0f;

                if (i == j)
                    E[i][j] = 1f;
            }

        for (int k = 0; k < size; k++) {
            //printMatrix(matrixD);
            temp = matrixD[k][k];

            for (int j = 0; j < size; j++) {
                matrixD[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < size; i++) {
                temp = matrixD[i][k];

                for (int j = 0; j < size; j++) {
                    matrixD[i][j] -= matrixD[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int k = size - 1; k > 0; k--) {
            //printMatrix(matrixD);
            for (int i = k - 1; i >= 0; i--) {
                temp = matrixD[i][k];

                for (int j = 0; j < size; j++) {
                    matrixD[i][j] -= matrixD[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                matrixD[i][j] = E[i][j];
        }
        return matrixD;
    }

    private static void printMatrix(double[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.printf("%5s", arr[i][j] + "|");
            }
            System.out.println();
        }
    }

}

