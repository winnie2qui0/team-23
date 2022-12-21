import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebTree {
	public WebNode root;
	public List<Callable<WebTree>> tasksScore = new ArrayList<>();
	
	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}
	
	public void setPostOrderScore() throws IOException, InterruptedException{
		setPostOrderScore(root);
	}
	
	private void setPostOrderScore(WebNode startNode) throws IOException, InterruptedException{
		//2. compute the score of children nodes via post-order, then setNodeScore for startNode
		for(WebNode child : startNode.children) {
			this.setPostOrderScore(child);
		}
		if(startNode.children != null) {
			ExecutorService executorService = Executors.newFixedThreadPool(5);
			List<Future<WebTree>> futures = executorService.invokeAll(tasksScore);
			executorService.shutdown();
			startNode.setNodeScore();
		}else {
			tasksScore.add(()->{
				startNode.setNodeScore();
				return null;
	        });
		}
		
	}
	
	public void eularPrintTree(){
		eularPrintTree(root);
	}
	
	private void eularPrintTree(WebNode startNode){
		int nodeDepth = startNode.getDepth();
		
		if(nodeDepth > 1) System.out.print("\n" + repeat("\t", nodeDepth-1));

		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);
		
		//3. print child via pre-order
		for(WebNode child : startNode.children) {
			this.eularPrintTree(child);
		}
		
		System.out.print(")");
				
		if(startNode.isTheLastChild()) System.out.print("\n" + repeat("\t", nodeDepth-2));	
	}
	
	private String repeat(String str, int repeat){
		String retVal = "";
		for(int i = 0; i < repeat; i++){
			retVal += str;
		}
		return retVal;
	}
}