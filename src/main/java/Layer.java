package Project;

public class Layer {
	
	public int numberOfNeurons;
	public int numberOfInputs;
	public Neuron neuron[];
	
	public void initializeLayer(int no, int noi) {
		numberOfNeurons= no;
		numberOfInputs= noi;
		neuron= new Neuron[numberOfNeurons];
		for(int ctr= 0; ctr< numberOfNeurons; ctr++) {
			neuron[ctr]= new Neuron();
			neuron[ctr].initializeNeuron(numberOfInputs);
		}
	}
	
	public void computeBackpropDeltaOutput(double[] out) {
		for(int ctr= 0; ctr< numberOfNeurons; ctr++) 
			neuron[ctr].computeBackpropDeltaOutput(out[ctr]);
	}
	
	public void computeOutput(double input[]) {
		
		for(int ctr= 0; ctr< numberOfNeurons; ctr++)
			neuron[ctr].calculateNet(input);
	}
	
	public double [] returnOutput() {
		double temp[]= new double[numberOfNeurons];
		temp[0]= neuron[0].output;
		for(int ctr= 0; ctr< numberOfNeurons; ctr++) 
			temp[ctr]= neuron[ctr].output;
		return temp;
	}

	public void computeBackPropDeltaHidden(Layer postLayer) {
		double errorSum;
		for(int ctr= 0; ctr< this.numberOfNeurons; ctr++ ) {
			errorSum= 0.0;
			for(int ctr1= 0; ctr1< postLayer.numberOfNeurons; ctr1++) 
				errorSum+= (postLayer.neuron[ctr1].weights[ctr]* postLayer.neuron[ctr1].delta);
			neuron[ctr].computeBackPropDeltaHidden(errorSum);
		}
	}	
	
	public void computeWeight(double input[], double learningRate) {
		for(int ctr= 0; ctr< numberOfNeurons; ctr++)
			neuron[ctr].computeWeight(input, learningRate);
	}
	
}


