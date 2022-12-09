import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

		long startTime=System.nanoTime();
		Search go = new Search("政大");

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
}
