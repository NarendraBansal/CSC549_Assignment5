package Parser;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser 
{
	private static ArrayList<Statement> theListOfStatements = new ArrayList<Statement>();
	
	public static ArrayList<Statement> getParsedStatements()
	{
		return Parser.theListOfStatements;
	}
	
	public static void display()
	{
		for(Statement s : theListOfStatements)
		{
			System.out.println(s);
		}
	}
	
	static ResolveExpression parseResolve(String name)
	{
		//parse this string into language objects
		//turn remember syntax into a ResolveStatement
		
		ResolveExpression rs = new ResolveExpression(name);
		return rs;
	}
	static DoMathExpression parseDoMath(String name)
	{
		//parse this string into language objects
		//turn remember syntax into a ResolveStatement
		
		DoMathExpression dm = new DoMathExpression(name);
		return dm;
	}
	
	static RememberStatement parseRemember(String type, String name, String valueExpression)
	{
		//parse this string into language objects
		//turn remember syntax into a RememberStatement
		Expression ex=null;
		try {
		ex = (ResolveExpression)Parser.parseExpression(valueExpression);
		}
		catch(Exception e) {
			ex = (DoMathExpression)Parser.parseExpression(valueExpression);
		}
		RememberStatement rs = new RememberStatement(type, name, ex);
		return rs;
	}
	static DoMathStatement parseDoMath(String type, String name, String valueExpression)
	{
		//parse this string into language objects
		//turn remember syntax into a RememberStatement
		DoMathExpression re = (DoMathExpression)Parser.parseExpression(valueExpression);
		DoMathStatement ds = new DoMathStatement(name);//DoMathStatement(type, name, re);
		return ds;
	}
	
	public static void parse(String filename)
	{
		try
		{
			Scanner input = new Scanner(new File(System.getProperty("user.dir") + 
					"/src/" + filename));
			//builds a single string that has the contents of the file
			String fileContents = "";
			while(input.hasNext())
			{
				fileContents += input.nextLine().trim();
			}
			
			String[] theProgramLines = fileContents.split(";");
			for(int i = 0; i < theProgramLines.length; i++)
			{
				parseStatement(theProgramLines[i]);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("File Not Found!!!");
		}
	}
	
	static Expression parseExpression(String expression)
	{
		//determine which kind of expression this is, and parse it
		//right now we only have a single kind of expression (ResolveExpression)
		
		if(expression.contains("do-math")) {
			return Parser.parseDoMath(expression);
			
		}
		
		return Parser.parseResolve(expression);
	}
	
	//parses the top level statements within our language
	static void parseStatement(String s)
	{
		//split the string on white space (1 or more spaces)
		String[] theParts = s.split("\\s+");
		//s = "remember int a = 5"
		//parts = {"remember", "int", "a", "=", "5"}
		//s = "resolve a"
		//parts = {"resolve", "a"}
		
		if(theParts[0].equals("remember"))
		{
			
			if(theParts[4].equals("do-math"))
					{
				theListOfStatements.add(Parser.parseRemember(theParts[1], 
						theParts[2], theParts[4]+" "+theParts[5]+" "+theParts[6]+" "+theParts[7]));
					}
			//parse a remember statement with type, name, and value
			else {
			theListOfStatements.add(Parser.parseRemember(theParts[1], 
					theParts[2], theParts[4]));
			}
		}
	
	}
}