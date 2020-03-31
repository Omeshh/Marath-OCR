package Project;
/****************************************************************/
/*                      Output	                            */
/*                                                              */
/****************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Summary description for Output
 *
 */
public class Output extends JDialog {
//	 Variables declaration
	private JLabel jlblOutput0;
	private JLabel jlblOutput1;
	private JLabel jlblOutput2;
	private JLabel jlblOutput3;
	private JLabel jlblOutput4;
	private JLabel jOutput5;
	private JButton jbtnNext;
	private JButton jbtnPrevious;
	private JPanel contentPane;
	// End of variables declaration

	double output[];
	int position[];
	int currentPosition= 0;
	String[] fileName;
	String[] marathi_char;

	public Output()
	{
		super();
		initializeComponent();
		//
		// TODO: Add any constructor code after initializeComponent call
		//

		this.setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always regenerated
	 * by the Windows Form Designer. Otherwise, retrieving design might not work properly.
	 * Tip: If you must revise this method, please backup this GUI file for JFrameBuilder
	 * to retrieve your design properly in future, before revising this method.
	 */
	private void initializeComponent()
	{
		jlblOutput0 = new JLabel();	
		jlblOutput1 = new JLabel();
		jlblOutput2 = new JLabel();
		jlblOutput3 = new JLabel();
		jlblOutput4 = new JLabel();
		jOutput5 = new JLabel();
		jbtnNext = new JButton();
		jbtnPrevious = new JButton();
		contentPane = (JPanel)this.getContentPane();

		Font font = new Font("", Font.BOLD,14);
		jlblOutput0.setFont(font);
		
		Font font1 = new Font("", Font.BOLD,14);
		jlblOutput1.setFont(font1);		
		jlblOutput2.setFont(font1);		
		jlblOutput3.setFont(font1);		
		jlblOutput4.setFont(font1);				
		jOutput5.setFont(font1);						
	

		//
		// jlblOutput1
		//
		jlblOutput1.setText("Unicode");
		//
		// jlblOutput2
		//
		jlblOutput2.setText("Unicode");
		//
		// jlblOutput3
		//
		jlblOutput3.setText("Unicode");
		//
		// jlblOutput4
		//
		jlblOutput4.setText("Unicode");
		//
		// jOutput5
		//
		jOutput5.setText("Unicode");
		//
		// jbtnNext
		//
		jbtnNext.setText("Next");
		jbtnNext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				jbtnNext_mouseClicked(e);
			}

		});
		//
		// jbtnPrevious
		//
		jbtnPrevious.setText("Exit");
		jbtnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jbtnPrevious_actionPerformed(e);
//				dispose();
			}

		});
		jbtnPrevious.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				jbtnPrevious_mouseClicked(e);
			}

		});
		
	
//		jlblOutput0.setOpaque(true);
		jlblOutput0.setForeground(Color.blue);	

//		jlblOutput0.setOpaque(true);
		jlblOutput1.setForeground(Color.red);				
		
		//
		// contentPane
		//
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(194, 215, 246));
		addComponent(contentPane, jlblOutput0, 37,3,204,37);		
		addComponent(contentPane, jlblOutput1, 37,28,204,37);
		addComponent(contentPane, jlblOutput2, 37,55,204,37);
		addComponent(contentPane, jlblOutput3, 37,83,204,37);
		addComponent(contentPane, jlblOutput4, 37,111,204,37);
		addComponent(contentPane, jOutput5, 37,138,204,37);
		addComponent(contentPane, jbtnNext, 99,187,83,28);
		addComponent(contentPane, jbtnPrevious, 216,187,83,28);
		
		//
		// Output
		//
		this.setTitle("Output ");
		this.setLocation(new Point(54, 80));
		this.setSize(new Dimension(428, 268));
		//this.setClosable(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	/** Add Component Without a Layout Manager (Absolute Positioning) */
	private void addComponent(Container container,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}

	//
	// TODO: Add any appropriate code in the following Event Handling Methods
	//
	private void jbtnNext_mouseClicked(MouseEvent e) {
	
		if(currentPosition >= output.length)
			this.dispose();

		jlblOutput1.setText("   " + marathi_char[position[(currentPosition+0 < output.length)? currentPosition+ 0 : position.length - 1 ]]+ "      :     " + output[position[(currentPosition+0 < output.length)? currentPosition+ 0 : position.length - 1 ]]);
		jlblOutput2.setText("   " + marathi_char[position[(currentPosition+1 < output.length)? currentPosition+ 1 : position.length - 1 ]]+ "      :     " + output[position[(currentPosition+1 < output.length)? currentPosition+ 1 : position.length - 1 ]]);
		jlblOutput3.setText("   " + marathi_char[position[(currentPosition+2 < output.length)? currentPosition+ 2 : position.length - 1 ]]+ "      :     " + output[position[(currentPosition+2 < output.length)? currentPosition+ 2 : position.length - 1 ]]);
		jlblOutput4.setText("   " + marathi_char[position[(currentPosition+3 < output.length)? currentPosition+ 3 : position.length - 1 ]]+ "      :     " + output[position[(currentPosition+3 < output.length)? currentPosition+ 3 : position.length - 1 ]]);
		jOutput5.setText("   " + marathi_char[position[(currentPosition+4 < output.length)? currentPosition+ 4 : position.length - 1 ]]+ "      :     " + output[position[(currentPosition+4 < output.length)? currentPosition+ 4 : position.length - 1 ]]);

		jlblOutput1.setForeground(Color.black);	

		currentPosition+= 5;
		
		System.out.println("\n nextcurpos: "+currentPosition);
		
		jbtnPrevious.setText("Previous");
		if(currentPosition >= output.length)
			jbtnNext.setText("End");
	}

	private void jbtnPrevious_actionPerformed(ActionEvent e)
	{
		System.out.println("\njbtnPrevious_actionPerformed(ActionEvent e) called.");
		// TODO: Add any handling code here

	}

	private void jbtnPrevious_mouseClicked(MouseEvent e)
	{
		currentPosition-= 5;			
		
		if(currentPosition == 0)
			this.dispose();

		jlblOutput1.setText("   " + marathi_char[position[(currentPosition- 5 > 0)? currentPosition- 5 : 0 ]]+ "      :     " + output[position[(currentPosition- 5 > 0)? currentPosition- 5 : 0 ]]);
		jlblOutput2.setText("   " + marathi_char[position[(currentPosition- 4 > 0)? currentPosition- 4 : 1 ]]+ "      :     " + output[position[(currentPosition- 4 > 0)? currentPosition- 4 : 1 ]]);
		jlblOutput3.setText("   " + marathi_char[position[(currentPosition- 3 > 0)? currentPosition- 3 : 2 ]]+ "      :     " + output[position[(currentPosition- 3 > 0)? currentPosition- 3 : 2 ]]);
		jlblOutput4.setText("   " + marathi_char[position[(currentPosition- 2 > 0)? currentPosition- 2 : 3 ]]+ "      :     " + output[position[(currentPosition- 2 > 0)? currentPosition- 2 : 3 ]]);
		jOutput5.setText("   " + marathi_char[position[(currentPosition- 1 > 0)? currentPosition- 1 : 4 ]]+ "      :     " + output[position[(currentPosition- 1 > 0)? currentPosition- 1 : 4 ]]);

			
		jbtnNext.setText("Next");


		
				System.out.println("\n prevcurpos: "+currentPosition);
				
		if(currentPosition == 5)
		{
			jbtnPrevious.setText("End");
			jlblOutput1.setForeground(Color.red);	
		}
			
			

	}

	public void updateLabel(double[] out, String []file, String []marathi_ch) {
		this.output= out;
		this.fileName= file;
		this.marathi_char = marathi_ch;
		position = new int[this.output.length];
		double max= 0, min= 1;		
		for(int j = 0; j < output.length; j++ ) {
			for( int i= 0; i< output.length; i++ ) 
				if(output[i] > max && output[i]< min) {
					max= output[i];
					position[j]= i;
				}
			min= max;
			max= 0;
		}
		currentPosition= 5;

		jlblOutput0.setText("CHAR  :     "+ " " + "PROBABILITY");		
		jlblOutput1.setText("   " + marathi_char[position[0]]+ "      :     " + output[position[0]]);
		jlblOutput2.setText("   " + marathi_char[position[1]]+ "      :     " + output[position[1]]);
		jlblOutput3.setText("   " + marathi_char[position[2]]+ "      :     " + output[position[2]]);
		jlblOutput4.setText("   " + marathi_char[position[3]]+ "      :     " + output[position[3]]);
		jOutput5.setText("   " + marathi_char[position[4]]+ "      :     " + output[position[4]]);
	}
	//
	// TODO: Add any method code to meet your needs in the following area
	//






























 

//============================= Testing ================================//
//=                                                                    =//
//= The following main method is just for testing this class you built.=//
//= After testing,you may simply delete it.                            =//
//======================================================================//
	/*public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception ex)
		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
		final JFrame w 		= new JFrame("Desktop Window");
		final JDesktopPane desktop = new JDesktopPane();
		JMenuBar menuBar 	= new JMenuBar();
		JMenu menu 			= new JMenu("Document");
		JMenuItem menuItem 	= new JMenuItem("New");
		w.setContentPane(desktop);
		w.setJMenuBar(menuBar);
		menuBar.add(menu);
		menu.add(menuItem);
		menuItem.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent e)
							{
								//-- Create a ProbableOutput --
								ProbableOutput JIF = new ProbableOutput();
								desktop.add(JIF);
								try
								{
									JIF.setSelected(true);
								}
								catch (java.beans.PropertyVetoException ee) {}
								//---------------------
							}});
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setSize(700,500);
		w.setLocation(50,50);
		w.setVisible(true);
	}*/		
}
