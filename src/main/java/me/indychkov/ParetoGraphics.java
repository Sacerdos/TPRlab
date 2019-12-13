package me.indychkov;

import java.util.ArrayList;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class ParetoGraphics {
    private ArrayList<Integer> paretoIndexes = new ArrayList<>();
    private double[][] mData;

    public ParetoGraphics(double[][] data, Set<Integer> paretoAlt) {
        paretoIndexes.addAll(paretoAlt);
        mData = new double[paretoIndexes.size() + 2][data[0].length];
        int a = 0;
        for (int i = 0; i < data.length + 1; i++) {
            if (paretoIndexes.contains(i)) {
                for (int j = 0; j < data[0].length; j++) {
                    mData[a][j] = data[i - 1][j];
                }
                a++;
            }
        }
        for (int i = 0; i < 5; i++) {
                mData[paretoIndexes.size()][i] = Math.max(mData[0][i], Math.max(mData[1][i], Math.max(mData[2][i], mData[3][i])));
        }
        mData[5][0] = -1;
        mData[5][1] = -1;
        mData[5][2] = 1;
        mData[5][3] = 1;
        mData[5][4] = -1;
     /*   for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[0].length; j++) {
                System.out.print(mData[i][j] + "\t");
            }
            System.out.println("");
        }*/
        XYSeries series1 = new XYSeries("1 Турция");
        XYSeries series2 = new XYSeries("4 Тайланд");
        XYSeries series3 = new XYSeries("5 Россия");
        XYSeries series4 = new XYSeries("9 Италия");
        for (int i = 0; i < 5; i++) {
            if (mData[5][i] == -1) {
                series1.add(i + 1, 5-mData[0][i] * 5.0 / mData[4][i]);
                series2.add(i + 1, 5-mData[1][i] * 5.0 / mData[4][i]);
                series3.add(i + 1, 5-mData[2][i] * 5.0 / mData[4][i]);
                series4.add(i + 1, 5-mData[3][i] * 5.0 / mData[4][i]);

            } else {
                series1.add(i + 1, mData[0][i] * 5.0 / mData[4][i]);
                series2.add(i + 1, mData[1][i] * 5.0 / mData[4][i]);
                series3.add(i + 1, mData[2][i] * 5.0 / mData[4][i]);
                series4.add(i + 1, mData[3][i] * 5.0 / mData[4][i]);
            }

        }
        XYDataset xyDataset = new XYSeriesCollection();
        ((XYSeriesCollection) xyDataset).addSeries(series1);
        ((XYSeriesCollection) xyDataset).addSeries(series2);
        ((XYSeriesCollection) xyDataset).addSeries(series3);
        ((XYSeriesCollection) xyDataset).addSeries(series4);
        JFreeChart chart = ChartFactory
                .createXYLineChart("Парето", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("Парето");
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(400, 300);
        frame.show();

    }
}
