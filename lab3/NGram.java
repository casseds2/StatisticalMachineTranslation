import java.util.*;

class NGram{
	public static void main(String [] args){

		String sample = "cat sat on the mat";

		String [] tokens = sample.split(" ");
		int n = 3;

		ArrayList <String> list = new ArrayList<>();
		list = nGram(tokens, n);

		for(String s : list){
			System.out.print(s + ",");
		}
		System.out.println();
	}

	NGram(){}

	/**Function Does For Any number of N**/
	static ArrayList<String> nGram(String [] tokens, int n){
		ArrayList<String> list = new ArrayList<>();
		for(int i = 0; i < tokens.length-(n-1); i++){
			int position = i;
			int count = 0;
			while(count < n){
				list.add(tokens[position]);
				count++;
				position++;
			}
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
