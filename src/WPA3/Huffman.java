package WPA3;

/**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA3
 * ******************************
 * STUDENT: You need to write this class. You MUST 
 * implement the public and private methods as shown. 
 * Feel free to include your own private fields and 
 * methods as well if you find it necessary.
 * 
 * NOTE: You do not need to implement your own 
 * priority queue; you can use PriorityQueue 
 * from java API (already included for you in this class).
 * 
 * Also make sure to comment your code, otherwise 2 points 
 * will be deducted.
 */

//Jaye Anne Laguardia
//jjlaguardia
//Feb 20, 2015
//Programming Assignment 3
//CS 241

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Huffman {

	public PriorityQueue<HuffmanNode> pQueue;
	public HashMap<Character, String> charToCode;
	public HashMap<String, Character> codeToChar;
	String text = "";
	String compressedText = "";

	HashMap<Character, Integer> characters; //HashMap of all characters within file and frequency

	// Constructor
	public Huffman(File file) throws IOException {		
		characters = new HashMap();	//initialize
		pQueue = new PriorityQueue();
		charToCode = new HashMap();
		codeToChar = new HashMap();

		//step 1 
		text = getText(file); //turn file into String

		//step 2
		ArrayList<Character> table = new ArrayList<Character>(); //ArrayList of characters in file for reference
		int size = 0; //total amount of characters within file


		//traverse String character by character
		for(int i = 0; i < text.length(); i++)	{
			Character token = text.charAt(i); //get character at index i

			//if the character has been added to HashMap already
			if(characters.containsKey(token) == true)	{
				characters.replace(token, characters.get(token) + 1); //add one to frequency
			}
			//otherwise it hasn't been added
			else	{
				characters.put(token, 1); //add to HashMap with frequency 1
				table.add(token); //add new character to table
			}
			size++; //increment total amount of characters within file
		}

		//step 3
		//for each character in reference table
		for(int i = 0; i < table.size(); i++) {
			pQueue.add(new HuffmanNode(table.get(i), characters.get(table.get(i)))); //add new HuffmanNode to PQueue
		}

		//step 4
		HuffmanNode root = buildTree(size);	//build tree

		//step 5
		buildTable(root);	//build tables
	}

	// This method assumes that the tree and dictionary are already built
	public String compress() {
		//go through each character in uncompressed String
		for(int i = 0; i < text.length(); i++){
			compressedText += charToCode.get(text.charAt(i));	//add to compressed String
		}
		text = "";	//reset uncompressed String
		return compressedText; //return compressed String
	}

	// This method assumes that the tree and dictionary are already built
	public String decompress() {
		String nuevo = "";	//uncompressed String to be built

		//for all chars/Huffman codes in compressed text
		for(int i = 0; i < compressedText.length(); i++) {
			nuevo += compressedText.charAt(i);	//built String

			//if built String is a valid code
			if(codeToChar.containsKey(nuevo)) {
				text += codeToChar.get(nuevo);	//add to uncompressed String
				nuevo = "";	//reset built String
			}
		}
		compressedText = "";	//reset compressed String
		return text;	//return uncompressed String
	}

	// This method builds the table for the compression and decompression
	private void buildTable(HuffmanNode root) {
		postOrder(root, "");	//creates tables using postOder
	}

	// This method builds the tree based on the frequency of every characters
	private HuffmanNode buildTree(int n) {
		//go through priority queue until only 1 node left
		while(1 < pQueue.size())
		{
			HuffmanNode hn = new HuffmanNode();	//new HuffmanNode w/o fields
			hn.left = pQueue.poll();	//make left child
			hn.right = pQueue.poll();	//make right child
			hn.frequency = hn.left.frequency + hn.right.frequency;	//new Huffman Node frequency
			pQueue.add(hn);	//add to PQueue
		}
		return pQueue.poll();	//return new root
	}

	public String getText(File file) throws IOException {
		String get = "";	//String to be constructed
		FileReader scanner = new FileReader(file);	//reads file
		int character = scanner.read();	//char input
		
		//while there are characters, add char to string; then read next char
		while(character != -1) {
			get += (char) character; 
			character = scanner.read();
		}
		return get; //return String
	}

	// This method is used to create the codes starting at root
	private void postOrder(HuffmanNode n, String s) {;
	//step 1
	if(n == null)	//base case to exit
		return;
	else {
		//step 2ys
		postOrder(n.left, s + "0");	//recursive left
		//step 3
		postOrder(n.right, s + "1");	//recursive right
		//step 4
		if(n.left == null && n.right == null) {
			charToCode.put(n.character, s);	//update charToCode map
			codeToChar.put(s, n.character);	//update codeToChar map
		}
	}
	}

	// Internal class
	// !!! DO NOT MAKE ANY CHANGES HERE!!!
	class HuffmanNode implements Comparable<HuffmanNode> {

		public char character;
		public int frequency;
		public HuffmanNode left, right; 

		public HuffmanNode() { }

		public HuffmanNode(char character, int frequency) {
			this.character = character;
			this.frequency = frequency;
		}

		public String toString() {
			return character + " " + frequency;
		}

		public int compareTo(HuffmanNode that) {
			return this.frequency - that.frequency;
		}

	}

}

