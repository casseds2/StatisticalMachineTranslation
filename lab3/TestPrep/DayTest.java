import java.io.*;
import java.util.ArrayList;

class DayTest{
	public static void main(String [] args){
		ArrayList<String> allRefs = new ArrayList<>();
		float bestLength = 0;
		float bestOne = 0;
		float bestTwo = 0;
		float bestThree = 0;
		float bestFour = 0;
		float bestFive = 0;
		float brevity = 0;
		String [] outputArray = null;
		String [] refArray = null;
		int nGram = 6;
		try{
			PrintWriter pw = new PrintWriter(new File("Output.txt"));
			BufferedReader br = new BufferedReader(new FileReader("Trans.txt"));
			String output = br.readLine();
			pw.println("Output : " + output);
			br = new BufferedReader(new FileReader("Refs.txt"));
			String ref = "";
			while((ref = br.readLine()) != null){
				pw.println("Reference : " + ref);
				allRefs.add(ref);
			}
			br.close();
			for(int i = 1; i < nGram; i++){
				outputArray = output.split(" ");
				for(String s : allRefs){
					refArray = s.split(" ");
					if(refArray.length == outputArray.length)
						bestLength = refArray.length;
					if(Math.abs(outputArray.length - refArray.length) < (outputArray.length - bestLength))
						bestLength = refArray.length;
					ArrayList<ArrayList<String>> transGram = calculateNGram(output, i);
					ArrayList<ArrayList<String>> refGram = calculateNGram(s, i);
					float precision = calculatePrecision(transGram, refGram);
					switch(i){
						case 1 :
							if(precision > bestOne)
								bestOne = precision;
							break;	
						case 2 :
							if(precision > bestTwo)
								bestTwo = precision;
							break;
						case 3 :
							if(precision > bestThree)
								bestThree = precision;
							break;
						case 4 :
							if(precision > bestFour)
								bestFour = precision;
							break;
						case 5 :
							if(precision > bestFive)
								bestFive = precision;
							break;		
					}
				}
			}
			if(outputArray.length < bestLength)
				brevity = (float) outputArray.length / bestLength;
			if(outputArray.length > bestLength)
				brevity = bestLength / (float) outputArray.length;
			if(outputArray.length == bestLength)
				brevity = 1;
			float totalPrecision = bestOne * bestTwo * bestThree * bestFour * bestFive;
			float bleuScore = (float) Math.pow(totalPrecision, 0.2) * brevity;
			pw.println("Best Precision (NGram 1) : " + bestOne);
			pw.println("Best Precision (NGram 2) : " + bestTwo);
			pw.println("Best Precision (NGram 3) : " + bestThree);
			pw.println("Best Precision (NGram 4) : " + bestFour);
			pw.println("Best Precision (NGram 5) : " + bestFive);  
			pw.println("Total Precision : " + totalPrecision);
			pw.println("Best Length : " + bestLength);
			pw.println("Brevity : " + brevity);
			pw.println("Bleu Score : " + bleuScore);
			pw.close();
		}
		catch(IOException e){
			System.out.println("I/O Error");
		}
	}

	static float calculatePrecision(ArrayList<ArrayList<String>> out, ArrayList<ArrayList<String>> ref){
		float similarCount = 0;
		ArrayList<ArrayList<String>> used = new ArrayList<>();
		for(int i = 0; i < out.size(); i++){
			if(ref.contains(out.get(i)) && !used.contains(out.get(i))){
				similarCount++;
				used.add(out.get(i));
			}
		}
		return similarCount / (float) out.size();
	}

	static ArrayList<ArrayList<String>> calculateNGram(String s, int n){
		ArrayList<ArrayList<String>> allGrams = new ArrayList<>();
		ArrayList<String> oneGram = new ArrayList<>();
		String [] array = s.split(" ");
		for(int i = 0; i < array.length-(n-1); i++){
			for(int j = i; j < i + n; j++){
				oneGram.add(array[j]);
			}
			allGrams.add(oneGram);
			oneGram = new ArrayList<>();
		}
		return allGrams;
	}
}