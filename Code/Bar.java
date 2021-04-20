
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.util.*;
import javax.swing.JPanel;
public class Bar extends ApplicationFrame {
	
	private static ArrayList<String> types;
	private static ArrayList<Integer> nums;
	
	public Bar(String title, ArrayList<String> types, ArrayList<Integer> nums)
	{
		super(title);
		
		this.types = types;
		this.nums = nums;
	}
	
	private static JFreeChart createChart(DefaultCategoryDataset data)
	{
		JFreeChart chart = ChartFactory.createBarChart("# of doses per vaccine type", "Vaccine Type", "# of doses", createDataset(), PlotOrientation.VERTICAL, false, true, false);
		return chart;
	}
	
	private static DefaultCategoryDataset createDataset()
	{
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		for (int i = 0; i < nums.size(); i++)
		{
			data.addValue(nums.get(i), "Vaccine Type", types.get(i));
		}
		return data;
	}
	
	public static JPanel createDemoPanel()
	{
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}
