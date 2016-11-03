package database.queryObject.wherebuilder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import database.InMemoryDatabase;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.Select;
import database.queryObject.SelectBuilder;
import database.queryObject.StringToOperatorEnum;
import database.queryObject.Where;
import database.queryObject.WhereLetter;
import datastructure.Instance;

public class WhereBuilder {
	private InMemoryDatabase db;
	private int actualLevel;
	public Map<Integer, Boolean> sides;/*bal-false, true-jobb*/
	
	private List<String> operandList;/*ebben 3 sztring lesz a végén, majd üríteni kell*/
	
	private List<BuilderNode> nodes;
	private Stack<BuilderNode> stackNodes;
	
	private List<Integer> deepOfNots;/*ebbe mélységi értékek vannak, amely mélységekhez not tartozik*/
	
	private Point collidedPoint;
	private boolean xValue = true;
	
	public SelectBuilder actaulSelectBuilder = null;
	
	public Point actualDistanceFromPoint;
	public String actualDistanceFromAttribute;
	private boolean distXValue = true;
	
	private boolean insideOfAngled = false;/*Ez az állapotváltozó jelzi,hogy most angledbe vok vagy sem*/
	
	public WhereBuilder(InMemoryDatabase db) {
		this.actualDistanceFromPoint = new Point();
		this.collidedPoint = new Point();
		this.db = db;
		actualLevel = 1;
		sides = new HashMap<Integer, Boolean>();
		this.operandList = new ArrayList<String>();
		this.nodes = new ArrayList<BuilderNode>();
		this.stackNodes = new Stack<BuilderNode>();
		this.deepOfNots = new ArrayList<Integer>();
	}
	
	/**
	 * This method give an operand piece to list
	 * */
	public void addOperandPiece(String piece){
		if(this.actaulSelectBuilder != null){
			this.actaulSelectBuilder.addOperandPiece(piece);
			return;
		}
		
		if(piece.equalsIgnoreCase("not")){
			this.deepOfNots.add(actualLevel + 1);
		}else if(piece.equalsIgnoreCase("and")){
			if(!sides.containsKey(actualLevel)){
				sides.put(actualLevel, false);
			}
			
			boolean not = false;
			for(int i=0;i<this.deepOfNots.size();i++){
				if(this.deepOfNots.get(i) == actualLevel){
					not = true;
					this.deepOfNots.remove(i);
					break;
				}
			}
			
			nodes.add(new BuilderNode(actualLevel, sides.get(actualLevel), null, Operators.AND, not));
			
			sides.replace(actualLevel, sides.get(actualLevel), !sides.get(actualLevel));
			
			
		}else if(piece.equalsIgnoreCase("or")){
			if(!sides.containsKey(actualLevel)){
				sides.put(actualLevel, false);
			}
			
			boolean not = false;
			for(int i=0;i<this.deepOfNots.size();i++){
				if(this.deepOfNots.get(i) == actualLevel){
					not = true;
					break;
				}
			}
			
			nodes.add(new BuilderNode(actualLevel, sides.get(actualLevel), null, Operators.OR, not));
			
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
				
				
				boolean not = false;
				for(int i=0;i<this.deepOfNots.size();i++){
					if(this.deepOfNots.get(i) == actualLevel + 1){
						not = true;
						let1.not = true;
						break;
					}
				}
				
				nodes.add(new BuilderNode(actualLevel + 1, sides.get(actualLevel + 1), let1, null, not));
				
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
		if(nodes.size() == 1){
			Where where = new Where();
			where.setRoot(this.nodes.get(0).getOperand());
			
			return where;
		}
		
		sortedList();
		
		for(int i = this.nodes.size() - 1;i>=0;i--){
        	stackNodes.push(this.nodes.get(i));
        }
		

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
		
		for(int i = this.nodes.size() - 1;i>=0;i--){
        	this.nodes.get(i).buildMyself();
        }
		
		Where where = new Where();
		where.setRoot(this.nodes.get(0).getNode());
		
		return where;
	}
	
	public void addRoundBracket(){
		if(this.actaulSelectBuilder != null){
			this.actaulSelectBuilder.addRoundBracket();;
			return;
		}
		this.actualLevel++;
	}
	
	public void removeRoundBracket(){
		if(this.actaulSelectBuilder != null){
			this.actaulSelectBuilder.removeRoundBracket();;
			return;
		}
		this.actualLevel--;
	}
	
	/*public void addDistanceFromMethod(String param){
		this.actualDistanceFromAttribute = param;
	}
	
	public void addDistanceFromValue(int value){
		if(this.distXValue){
			this.actualDistanceFromPoint.x = value;
		}else{
			this.actualDistanceFromPoint.y = value;
		}
	}*/
	
	public void addAngledBracket(){
		if(this.actaulSelectBuilder != null){
			this.actaulSelectBuilder.addAngledBracket();
			return;
		}
		this.insideOfAngled = true;
		this.collidedPoint = new Point();
		this.xValue = true;
	}
	
	public void addPointParameter(String value){
		if(this.actaulSelectBuilder != null){
			this.actaulSelectBuilder.addPointParameter(value);
			return;
		}
		
		if(xValue){
			this.collidedPoint.x = Integer.valueOf(value);
			xValue = !xValue;
		}else{
			this.collidedPoint.y = Integer.valueOf(value);
			xValue = !xValue;
		}
	}
	
	public void addPointParameter(Select select){
		if(this.actaulSelectBuilder != null){
			this.actaulSelectBuilder.addPointParameter(select);
			return;
		}
		List<Instance> inst = select.execute();

		if(xValue){
			this.collidedPoint.x = (Integer)inst.get(0).getValue();
			xValue = !xValue;
		}else{
			this.collidedPoint.y = (Integer)inst.get(0).getValue();
			xValue = !xValue;
		}
	}
	
	public void removeAngledBracket(){
		if(this.actaulSelectBuilder != null){
			this.actaulSelectBuilder.removeAngledBracket();;
			return;
		}
		
		if(!sides.containsKey(actualLevel + 1)){
			sides.put(actualLevel + 1, false);
		}
		
		
		Operand op3 = null;
		Operand op4 = null;
		Operators oper2 = null;
		
		
		if(operandList.get(0) != null){
			op3 = new Operand(operandList.get(0),true);
			op4 = new Operand(false, db,String.valueOf(this.collidedPoint.x), String.valueOf(this.collidedPoint.y));
			oper2 = StringToOperatorEnum.convertStringToEnum(operandList.get(1));
		}else if(operandList.get(2) != null){
			op3 = new Operand(operandList.get(0),true);
			op4 = new Operand(false, db,String.valueOf(this.collidedPoint.x), String.valueOf(this.collidedPoint.y));
			oper2 = StringToOperatorEnum.convertStringToEnum(operandList.get(1));
		}
		
		
		
		WhereLetter let1 = new WhereLetter(op3, op4, oper2);
		
		
		boolean not = false;
		for(int i=0;i<this.deepOfNots.size();i++){
			if(this.deepOfNots.get(i) == actualLevel + 1){
				not = true;
				let1.not = true;
				break;
			}
		}
		
		nodes.add(new BuilderNode(actualLevel + 1, sides.get(actualLevel + 1), let1, null, not));
		
		sides.replace(actualLevel + 1, sides.get(actualLevel + 1), !sides.get(actualLevel + 1));
		this.insideOfAngled = false;
		this.operandList.clear();
	}

	public List<BuilderNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<BuilderNode> nodes) {
		this.nodes = nodes;
	}
	
	
	
	
	/*ennetől kezdve ezekkel az alselectt hívjuk tovább..*/
	public void createAnAlSelect(){
		this.actaulSelectBuilder = new SelectBuilder(this.db);
	}
	
	public void setResultObject(String result){
		this.actaulSelectBuilder.setResultObject(result);
	}
	
	public void setFrom(String from){
		this.actaulSelectBuilder.setFrom(from);
	}
	
	public void setOrderByAttribute(String attribute){
		this.actaulSelectBuilder.setOrderByAttribute(attribute);
	}
	
	public void setOrderBySort(String orderSort){
		this.actaulSelectBuilder.setOrderBySort(orderSort);
	}
	
	public void setLimit(int number){
		this.actaulSelectBuilder.setLimit(number);
	}
	
	public void buildAlSelectAndPutAsOperand(){
		
		
		Select select = (Select)this.actaulSelectBuilder.build();
		if(this.insideOfAngled){
			addPointParameter(select);
			return;
		}
		
		
		if(this.operandList.size() == 0){
			
		}else{
			if(!sides.containsKey(actualLevel + 1)){
				sides.put(actualLevel + 1, false);
			}
			
			Operand op3 = new Operand(operandList.get(0),true);
			Operand op4 = new Operand(select, false);
			Operators oper2 = StringToOperatorEnum.convertStringToEnum(operandList.get(1));
			
			WhereLetter let1 = new WhereLetter(op3, op4, oper2);
			
			
			boolean not = false;
			for(int i=0;i<this.deepOfNots.size();i++){
				if(this.deepOfNots.get(i) == actualLevel + 1){
					not = true;
					let1.not = true;
					break;
				}
			}
			
			nodes.add(new BuilderNode(actualLevel + 1, sides.get(actualLevel + 1), let1, null, not));
			
			sides.replace(actualLevel + 1, sides.get(actualLevel + 1), !sides.get(actualLevel + 1));
			
			this.operandList.clear();
		}
		
		this.actaulSelectBuilder = null;
	}
	
}