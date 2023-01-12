package backEnd;

import java.util.ArrayList;
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
			ArrayList<String> list = new ArrayList<String>(); 
			for(char ch : out.webPage.name.toCharArray()) {
				list.add(Character.toString(ch));
			}
			for(int i = 0; i<list.size(); i++) {
				if(i != 0 || i != list.size()) {
					if(list.get(i).equals("\"")) {
						list.remove(i);
					}
				}
			}
			StringBuilder webName = new StringBuilder();
			webName.append("\"");
			for (String s : list) {
				webName.append(s);
			}
			webName.append("\"");
//			object.put("webName", out.webPage.name.replaceAll("&quot;", "\""));
			object.put("webName", webName);
//			sb.append(out.webPage.name);
//			sb.append(":");
//			sb.append(out.webPage.url + "\n");
			object.put("webUrl", out.webPage.url);
//			sb.append("\n");
			array.add(object);
			
		}
		return array;
			
	}

}
