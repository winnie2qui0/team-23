import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class Evaluating {
	private HashMap<String, Double> keywords = new HashMap<String, Double>();
	private String urlStr;
    private String content;
	
	public Evaluating(String urlStr) {
		this.keywords.put("笑死", 7.0);
		this.keywords.put("笑爛", 7.0);
		this.keywords.put("地獄", 5.0);
		this.keywords.put("好笑", 4.0);
		this.keywords.put("有笑", 3.0);
		this.keywords.put("XD", 3.0);
		this.keywords.put("有梗", 5.0);
		this.keywords.put("迷因", 7.0);
		this.keywords.put("MEME", 6.0);
		this.keywords.put("哈", 0.5);
		this.keywords.put("超好笑", 7.0);
		this.keywords.put("不好笑", -8.0);
		this.keywords.put("爛梗", -3.0);
		this.keywords.put("還好", -1.5);
		this.keywords.put("下去", -5.0);
		this.keywords.put("沒梗", -5.0);
		this.keywords.put("沒有梗", -10.0);
		
		this.urlStr = urlStr;
	}
    
    private String fetchContent() throws IOException{
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
		String retVal = "";
	
		String line = null;
		
		while ((line = br.readLine()) != null){
		    retVal = retVal + line + "\n";
		}
	
		return retVal;
    }
    
    public int countKeyword(String keyword) throws IOException{
		if (content == null){
		    content = fetchContent();
		}
		
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
	
		int retVal = 0;
		int fromIdx = 0;
		int found = -1;
	
		while ((found = content.indexOf(keyword, fromIdx)) != -1){
		    retVal++;
		    fromIdx = found + keyword.length();
		}
		return retVal;
    }
    
    public int setScore() throws IOException{
    	int totalScore = 0;
    	for(String keyword : this.keywords.keySet()){
    		double keyScore = this.countKeyword(keyword) * keywords.get(keyword);
    		totalScore += keyScore;
    	}
    	return totalScore;
    }
}
