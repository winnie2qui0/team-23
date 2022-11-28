import java.io.IOException;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Main {
	
	public static void main(String args[]) throws IOException {

		long startTime=System.nanoTime();
		Search go = new Search("政大");

		HashMap<String, String> urls = go.query();
		System.out.println(urls);
		Ranking rank = new Ranking();
		for(String title : urls.keySet()) {
			System.out.println(title);
			String encodedURL = urls.get(title);
			System.out.println("  encoded url ");
			System.out.println(encodedURL);
			System.out.println("  decoded url ");
			String decodedURL = "";
			try {
			      // 進行 URL 百分比解碼
			      String url = URLDecoder.decode(encodedURL, "UTF-8");

			      // 輸出結果
			      System.out.println(url);
			      decodedURL = url;

			    } catch (UnsupportedEncodingException e) {
			      // 例外處理 ...
			    	System.out.println("url decode problem");
			    }
			BuildTree urlTree = new BuildTree(decodedURL, title);
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
