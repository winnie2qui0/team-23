package backEnd;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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
		Scanner sc = new Scanner(System.in);
		UserInput input = new UserInput(sc.next());
		sc.close();
		
		long startTime=System.nanoTime();
		Search main1 = new Search(input.input + "+笑話");
		Search main2 = new Search(input.input + "+joke");
		
		HashMap<String, String> urls = new HashMap<String, String>();
		List<Callable<HashMap<String, String>>> tasksUrls = new ArrayList<>();
		ArrayList<String> relList = main1.semanticsAnalysis();
		ArrayList<String> relList2 = main2.semanticsAnalysis();
		relList.addAll(relList2);
		
		int limitation = 15;
		if(!(relList.isEmpty())) {
			SemanticsAnalysis sa = new SemanticsAnalysis();
			String mostRel = sa.find(input.input, relList);
			System.out.println(mostRel);
			Search sub = new Search(mostRel);
			tasksUrls.add(()->{
				HashMap<String, String> urls3 = sub.query(10);
				urls3.forEach((k, v) -> urls.putIfAbsent(k, v));
				return urls3;
	        });
		}
		tasksUrls.add(()->{
			HashMap<String, String> urls1 = main1.query(limitation);
			urls1.forEach((k, v) -> urls.putIfAbsent(k, v));
			return urls1;
        });
		tasksUrls.add(()->{
			HashMap<String, String> urls2 = main2.query(limitation);
			urls2.forEach((k, v) -> urls.putIfAbsent(k, v));
			return urls2;
        });
		
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		List<Future<HashMap<String, String>>> futures = executorService.invokeAll(tasksUrls, 10, TimeUnit.SECONDS);

//		for(String title : urls.keySet()) {
//			System.out.print(title + ":");
//			System.out.println(urls.get(title));
//		}
//		System.out.println(urls);

		List<Callable<WebTree>> tasksTree = new ArrayList<>();
//		
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
//			System.out.println(dURL);
			
			tasksTree.add(()->{
            	BuildTree urlTree = new BuildTree(dURL, title);
    			WebTree tree = urlTree.buildIt();
    			System.out.println(tree.root.webPage.name);
                return tree;
            });
		}	

		List<Future<WebTree>> futuresTree = executorService.invokeAll(tasksTree, 10, TimeUnit.SECONDS);
		
		List<Callable<WebTree>> tasksContent = new ArrayList<>();
		Ranking rank = new Ranking();
		Ranking failureRank = new Ranking();
		
        for(int i = 0; i < futuresTree.size(); i++) {
	        try {
	        	WebTree tree = (WebTree) futuresTree.get(i).get();
	    		tasksContent.add(()->{
	    			tree.setPostOrderScore();
	    			if(tree.root.nodeScore > 10) {
	    				rank.add(tree.root);
	    			}else{
	    				failureRank.add(tree.root);
	    			}
	    			tree.eularPrintTree();
	                return tree;
	    		});
	        }catch(CancellationException e){
	        	
	        }
        }
        
        List<Future<WebTree>> futuresContent = executorService.invokeAll(tasksContent);

		rank.output();
		System.out.println("Fails:");
		failureRank.output();
		long endTime=System.nanoTime();
		System.out.println("總執行時間： "+(endTime-startTime)+" NS ");
		
		System.exit(0);
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
