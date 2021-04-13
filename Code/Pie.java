import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.util.*;
public class Pie extends ApplicationFrame {
	
	private static ArrayList<String> places;
	private static ArrayList<Integer> nums;
	
	public Pie(String title, ArrayList<String> places, ArrayList<Integer> nums)
	{
		super(title);
		
		this.places = places;
		this.nums = nums;
		
		setContentPane(createDemoPanel());
	}
	
	private static PieDataset createDataset()
	{
		DefaultPieDataset data = new DefaultPieDataset();
		
		for(int i = 0; i < nums.size(); i++)
		{
			data.setValue(places.get(i),nums.get(i));
		}
		return data;
	}
	
	private static JFreeChart createChart(PieDataset data)
	{
		JFreeChart chart = ChartFactory.createPieChart("# of doses per location", data, true, true, false);
		return chart;
	}
	
	public static JPanel createDemoPanel()
	{
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}
