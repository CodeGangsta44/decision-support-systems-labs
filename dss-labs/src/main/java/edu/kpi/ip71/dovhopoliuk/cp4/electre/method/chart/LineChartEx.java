package edu.kpi.ip71.dovhopoliuk.cp4.electre.method.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

public class LineChartEx extends JFrame {

    private XYDataset dataset;
    private String yLabel;
    private String xLabel;
    private String title;

    public LineChartEx() {
        initUI();
    }

    public LineChartEx(XYDataset dataset, String yLabel, String xLabel, String title) {
        this.dataset = dataset;
        this.yLabel = yLabel;
        this.xLabel = xLabel;
        this.title = title;
        initUI();
    }

    private void initUI() {

        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(new Color(26, 26, 26));
        chart.setBackgroundPaint(new Color(26, 26, 26));
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, new Color(84, 183, 87));
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(new Color(40, 40, 40));

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.WHITE);

        setAxisFontColor(plot.getDomainAxis(), Color.WHITE);
        setAxisFontColor(plot.getRangeAxis(), Color.WHITE);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.WHITE);

        chart.getLegend().setFrame(BlockBorder.NONE);

        TextTitle title = new TextTitle(this.title,
                new Font("Serif", Font.BOLD, 18));

        title.setPaint(Color.WHITE);

        chart.setTitle(title);

        return chart;
    }

    private void setAxisFontColor(Axis axis, Color fontColor) {
        if (!fontColor.equals(axis.getLabelPaint()))
            axis.setLabelPaint(fontColor);
        if (!fontColor.equals(axis.getTickLabelPaint()))
            axis.setTickLabelPaint(fontColor);
    }
}
