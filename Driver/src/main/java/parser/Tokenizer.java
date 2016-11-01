package parser;

import java.util.ArrayList;

/**
 * Split input string to predefined tokens. 
 */
public class Tokenizer {
	
	public ArrayList<Token> tokenize(String text) {
		ArrayList<Token> tokens = new ArrayList<Token>();
		this.text = text;
		this.index = 0;
		Token token = getNextToken();
		while (token != null) {
			tokens.add(token);
			token = getNextToken();
		}
		return tokens;
	}
	
	/**
	 * Get the next token from the input text.
	 */
	public Token getNextToken() {
		Token token = null;
		while (index < text.length() && token == null) {
			char ch = text.charAt(index);
			if (isLetter(ch)) {
				token = readNameOrKeyword();
			}
			else if (isDigit(ch)) {
				token = readNumber();
			}
			else if (isOperator(ch)) {
				token = readOperator();
			}
			else if (ch == '"') {
				token = readStringLiteral();
			}
			else if (ch == '(' || ch == ')') {
				token = new Token(TokenType.PARENTHESIS, "" + ch);
			}
			else if (ch == ';') {
				token = new Token(TokenType.SEMICOLON, "" + ch);
			}
			index += 1;
		}
		return token;
	}
	
	/**
	 * Is it a letter?
	 */
	public boolean isLetter(char ch) {
		return Character.isLetter(ch);
	}
	
	/**
	 * Is it a number?
	 */
	public boolean isDigit(char ch) {
		return Character.isDigit(ch);
	}

	/**
	 * Is it an operator?
	 */
	public boolean isOperator(char ch) {
		String operatorPrefixes = "=<>+*-/.";
		if (operatorPrefixes.indexOf(ch) != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Is it parenthesis?
	 */
	public boolean isParenthesis(char ch) {
		return ch == '(' || ch == ')';
	}
	
	/**
	 * Is keyword?
	 */
	public boolean isKeyword(String name) {
		String[] keywords = {
		    "SELECT", "UPDATE", "INSERT", "DELETE", "DROP",
		    "WHERE", "IN", "HAS",
		    "AND", "OR", "NOT"
		};
		for (String keyword : keywords) {
			if (name.toUpperCase().equals(keyword)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Read string literal.
	 */
	Token readStringLiteral() {
		String value = "";
		++index;
		while (index < text.length() && text.charAt(index) != '"') {
			value += text.charAt(index);
			++index;
		}
		if (index == text.length()) {
			throw new RuntimeException("Missing closer \" character!");
		}
		Token token = new Token(TokenType.STRING, value);
		return token;
	}
	
	/**
	 * Read name.
	 */
	Token readNameOrKeyword() {
		String value = "";
		while (index < text.length() && (isLetter(text.charAt(index)) || isDigit(text.charAt(index)))) {
			value += text.charAt(index);
			++index;
		}
		--index;
		Token token;
		if (isKeyword(value)) {
			token = new Token(TokenType.KEYWORD, value);
		}
		else {
			token = new Token(TokenType.NAME, value);
		}
		return token;
	}
	
	/**
	 * Read number.
	 */
	Token readNumber() {
		// TODO: Handle negative numbers!
		// TODO: Handle floating point numbers!
		String value = "";
		while (index < text.length() && isDigit(text.charAt(index))) {
			value += text.charAt(index);
			++index;
		}
		--index;
		Token token = new Token(TokenType.NUMBER, value);
		return token;
	}
	
	/**
	 * Read operator.
	 */
	Token readOperator() {
		char ch = text.charAt(index);
		String value = "" + ch;
		if (index + 1 < text.length()) {
			char nextCh = text.charAt(index + 1);
			if (nextCh == '=') {
				if (ch == '<' || ch == '>' || ch == '=') {
					value += nextCh;
					++index;
				}
			}
		}
		Token token = new Token(TokenType.OPERATOR, value);
		return token;
	}
	
	/**
	 * Input text
	 */
	private String text;
	
	/**
	 * The index of the processed character
	 */
	private int index = 0;
}
