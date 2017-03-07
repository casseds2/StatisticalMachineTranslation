import java.util.*;

class FreqCounter{
	public static void main(String[] args) {
		String input = "the cat sat on the mat with a cat";
		String [] splitString = input.split(" ");
		int length = splitString.length;
		HashMap<String, Integer> wordMap = freqCounter(input);
		for(Map.Entry<String, Integer> e : wordMap.entrySet()){
			System.out.println("The word '" + e.getKey() + "' frequency is " + (double) e.getValue() / (double) length);
		}
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
}