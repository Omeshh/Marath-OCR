package Project;

/****************************************************************/
/*                      TrainingProgress	                            */
/*                                                              */
/****************************************************************/
import java.awt.*;
import javax.swing.*;
/**
 * Summary description for TrainingProgress
 *
 */
public class TrainingProgress extends JDialog
{
	// Variables declaration
	private JLabel jlblProgress;
	private JProgressBar jProgressrBarTrain;
	private JPanel contentPane;
	// End of variables declaration
	String label;
	
	public TrainingProgress() {
	}
	public TrainingProgress(Frame w, String Title, String lbl)
	{
		super(w);
		label= lbl;
		initializeComponent(Title, label);
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
	private void initializeComponent(String Title, String lbl)
	{
		jlblProgress = new JLabel();
		jProgressrBarTrain = new JProgressBar();
		contentPane = (JPanel)this.getContentPane();

		//
		// jlblProgress
		//
		jlblProgress.setText(lbl);
		//
		// jProgressrBarTrain
		//
		jProgressrBarTrain.setToolTipText("Initailize the System and the vairables.");
		//
		// contentPane
		//
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(194, 215, 246));
		contentPane.setToolTipText(label);
		addComponent(contentPane, jlblProgress, 44,24,337,18);
		addComponent(contentPane, jProgressrBarTrain, 44,53,337,17);
		//
		// TrainingProgress
		//
		this.setTitle(Title);
		this.setLocation(new Point(54, 80));
		this.setSize(new Dimension(439, 123));
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
	}

	/** Add Component Without a Layout Manager (Absolute Positioning) */
	private void addComponent(Container container,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}

	//
	// TODO: Add any method code to meet your needs in the following area
	//
 

//============================= Testing ================================//
//=                                                                    =//
//= The following createFrame method is just for creating this class you built.=//
//= After testing,you may simply delete it.                            =//
//======================================================================//
	
	public void updateProgress() {
		this.jProgressrBarTrain.setValue(this.jProgressrBarTrain.getValue()+ 1);
		jlblProgress.setText(label+" "+ this.jProgressrBarTrain.getValue()+" % Completed");
	}
	
	public void close() {
		this.dispose();
	}
}
