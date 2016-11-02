package parser;

import java.util.ArrayList;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import database.queryObject.SelectBuilder;
import database.queryObject.alter.AlterBuilder;
import database.queryObject.delete.DeleteBuilder;
import database.queryObject.insert.InsertBuilder;
import database.queryObject.update.UpdateBuilder;

/**
 * Parse the query and return a query object.
 */
public class Parser {
	
	private Tokenizer tokenizer;
	
	public Parser() {
		this.tokenizer = new Tokenizer();
	}

	public IQueryObject parse(InMemoryDatabase db, String queryExpression) {
		IQueryObject queryObject = null;

		ArrayList<Token> tokens = this.tokenizer.tokenize(queryExpression);
		
		if (tokens.get(0).type == TokenType.KEYWORD) {
			if (tokens.get(0).value.equals("SELECT")) {
				SelectBuilder selectBuilder = new SelectBuilder(db);
				parseSelect(selectBuilder, tokens);
				queryObject = selectBuilder.build();
			}
			else if (tokens.get(0).value.equals("INSERT")) {
				InsertBuilder insertBuilder = new InsertBuilder(db);
				parseInsert(insertBuilder, tokens);
				queryObject = insertBuilder.build();
			}
			else if (tokens.get(0).value.equals("UPDATE")) {
				UpdateBuilder updateBuilder = new UpdateBuilder(db);
				parseUpdate(updateBuilder, tokens);
				queryObject = updateBuilder.build();
			}
			else if (tokens.get(0).value.equals("ALTER")) {
				AlterBuilder alterBuilder = new AlterBuilder(db);
				parseAlter(alterBuilder, tokens);
				queryObject = alterBuilder.build();
			}
			else if (tokens.get(0).value.equals("DELETE")) {
				DeleteBuilder DeleteBuilder = new DeleteBuilder(db);
				parseDelete(DeleteBuilder, tokens);
				queryObject = DeleteBuilder.build();
			}
			else if (tokens.get(0).value.equals("DROP")) {
				/*
				DropBuilder DropBuilder = new DropBuilder(db);
				parseDrop(DropBuilder, tokens);
				queryObject = DropBuilder.build();
				*/
			}
			else {
				throw new RuntimeException("Invalid keyword!");
			}
		} else {
			throw new RuntimeException("Missing keyword from the beginning of query expression!");
		}
		
		return queryObject;
	}
	
	private void parseSelect(SelectBuilder builder, ArrayList<Token> tokens) {
		System.out.println(tokens);
		if (tokens.size() < 4) {
			throw new RuntimeException("The SELECT expression is too short!");
		}
		if (tokens.get(1).type != TokenType.NAME) {
			throw new RuntimeException("Missing name after SELECT keyword!");
		}
		builder.setResultObject(tokens.get(1).value);
		if (tokens.get(2).type != TokenType.KEYWORD || tokens.get(2).value.equals("FROM") == false) {
			throw new RuntimeException("Missing FROM keyword!");
		}
		if (tokens.get(3).type != TokenType.NAME) {
			throw new RuntimeException("Missing table name!");
		}
		builder.setFrom(tokens.get(3).value);
		
		int tokenIndex = 5;
		boolean hasSubSelect = false;
		while (tokenIndex < tokens.size()) {
			Token token = tokens.get(tokenIndex);
			if (token.type == TokenType.KEYWORD && token.value.equals("ORDER")) {
				++tokenIndex; // Skip the BY keyword!
				++tokenIndex;
				token = tokens.get(tokenIndex);
				builder.setOrderBySort(token.value);
				// TODO: Use order by attribute!
			}
			else if (token.type == TokenType.KEYWORD && token.value.equals("LIMIT")) {
				++tokenIndex;
				token = tokens.get(tokenIndex);
				int limit = Integer.parseInt(token.value);
				builder.setLimit(limit);
			}
			else if (token.type == TokenType.KEYWORD && token.value.equals("SELECT")) {
				hasSubSelect = true;
			}
			else if (token.type == TokenType.PARENTHESIS) {
				if (token.value.equals("(")) {
					builder.addRoundBracket();
				}
				else {
					builder.removeRoundBracket();
				}
			}
			else {
				builder.addOperandPiece(token.value);
			}
			++tokenIndex;
		}
		
		if (hasSubSelect) {
			builder.buildAlSelectAndPutAsOperand();
		}
	}
	
	private void parseInsert(InsertBuilder builder, ArrayList<Token> tokens) {
		// ...
	}
	
	private void parseUpdate(UpdateBuilder builder, ArrayList<Token> tokens) {
		// ...
	}
	
	private void parseAlter(AlterBuilder builder, ArrayList<Token> tokens) {
		// ...
	}
	
	private void parseDelete(DeleteBuilder builder, ArrayList<Token> tokens) {
		// ...
	}
	
	/*
	private void parseDrop(DropBuilder builder, ArrayList<Token> tokens) {
		// ...
	}
	*/
	

}
