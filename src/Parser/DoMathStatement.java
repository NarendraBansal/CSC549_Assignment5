package Parser;

public class DoMathStatement extends Statement 
{
	private String name;
	
	public DoMathStatement(String name)
	{
		super("Do-Math Statement");
		this.name = name;
	}
	
	public String toString()
	{
		return super.toString() + "\n\t" + this.name;
	}

	public String getName() 
	{
		return name;
	}	
}
