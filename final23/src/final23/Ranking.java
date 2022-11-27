
import java.util.PriorityQueue;

public class Ranking {
	PriorityQueue<WebNode> result;
	
	public Ranking(){
		this.result = new PriorityQueue<WebNode>(100, new NodeComparator());
	}
	
	public void add(WebNode treeRoot) {
		this.result.offer(treeRoot);
	}
	
	public void output(){
		//3. print the output in order and remove all element
		while(!(result.isEmpty())) {
			StringBuilder sb = new StringBuilder();
			WebNode out = result.remove();
			sb.append(out.webPage.name);
			sb.append(":");
			sb.append(out.nodeScore);
			System.out.println(sb.toString());
		}
		
			
	}

}
