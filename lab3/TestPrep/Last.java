import java.io.*;
import java.util.ArrayList;

class Last{
	public static void main(String[] args) {
		float bestOne = 0;
		float bestTwo = 0;
		float bestThree = 0;
		float bestFour = 0;
		float brevity = 0;
		float bestLength = 0;
		String output = "";
		String ref = "";
		String [] outputArray = null;
		String [] refArray = null;
		ArrayList<String> allRefs = new ArrayList<>();
		try{
			PrintWriter pw = new PrintWriter(new File("Output.txt"));
			BufferedReader br = new BufferedReader(new FileReader("Trans.txt"));
			output = br.readLine();
			pw.println("Output : " + output);
			br = new BufferedReader(new FileReader("Refs.txt"));
			while((ref = br.readLine()) != null){
				pw.println("Ref : " + ref);
				allRefs.add(ref);
			}
			for(int i = 1; i < 5; i++){
				outputArray = output.split(" ");
				for(String s : allRefs){
					refArray = s.split(" ");
					if(Math.abs(outputArray.length - refArray.length) < Math.abs(outputArray.length - bestLength))
						bestLength = refArray.length;
					if(outputArray.length == refArray.length)
						bestLength = refArray.length;
					ArrayList<ArrayList<String>> nOut = calcNGram(output, i);
					ArrayList<ArrayList<String>> nRef = calcNGram(s, i);
					float precision = calcPrecision(nOut, nRef);
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
					}
				}
			}
			if(outputArray.length < bestLength)
				brevity = (float) outputArray.length / brevity;
			if(outputArray.length > bestLength)
				brevity = brevity / (float) outputArray.length;
			if(outputArray.length == bestLength)
				brevity = 1;
			float totalPrecision = bestOne * bestTwo * bestThree * bestFour;
			float bleuScore = (float) Math.pow(totalPrecision, 0.25) * brevity;
			pw.println("Best NGram 1 : " + bestOne);
			pw.println("Best NGram 2 : " + bestTwo);
			pw.println("Best NGram 3 : " + bestThree);
			pw.println("Best NGram 4 : " + bestFour);
			pw.println("Total Precuision : " + totalPrecision);
			pw.println("Best Length : " + bestLength);
			pw.println("Brevity : " + brevity);
			pw.println("Bleu Score : " + bleuScore);
			pw.close();
		}
		catch(IOException e){
			System.out.println("I/O Error");
		}
	}

	static float calcPrecision(ArrayList<ArrayList<String>> out, ArrayList<ArrayList<String>> ref){
		ArrayList<ArrayList<String>> used = new ArrayList<>();
		float similars = 0;
		for(int i = 0; i < out.size(); i++){
			if(ref.contains(out.get(i)) && !used.contains(out.get(i))){
				used.add(out.get(i));
				similars++;
			}
		}
		return similars / (float) out.size();
	}

	static ArrayList<ArrayList<String>> calcNGram(String s, int n){
		ArrayList<ArrayList<String>> allSets = new ArrayList<>();
		ArrayList<String> oneSet = new ArrayList<>();
		String [] array = s.split(" ");
		for(int i = 0; i < array.length-(n-1); i++){
			for(int j = i; j < n+1; j++){
				oneSet.add(array[j]);
			}
			allSets.add(oneSet);
			oneSet = new ArrayList<>();
		}
		return allSets;
	}
}