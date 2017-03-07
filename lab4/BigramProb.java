import java.util.*;
import java.io.*;

/*
Count pair words are together / count of first word followed by any word
*/

class BigramProb{
	public static void main(String[] args) {
		String input = "";
		try{
			BufferedReader in = new BufferedReader(new FileReader("input.txt"));
			input = "";
			String next = "";
			while((next = in.readLine()) != null){
				input = input + " " + next;
			}
			in.close();
		}
		catch(Exception e){
			System.out.println("I/O Error");
		}
		double score = 1;
		//String input = "<s> a cat sat on the mat </s>";
		String [] splitString = input.split(" ");
		int length = splitString.length;
		int wordFrequency = 0;
		ArrayList<HashMap<String, String>> pairedWords = splitIntoPairs(input);
		HashMap<String, Integer> wordMap = freqCounter(input);
		String firstWord = "";
		String followingWord = "";
		int firstCount = 0;
		int secondCount = 0;
		double finalScore = 1;
		//ArrayList of HashMaps containing single key/value pairs
		//Pairing words effectively
		//If the key is the same, it indicates that the word at splitstring[i] is followed by a word
		//It can increment again if it is follows  by a different word
		//firstCount increments based off the amount of times a word is paired with its following word in a sentence
		for(int i = 0; i < length-1; i++){
			for(HashMap<String, String> h : pairedWords){
				for(Map.Entry<String, String> e : h.entrySet()){
					if(e.getKey().equals(splitString[i])){
						secondCount++;
						if(e.getValue().equals(splitString[i+1]))
							firstCount++;
					}
				}
			}
			double currentScore = (double) firstCount / (double) secondCount;
			System.out.println("Word : " + splitString[i] + " :" + (double) firstCount / (double) secondCount);
			finalScore = finalScore * currentScore;
			firstCount = 0;
			secondCount = 0;
		}
		System.out.println("Final Score : " + finalScore);
	}

	//Returns a wordmap containing a word and its frequency in a string
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

	//multiply all score values of a hashmap
	public static double getScore(HashMap<String, Integer> wordMap, int length, double score){
		for(Map.Entry<String, Integer> e : wordMap.entrySet()){
			score = score * ((double) e.getValue() / (double) length);
		}
		return score;
	}

	//SPlit a string into a list of hashmap string pairs
	public static ArrayList<HashMap<String, String>> splitIntoPairs(String input){
		ArrayList<HashMap<String, String>> listPairs = new ArrayList<>();
		HashMap<String, String> pairedWords = new HashMap<>();
		String [] splitString = input.split(" ");
		String firstWord = splitString[0];
		String followingWord = "";
		int length = splitString.length;
		for(int i = 0; i < length-1; i++){
			firstWord = splitString[i];
			followingWord = splitString[i+1];
			pairedWords.put(firstWord, followingWord);
			listPairs.add(pairedWords);
			pairedWords = new HashMap<>();
		}
		return listPairs;
	}

	//Prints a hashmap
	public static void printHash(HashMap<String, String> wordMap){
		for(Map.Entry<String, String> e : wordMap.entrySet()){
			System.out.println("Word:" + e.getKey() + ", Pair:" + e.getValue());
		}
	}
}