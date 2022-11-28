import java.io.IOException;
import java.util.HashMap;

public class Main {
	
	public static void main(String args[]) throws IOException {
		long startTime=System.nanoTime();
		Search go = new Search("政大");
		HashMap<String, String> urls = go.query();
		Ranking rank = new Ranking();
		for(String title : urls.keySet()) {
//			System.out.print(title);
//			System.out.println(urls.get(title));
			BuildTree urlTree = new BuildTree(urls.get(title), title);
			WebTree tree = urlTree.buildIt();
			tree.setPostOrderScore();
			rank.add(tree.root);
		}
		//以下為test
//		BuildTree urlTree = new BuildTree("https://www.ptt.cc/bbs/joke/M.1669395372.A.65F.html", "pttJoke1");
//		WebTree tree = urlTree.buildIt();
//		tree.setPostOrderScore();
//		rank.add(tree.root);
//		System.out.println(tree.root.children);
//		urlTree = new BuildTree("https://www.ptt.cc/bbs/joke/M.1669037987.A.8BD.html", "pttJoke2");
//		tree = urlTree.buildIt();
//		tree.setPostOrderScore();
//		rank.add(tree.root);
		rank.output();
		long endTime=System.nanoTime();
		System.out.println("執行時間： "+(endTime-startTime)+" NS ");
	}
}
