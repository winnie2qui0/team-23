package final23;
import java.util.PriorityQueue;

public class Ranking {
	PriorityQueue<WebNode> result;
	
	public Ranking(){
		this.result = new PriorityQueue<WebNode>(100, new NodeComparator());
	}
	
	public void output(){
		//3. print the output in order and remove all element
		StringBuilder sb = new StringBuilder();
		while(!(result.isEmpty())) {
			sb.append(result.remove().toString());
		}
		
		System.out.println(sb.toString());	
	}

}
