import java.util.*;

class UnigramModel{
	public static void main(String[] args) {
		double score = 1;
		String input = "the cat sat on the mat with a cat";
		String [] splitString = input.split(" ");
		int length = splitString.length;
		HashMap<String, Integer> wordMap = freqCounter(input);
		score = getScore(wordMap, length);
		System.out.println("Score : " + score);
	}

	public static HashMap<String, Integer> freqCounter(String input){
		HashMap<String, Integer> wordMap = new HashMap<>();
		String [] splitString = input.split(" ");
		for(int i = 0; i < splitString.length; i++){
			if(wordMap.containsKey(splitString[i])){
				wordMap.put(splitString[i], wordMap.get(splitString[i]) + 1);
			}
			else{
				wordMap.put(splitString[i], 1);
			}
		}
		return wordMap;
	}

	public static double getScore(HashMap<String, Integer> wordMap, int length){
		double score = 1;
		for(Map.Entry<String, Integer> e : wordMap.entrySet()){
			score = score * ((double) e.getValue() / (double) length);
		}
		return score;
	}
}