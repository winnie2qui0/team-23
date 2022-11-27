import java.io.IOException;
import java.util.HashMap;

public class Main {
	
	public static void main(String args[]) throws IOException {
		Search go = new Search("Justin");
		HashMap<String, String> urls = go.query();
		Ranking rank = new Ranking();
		for(String title : urls.keySet()) {
			//System.out.print(title);
			//System.out.println(urls.get(title));
			//BuildTree urlTree = new BuildTree(urls.get(title), title);
			//WebTree tree = urlTree.buildIt();
			//rank.add(tree.root);
		}
		BuildTree urlTree = new BuildTree("https://www.ptt.cc/bbs/joke/M.1669395372.A.65F.html", "pttJoke1");
		WebTree tree = urlTree.buildIt();
		tree.setPostOrderScore();
		rank.add(tree.root);
		urlTree = new BuildTree("https://www.ptt.cc/bbs/joke/M.1669037987.A.8BD.html", "pttJoke2");
		tree = urlTree.buildIt();
		tree.setPostOrderScore();
		rank.add(tree.root);
		rank.output();
	}
}
