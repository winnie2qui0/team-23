import java.util.HashMap;
import java.net.URLDecoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
	public static void main(String args[]) throws IOException {
		long startTime = System.nanoTime();
		Search go = new Search("政大");

		HashMap<String, String> urls = go.query();
		urls.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + "\n" + entry.getValue());
		});
		Ranking rank = new Ranking();
		System.out.println("\nFUCK\n");

		urls.keySet().parallelStream().forEach(title -> {
			System.out.println(title);
			String encodedURL = urls.get(title);
			System.out.println("  encoded url ");
			System.out.println(encodedURL);
			System.out.println("  decoded url ");
			String decodedURL = "";
			try {
				// encoding=utf8
				String url = URLDecoder.decode(encodedURL, "UTF-8");
				// 輸出結果
				System.out.println(url);
				decodedURL = url;
			} catch (UnsupportedEncodingException e) {
				// 例外處理 ...
				System.out.println("url decode problem");
			}

			BuildTree urlTree = new BuildTree(decodedURL, title);
			WebTree tree = null;
			try {
				tree = urlTree.buildIt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				tree.setPostOrderScore();
			} catch (IOException e) {
				e.printStackTrace();
			}
			rank.add(tree.root);
		});

//		for (String title : urls.keySet()) {
//			System.out.println(title);
//			String encodedURL = urls.get(title);
//			System.out.println("  encoded url ");
//			System.out.println(encodedURL);
////			System.out.println("  decoded url ");
////			String decodedURL = "";
////			try {
////				// 進行 URL 百分比解碼
////				String url = URLDecoder.decode(encodedURL, "UTF-8");
////				// 輸出結果
////				System.out.println(url);
////				decodedURL = url;
////			} catch (UnsupportedEncodingException e) {
////				// 例外處理 ...
////				System.out.println("url decode problem");
////			}
//			BuildTree urlTree = new BuildTree(encodedURL, title);//0sec
//			WebTree tree = urlTree.buildIt();//19sec
//			tree.setPostOrderScore();//34sec
//			rank.add(tree.root);//36sec
//		}
		rank.output();
		long endTime = System.nanoTime();
		System.out.println("執行時間： " + (endTime - startTime) / 1000000000 + " S");
	}
}
