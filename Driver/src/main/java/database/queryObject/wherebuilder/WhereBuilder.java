package database.queryObject.wherebuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.StringToOperatorEnum;
import database.queryObject.Where;
import database.queryObject.WhereLetter;

public class WhereBuilder {
	private int actualLevel;
	public Map<Integer, Boolean> sides;/*bal-false, true-jobb*/
	
	private List<String> operandList;/*ebben 3 sztring lesz a végén, majd üríteni kell*/
	
	private List<BuilderNode> nodes;
	private Stack<BuilderNode> stackNodes;
	
	public WhereBuilder() {
		actualLevel = 1;
		sides = new HashMap<Integer, Boolean>();
		this.operandList = new ArrayList<String>();
		this.nodes = new ArrayList<BuilderNode>();
		this.stackNodes = new Stack<BuilderNode>();
	}
	
	/**
	 * This method give an operand piece to list
	 * */
	public void addOperandPiece(String piece){
		
		
		if(piece.equalsIgnoreCase("and")){
			if(!sides.containsKey(actualLevel)){
				sides.put(actualLevel, false);
			}
			
			nodes.add(new BuilderNode(actualLevel, sides.get(actualLevel), null, Operators.AND));
			
			sides.replace(actualLevel, sides.get(actualLevel), !sides.get(actualLevel));
			
			
		}else if(piece.equalsIgnoreCase("or")){
			if(!sides.containsKey(actualLevel)){
				sides.put(actualLevel, false);
			}
			
			nodes.add(new BuilderNode(actualLevel, sides.get(actualLevel), null, Operators.OR));
			
			sides.replace(actualLevel, sides.get(actualLevel), !sides.get(actualLevel));
			
		}else{
			this.operandList.add(piece);
			
			if(this.operandList.size() == 3){
				/*Ilyenkor megvan a teljes operandus a listában*/
				
				if(!sides.containsKey(actualLevel + 1)){
					sides.put(actualLevel + 1, false);
				}
				
				Operand op3 = new Operand(operandList.get(0),true);
				Operand op4 = new Operand(operandList.get(2),false);
				Operators oper2 = StringToOperatorEnum.convertStringToEnum(operandList.get(1));
				
				WhereLetter let1 = new WhereLetter(op3, op4, oper2);
				
				
				nodes.add(new BuilderNode(actualLevel + 1, sides.get(actualLevel + 1), let1, null));
				
				sides.replace(actualLevel + 1, sides.get(actualLevel + 1), !sides.get(actualLevel + 1));
				
				this.operandList.clear();
			}
		}
	}
	
	private List<BuilderNode> sortedList(){
		int actualDeepNumber = 1;
		boolean actualNeededSide = false;
		List<BuilderNode> returnList = new ArrayList<BuilderNode>();
	
		
		outer:while(nodes.size() > 0){	
			for(int i=0;i<nodes.size();i++){
				if(actualDeepNumber == 1){
					if(nodes.get(i).getDeep() == 1){
						returnList.add(nodes.get(i));
						nodes.remove(i);
						actualDeepNumber++;
						continue outer;
					}
				}else{
					if(nodes.get(i).getDeep() == actualDeepNumber && nodes.get(i).getSide() == actualNeededSide){
						returnList.add(nodes.get(i));
						nodes.remove(i);
						actualNeededSide = !actualNeededSide;
						continue outer;
					}
				}
			}
			
			boolean rise = true;
			for(int i=0;i<nodes.size();i++){
				if(nodes.get(i).getDeep() == actualDeepNumber){
					rise = false;
				}
			}
			
			if(rise){
				actualDeepNumber++;
			}
			
		}
		this.nodes = returnList;
		return returnList;
	}
	
	public Where build(){
		sortedList();
		
		for(int i = this.nodes.size() - 1;i>=0;i--){
        	stackNodes.push(this.nodes.get(i));
        }
		
		/*for(int i=0;i<this.nodes.size();i++){
			System.out.println(this.nodes.get(i));
		}
		
		for(int i=0;i<stackNodes.size();i++){
			System.out.println(stackNodes.get(i));
		}*/

		for(int i=0;i<this.nodes.size();i++){
        	if(i == 0){
        		stackNodes.pop();
        		
        		if(!stackNodes.isEmpty()){
        			this.nodes.get(i).setLeftChild(stackNodes.pop());
        		}
        		
        		if(!stackNodes.isEmpty()){
        			this.nodes.get(i).setRightChild(stackNodes.pop());
        		}
        	}else{
        		if(this.nodes.get(i).getOperator() != null){
        			if(!stackNodes.isEmpty()){
        				this.nodes.get(i).setLeftChild(stackNodes.pop());
            		}
            		
            		if(!stackNodes.isEmpty()){
            			this.nodes.get(i).setRightChild(stackNodes.pop());
            		}
        		}
        	}
        }
		
		/*Mostmár megvan, hogy melyik csomópontnak mik a gyerekei.Mostmár hoozunk létre a Where objektumot.*/
		
		System.out.println("Ennek: " + this.nodes.get(0));
		System.out.println(this.nodes.get(0).getLeftChild());
		System.out.println(this.nodes.get(0).getRightChild());
		
		for(int i = this.nodes.size() - 1;i>=0;i--){
        	this.nodes.get(i).buildMyself();
        }
		
		Where where = new Where();
		where.setRoot(this.nodes.get(0).getNode());
		
		return where;
	}
	
	public void addRoundBracket(){
		this.actualLevel++;
	}
	
	public void removeRoundBracket(){
		this.actualLevel--;
	}
}