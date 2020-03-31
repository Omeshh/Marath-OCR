package Project;

public class NeuralNetwork {
	public int numberOfInput;
	public int numberOfOutput;
	public int numberOfLayers;
	
	public Layer layers[];
	public int numOfNeuronPerLayer[];
	
	public final int OutputLayer= 3;
	public final int InputLayer= 0;
	public double learningRate= 0.3;
	
	NeuralNetwork(int noInput, int noOutput ) {
		numberOfOutput= noOutput;
		numberOfInput= noInput;
		numberOfLayers= 4;
		layers= new Layer[numberOfLayers];
		
		for(int ctr= 0; ctr< numberOfLayers; ctr++ ) 
			layers[ctr]= new Layer();
		
		numOfNeuronPerLayer= new int[numberOfLayers];
		numOfNeuronPerLayer[0]= 70;
		numOfNeuronPerLayer[1]= 60;
		numOfNeuronPerLayer[2]= 36;
		numOfNeuronPerLayer[3]= 34;
		layers[0].initializeLayer(numOfNeuronPerLayer[0], numOfNeuronPerLayer[0]);
		layers[1].initializeLayer(numOfNeuronPerLayer[1], numOfNeuronPerLayer[0]);
		layers[2].initializeLayer(numOfNeuronPerLayer[2], numOfNeuronPerLayer[1]);
		layers[3].initializeLayer(numOfNeuronPerLayer[3], numOfNeuronPerLayer[2]);
	}
	
	void train(double input[], double output[]) {
		double temp[][]= new double[numberOfLayers+1][];
		
		temp[0]= input;
		/*	in this section compute the output first */
		
		for(int lyrs= 0; lyrs<numberOfLayers; lyrs++) {
			layers[lyrs].computeOutput(temp[lyrs]);
			temp[lyrs+1]= layers[lyrs].returnOutput();
		}
		
		/*	BackPropagation algorithm begins here 	*/		
		layers[OutputLayer].computeBackpropDeltaOutput(output);
		//layers[OutputLayer].computeWeight(temp[OutputLayer], learningRate);
		
		for(int ctr= OutputLayer - 1; ctr>= InputLayer; ctr--) //{
			layers[ctr].computeBackPropDeltaHidden(layers[ctr+1]);
			//layers[ctr].computeWeight(temp[ctr], learningRate);
		//}
		for(int ctr= OutputLayer; ctr>= InputLayer; ctr--) 
			layers[ctr].computeWeight(temp[ctr], learningRate);
	}
	
	double[] test(double input[]) {
		double output[];
		
		output= input;
		/*	in this section compute the output first */
		
		for(int lyrs= 0; lyrs<numberOfLayers; lyrs++) {
			layers[lyrs].computeOutput(output);
			output= layers[lyrs].returnOutput();
		}
		
		return output;
	}
	
}
