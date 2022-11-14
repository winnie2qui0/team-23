package final23;
import java.util.HashMap;

public class Evaluating {
	HashMap<String, Double> keywords = new HashMap<String, Double>();
	
	public Evaluating() {
		this.keywords.put("笑死", 7.0);
		this.keywords.put("笑爛", 7.0);
		this.keywords.put("地獄", 5.0);
		this.keywords.put("好笑", 4.0);
		this.keywords.put("有笑", 3.0);
		this.keywords.put("XD", 3.0);
		this.keywords.put("有梗", 5.0);
		this.keywords.put("迷因", 7.0);
		this.keywords.put("meme", 6.0);
		this.keywords.put("哈", 0.5);
		this.keywords.put("超好笑", 7.0);
		this.keywords.put("不好笑", -8.0);
		this.keywords.put("爛梗", -3.0);
		this.keywords.put("還好", -1.5);
		this.keywords.put("下去", -5.0);
		this.keywords.put("沒梗", -5.0);
		this.keywords.put("沒有梗", -10.0);
	}
}
