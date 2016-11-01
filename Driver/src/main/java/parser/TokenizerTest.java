package parser;

import java.util.ArrayList;

public class TokenizerTest {

	public static void testGeneralExpression() {
		String expression = "SELECT mine.id FROM azeroth WHERE mine.status == \"ok\" AND mine.x >= 123";
		Tokenizer tokenizer = new Tokenizer();
		ArrayList<Token> tokens = tokenizer.tokenize(expression);
		System.out.println(tokens);
	}
	
	public static void main(String[] args) {
		testGeneralExpression();
	}

}
