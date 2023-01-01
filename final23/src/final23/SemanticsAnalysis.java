import java.util.ArrayList;

public class SemanticsAnalysis {
	
	public String find(String input, ArrayList<String> lst){
		int maxValue = -1;
		String mostRel = null;
		
		for(int i = 0; i < lst.size(); i++){
			int lcs = findLCS(lst.get(i), input);

			if(lcs > maxValue){
				maxValue = lcs;
				mostRel = lst.get(i);
			}
		}
		return mostRel;
	}
	
	public int findLCS(String x, String y){
		//1. Implement the LCS algorithm
		int[][] matLCS = new int[x.length()+1][y.length()+1];
		for(int i = 0; i < x.length();i++) {
			matLCS[i][0] = 0;
		}
		for(int j = 0; j < y.length();j++) {
			matLCS[0][j] = 0;
		}
		for(int i = 1; i <= x.length();i++) {
			for(int j = 1; j <= y.length();j++) {
				if(x.substring(i-1, i).equals(y.substring(j-1, j))) {
					matLCS[i][j] = matLCS[i-1][j-1] + 1;
				}else {
					matLCS[i][j] = Math.max(matLCS[i-1][j], matLCS[i][j-1]);
				}
			}
		}

		return matLCS[x.length()][y.length()]; 
	}
}
