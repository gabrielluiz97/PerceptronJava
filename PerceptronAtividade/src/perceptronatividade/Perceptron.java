
package perceptronatividade;

import static java.lang.Math.exp;

public class Perceptron {
    
   private static Perceptron uniqueInstance; 
   
   private double[] Weight;
    
   private double Bias=1;
   
   private double LearnRate=0.15;
    
   private int Interactions=1000;
   
   private boolean IsFit = false;
   
   private int ErrorAmmount = 0;
   
   
   
    private Perceptron(){
        
    }        
    
    public static synchronized Perceptron getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new Perceptron();
        return uniqueInstance;
    }
    
    public double Predict(double x, double y){
        if(!IsFit){
            double[][] intputFit= {{30.0, 0}, {25.0,5.0}, {20.0,3.0}, {15.0,8.0}, {0.0,10.0}, {6.0,7.0}};
            double[] outputFit= {1.0, 1.0, 1.0, 0, 0, 0};
            Fit(intputFit, outputFit);
        }
        return 0;
        //return Create(x, y, 0);

    }
    
    private void Fit(double[][] input, double[] output){
        System.out.println(input.length);
        this.Randomize(input.length);
        
        int interaction=0;
        boolean error = true;
        
        while(error && interaction < this.Interactions){
            
            double errorRate = 0;
            
            error = false;

            for(int i=0; i<input.length; i++){
                double result = Create(input);
                if(result != output[i]){
                    error = true;
                    errorRate = output[i] - result;
                    this.RecalculateWeights(errorRate, input[i][0], input[i][1], i);
                    ErrorAmmount++;
                }
                else{
                    error = false;
                }
            }
            System.out.println("Interação:"+ interaction + " | " + "Erro:" + String.valueOf(errorRate));
            interaction++;
        }
        IsFit = true;
    }
    
    public double Create(double[][] input){
        
       double sum = 0.0;    
       
        for (int i = 0; i < input.length; i++) {
            sum += CalculateMagnitude(input[i][0], input[i][1]) * this.Weight[i]; 
        }
        
       sum += this.Bias;
       return Sigmoid(sum);
    }
    
    private double Sigmoid(double x){
        return (1/(1+exp(-1*x)));
    }   
    
    
    private void RecalculateWeights(double errorRate, double x, double y, int line){
                    
            double magnitude = CalculateMagnitude(x, y);
            double err = this.LearnRate * errorRate * magnitude;
            this.Weight[line] = this.Weight[line] + err;
    }
    
    private void Randomize(int inputAmmount){
        
        Weight = new double[inputAmmount]; 
        Bias = (Math.random()*10);
        
        for(int i = 0; i<inputAmmount ; i++){
            Weight[i] = (Math.random()*10);               
        }
    }
    
      
    
    private double CalculateMagnitude(double x, double y){
         double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
         return(magnitude);
    }
}
