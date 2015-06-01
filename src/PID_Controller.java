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
    private double pDt = 100.0; // delta time
    private double pOutput = 0; // the drive amount that effects the PV.
    private double pNoisePercent = 0.02; // amount of the full range to randomly alter the PV
    private double pNoise = 0;  // random noise added to PV

    private long _timeElapsed = 0;
    private long _flatErrorTimeElapsed = 0;
    private long _maxTimeAllowed;
    private long _minErrorTimeRequired;
    private float _minErrorAllowed;
    private float _desiredOutput;




    public PID_Controller(long maxTimeAllowed, long minErrorTimeRequired, float minErrorAllowed, double interval) {
        _maxTimeAllowed = maxTimeAllowed;
        _minErrorTimeRequired = minErrorTimeRequired;
        _minErrorAllowed = minErrorAllowed;
        pDt = interval;
    }

    public PID_Controller(long maxTimeAllowed, long minErrorTimeRequired, float minErrorAllowed) {
        _maxTimeAllowed = maxTimeAllowed;
        _minErrorTimeRequired = minErrorTimeRequired;
        _minErrorAllowed = minErrorAllowed;
    }

    public long runPID(float startingInput, float expectedOutput, float p, float i, float d) {
        Kp = p;
        Ki = i;
        Kd = d;
        pSetpoint = startingInput;
        _desiredOutput=expectedOutput;

        while(CheckTimeConditions()) {
            pidStep();
        }


        return _timeElapsed;
    }

    private boolean CheckTimeConditions() {
        return (_timeElapsed<=_maxTimeAllowed && _flatErrorTimeElapsed> _minErrorTimeRequired);
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
        pOutput = (Kp * pError) + (Ki * pIntegral) + (Kd * pDerivative);

        // remember the error for the next time around.
        pPreError = pError;
        _timeElapsed+=pDt;

        if(Math.abs(pError)<=_minErrorAllowed) {
            _flatErrorTimeElapsed+=pDt;
        } else {
            _flatErrorTimeElapsed = 0;
        }

    }

}
