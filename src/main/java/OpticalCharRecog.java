package Project;

/****************************************************************/
/*                      OpticalCharRecog	                            */
/*                                                              */
/****************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.io.*;
import java.util.*;
/**
 * Summary description for OpticalCharRecog
 *
 */
public class OpticalCharRecog extends JFrame {
	// Variables declaration
	private JLabel jlblFileName;
	private JLabel jlblDirName;
	private JLabel jlblRecog;
	private JTextField jtxtFileName;
	private JTextField jtxtDirName;
	private JButton jbtnSelectFile;
	private JButton jbtnRecognize;
	private JPanel contentPane;
	// End of variables declaration

	private NeuralNetwork neuralNetwork;
	
	//
	//Variables for browsing out for the input file 
	//
	
	JFileChooser jfileChooser;
	String fileName;
	String dirName;
	String dirName1;
	
	//Date 25 Feb
	double outputList[];
	
	public OpticalCharRecog chRecog;
	// End of variables declaration
	
	/* New Neural Network's  code 
	 *Date : 4 March '07	*/
	
	Sample sample;
    
    
    
    /* Variable Ends here for Neural Network initialization	
     *ends 4 March*/
	
	/* for training and recog   /**/
	
	double base[]= new double[70];
	

	public OpticalCharRecog() throws IOException {
		super();
		initializeComponent();
		//
		// TODO: Add any constructor code after initializeComponent call
		//
		
		/* march 6 hided the code 2 implement NN all over Again 
		neuralNetwork= new Nnetwork(70, 30, 6);
		
		neuralNetwork.FirstTimeSettings();*/
		
		//
		// TODO: Add any constructor code after initializeComponent call
		//
		chRecog= this;
		
		this.setVisible(true);

		/* calculation of base */
		int ctr= 0;
		for( ctr= 0; ctr< 70; ctr++) 
			base[ctr]= 1;
			
		/* 4 March */
		
		/* March 6 */
		//initPerceptron();
		
		neuralNetwork= new NeuralNetwork(70, 6);
		sample= new Sample();
	
		/* Ends here */
		
				
		// 
		// Training 
		//
		learn();	

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always regenerated
	 * by the Windows Form Designer. Otherwise, retrieving design might not work properly.
	 * Tip: If you must revise this method, please backup this GUI file for JFrameBuilder
	 * to retrieve your design properly in future, before revising this method.
	 */
	private void initializeComponent() {
		jlblFileName = new JLabel();
		jlblDirName = new JLabel();
		jlblRecog = new JLabel();
		jtxtFileName = new JTextField();
		jtxtDirName = new JTextField();
		jbtnSelectFile = new JButton();
		jbtnRecognize = new JButton();
		contentPane = (JPanel)this.getContentPane();

		jfileChooser= new JFileChooser();
		
		
		//
		// jlblFileName
		//
		jlblFileName.setHorizontalAlignment(SwingConstants.CENTER);
		jlblFileName.setForeground(new Color(46, 43, 43));
		jlblFileName.setText("File Name");
		jlblFileName.setToolTipText("This text represents the file name if the input image");
		//
		// jlblDirName
		//
		jlblDirName.setHorizontalAlignment(SwingConstants.CENTER);
		jlblDirName.setForeground(new Color(46, 43, 43));
		jlblDirName.setText("Dir Name");
		jlblDirName.setToolTipText("Path of source image");
		//
		// jlblRecog
		//
		jlblRecog.setText("Recognize the character..........");
		//
		// jtxtFileName
		//
		jtxtFileName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jtxtFileName_actionPerformed(e);
			}

		});
		jtxtFileName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e)
			{
				jtxtFileName_focusGained(e);
			}

			public void focusLost(FocusEvent e)
			{
				jtxtFileName_focusLost(e);
			}

		});
		//
		// jtxtDirName
		//
		jtxtDirName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jtxtDirName_actionPerformed(e);
			}

		});
		jtxtDirName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e)
			{
				jtxtDirName_focusGained(e);
			}

			public void focusLost(FocusEvent e)
			{
				jtxtDirName_focusLost(e);
			}

		});
		//
		// jbtnSelectFile
		//
		jbtnSelectFile.setText("Select File");
		jbtnSelectFile.setToolTipText("Search File on Disk");
		jbtnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jbtnSelectFile_actionPerformed(e);
			}

		});
		jbtnSelectFile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				jbtnSelectFile_mouseClicked(e);
			}

		});
		//
		// jbtnRecognize
		//
		jbtnRecognize.setText("Recognize");
		jbtnRecognize.setToolTipText("Recognize the Character");
		jbtnRecognize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbtnRecognize_actionPerformed(e);
			}

		});
		jbtnRecognize.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jbtnRecognize_mouseClicked(e);
			}

		});
		//
		// contentPane
		//
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(194, 215, 246));
		addComponent(contentPane, jlblFileName, 61,26,69,22);
		addComponent(contentPane, jlblDirName, 48,66,93,24);
		addComponent(contentPane, jlblRecog, 227,153,222,22);
		addComponent(contentPane, jtxtFileName, 144,26,200,24);
		addComponent(contentPane, jtxtDirName, 143,68,200,22);
		addComponent(contentPane, jbtnSelectFile, 374,45,120,26);
		addComponent(contentPane, jbtnRecognize, 241,122,97,28);
		//
		// OpticalCharRecog
		//
		this.setTitle("OpticalCharRecog - extends JFrame");
		this.setLocation(new Point(50, 50));
		this.setSize(new Dimension(555, 212));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
	}

	/** Add Component Without a Layout Manager (Absolute Positioning) */
	private void addComponent(Container container,Component c,int x,int y,int width,int height)	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}

	//
	// TODO: Add any appropriate code in the following Event Handling Methods
	//
	private void jtxtFileName_actionPerformed(ActionEvent e) {
		System.out.println("\njtxtFileName_actionPerformed(ActionEvent e) called.");
		// TODO: Add any handling code here

	}

	private void jtxtFileName_focusGained(FocusEvent e) {
		jtxtFileName.setText("");
	}

	private void jtxtFileName_focusLost(FocusEvent e) {
		System.out.println("\njtxtFileName_focusLost(FocusEvent e) called.");
		// TODO: Add any handling code here

	}

	private void jtxtDirName_actionPerformed(ActionEvent e)	{
		System.out.println("\njtxtDirName_actionPerformed(ActionEvent e) called.");
		// TODO: Add any handling code here

	}

	private void jtxtDirName_focusGained(FocusEvent e) {
		jtxtDirName.setText("");
	}

	private void jtxtDirName_focusLost(FocusEvent e) {
		dirName=jtxtDirName.getText();
		dirName = dirName.replace('\\','/');
		jtxtDirName.setText(dirName);
	}

	private void jbtnSelectFile_actionPerformed(ActionEvent e) {
		
	}

	private void jbtnSelectFile_mouseClicked(MouseEvent e) {
		int fcVal = jfileChooser.showOpenDialog(chRecog);
		if(fcVal == JFileChooser.APPROVE_OPTION) {
			String filePath=jfileChooser.getCurrentDirectory().getPath()+"\\"+jfileChooser.getSelectedFile().getName();
			fileName = new String(jfileChooser.getSelectedFile().getName());
			dirName1 = new String(jfileChooser.getCurrentDirectory().getPath());
			jtxtFileName.setText(filePath.replace('\\','/'));
			System.out.println(fileName+" " + dirName1);
		}
	}

	private void jbtnRecognize_actionPerformed(ActionEvent e) {
		System.out.println("\njbtnRecognize_actionPerformed(ActionEvent e) called.");
		// TODO: Add any handling code here

	}

	private void jbtnRecognize_mouseClicked(MouseEvent e) {
		test();
	}


	public void learn() {
		double desiredOutput[][]=new double [34][34];
								/*= {{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								   {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								   {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								   {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
								   {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
								   {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
								   {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
								   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
								   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
								   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}};*/
		for(int i= 0; i< 34; i++) 
			for(int j= 0; j< 34; j++ )
				if(i == j)
					desiredOutput[i][j]= 1;
				else
					desiredOutput[i][j]= 0;
		
		TrainingProgress t= new TrainingProgress(this, "Training :-                           Status", "Training in Progress");
		
		
		double ctr= 100;//10000;
		while(ctr>0) {
			for(int i= 0; i< 5; i++)
				for(int j= 0; j< 34; j++) {
					neuralNetwork.train(sample.input[j][i], desiredOutput[j]);
				}
			ctr--;
			System.out.println(ctr+ " ");
			if(ctr% 100 == 0)
				t.updateProgress();
		}
		System.out.println(" ");
		
		t.close();
		t= null;
	}

	public void test() {
		
		System.out.println(fileName+"  "+dirName1+"  "+dirName);
		Binarize bin= new Binarize(fileName,dirName1,dirName);
		double output[];
		double list[]= new double[70];
		int ctr= 0;
		for( ctr= 0; ctr< 70; ctr++) 
			list[ctr]= (bin.moments[ctr]/1000); //sample.base[ctr]);
		
		output= neuralNetwork.test(list);
		
		System.out.println("Char Nm\tProb");
		for(int i= 0; i< output.length; i++)
			System.out.println(sample.filename[i]+ "\t=>" + output[i]);
		/*double max= 0;
		int loc= 0;
		for( int i= 0; i< output.length; i++ )
			if(output[i] > max) {
				max= output[i];
				loc = i;
			}*/
		/* Output is displayed Here  */
		Output JIF = new Output();
		JIF.updateLabel(output, sample.filename, sample.marathi_char);
    }
    
    /* Update Ends here	*/
	//
	// TODO: Add any method code to meet your needs in the following area
	//






























 

//============================= Testing ================================//
//=                                                                    =//
//= The following main method is just for testing this class you built.=//
//= After testing,you may simply delete it.                            =//
//======================================================================//
	public static void main(String[] args) throws IOException	{
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
		new OpticalCharRecog();
	}
//= End of Testing =
}
