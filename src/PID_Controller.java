import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Bartek on 2015-05-31.
 */
public class PID_Controller {

    private double pSetpoint = 0;
    private double pPV = 0;  // actual possition (Process Value)
    private double pError = 0;   // how much SP and PV are diff (SP - PV)
    private double pIntegral = 0; // curIntegral + (error * Delta Time)
    private double pDerivative = 0;  //(error - prev error) / Delta time
    private double pPreError = 0; // error from last time (previous Error)
    private double Kp = 0.2, Ki = 0.01, Kd = 1; // PID constant multipliers
    private double pDt = 150.0; // delta time
    private double pOutput = 0; // the drive amount that effects the PV.
    private int pNoisePercent = 0; // amount of the full range to randomly alter the PV
    private double pOutputWithNoiseValue = 0;  // random noise added to PV
    private boolean pNoiseEnabled = false;

    private int _timeElapsed = 0;
    private long _flatErrorTimeElapsed = 0;
    private long _maxTimeAllowed;
    private long _minErrorTimeRequired;
    private float _minErrorAllowed;
    private float _desiredOutput;
    public int sumError;



    public PID_Controller(int maxTimeAllowed, int minErrorTimeRequired, float minErrorAllowed, double interval) {
        _maxTimeAllowed = maxTimeAllowed;
        _minErrorTimeRequired = minErrorTimeRequired;
        _minErrorAllowed = minErrorAllowed;
        pDt = interval;
    }

    public PID_Controller(int maxTimeAllowed, int minErrorTimeRequired, float minErrorAllowed) {
        _maxTimeAllowed = maxTimeAllowed;
        _minErrorTimeRequired = minErrorTimeRequired;
        _minErrorAllowed = minErrorAllowed;
    }

    public LinkedBlockingQueue runPID(float startingInput, float expectedOutput, double p, double i, float d) {
        Kp = p;
        Ki = i;
        Kd = d;
        pPV = startingInput;
        pSetpoint=expectedOutput;
        int step = 0;
        LinkedBlockingQueue<Integer> tempQueue = new LinkedBlockingQueue<Integer>();
        System.out.println("Input: "+startingInput+ "; Expected output: "+expectedOutput);
        while(step<10000) {
            step+=1;
            pidStep();
            tempQueue.add((int)pPV);

            //tempQueue.add(Math.abs((int) pPV) % 300);
           // System.out.println(step+". I: "+pIntegral+"; D: "+pDerivative+"; Output: "+pOutput);
           // System.out.println(step+". Error: "+pError);
            System.out.println(step+". pPV: "+(pPV));
        }

        return tempQueue;
    }


    public void SetNoise(boolean noiseEnabled, int maxPercentValue) {
        if(noiseEnabled) {
            pNoiseEnabled = true;
            pNoisePercent = maxPercentValue;
        }
        else {
            pNoiseEnabled = false;
            pNoisePercent = 0;
        }
    }

    private void pidStep()
    {
        // calculate the difference between the desired value and the actual value
        pError = pSetpoint - pPV;
        // track error over time, scaled to the timer interval
        pIntegral = pIntegral + (pError * pDt);
        // determin the amount of change from the last time checked
        pDerivative = (pError - pPreError) / pDt;

        // calculate how much drive the output in order to get to the
        // desired setpoint.
        //pOutput = (Kp * pError) + (Ki * pIntegral) + (Kd * pDerivative);
        pOutput = (Kp * pError) + (Ki * pIntegral) + (Kd * pDerivative);

        // remember the error for the next time around.
        pPreError = pError;
        sumError += pError;

        _timeElapsed+=pDt;

        if(pNoiseEnabled) {
            calculateNoise();
            pPV = pPV + pOutputWithNoiseValue;
        } else {
            pPV = pPV + pOutput/300; // zobaczylem, ze ta 300 ratuje owiat ;)
        }
    }

    private void calculateNoise() {
        if(pNoisePercent !=0) {

            Random rand = new Random();
            if(pNoisePercent<2) { pNoisePercent=2; }
            int tempVal =  rand.nextInt(pNoisePercent);
            pOutputWithNoiseValue = pOutput*tempVal/300;
        }
    }

}