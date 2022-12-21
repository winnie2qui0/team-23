import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;




public class Main {
	
	public static void main(String args[]) throws IOException, InterruptedException, ExecutionException, TimeoutException {
//		SpringApplication.run(Main.class, args);

		long startTime=System.nanoTime();
		Search go = new Search("郁方");

		HashMap<String, String> urls = go.query();
		Ranking rank = new Ranking();
		
		List<Callable<WebTree>> tasksTree = new ArrayList<>();
		
		for(String title : urls.keySet()) {
			String encodedURL = urls.get(title);
			String decodedURL = "";
			try {
			      // 進行 URL 百分比解碼
			      String url = URLDecoder.decode(encodedURL, "UTF-8");
			      decodedURL = url;

			    } catch (UnsupportedEncodingException e) {
			      // 例外處理 ...
			    	System.out.println("url decode problem");
			    }
			
			String dURL = decodedURL;
			
			tasksTree.add(()->{
            	BuildTree urlTree = new BuildTree(dURL, title);
    			WebTree tree = urlTree.buildIt();
    			tree.eularPrintTree();
                return tree;
            });
		}	
		
		ExecutorService executorService = Executors.newFixedThreadPool(urls.size());
		List<Future<WebTree>> futures = executorService.invokeAll(tasksTree, 8, TimeUnit.SECONDS);
		
		List<Callable<WebTree>> tasksContent = new ArrayList<>();
		
        for(int i = 0; i < futures.size(); i++) {
	        try {
	        	WebTree tree = (WebTree) futures.get(i).get();
	    		tasksContent.add(()->{
	    			tree.setPostOrderScore();
		    		rank.add(tree.root);
	                return tree;
	    		});
	        }catch(CancellationException e){
	        	
	        }
        }
        
        List<Future<WebTree>> futuresContent = executorService.invokeAll(tasksContent, 8, TimeUnit.SECONDS);
        executorService.shutdown();

		rank.output();
		long endTime=System.nanoTime();
		System.out.println("總執行時間： "+(endTime-startTime)+" NS ");

	}
//	public static void main(String args[]) throws IOException {
//		long startTime = System.nanoTime();
//		Search go = new Search("郁方");
//
//		HashMap<String, String> urls = go.query();
//		urls.entrySet().forEach(entry -> {
//			System.out.println(entry.getKey() + "\n" + entry.getValue());
//		});
//		Ranking rank = new Ranking();
//
//		final long end = System.nanoTime() + TimeUnit.SECONDS.toNanos(20L);
//		urls.keySet().parallelStream().filter(e -> System.nanoTime() <= end).forEach(title -> {
//			System.out.println(title);
//			String encodedURL = urls.get(title);
//			System.out.println("  encoded url ");
//			System.out.println(encodedURL);
//			System.out.println("  decoded url ");
//			String decodedURL = "";
//			try {
//				// encoding=utf8
//				String url = URLDecoder.decode(encodedURL, "UTF-8");
//				// 輸出結果
//				System.out.println(url);
//				decodedURL = url;
//			} catch (UnsupportedEncodingException e) {
//				// 例外處理 ...
//				System.out.println("url decode problem");
//			}
//
//			BuildTree urlTree = new BuildTree(decodedURL, title);
//			WebTree tree = null;
//			try {
//				tree = urlTree.buildIt();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			tree.eularPrintTree();
//			try {
//				tree.setPostOrderScore();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			rank.add(tree.root);
//		});
//
////		for (String title : urls.keySet()) {
////			System.out.println(title);
////			String encodedURL = urls.get(title);
////			System.out.println("  encoded url ");
////			System.out.println(encodedURL);
//////			System.out.println("  decoded url ");
//////			String decodedURL = "";
//////			try {
//////				// 進行 URL 百分比解碼
//////				String url = URLDecoder.decode(encodedURL, "UTF-8");
//////				// 輸出結果
//////				System.out.println(url);
//////				decodedURL = url;
//////			} catch (UnsupportedEncodingException e) {
//////				// 例外處理 ...
//////				System.out.println("url decode problem");
//////			}
////			BuildTree urlTree = new BuildTree(encodedURL, title);//0sec
////			WebTree tree = urlTree.buildIt();//19sec
////			tree.setPostOrderScore();//34sec
////			rank.add(tree.root);//36sec
////		}
//		rank.output();
//		long endTime = System.nanoTime();
//		System.out.println("執行時間： " + (endTime - startTime) / 1000000000 + " S");
//	}
}
