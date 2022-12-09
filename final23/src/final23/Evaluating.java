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
    public String content;
	
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
    	String retVal = "";
    	try {
    		URL u = new URL(urlStr);
    		URLConnection conn = u.openConnection();
    		//set HTTP header
    		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107 Chrome/40.0.2214.38 Safari/537.36");
    		InputStream in = conn.getInputStream();

    		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
    		BufferedReader bufReader = new BufferedReader(inReader);
    		String line = null;

    		while((line = bufReader.readLine()) != null){
    			retVal += line;
    		}
			
    	}catch(IOException e){

    	}
		
		return retVal;
    }
    
    public int countKeyword(String keyword) throws IOException{	
		if (content == null){
		    content = fetchContent();
		}
		
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:
		//Upper case
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
