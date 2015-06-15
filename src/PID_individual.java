

public class PID_individual 
{
	private float p;
	private float i;
	private float d;
	
	public PID_individual(float p, float i, float d) {
		super();
		this.p = p;
		this.i = i;
		this.d = d;
	}

	public int fintnessFunction()
	{
        PID_Controller pid = new PID_Controller(100000,10000,170);
        pid.runPID(0,150,p,i,d);
        return pid.sumError;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(d);
		result = prime * result + Float.floatToIntBits(i);
		result = prime * result + Float.floatToIntBits(p);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PID_individual other = (PID_individual) obj;
		if (Float.floatToIntBits(d) != Float.floatToIntBits(other.d))
			return false;
		if (Float.floatToIntBits(i) != Float.floatToIntBits(other.i))
			return false;
		if (Float.floatToIntBits(p) != Float.floatToIntBits(other.p))
			return false;
		return true;
	}

	
	@Override
	public String toString() 
	{
		return "(p: "+p+",i: "+i+",d: "+d+")";
	}

	public float getP() {
		return p;
	}

	public void setP(float p) {
		this.p = p;
	}

	public float getI() {
		return i;
	}

	public void setI(float i) {
		this.i = i;
	}

	public float getD() {
		return d;
	}

	public void setD(float d) {
		this.d = d;
	}

}
	