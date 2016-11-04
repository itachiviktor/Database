package database.queryObject.selectBuild;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.plaf.synth.SynthSpinnerUI;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.OrderBy;
import database.queryObject.OrderBySort;
import database.queryObject.Select;
import database.queryObject.StringToOperatorEnum;
import database.queryObject.Where;
import database.queryObject.WhereLetter;
import database.queryObject.wherebuilder.BuilderNode;

public class SelectNode {
	private String source;
	private String from;
	
	private List<SelectChild> op;
	private List<BuilderNode> buildNodes;/*ebbe lesz a fentiből létrehozva*/
	private Stack<BuilderNode> stackNodes;
	
	private List<SelectNode> nodes;
	private SelectNode parent = null;/*Ha ez null, akkor ez a gyökés select.*/
	
	private InMemoryDatabase db;
	
	private List<Integer> deepOfNots;/*ebbe mélységi értékek vannak, amely mélységekhez not tartozik*/
	
	private Point collidedPoint;
	private boolean xValue = true;
	
	private int actualLevel;
	
	public Map<Integer, Boolean> sides;/*bal-false, true-jobb*/
	
	
	private Operand op3 = null;
	private Operand op4 = null;
	private Operators oper2 = null;
	private int operandNumber;/*Ez ha 3 lesz, akkor állhat össze egy leaf*/
	
	private SelectBuilder selectBuilder;
	
	private ParameterPuffer parameterPuffer;
	private boolean parameter = false;
	
	private OrderBy by;
	private int limit = -1;
	
	
	public SelectNode(InMemoryDatabase db, SelectBuilder selectBuilder) {
		this.parameterPuffer = new ParameterPuffer();
		this.selectBuilder = selectBuilder;
		this.deepOfNots = new ArrayList<Integer>();
		this.buildNodes = new ArrayList<BuilderNode>();
		this.stackNodes = new Stack<BuilderNode>();
		this.db = db;
		this.nodes = new ArrayList<SelectNode>();
		this.op = new ArrayList<SelectChild>();
		actualLevel = 1;
		operandNumber = 1;
		this.sides = new HashMap<Integer, Boolean>();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	private String castDistanceFrom(String value){
		String[] array = value.split("\\.");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<array.length - 1;i++){
			sb.append(array[i]);
		}
		
		return sb.toString();
	}


	public void addOp(String op) {
		SelectChild child = new SelectChild();
		child.setOp(op);
		this.op.add(child);
	}
	
	public void addSelect(SelectNode node){
		SelectChild child = new SelectChild();
		child.setNode(node);
		this.op.add(child);
	}

	public List<SelectNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<SelectNode> nodes) {
		this.nodes = nodes;
	}
	
	public SelectNode getParent(){
		return this.parent;
	}
	
	
	
	public void setParent(SelectNode parent) {
		this.parent = parent;
	}

	/*Ezzel adja vissza magát a szülőnek, ha az kéri.*/
	public Select build(){
		Select sel = new Select(db, this.getSource());
		sel.setFrom(new From(this.from));
		
		if(op.size() == 0){
			sel.where = null;
			return sel;
		}
		
		for(int i=0;i<op.size();i++){
			System.out.println(op.get(i).getOp());
			if(op.get(i).getOp() != null){
				if(op.get(i).getOp().equals("[")){
					parameter = true;
				}else if(op.get(i).getOp().equals("]")){
					if(oper2 != null && oper2 == oper2.COLLIDE){
						if(parameterPuffer.getSelects().containsKey(0) && parameterPuffer.getSelects().containsKey(1)){
							op4 = new Operand(false,db,parameterPuffer.getSelects().get(0), parameterPuffer.getSelects().get(1));
						}else if(parameterPuffer.getStrings().containsKey(0) && parameterPuffer.getSelects().containsKey(1)){
							op4 = new Operand(false,db,parameterPuffer.getStrings().get(0), parameterPuffer.getSelects().get(1));
						}else if(parameterPuffer.getSelects().containsKey(0) && parameterPuffer.getStrings().containsKey(1)){
							op4 = new Operand(false,db,parameterPuffer.getSelects().get(0), parameterPuffer.getStrings().get(1));
						}else if(parameterPuffer.getStrings().containsKey(0) && parameterPuffer.getStrings().containsKey(1)){
							op4 = new Operand(false,db,parameterPuffer.getStrings().get(0), parameterPuffer.getStrings().get(1));
						}
						if(!sides.containsKey(actualLevel + 1)){
							sides.put(actualLevel + 1, false);
						}
						
						this.operandNumber++;

						
						WhereLetter let1 = new WhereLetter(op3, op4, oper2);
						
						op3 = null;
						op4 = null;
						oper2 = null;
						
						boolean not = false;
						for(int j=0;j<this.deepOfNots.size();j++){
							if(this.deepOfNots.get(j) == actualLevel + 1){
								not = true;
								let1.not = true;
								break;
							}
						}
						
						buildNodes.add(new BuilderNode(actualLevel + 1, sides.get(actualLevel + 1), let1, null, not));
						
						sides.replace(actualLevel + 1, sides.get(actualLevel + 1), !sides.get(actualLevel + 1));
						
						
						this.operandNumber = 1;
						
					}else if(op3 != null && op3.longOperand != null && op3.methodCall){
						if(parameterPuffer.getSelects().containsKey(0) && parameterPuffer.getSelects().containsKey(1)){
							op3.setMethodParameters(parameterPuffer.getSelects().get(0), parameterPuffer.getSelects().get(1));
						}else if(parameterPuffer.getStrings().containsKey(0) && parameterPuffer.getSelects().containsKey(1)){
							op3.setMethodParameters(Integer.parseInt(parameterPuffer.getStrings().get(0)), parameterPuffer.getSelects().get(1));
						}else if(parameterPuffer.getSelects().containsKey(0) && parameterPuffer.getStrings().containsKey(1)){
							op3.setMethodParameters(parameterPuffer.getSelects().get(0), Integer.parseInt(parameterPuffer.getStrings().get(1)));
						}else if(parameterPuffer.getStrings().containsKey(0) && parameterPuffer.getStrings().containsKey(1)){
							op3.setMethodParameters(Integer.parseInt(parameterPuffer.getStrings().get(0)), Integer.parseInt(parameterPuffer.getStrings().get(1)));
						}
						
					}else if(op4 != null && op4.longOperand != null && op4.methodCall){
						if(parameterPuffer.getSelects().containsKey(0) && parameterPuffer.getSelects().containsKey(1)){
							op4.setMethodParameters(parameterPuffer.getSelects().get(0), parameterPuffer.getSelects().get(1));
						}else if(parameterPuffer.getStrings().containsKey(0) && parameterPuffer.getSelects().containsKey(1)){
							op4.setMethodParameters(Integer.parseInt(parameterPuffer.getStrings().get(0)), parameterPuffer.getSelects().get(1));
						}else if(parameterPuffer.getSelects().containsKey(0) && parameterPuffer.getStrings().containsKey(1)){
							op4.setMethodParameters(parameterPuffer.getSelects().get(0), Integer.parseInt(parameterPuffer.getStrings().get(1)));
						}else if(parameterPuffer.getStrings().containsKey(0) && parameterPuffer.getStrings().containsKey(1)){
							op4.setMethodParameters(Integer.parseInt(parameterPuffer.getStrings().get(0)), Integer.parseInt(parameterPuffer.getStrings().get(1)));
						}
					}
					
					parameter = false;
					parameterPuffer.cleanBuffer();
				}else{
					if(parameter){
						parameterPuffer.addString(op.get(i).getOp());
					}else{
						addOperandPiece(op.get(i).getOp());
					}
				}
				
			}else{
				if(parameter){
					Select s = op.get(i).getNode().build();
					parameterPuffer.addSelect(s);
				}else{
					Select s = op.get(i).getNode().build();
					addOperandPiece(s);
				}
			}
		}
	
		Where where = whereBuild();
		
		sel.where = where;
		
		if(this.by != null){
			sel.setOrderby(by);
		}
		
		if(this.limit > -1){
			sel.setLimit(limit);
		}
		return sel;
	}
	
	private void addOperandPiece(Select select){
		if(this.operandNumber == 1){
			op3 = new Operand(select,true);
			this.operandNumber++;
		}else if(this.operandNumber == 2){
			throw new RuntimeException("Select cant be an operator!");
		}else if(this.operandNumber == 3){
			if(!sides.containsKey(actualLevel + 1)){
				sides.put(actualLevel + 1, false);
			}
			
			op4 = new Operand(select,false);
			this.operandNumber++;

			
			WhereLetter let1 = new WhereLetter(op3, op4, oper2);
			
			op3 = null;
			op4 = null;
			oper2 = null;
			
			boolean not = false;
			for(int i=0;i<this.deepOfNots.size();i++){
				if(this.deepOfNots.get(i) == actualLevel + 1){
					not = true;
					let1.not = true;
					break;
				}
			}
			
			buildNodes.add(new BuilderNode(actualLevel + 1, sides.get(actualLevel + 1), let1, null, not));
			
			sides.replace(actualLevel + 1, sides.get(actualLevel + 1), !sides.get(actualLevel + 1));
			
			
			this.operandNumber = 1;
			return;
		}
	}
	
	private void addOperandPiece(String piece){
		if(piece.equals("{")){
		
		}
		else if(piece.equals("}")){
			this.selectBuilder.setActualSelect(this.parent);
		}else if(piece.equals("(")){
			addRoundBracket();
		}else if(piece.equals(")")){
			removeRoundBracket();
		}else if(piece.equalsIgnoreCase("not")){
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
			
			buildNodes.add(new BuilderNode(actualLevel, sides.get(actualLevel), null, Operators.AND, not));
			
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
			
			buildNodes.add(new BuilderNode(actualLevel, sides.get(actualLevel), null, Operators.OR, not));
			
			sides.replace(actualLevel, sides.get(actualLevel), !sides.get(actualLevel));
			
		}else{
			if(this.operandNumber == 1){
				if(piece.contains("distanceFrom")){
					op3 = new Operand(this.castDistanceFrom(piece),true);
					op3.methodCall = true;
				}else{
					op3 = new Operand(piece,true);
				}
				this.operandNumber++;
			}else if(this.operandNumber == 2){
				oper2 = StringToOperatorEnum.convertStringToEnum(piece);
				this.operandNumber++;
			}else if(this.operandNumber == 3){
				if(!sides.containsKey(actualLevel + 1)){
					sides.put(actualLevel + 1, false);
				}
				
				if(piece.contains("distanceFrom")){
					op4 = new Operand(this.castDistanceFrom(piece),false);
					op4.methodCall = true;
				}else{
					op4 = new Operand(piece,false);
				}
				
				
				
				
				WhereLetter let1 = new WhereLetter(op3, op4, oper2);
				
				op3 = null;
				op4 = null;
				oper2 = null;
				
				boolean not = false;
				for(int i=0;i<this.deepOfNots.size();i++){
					if(this.deepOfNots.get(i) == actualLevel + 1){
						not = true;
						let1.not = true;
						break;
					}
				}
				
				buildNodes.add(new BuilderNode(actualLevel + 1, sides.get(actualLevel + 1), let1, null, not));

				
				sides.replace(actualLevel + 1, sides.get(actualLevel + 1), !sides.get(actualLevel + 1));
				
				
				
				this.operandNumber = 1;
				return;
			}
		}
	}
	
	private void addRoundBracket(){
		this.actualLevel++;
	}
	
	private void removeRoundBracket(){
		this.actualLevel--;
	}
	
	private Where whereBuild(){
		if(buildNodes.size() == 1){
			/*Ilyenkor nincs OR AND, hanem csak egy leaf a where után*/
			Where where = new Where();
			where.setRoot(this.buildNodes.get(0).getOperand());
			
			return where;
		}

		
		sortedList();

		for(int i = this.buildNodes.size() - 1;i>=0;i--){
        	stackNodes.push(this.buildNodes.get(i));
        }
		

		for(int i=0;i<this.buildNodes.size();i++){
        	if(i == 0){
        		stackNodes.pop();
        		
        		if(!stackNodes.isEmpty()){
        			this.buildNodes.get(i).setLeftChild(stackNodes.pop());
        		}
        		
        		if(!stackNodes.isEmpty()){
        			this.buildNodes.get(i).setRightChild(stackNodes.pop());
        		}
        	}else{
        		if(this.buildNodes.get(i).getOperator() != null){
        			if(!stackNodes.isEmpty()){
        				this.buildNodes.get(i).setLeftChild(stackNodes.pop());
            		}
            		
            		if(!stackNodes.isEmpty()){
            			this.buildNodes.get(i).setRightChild(stackNodes.pop());
            		}
        		}
        	}
        }
		
		/*Mostmár megvan, hogy melyik csomópontnak mik a gyerekei. Mostmár hozzunk létre a Where objektumot.*/
		
		for(int i = this.buildNodes.size() - 1;i>=0;i--){
        	this.buildNodes.get(i).buildMyself();
        }
		
		Where where = new Where();
		where.setRoot(this.buildNodes.get(0).getNode());
		
		return where;
		
	}
	
	public void setOrderByAttribute(String attribute){
		by = new OrderBy(attribute);
	}
	
	public void setOrderBySort(String orderSort){
		
		if(orderSort.equalsIgnoreCase("ASC")){
			by.setSort(OrderBySort.ASC);
		}else if(orderSort.equalsIgnoreCase("DESC")){
			by.setSort(OrderBySort.DESC);
		}
		
	}
	
	public void setLimit(int number){
		this.limit = number;
	}
	
	private List<BuilderNode> sortedList(){
		int actualDeepNumber = 1;
		boolean actualNeededSide = false;
		List<BuilderNode> returnList = new ArrayList<BuilderNode>();
	
		
		outer:while(buildNodes.size() > 0){
			for(int i=0;i<buildNodes.size();i++){
				if(actualDeepNumber == 1){
					if(buildNodes.get(i).getDeep() == 1){
						returnList.add(buildNodes.get(i));
						buildNodes.remove(i);
						actualDeepNumber++;
						continue outer;
					}
				}else{
					if(buildNodes.get(i).getDeep() == actualDeepNumber && buildNodes.get(i).getSide() == actualNeededSide){
						returnList.add(buildNodes.get(i));
						buildNodes.remove(i);
						actualNeededSide = !actualNeededSide;
						continue outer;
					}
				}
			}
			
			boolean rise = true;
			for(int i=0;i<buildNodes.size();i++){
				if(buildNodes.get(i).getDeep() == actualDeepNumber){
					rise = false;
				}
			}
			
			if(rise){
				actualDeepNumber++;
			}
			
		}
		this.buildNodes = returnList;
		return returnList;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<op.size();i++){
			if(op.get(i).getOp() != null){
				sb.append(op.get(i).getOp());
			}else{
				sb.append(op.get(i).getNode().toString());
			}
		}
		return sb.toString();
	}
}
