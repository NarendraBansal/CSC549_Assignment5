package Interpreter;

import java.util.ArrayList;

import Parser.*;

public class SpyderInterpreter 
{
	private static VariableEnvironment theEnv = new VariableEnvironment();
	private static ArrayList<String> theOutput = new ArrayList<String>();
	
	public static void displayResults()
	{
		System.out.println("Current Variable Environment");
		SpyderInterpreter.theEnv.display();
		for(String s : SpyderInterpreter.theOutput)
		{
			System.out.println(s);
		}
	}
	
	public static void interpret(ArrayList<Statement> theStatements)
	{
		for(Statement s : theStatements)
		{
			if(s instanceof RememberStatement)
			{
				//interpret a remember statement
				SpyderInterpreter.interpretRememberStatement((RememberStatement)s);
			}
			
		}
	}
	
	//determines if a String contains all digits (numbers)
	private static boolean isInteger(String s)//From hear new 
	{
		for(int i = 0; i < s.length(); i++)
		{
			if(!Character.isDigit(s.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	private static int interpretResolveExpression(ResolveExpression rs)
	{
		
		//only look up the variable in the env if it is not a LITERAL
		//Literal Types: int
		//this try/catch attempts to convert a string to an int and if it fails it
		//looks the string up as a variable name
		try
		{
			//tries to treat it as a int literal
			return Integer.parseInt(rs.getName());	
		}
		catch(Exception e)
		{
			try
			{
				//if not a literal, look it up in our environment
				SpyderInterpreter.theEnv.addVariable("c", SpyderInterpreter.theEnv.getValue(rs.getName()));
				return SpyderInterpreter.theEnv.getValue(rs.getName());
			}
			catch(Exception e2)
			{
				//return 0// interpretDoMathExpression(rs.getExpressionType());
				throw new RuntimeException("Variable " + rs.getName() + " NOT FOUND!");
			}
		}
	}
	
	private static int interpretDoMathExpression(DoMathExpression rs)
	{
		//only look up the variable in the env if it is not a LITERAL
				//Literal Types: int
				//this try/catch attempts to convert a string to an int and if it fails it
				//looks the string up as a variable name
				try
				{
					//tries to treat it as a int literal
					return Integer.parseInt(rs.getName());	
				}
				catch(Exception e)
				{
					try
					{
						
						String[] theParts = rs.getName().split("\\s+");
						
						
						//if not a literal, look it up in our environment
						
						int var1= SpyderInterpreter.theEnv.getValue(theParts [1]);
						int var2=0;
						
						try
						{
							//tries to treat it as a int literal
							var2= Integer.parseInt(theParts [3]);	
						}
						catch(Exception ee)
						{
							var2= SpyderInterpreter.theEnv.getValue(theParts [3]);
						}

						if(theParts[2].equals("+")) {
							return var1+var2;
						}
						if(theParts[2].equals("-")) {
							return var1-var2;
						}
						if(theParts[2].equals("*")) {
							return var1*var2;
						}
						if(theParts[2].equals("/")) {
							return var1/var2;
						}
						
					}
					catch(Exception e2)
					{
						//return 0// interpretDoMathExpression(rs.getExpressionType());
						throw new RuntimeException("Variable " + rs.getName() + " NOT FOUND!");
					}
				}
				return -1;
	}
	
	private static int getExpressionValue(Expression e)
	{
		if(e instanceof ResolveExpression)
		{
			return SpyderInterpreter.interpretResolveExpression((ResolveExpression)e);
		}
		
		if(e instanceof DoMathExpression)
		{
			return SpyderInterpreter.interpretDoMathExpression((DoMathExpression)e);
		}
		throw new RuntimeException("Not a known expression type: " + e.getExpressionType());
	}
	
	private static void interpretRememberStatement(RememberStatement rs)
	{
		//we need to resolve this expression before we can actually remember anything
		Expression valueExpression = rs.getValueExpression();
		int answer = SpyderInterpreter.getExpressionValue(valueExpression);
		
		SpyderInterpreter.theEnv.addVariable(rs.getName(), answer);
		SpyderInterpreter.theOutput.add("<HIDDEN> Added " + rs.getName() + " = " + answer + " to the variable environment.");
	}
}