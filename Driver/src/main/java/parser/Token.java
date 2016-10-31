package parser;

/**
 * Token 
 */
public class Token {
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public TokenType type;
	public String value;
}
