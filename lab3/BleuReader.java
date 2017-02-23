import java.io.*;
import java.util.ArrayList;

class BleuReader{
	public static void main(String [] args){
		ArrayList<ArrayList<String>> candList = null;
		ArrayList<ArrayList<String>> refList = null;
		NGram ngram = new NGram();
		String cand = ""; //Stores Candidate String
		String ref = ""; //Stores Reference String
		String [] candA = null; //Split candidate into array
		String [] refA = null; //Split reference into array
		float score = 0; // Final BLEU Score
		float currentNGram = 0; //Score of the current nGram
		int nGram = 5; //Dictates how many nGrams will be called (nGram - 1 calls)
		float brevity = 0; //Brevity score
		int flag = 0; //Personal flag to see what brevity score is;

		ArrayList<String> allRefs = new ArrayList<>();

		try{
			BufferedReader in = new BufferedReader(new FileReader("TestStrings.txt"));
			cand = in.readLine();
			System.out.println(cand);
			while((ref = in.readLine()) != null){
				allRefs.add(ref);
			}
			in.close();

			candA = cand.split(" ");
			candList = ngram.nGram(candA, i);

			for(String s : allRefs){

				refA = s.split(" ");
				refList = ngram.nGram(refA, i);
				for(ArrayList a : refList){

				}

			}

		}
		catch(Exception e){
			System.out.println("I/O Error");
		}
		printArrayList(allRefs);
	}

	static void printArrayList(ArrayList<String> list){
		for(String s : list){
			System.out.println(s);
		}
		System.out.println();
	}
}