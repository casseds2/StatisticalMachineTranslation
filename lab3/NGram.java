import java.util.*;

/**Class To Show NGram Calculations**/
class NGram{
	public static void main(String [] args){

		String sample = "cat sat on the mat";

		String [] tokens = sample.split(" ");
		int n = 1;

		ArrayList <ArrayList<String>> list = new ArrayList<>();
		list = nGram(tokens, n);

		for(ArrayList<String> a : list){
			for(String s : a){
				System.out.print(s + ", ");
			}
		}
		System.out.println();
	}

	NGram(){}

	/**Function Does For Any number of N**/
	static ArrayList<ArrayList<String>> nGram(String [] tokens, int n){
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		ArrayList<String> setOfWords = new ArrayList<>();
		for(int i = 0; i < tokens.length-(n-1); i++){
			int position = i;
			int count = 0;
			while(count < n){
				setOfWords.add(tokens[position]);
				count++;
				position++;
			}
			list.add(setOfWords);
			setOfWords = new ArrayList<>();
			count = 0;
		}
		return list;
	}

	static void oneGram(String [] tokens){
		System.out.println("//////////////");
		System.out.println("1 Gram");
		for(int i = 0; i < tokens.length; i++){
			System.out.print(tokens[i] + " ");
		}
		System.out.println();
	}

	static void twoGram(String [] tokens){
		System.out.println("//////////////");
		System.out.println("2 Gram");
		System.out.print("{");
		for(int i = 0; i < tokens.length-1; i++){
			System.out.print(tokens[i] + ", " + tokens[i+1] + "," + " ");
		}
		System.out.print("}");
		System.out.println();
	}

	static void threeGram(String [] tokens){
		System.out.println("//////////////");
		System.out.println("3 Gram");
		System.out.print("{");
		for(int i = 0; i < tokens.length-2; i++){
			System.out.print(tokens[i] + "," + tokens[i+1] + "," + tokens[i + 2] + ", ");
		}
		System.out.print("}");
		System.out.println();
	}
		static void fourGram(String [] tokens){
		System.out.println("//////////////");
		System.out.println("3 Gram");
		System.out.print("{");
		for(int i = 0; i < tokens.length-3; i++){
			System.out.print(tokens[i] + "," + tokens[i+1] + "," + tokens[i + 2] + "," + tokens[i + 3] + ", ");
		}
		System.out.print("}");
		System.out.println();
	}
}
