package datastructure;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ClassDefinition {
	public Map<String, String> attributes;
	/*attribute neve, attribute típusa*/
	
	public String className;
	
	public ClassDefinition(String className) {
		attributes = new HashMap<String, String>();
		this.className = className;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public JSONObject getJSONrepresentation(){
		JSONObject inner = new JSONObject();
		
		for(String x : attributes.keySet()){
			inner.put(x, attributes.get(x));
		}
		
		
		return inner;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("className: " + className);
		
		for(String x: attributes.keySet()){
			sb.append(" " + x + " : " + attributes.get(x));
		}
		return sb.toString();
	}
		
}