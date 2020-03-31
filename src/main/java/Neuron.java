package Project;

import java.util.Random;
import java.lang.Math;

/* The Structure Of a Neuron: 
 * Each Neuron in the network has:
 *		- branching input (called dendrite),
 *		- branching output (called axon)
 *		- Weights (float values between 0...1)
 *		- Output function	*/
 
 
public class Neuron {
	public double weights[];
	public double output;
	public double error;
	public double delta;
	
	public void initializeNeuron(int no) {
		weights= new double[no];
		output= 0.0;
		error= 0.0;
		Random weightGen= new Random();
/* 		Weights are initialized with the random values 		*/
		for(int ctr= 0; ctr< no; ctr++)
			weights[ctr]= (weightGen.nextDouble()-.5)*3;
	}
	
	public void calculateNet(double input[]) {
		double net= 0.0;
		for(int ctr= 0; ctr< weights.length; ctr++)
			net+= (weights[ctr]* input[ctr]);
		
		sigmoid(net);	
	}
	
	private void sigmoid(double net) {
		this.output = 1.0/(1.0 + Math.exp(-net)); // sigmoid function
	}
	
	public double getOutput() {
    	return output;
	}
	
	public void computeBackpropDeltaOutput(double expectedOP) // for an output neuron
	{
    	delta = (expectedOP - output) * output * (1.0 - output);
	}
	
	public void computeBackPropDeltaHidden(double errorSum) {
		delta = (errorSum) * output * (1.0 - output);
	}
  	
  	public void computeWeight(double input[], double learningRate) {
		for(int ctr= 0; ctr< weights.length; ctr++) {
			weights[ctr]= weights[ctr]+ (learningRate* input[ctr]* delta);
		}
	}

}