package com.m4rc310.rcp.mercado.livre.ml.parts;

import java.awt.Color;

import javax.inject.Inject;
import javax.swing.JPanel;

import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RectangleInsets;

public class PartChartUmidadeDensidade {
	@Inject
	public PartChartUmidadeDensidade() {

	}

//	@PostConstruct
	public void postConstruct(Composite parent) {

//		JFreeChart chart = createChart();

//		new ChartComposite(parent, SWT.NONE, chart);
		
		
//		ChartComposite frame = new ChartComposite(parent, SWT.NONE, chart, true);
//		frame.setDisplayToolTips(false);
//		frame.setHorizontalAxisTrace(true);
//		frame.setVerticalAxisTrace(true);

	}

	private static JFreeChart createChart() {
		XYDataset dataset1 = createDataset("Series 1", 100.0, new Minute(), 200);

		JFreeChart chart = ChartFactory.createTimeSeriesChart("Multiple Axis Demo 3", "Time of Day",
				"Primary Range Axis", dataset1, true, true, false);

		chart.setBackgroundPaint(Color.white);
		chart.setBorderVisible(true);
		chart.setBorderPaint(Color.BLACK);
		TextTitle subtitle = new TextTitle("Four datasets and four range axes.");
		chart.addSubtitle(subtitle);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, Color.black);

		// AXIS 2
		NumberAxis axis2 = new NumberAxis("Range Axis 2");
		axis2.setAutoRangeIncludesZero(false);
		axis2.setLabelPaint(Color.red);
		axis2.setTickLabelPaint(Color.red);
		plot.setRangeAxis(1, axis2);
		plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_LEFT);

		XYDataset dataset2 = createDataset("Series 2", 1000.0, new Minute(), 170);
		plot.setDataset(1, dataset2);
		plot.mapDatasetToRangeAxis(1, 1);
		XYItemRenderer renderer2 = new StandardXYItemRenderer();
		renderer2.setSeriesPaint(0, Color.red);
		plot.setRenderer(1, renderer2);

		// AXIS 3
		NumberAxis axis3 = new NumberAxis("Range Axis 3");
		axis3.setLabelPaint(Color.blue);
		axis3.setTickLabelPaint(Color.blue);
		// axis3.setPositiveArrowVisible(true);
		plot.setRangeAxis(2, axis3);

		XYDataset dataset3 = createDataset("Series 3", 10000.0, new Minute(), 170);
		plot.setDataset(2, dataset3);
		plot.mapDatasetToRangeAxis(2, 2);
		XYItemRenderer renderer3 = new StandardXYItemRenderer();
		renderer3.setSeriesPaint(0, Color.blue);
		plot.setRenderer(2, renderer3);

		// AXIS 4
		NumberAxis axis4 = new NumberAxis("Range Axis 4");
		axis4.setLabelPaint(Color.green);
		axis4.setTickLabelPaint(Color.green);
		plot.setRangeAxis(3, axis4);

		XYDataset dataset4 = createDataset("Series 4", 25.0, new Minute(), 200);
		plot.setDataset(3, dataset4);
		plot.mapDatasetToRangeAxis(3, 3);

		XYItemRenderer renderer4 = new StandardXYItemRenderer();
		renderer4.setSeriesPaint(0, Color.green);
		plot.setRenderer(3, renderer4);

		return chart;

	}

	/**
	 * Creates a sample dataset.
	 *
	 * @param name  the dataset name.
	 * @param base  the starting value.
	 * @param start the starting period.
	 * @param count the number of values to generate.
	 *
	 * @return The dataset.
	 */
	private static XYDataset createDataset(String name, double base, RegularTimePeriod start, int count) {

		TimeSeries series = new TimeSeries(name);
		RegularTimePeriod period = start;
		double value = base;
		for (int i = 0; i < count; i++) {
			series.add(period, value);
			period = period.next();
			value = value * (1 + (Math.random() - 0.495) / 10.0);
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series);
 
		return dataset;

	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 *
	 * @return A panel.
	 */
	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart();
		return new ChartPanel(chart);
	}

	@SuppressWarnings("unused")
	private XYDataset createDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();

		XYSeries series = new XYSeries("Y = X + 2");
		series.add(2, 4);
		series.add(8, 10);
		series.add(10, 12);
		series.add(13, 15);
		series.add(17, 19);
		series.add(18, 20);
		series.add(21, 23);

		// Add series to dataset
		dataset.addSeries(series);

		return dataset;
	}

}