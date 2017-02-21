import java.io.*;
import java.util.*;

class BleuCalculator{
	public static void main(String [] args){
		ArrayList<String> transList = null;
		ArrayList<String> refList = null;
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
			trans = /*in.readLine();*/ "The gunman was shot dead by police";
			System.out.println(trans);
			transA = trans.split(" ");
			ref = /*in.readLine();*/ "The gunman was shot dead by the police";
			System.out.println(ref);
			refA = ref.split(" ");
			in.close();
		}
		catch(Exception e){
			System.out.println("Error Reading Input");
		}
		for(int i = 1; i < 8; i++){
			transList = ngram.nGram(transA, i);
			refList = ngram.nGram(refA, i);
			printArrayList(transList);
			printArrayList(refList);
			//transList = new ArrayList<String>(Arrays.asList(transA));
			//refList = new ArrayList<String>(Arrays.asList(refA));
			if(score != 0)
				score = score * calculatePercentDiff(transList, refList);
			else
				score = calculatePercentDiff(transList, refList);
			brevity = (float)transList.size() / (float)refList.size();
			System.out.println("BREVITY:" + brevity);
			System.out.println("Score for nGram(" + i + ") :" + root(score, (float)transList.size())*brevity);
		}
		System.out.println("Final Score (All nGrams): " + root(score, (float)transList.size())*brevity);
	}

	static float calculatePercentDiff(ArrayList<String> trans, ArrayList<String> ref){
		float count = 0;
		for(int i = 0; i < trans.size(); i++){
			if(trans.get(i).equals(ref.get(i)))
				count++;
		}
		return count/ref.size();
	}

	static double root(double num, double root) {
	    return Math.pow(Math.E, Math.log(num)/root);
	}

	static void printArrayList(ArrayList<String> arry){
		System.out.print("{");
		for(String s : arry){
			System.out.print(s + " ");
		}
		System.out.print("} \n");
	}
}
