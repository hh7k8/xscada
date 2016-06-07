package myE4Package;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class ChartApp {
	public String[] legendItem;
	public DoubleProperty height = new SimpleDoubleProperty();
	public DoubleProperty lowerBound = new SimpleDoubleProperty();
	public DoubleProperty upperBound = new SimpleDoubleProperty();
	public class Limits {
		private DoubleProperty hihi = new SimpleDoubleProperty(this, "hihi");
		private DoubleProperty hi = new SimpleDoubleProperty(this, "hi");
		private DoubleProperty low = new SimpleDoubleProperty(this, "low");
		private DoubleProperty lowlow = new SimpleDoubleProperty(this, "lowlow");
	}
	Limits seriesLimit = new Limits();
	Limits pixelLimit = new Limits();
	
	@PostConstruct
	void initUI(BorderPane pane) {
		legendItem = new String[6];
		legendItem[0] = "suction";
		legendItem[1] = "discharge";
		legendItem[2] = "air";
		legendItem[3] = "water";
		legendItem[4] = "other";
		legendItem[5] = "last";
		final Parent[] chartLegendItem = new Parent[legendItem.length];
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel(legendItem[1]);				// second args is the y axis label
		final LineChart<Number, Number> lc = new LineChart<Number, Number>(xAxis, yAxis);
		lc.setTitle(legendItem[0]);
		ArrayList<XYChart.Series<Number, Number>> seriesList = new ArrayList<XYChart.Series<Number, Number>>(legendItem.length - 2);
		
		// start at 2 because the first string is the data space prefix and the second is the y axis name
		for (int i = 2; i < legendItem.length; i++) {
			final XYChart.Series<Number, Number> dataSeries = new XYChart.Series<Number, Number>();
			dataSeries.setName(legendItem[i]);
			dataSeries.getData().add(new XYChart.Data<Number, Number>(0 + i*10, 50 - i*10));	// dummy data
			dataSeries.getData().add(new XYChart.Data<Number, Number>(90 + i*10, 50 - i*10));	// dummy data
			dataSeries.getData().add(new XYChart.Data<Number, Number>(90 - i*10, 50 + i*10));	// dummy data
			dataSeries.getData().add(new XYChart.Data<Number, Number>(90 + i*15, 50 - i*5));	// dummy data

			seriesList.add(i-2, dataSeries);
			lc.getData().add(dataSeries);
		}
		
		Region plotRegion = (Region) lc.lookup(".chart-plot-background");

		int i = 0;
		for(Node cli : lc.lookupAll(".chart-legend-item")){
			chartLegendItem[i] = (Parent) cli;
			chartLegendItem[i].setOnMouseEntered( e -> hideOtherTracks(e, chartLegendItem, seriesList, plotRegion, yAxis, lc));
			chartLegendItem[i++].setOnMouseExited(e -> showOtherTracks(e, chartLegendItem, seriesList, plotRegion, lc));
		}
		pane.setCenter(lc);
	}
	
	public void calculateLimits() {
		pixelLimit.hihi.set(10.0);
		pixelLimit.hi.set(50.0);
		pixelLimit.low.set(100.0);
		pixelLimit.lowlow.set(50);
	}
//	public static void printProp(ReadOnlyProperty<?> p) {
//		String name = p.getName();
//        Object value = p.getValue();
//        Object bean = p.getBean();
//        String beanClassName = (bean == null)? "null":bean.getClass().getSimpleName();
//        String propClassName = p.getClass().getSimpleName();
//
//        System.out.print(propClassName);
//        System.out.print("[Name:" + name);
//        System.out.print(", Bean Class:" + beanClassName);
//        System.out.println(", Value:" + value + "]");
//	}
		
	public void hideOtherTracks ( MouseEvent e, Parent[] chartLegendItem, ArrayList<XYChart.Series<Number, Number>> seriesList, Region plotRegion, NumberAxis yAxis, LineChart<Number, Number> lc ) {
		for (int i = 0; i < chartLegendItem.length - 2; i++) {
			height.set(plotRegion.getHeight());
			lowerBound.set(yAxis.getLowerBound());
			upperBound.set(yAxis.getUpperBound());
			calculateLimits();
//			printProp(pixelLimit);
			plotRegion.setStyle("-fx-background-color: mistyrose, papayawhip, white;" +
		    			  "-fx-background-insets: 0 0 0 0, " + pixelLimit.hihi.get() +" 0 " + pixelLimit.hi.get() + " 0, " + pixelLimit.low.get() +" 0 "+ pixelLimit.lowlow.get() +"0; " +
		    			  "-fx-bachground-radius: 0 0 0 0, 0 0 0 0, 0 0 0 0;");
			if (e.getSource().equals(chartLegendItem[i])) {
				chartLegendItem[i].setStyle("-fx-background-color: lightgrey");
			} else {
				seriesList.get(i).getNode().setStyle("-fx-stroke: transparent; -fx-symbol: transparent");
				for (int j = 0; j < seriesList.get(i).getData().size(); j++) {
					Node cls = lc.lookup(".chart-line-symbol.series" + i + ".data" + j);
					cls.setStyle("-fx-background-color: transparent;");
				}
			}
		}
	}

	public void showOtherTracks ( MouseEvent e, Parent[] chartLegendItem, ArrayList<XYChart.Series<Number, Number>> seriesList, Region plotRegion, LineChart<Number, Number> lc ) {
		for (int i = 0; i < chartLegendItem.length - 2; i++) {
			plotRegion.setStyle("-fx-background-color: lightcoral, palegoldenrod, white;" +
	    			  "-fx-background-insets: 0 0 0 0, 0 0 0 0, 0 0 0 0; ");
			if (e.getSource().equals(chartLegendItem[i])) {
				chartLegendItem[i].setStyle("-fx-background-color: inherit");
			} else {
				seriesList.get(i).getNode().setStyle("-fx-background-color: inherit");
				chartLegendItem[i].setStyle("-fx-background-color: inherit");
				for (int j = 0; j < seriesList.get(i).getData().size(); j++) {
					Node cls = lc.lookup(".chart-line-symbol.series" + i + ".data" + j);
					cls.setStyle("-fx-stroke: transparent;");
				}

			}
		}
	}
}
