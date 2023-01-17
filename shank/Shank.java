package shank;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Shank {

	public static void main(String[] args) throws IOException {

		String filename;
		Scanner scanner = new Scanner(System.in);
		ArrayList<String> testFiles = new ArrayList<>();
		ArrayList<String> fileLines = new ArrayList<>();
		ArrayList<Token> tokenList = new ArrayList<>();
		ArrayList<Node> parsedList = new ArrayList<>();

		testFiles = getTestFiles();
		menu(testFiles);

		//Necessary functions DO NOT COMMENT OR DELETE THEM
		filename = menuChoice(testFiles, scanner);
		fileLines = getAllFileLines(filename);
		tokenList = makeTokenList(fileLines);

//		System.out.println("Printing Token List...");
//		printTokenList(fileLines, tokenList);

//		System.out.println("Printing Parsed Nodes...");
		parsedList = parseNodes(fileLines, tokenList);
//		printParsedNodes(parsedList);
		
		Interpreter.addBuiltinFunctions();
		Interpreter.interpretFunction((FunctionASTNode)Interpreter.allFunctions.get("start"), null);
		
//		System.out.println("Printing Resolutions...");
//		printResolutions(parsedList);

/*
 * + NS W
 * - NS W
 * \ NS W
 * * NS W
 * % NS W
 */
		
		scanner.close();
	}



	public static ArrayList<String> getTestFiles() {
		File directoryPath = new File("D:\\UAlbany\\Fall 2022\\Principles Programming\\Shank\\Shank");
		String contents[] = directoryPath.list();
		ArrayList<String> testFiles = new ArrayList<>();

		for (String string : contents) {
			if (string.contains(".txt")) {
				testFiles.add(string);
			}
		}
		return testFiles;
	}

	public static void menu(ArrayList<String> testFiles) {
		System.out.println("List of test files found");
		System.out.println("------------------------");
		for (int i = 0; i < testFiles.size(); i++) {
			System.out.println( (i + 1) + ")" + testFiles.get(i));
		}
	}

	public static String menuChoice(ArrayList<String> testFiles, Scanner scanner) {
		String filename;
		int fileChoice;

		System.out.print("\nChoose a file: ");
		fileChoice = scanner.nextInt();

		if (fileChoice > -1 && fileChoice < testFiles.size() + 1) {
			filename = testFiles.get(fileChoice - 1);
			System.out.println("\n" + filename);
			System.out.println("---------------------");
		}else 
			throw new ShankException("Invalid choice");


		return filename;
	}

	public static ArrayList<String> getAllFileLines(String filename) {
		try {
			Path path = Paths.get(filename);
			ArrayList<String> fileLines = new ArrayList<>();

			fileLines = (ArrayList<String>) Files.readAllLines(path);
			return fileLines;

		}catch(Exception e){
			System.out.printf("Error gathering data from file: %s\n", filename);
		}

		return null;
	}

	public static ArrayList<Token> makeTokenList(ArrayList<String> fileLines){

		ArrayList<Token> tokenList = new ArrayList<>();
		Lexer lexer = new Lexer();

		for (String data : fileLines) {
			lexer.setLine(data);
			tokenList = lexer.lex();
		}
		return tokenList;
	}

	public static void printTokenList(ArrayList<String> fileLines, ArrayList<Token> tokenList) {
		//Prints the list of tokens gathered from file

		for (int i = 0; i < tokenList.size(); i++) {


			if (tokenList.get(i).getToken_enum() == Token_enum.EOL) {
				System.out.print(tokenList.get(i) + " ");
				System.out.println();

			}else
				System.out.print(tokenList.get(i) + " ");

		}
		System.out.println();
	}

	public static Node parseNode(ArrayList<Token> tokenList) {
		Parser parser = new Parser(tokenList);
		Node output =  parser.parse();

		return output;
	}

	public static ArrayList<Node> parseNodes(ArrayList<String> fileLines, ArrayList<Token> tokenList) {

		ArrayList<Node> parsedList = new ArrayList<>();
		Node outputNode;
		
		while (!tokenList.isEmpty()) {
			outputNode = parseNode(tokenList);
			parsedList.add(outputNode);
		}
		
		return parsedList;
	}

	public static void printParsedNodes(ArrayList<Node> parsedList) {
		for (int i = 0; i < parsedList.size(); i++) {

			System.out.println((i + 1 ) + ") " + parsedList.get(i));

		}
		System.out.println();
	}

//	public static void printResolutions(ArrayList<Node> parsedList) {
//		for (int i = 0; i < parsedList.size(); i++) {
//
//			System.out.println((i + 1 ) + ") " + Interpreter.resolve(parsedList.get(i)));
//
//		}
//	}

}
