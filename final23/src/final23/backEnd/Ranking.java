package backEnd;

import java.util.PriorityQueue;

public class Ranking {
	PriorityQueue<WebNode> result;
	
	public Ranking(){
		this.result = new PriorityQueue<WebNode>(100, new NodeComparator());
	}
	
	public void add(WebNode treeRoot) {
		this.result.offer(treeRoot);
	}
	
	public String output(){
		//3. print the output in order and remove all element
		StringBuilder sb = new StringBuilder();
		while(!(result.isEmpty())) {
			WebNode out = result.remove();
			sb.append(out.webPage.name);
			sb.append(":");
			sb.append(out.webPage.url + "\n");
			sb.append("\n");
		}
		return sb.toString();
			
	}

}
