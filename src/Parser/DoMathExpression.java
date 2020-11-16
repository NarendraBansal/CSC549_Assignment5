package Parser;

public class DoMathExpression extends Expression 
{
	private String name;
	
	public DoMathExpression(String name)
	{
		super("Resolve Expression");
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
