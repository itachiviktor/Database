package parser;

import java.util.List;
import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import database.queryObject.selectBuild.SelectBuilder;
import database.queryObject.alter.AlterBuilder;
import database.queryObject.create.CreateBuilder;
import database.queryObject.delete.DeleteBuilder;
import database.queryObject.drop.DropBuilder;
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

		List<Token> tokens = this.tokenizer.tokenize(queryExpression);
		
		if (tokens.get(0).type == TokenType.KEYWORD) {
			if (tokens.get(0).value.equals("CREATE")) {
				CreateBuilder createBuilder = new CreateBuilder(db);
				parseCreate(createBuilder, tokens);
				queryObject = createBuilder.build();
			}
			else if (tokens.get(0).value.equals("SELECT")) {
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
				DropBuilder DropBuilder = new DropBuilder(db);
				parseDrop(DropBuilder, tokens);
				queryObject = DropBuilder.build();
			}
			else {
				throw new RuntimeException("Invalid keyword!");
			}
		} else {
			throw new RuntimeException("Missing keyword from the beginning of query expression!");
		}
		
		return queryObject;
	}
	
	private void parseCreate(CreateBuilder builder, List<Token> tokens) {
		if (tokens.size() < 3) {
			throw new RuntimeException("Invalid CREATE expression!");
		}
		String createType = tokens.get(1).value;
		builder.setCreateType(createType);
		if (createType.equals("DATABASE")) {
			builder.setTheCommonValue(tokens.get(2).value);
		}
		else if (createType.equals("MAP")) {
			builder.setTheCommonValue(tokens.get(2).value);
		}
		else if (createType.equals("CLASS")) {
			builder.setTheCommonValue(tokens.get(2).value);
			int tokenIndex = 4;
			Token token = tokens.get(tokenIndex);
			while (tokenIndex < tokens.size() && (token.type == TokenType.PARENTHESIS && token.value == ")") == false) {
				builder.addAttributeParameter(token.value);
				++tokenIndex;
				token = tokens.get(tokenIndex);
				builder.addAttributeParameter(token.value);
				Token nextToken = tokens.get(tokenIndex + 1);
				if (nextToken.type == TokenType.KEYWORD && nextToken.value == "DEFAULT") {
					tokenIndex += 2;
					token = tokens.get(tokenIndex);
					builder.addAttributeParameter(token.value);
				}
				builder.insertAttribute();
				++tokenIndex;
				token = tokens.get(tokenIndex);
			}
		}
	}
	
	private List<Token> extractSubSelectTokens(List<Token> tokens, int braceIndex) {
		int j = braceIndex;
		++j;
		Token token = tokens.get(j);
		int nRequiredCloser = 1;
		while (j < tokens.size() && nRequiredCloser > 0) {
			if (token.type == TokenType.BRACE) {
				if (token.value.equals("{")) {
					++nRequiredCloser;
				}
				else if (token.value.equals("}")) {
					--nRequiredCloser;
				}
			}
			token = tokens.get(j);
			++j;
		}
		--j;
		if (j == tokens.size()) {
			throw new RuntimeException("Missing closer } character!");
		}
		List<Token> selectTokens = tokens.subList(braceIndex + 1, j - 1);
		return selectTokens;
	}
	
	private void parseSelect(SelectBuilder builder, List<Token> tokens) {
		System.out.println(tokens);
		if (tokens.size() < 4) {
			throw new RuntimeException("The SELECT expression is too short!");
		}
		if (tokens.get(1).type != TokenType.NAME) {
			throw new RuntimeException("Missing name after SELECT keyword!");
		}
		builder.addResource(tokens.get(1).value);
		if (tokens.get(2).type != TokenType.KEYWORD || tokens.get(2).value.equals("FROM") == false) {
			throw new RuntimeException("Missing FROM keyword!");
		}
		if (tokens.get(3).type != TokenType.NAME) {
			throw new RuntimeException("Missing table name!");
		}
		builder.setFrom(tokens.get(3).value);
		
		int tokenIndex = 5;
		while (tokenIndex < tokens.size()) {
			Token token = tokens.get(tokenIndex);
			if (token.type == TokenType.KEYWORD && token.value.equals("ORDER")) {
				// ++tokenIndex; // Skip the BY keyword!
				++tokenIndex;
				token = tokens.get(tokenIndex);
				System.out.println(token.value + " mostirtam");
				builder.setOrderByAttribute(token.value);
				Token nextToken = tokens.get(tokenIndex + 1);
				if (nextToken.type == TokenType.KEYWORD && (nextToken.value.equals("ASC") || nextToken.value.equals("DESC"))) {
					builder.setOrderBySort(nextToken.value);
					++tokenIndex;
				}
				
				// TODO: Use order by attribute!
			}
			else if (token.type == TokenType.KEYWORD && token.value.equals("LIMIT")) {
				++tokenIndex;
				token = tokens.get(tokenIndex);
				int limit = Integer.parseInt(token.value);
				builder.setLimit(limit);
			}
			else if (token.type == TokenType.PARENTHESIS) {
				if (token.value.equals("(")) {
					builder.addRoundedBracket();
				}
				else {
					builder.removeRoundedBracket();
				}
			}
			else if (token.type == TokenType.BRACE && token.value.equals("{")) {
				List<Token> selectTokens = this.extractSubSelectTokens(tokens, tokenIndex);
				this.parseSelect(builder, selectTokens);
				tokenIndex += selectTokens.size() + 1;
				token = tokens.get(tokenIndex);
				builder.addOp(token.value);
			}
			else {
				builder.addOp(token.value);
			}
			++tokenIndex;
		}
	}
	
	private void parseInsert(InsertBuilder builder, List<Token> tokens) {
		int tokenIndex = 1;
		Token token = tokens.get(tokenIndex);
		if (token.type == TokenType.NAME) {
			builder.addClassName(token.value);
		}
		else {
			throw new RuntimeException("Missing class name!");
		}
		++tokenIndex;
		token = tokens.get(tokenIndex);
		if (token.type != TokenType.PARENTHESIS || token.value.equals("(") == false) {
			throw new RuntimeException("Missing parenthesis!");
		}
		++tokenIndex;
		token = tokens.get(tokenIndex);
		// Read attributes
		while (tokenIndex < tokens.size() && (token.type == TokenType.KEYWORD && token.value.equals("INTO")) == false) {
			if (token.type == TokenType.NAME) {
				builder.addAttribute(token.value);
			}
			else if (token.type == TokenType.PARENTHESIS && token.value.equals(")")) {
				// Check, that the ) is not directly before INTO keyword.
				if (tokenIndex + 1 < tokens.size()) {
					Token nextToken = tokens.get(tokenIndex);
					if (nextToken.type != TokenType.KEYWORD || nextToken.value.equals("INTO") == false) {
						builder.removeRoundBracketAttribute();						
					}
				}
			}
			++tokenIndex;
			token = tokens.get(tokenIndex);			
		}
		if (tokenIndex == tokens.size()) {
			throw new RuntimeException("Unexpected end of INSERT expression!");
		}
		// Skip the INTO keyword.
		++tokenIndex;
		token = tokens.get(tokenIndex);	
		if (token.type == TokenType.NAME) {
			builder.addMapName(token.value);
		}
		else {
			throw new RuntimeException("Missing map name from INSERT expression!");
		}
		++tokenIndex;
		token = tokens.get(tokenIndex);	
		if (token.type != TokenType.KEYWORD || token.value.equals("VALUES") == false) {
			throw new RuntimeException("Missing VALUES keyword from INSERT expression!");
		}
		++tokenIndex;
		token = tokens.get(tokenIndex);	
		if (token.type != TokenType.PARENTHESIS || token.value.equals("(") == false) {
			throw new RuntimeException("Missing parenthesis before values from INSERT expression!");
		}
		++tokenIndex;
		token = tokens.get(tokenIndex);
		while (tokenIndex < tokens.size() && (token.type == TokenType.KEYWORD && token.value.equals("LAYER")) == false) {
			if (token.type == TokenType.PARENTHESIS && token.value.equals("(")) {
				builder.addRoundBracketValue();
			}
			else if (token.type != TokenType.PARENTHESIS && token.type != TokenType.OPERATOR) {
				builder.addValue(token.value);
			}
			++tokenIndex;
			token = tokens.get(tokenIndex-1);
		}
		if (tokenIndex < tokens.size()) {
			// Skip the LAYER keyword.
			++tokenIndex;
			token = tokens.get(tokenIndex);
			if (token.type == TokenType.NUMBER) {
				// TODO: Set the layer to the builder!
			}
		}
	}
	
	private void parseUpdate(UpdateBuilder builder, List<Token> tokens) {
		// ...
	}
	
	private void parseAlter(AlterBuilder builder, List<Token> tokens) {
		if (tokens.size() >= 4) {
			builder.createAlter(tokens.get(2).value);
			String typeName = tokens.get(3).value;
			builder.setAlterType(typeName);
			if (typeName.equals("RENAMEATTRIBUTE")) {
				if (tokens.size() == 7) {
					builder.setStringAttributeName(tokens.get(4).value);
					builder.setStringOptionalValue(tokens.get(6).value);
				} else {
					throw new RuntimeException("Invalid RENAMEATTRIBUTE expression!");
				}
			} else if (typeName.equals("DELETEATTRIBUTE")) {
				if (tokens.size() == 5) {
					builder.setStringAttributeName(tokens.get(4).value);
				} else {
					throw new RuntimeException("Invalid DELETEATTRIBUTE expression!");
				}
			} else if (typeName.equals("ADDATTRIBUTE")) {
				if (tokens.size() == 6) {
					builder.setStringAttributeName(tokens.get(4).value);
					builder.setStringOptionalValue(tokens.get(5).value);
				} else {
					throw new RuntimeException("Invalid ADDATTRIBUTE expression!");
				}
			} else if (typeName.equals("RENAMECLASS")) {
				if (tokens.size() == 5) {
					builder.setStringAttributeName(tokens.get(4).value);
				} else {
					throw new RuntimeException("Invalid RENAMECLASS expression!");
				}
			}
		} else {
			throw new RuntimeException("Invalid ALTER expression!");
		}
	}
	
	private void parseDelete(DeleteBuilder builder, List<Token> tokens) {

	}
	
	private void parseDrop(DropBuilder builder, List<Token> tokens) {
		if (tokens.size() >= 3) {
			builder.setType(tokens.get(1).value);
			builder.setName(tokens.get(2).value);
		} else {
			throw new RuntimeException("Invalid DROP expression!");
		}
	}
	

}
