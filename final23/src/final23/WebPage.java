
import java.io.IOException;

public class WebPage {
	public String url;
	public String name;
	public Evaluating eva;
	public double score;
	
	public WebPage(String url, String name){
		this.url = url;
		this.name = name;
		this.eva = new Evaluating(url);	
	}
	
	public void passContent(String content) {
		this.eva.content = content;
	}
	
	public void setScore() throws IOException{
		score = this.eva.setScore();
	}	
}