package backEnd;

import java.util.PriorityQueue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Ranking {
	PriorityQueue<WebNode> result;
	
	public Ranking(){
		this.result = new PriorityQueue<WebNode>(100, new NodeComparator());
	}
	
	public void add(WebNode treeRoot) {
		this.result.offer(treeRoot);
	}
	
	public JSONArray output(){
		//3. print the output in order and remove all element
		StringBuilder sb = new StringBuilder();
		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		while(!(result.isEmpty())) {
			WebNode out = result.remove();
			System.out.println("\n\nabc");
			if(out.webPage.name.indexOf("&quot讚美馬英九徵文比賽&quot") != -1) {
				continue;
			}
			System.out.println(out.webPage.name);
			System.out.println("\n\naaa");
			object = new JSONObject();
			object.put("webName", out.webPage.name.replaceAll("&quot;", "\""));
//			sb.append(out.webPage.name);
			sb.append(":");
//			sb.append(out.webPage.url + "\n");
			object.put("webUrl", out.webPage.url);
			sb.append("\n");
			array.add(object);
			
		}
		return array;
			
	}

}
