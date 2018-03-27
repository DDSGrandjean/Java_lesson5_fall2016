/**
 * COMI2510 - Advanced Java Programming
 * October 12th, 2016
 * 
 * Program designed to create a window and menu
 * to select a dorm, and meal plan at a university.
 * 
 * @author Dylan Grandjean
 */
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CollegeMenuWindow extends JFrame
{
	//Fields:
	private double dormAmount = 1500.0;
	private double mealAmount = 560.0;
	private double moreAmount = 0.0;
	private double sliderAmount = 100.0;
	
	boolean gym = false;
	boolean pool = false;
	boolean run = false;
	boolean laundry = false;
	boolean dryer = false;
	boolean bookOrder = false;
	
	private ArrayList<String> list = new ArrayList<String>();

	private JTextField totalAmount;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu dormMenu;
	private JMenu mealMenu;
	private JMenu moreMenu;
	private JMenuItem exitItem;
	private JSlider slider;
	private JScrollPane scrollPane;
	private JPanel scrollingPanel;
	private JPanel southPanel;
	private JList jList;
	private JRadioButtonMenuItem allenItem;
	private JRadioButtonMenuItem pikeItem;
	private JRadioButtonMenuItem farthingItem;
	private JRadioButtonMenuItem universityItem;
	private JRadioButtonMenuItem sevenMealItem;
	private JRadioButtonMenuItem fourteenMealItem;
	private JRadioButtonMenuItem unlimitedMealItem;
	private JCheckBoxMenuItem gymItem;
	private JCheckBoxMenuItem poolItem;
	private JCheckBoxMenuItem runItem;
	private JCheckBoxMenuItem laundryItem;
	private JCheckBoxMenuItem dryerItem;
	private JCheckBoxMenuItem bookOrderItem;
	
	//Constructor:
	/**
	 * CollegeMenuWindow -- no arg constructor
	 */
	public CollegeMenuWindow()
	{
		//Set title
		setTitle("University Plans");
		
		//Set BorderLayout manager
		setLayout(new BorderLayout());
		
		//Specify action for the close button
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create message textfields, slider, and JList
		totalAmount = new JTextField(20);
		totalAmount.setHorizontalAlignment(JTextField.CENTER);
		totalAmount.setEditable(false);
		totalAmount.setText(String.format("Total:   $%,.2f", (dormAmount + mealAmount + moreAmount + sliderAmount)));
		
		//Set up slider's settings
		slider = new JSlider(JSlider.HORIZONTAL, 100, 1000, 100);
		slider.setMajorTickSpacing(100);
		slider.setMinorTickSpacing(50);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.addChangeListener(new SliderListener());
		
		//------------NOT PART OF THE ASSIGNMENT---------------------------------------------*
		//Adds item to list																   //*
		list.add(String.format("%-63s$%,10.2f", "Allen Hall", dormAmount)); 			   //*
		list.add(String.format("%-56s$%,10.2f", "7 meals/week", mealAmount));              //*
		list.add(String.format("%-54s$%,10.2f", "Charge Amount", sliderAmount));           //*
		jList = new JList(list.toArray());          									   //*
		jList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));				   //*
		jList.setVisibleRowCount(6);													   //*
																						   //*
		//Create a scrollPane from the JList above										   //*
		scrollPane = new JScrollPane(jList);											   //*
		scrollingPanel = new JPanel();													   //*
		scrollingPanel.add(scrollPane);													   //*
		//-----------------------------------------------------------------------------------*	
		
		//Create a small panel to format south border display
		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2,1));
		southPanel.add(new JLabel("Charge Account", SwingConstants.CENTER));
		southPanel.add(slider);
		
		//Add labels to the content panel												   
		this.add(totalAmount, BorderLayout.NORTH);							   													
		this.add(scrollingPanel, BorderLayout.EAST);
		this.add(southPanel, BorderLayout.SOUTH);
		
		//Build menu bar
		buildMenuBar();
		
		//Pack, center, and display window
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	//Other Methods
	/**
	 * buildMenuBar -- build menu bar with created components
	 */
	private void buildMenuBar()
	{
		//Create menu bar
		menuBar = new JMenuBar();
		
		//Create the file, dorm, meal, and services menu
		buildFileMenu();
		buildDormMenu();
		buildMealMenu();
		buildMoreMenu();
		
		//Add menus to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(dormMenu);
		menuBar.add(mealMenu);
		menuBar.add(moreMenu);
		
		setJMenuBar(menuBar);
	}
	
	/**
	 * builldGileMenu -- create a menu to hold File options
	 */
	private void buildFileMenu()
	{
		//Create an Exit menu item
		exitItem = new JMenuItem("Exit                   Alt+X");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ExitListener());
				
		//Create a JMenu object for the File Menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
				
		//Add Exit menu item to the File menu
		fileMenu.add(exitItem);
	}
	
	/**
	 * buildDormMenu -- create a menu to hold Dorm options
	 */
	private void buildDormMenu()
	{
		//Create radio buttons       
		allenItem = new JRadioButtonMenuItem("Allen Hall                       Alt+1", true);
		allenItem.setMnemonic(KeyEvent.VK_1);
		allenItem.addActionListener(new DormListener());
		
		pikeItem = new JRadioButtonMenuItem("Pike Hall                        Alt+2");
		pikeItem.setMnemonic(KeyEvent.VK_2);
		pikeItem.addActionListener(new DormListener());
		
		farthingItem = new JRadioButtonMenuItem("Farthing Hall                 Alt+3");
		farthingItem.setMnemonic(KeyEvent.VK_3);
		farthingItem.addActionListener(new DormListener());
		
		universityItem = new JRadioButtonMenuItem("University Suites         Alt+4");
		universityItem.setMnemonic(KeyEvent.VK_4);
		universityItem.addActionListener(new DormListener());
		
		//Create group for buttons
		ButtonGroup group = new ButtonGroup();
		group.add(allenItem);
		group.add(pikeItem);
		group.add(farthingItem);
		group.add(universityItem);
		
		//Create JMenu for the Dorm menu
		dormMenu = new JMenu("Dorm");
		dormMenu.setMnemonic(KeyEvent.VK_D);
		
		//Add menu items to menu
		dormMenu.add(allenItem);
		dormMenu.add(pikeItem);
		dormMenu.add(farthingItem);
		dormMenu.add(universityItem);
	}
	
	/**
	 * buildMealMenu -- create a menu to hold Meal Plans
	 */
	private void buildMealMenu()
	{
		//Create radio buttons                
		sevenMealItem = new JRadioButtonMenuItem("7 meals/week                      Alt+5", true);
		sevenMealItem.setMnemonic(KeyEvent.VK_5);
		sevenMealItem.addActionListener(new MealListener());
		                                             
		fourteenMealItem = new JRadioButtonMenuItem("14 meals/week                    Alt+6");
		fourteenMealItem.setMnemonic(KeyEvent.VK_6);
		fourteenMealItem.addActionListener(new MealListener());
				
		unlimitedMealItem = new JRadioButtonMenuItem("Unlimited meals                  Alt+7");
		unlimitedMealItem.setMnemonic(KeyEvent.VK_7);
		unlimitedMealItem.addActionListener(new MealListener());
				
		//Create group for buttons
		ButtonGroup group = new ButtonGroup();
		group.add(sevenMealItem);
		group.add(fourteenMealItem);
		group.add(unlimitedMealItem);
				
		//Create JMenu for the Dorm menu
		mealMenu = new JMenu("Meal Plans");
		mealMenu.setMnemonic(KeyEvent.VK_M);
				
		//Add menu items to menu
		mealMenu.add(sevenMealItem);
		mealMenu.add(fourteenMealItem);
		mealMenu.add(unlimitedMealItem);
	}
	
	/**
	 * buildMoreMenu -- create a menu to hold Additional Services
	 */
	private void buildMoreMenu()
	{
		//Create radio buttons
		gymItem = new JCheckBoxMenuItem("Gym Access                       Alt+8");
		gymItem.setMnemonic(KeyEvent.VK_8);
		gymItem.addActionListener(new GymListener());
		gymItem.setToolTipText("Full access to workout equipement");
		
		poolItem = new JCheckBoxMenuItem("Pool Access                       Alt+9");
		poolItem.setMnemonic(KeyEvent.VK_9);
		poolItem.addActionListener(new PoolListener());
		poolItem.setToolTipText("Full access to both swimming pool buildings on campus");
		
		runItem = new JCheckBoxMenuItem("Running Track Access    Alt+0");
		runItem.setMnemonic(KeyEvent.VK_0);
		runItem.addActionListener(new RunListener());
		runItem.setToolTipText("Full access to running tracks");
				
		laundryItem = new JCheckBoxMenuItem("Laundry                               Alt+J");
		laundryItem.setMnemonic(KeyEvent.VK_J);
		laundryItem.addActionListener(new LaundryListener());
		laundryItem.setToolTipText("One time fee for unlimited access to laundry machine");
		
		dryerItem = new JCheckBoxMenuItem("Dryer                                    Alt+K");
		dryerItem.setMnemonic(KeyEvent.VK_K);
		dryerItem.addActionListener(new DryerListener());
		dryerItem.setToolTipText("One time fee for unlimited access to cloth dryer machine");
		
		bookOrderItem = new JCheckBoxMenuItem("Pre-order Books               Alt+B");
		bookOrderItem.setMnemonic(KeyEvent.VK_B);
		bookOrderItem.addActionListener(new BookOrderListener());
		bookOrderItem.setToolTipText("Pre-order all your book for this semester for one minimum fee");
				
		//Create JMenu for the Dorm menu
		moreMenu = new JMenu("Additional Services");
		moreMenu.setMnemonic(KeyEvent.VK_A);
				
		//Add menu items to menu
		moreMenu.add(gymItem);
		moreMenu.add(poolItem);
		moreMenu.add(runItem);
		moreMenu.addSeparator();
		moreMenu.add(laundryItem);
		moreMenu.add(dryerItem);
		moreMenu.addSeparator();
		moreMenu.add(bookOrderItem);
	}
	
	/**
	 * reCalcAmount -- recalculates and updates the display
	 */
	private void reCalcAmount()
	{
		totalAmount.setText(String.format("Total:   $%,.2f", (dormAmount + mealAmount + moreAmount + sliderAmount)));
		list.set(2, String.format("%-54s$%,10.2f", "Charge Amount", sliderAmount));
		jList.setListData(list.toArray());
		jList.setVisibleRowCount(6);
		pack();
	}
	
	
	//----------------LISTENERS--------------------------------
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects Exit from the File menu.
	 */
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects dorm option from the
	 * Dorm menu.
	 */
	private class DormListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(allenItem.isSelected())
			{
				dormAmount = 1500;
				list.set(0, String.format("%-63s$%,10.2f", "Allen Hall", dormAmount));
			}
			else if(pikeItem.isSelected())
			{
				dormAmount = 1600;
				list.set(0, String.format("%-63s$%,10.2f", "Pike Hall", dormAmount));
			}
			else if(farthingItem.isSelected())
			{
				dormAmount = 1200;
				list.set(0, String.format("%-60s$%,10.2f", "Farthing Hall", dormAmount));
			}
			else if(universityItem.isSelected())
			{
				dormAmount = 1800;
				list.set(0, String.format("%-56s$%,10.2f", "University Suites", dormAmount));
			}
			
			reCalcAmount();
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects a menu option from the
	 * Meal Plans menu.
	 */
	private class MealListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(sevenMealItem.isSelected())
			{
				mealAmount = 560;
				list.set(1, String.format("%-56s$%,10.2f", "7 meals/week", mealAmount));
			}
			else if(fourteenMealItem.isSelected())
			{
				mealAmount = 1095;
				list.set(1, String.format("%-55s$%,9.2f", "14 meals/week", mealAmount));
			}
			else if(unlimitedMealItem.isSelected())
			{
				mealAmount = 1500;
				list.set(1, String.format("%-55s$%,9.2f", "14 meals/week", mealAmount));
			}
			
			reCalcAmount();
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects Gym Access from the
	 * Additional Services menu.
	 */
	private class GymListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			if(gymItem.isSelected())
			{
				moreAmount += 100;
				list.add(String.format("%-57s$%10s", "Gym Access", "100.00"));
			}
			else
			{
				moreAmount -= 100;
				list.remove(String.format("%-57s$%10s", "Gym Access", "100.00"));
			}
			
			reCalcAmount();
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects Pool Access from the
	 * Additional Services menu.
	 */
	private class PoolListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			if(poolItem.isSelected())
			{
				moreAmount += 68;
				list.add(String.format("%-58s$%10s", "Pool Access", "100.00"));
			}
			else
			{
				moreAmount -= 68;
				list.remove(String.format("%-58s$%10s", "Pool Access", "100.00"));
			}
			
			reCalcAmount();
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects Running Track Access from the
	 * Additional Services menu.
	 */
	private class RunListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			if(runItem.isSelected())
			{
				moreAmount += 35;
				list.add(String.format("%-48s$%11s", "Running Track Access", "35.00"));
			}
			else
			{
				moreAmount -= 35;
				list.remove(String.format("%-48s$%11s", "Running Track Access", "35.00"));
			}
				
			reCalcAmount();
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects Laundry from the
	 * Additional Services menu.
	 */
	private class LaundryListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			if(laundryItem.isSelected())
			{
				moreAmount += 24;
				list.add(String.format("%-62s$%11s", "Laundry", "24.00"));
			}
			else
			{
				moreAmount -= 24;
				list.remove(String.format("%-62s$%11s", "Laundry", "24.00"));
			}
			
			reCalcAmount();
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects Dryer from the
	 * Additional Services menu.
	 */
	private class DryerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			if(dryerItem.isSelected())
			{
				moreAmount += 24;
				list.add(String.format("%-65s$%11s", "Dryer", "24.00"));
			}
			else
			{
				moreAmount -= 24;
				list.remove(String.format("%-65s$%11s", "Dryer", "24.00"));
			}
			
			reCalcAmount();
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects Pre-order Books from the
	 * Additional Services menu.
	 */
	private class BookOrderListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			if(bookOrderItem.isSelected())
			{
				moreAmount += 250;
				list.add(String.format("%-54s$%10s", "Pre-order Books", "250.00"));
			}
			else
			{
				moreAmount -= 250;
				list.remove(String.format("%-53s%8s", "Pre-order Books", "$250.00"));
			}
			reCalcAmount();
		}
	}
	
	/**
	 * Private inner class that handles the event that is generated
	 * when the user selects slides through the bar on the window.
	 */
	private class SliderListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			sliderAmount = slider.getValue();
			reCalcAmount();
		}
	}
	
	/**
	 * The main method creates an instance of the MEnuWindow class, which 
	 * causes it to display its window
	 */
	public static void main(String[] args)
	{
		new CollegeMenuWindow();
	}
	
}