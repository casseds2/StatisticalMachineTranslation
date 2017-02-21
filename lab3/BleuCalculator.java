import java.io.*;
import java.util.*;

class BleuCalculator{
	public static void main(String [] args){
		ArrayList<String> transList = new ArrayList<>();
		ArrayList<String> refList = new ArrayList<>();
		NGram ngram = new NGram();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String trans = "";
		String [] transA = null;
		String [] refA = null;
		String ref = "";
		float score = 0;
		float brevity = 0;
		try{
			System.out.println("Enter 2 Sentences To Be Compared:");
			trans = in.readLine();
			transA = trans.split(" ");
			ref = in.readLine();
			refA = ref.split(" ");
			in.close();
		}
		catch(Exception e){
			System.out.println("Error Reading Input");
		}
		for(int i = 1; i < 5; i++){
			transList = ngram.nGram(transA, i);
			refList = ngram.nGram(refA, i);
			brevity = (float)transList.size() / (float)refList.size();
			System.out.println("BREVITY:" + brevity);
			if(score != 0)
				score = score * calculator(transList, refList);
			else
				score = calculator(transList, refList);
			System.out.println("Score for nGram(" + i + ") :" + root(score, 4)*brevity);
		}
		System.out.println("Final Score (All nGrams): " + root(score, 4)*brevity);
	}

	static float calculator(ArrayList<String> trans, ArrayList<String> ref){
		float count = 0;
		for(int i = 0; i < trans.size(); i++){
			if(trans.get(i).equals(ref.get(i)))
				count++;
		}
		return count/ref.size();
	}

	public static double root(double num, double root) {
	    return Math.pow(Math.E, Math.log(num)/root);
	}
}
