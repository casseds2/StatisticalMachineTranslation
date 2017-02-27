import java.io.*;
import java.util.*;

class BleuCalculator{
	public static void main(String [] args){
		NGram ngram = new NGram();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String cand = ""; //Stores Candidate String
		String ref = ""; //Stores Reference String
		String [] candA = null; //Split candidate into array
		String [] refA = null; //Split reference into array
		float score = 0; // Final BLEU Score
		int nGram = 5; //Dictates how many nGrams will be called (nGram - 1 calls)
		float brevity = 0; //Brevity score

		try{
			System.out.println("Enter 2 Sentences To Be Compared:");
			cand = "The gunman was shot dead by police .";
			//cand = "Israeli officials are responsible for airport security";
			//cand  = in.readLine();
			System.out.println(cand);
			candA = cand.split(" ");
			ref = "The gunman was shot dead by the police .";
			//ref = "airport security Israeli officials are responsible";
			//ref = in.readLine();
			System.out.println(ref);
			refA = ref.split(" ");
			in.close();
		}
		catch(Exception e){
			System.out.println("Error Reading Input");
		}
		brevity = (float) candA.length / (float) refA.length;
		score = calcPrecisionForNgram(candA, refA, nGram);
		System.out.println("Brevity = " + brevity);
		System.out.println("Total Precisions: " + score);
		System.out.println("Final Score (BLEU) : " + root(score, 4)*brevity);
	}

	static float calculatePercentDiff(ArrayList<ArrayList<String>> cand, ArrayList<ArrayList<String>> ref){
		float count = 0;
		for(int i = 0; i < cand.size(); i++){
			if(ref.contains(cand.get(i)))
				count++;
		}
		return count/cand.size();
	}

	static double root(double num, double root) {
	    return Math.pow(Math.E, Math.log(num)/root);
	}

	static void printArrayList(ArrayList<ArrayList<String>> list){
		System.out.print("{");
		for(ArrayList<String> a : list){
			System.out.print("{");
			for(String s : a){
				System.out.print(s + " ");
			}
			System.out.print("}");
		}
		System.out.print("}");
		System.out.println();
	}

	static float calcPrecisionForNgram(String [] candA, String [] refA, int nGram){
		float score = 0;
		float tempScore = 0;
		NGram ngram = new NGram();
		ArrayList<ArrayList<String>> candList = new ArrayList<>();
		ArrayList<ArrayList<String>> refList = new ArrayList<>();
		for(int i = 1; i < nGram; i++){
			candList = ngram.breakIntoNGrams(candA, i);
			refList = ngram.breakIntoNGrams(refA, i);
			if(score == 0){
				score = calculatePercentDiff(candList, refList);
				System.out.println("Current Precison Score : " + score);
			}
			else{
				tempScore = calculatePercentDiff(candList, refList);
				score = score * tempScore;
				System.out.println("Current Precison Score : " + tempScore);
			}
			printArrayList(candList);
			printArrayList(refList);
		}
		return score;
	}
}
