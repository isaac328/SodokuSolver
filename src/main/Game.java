package main;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class Game implements Observer{
	private Cell[][] board;
	private HashSet<String> characters;
	private int size;
	private int blockSize;

	public Game(InputStream in)
	{
		characters = new HashSet<>();
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			size = Integer.parseInt(reader.readLine());
			blockSize = (int)Math.sqrt(size);
			board = new Cell[size][size];

			String[] stringCharacters = reader.readLine().split("\\s");
			for(String character : stringCharacters){
				characters.add(character);
			}

			for(int j = 0; j < size; j++){
				String[] boardLine = reader.readLine().split("\\s");
				boolean valid = validateCharacters(boardLine);
				if(!valid)
					throw new Exception("Invalid Characters");

				for(int i = 0; i < size; i++){
					board[i][j] = new Cell(boardLine[i], Arrays.copyOf(characters.toArray(),
							characters.toArray().length, String[].class), i, j);
					board[i][j].Attach(this);
				}
			}
		}
		catch (Exception ex){ System.out.println(ex.toString()); }
		finally {
			try{ in.close(); }catch (Exception ex){ System.out.println(ex.toString());}
		}
	}

	public void updateRow(Cell cell) throws Exception{
		int y = cell.getyCoord();

		String value = cell.toString();

		for(Cell c : getRow(y)){
			c.removePossibleValue(value);
		}
	}

	public void updateColumn(Cell cell) throws Exception{
		int x = cell.getxCoord();

		String value = cell.toString();

		for(Cell c : getColumn(x)){
			c.removePossibleValue(value);
		}
	}

	public void updateBlock(Cell cell){
		String value = cell.toString();

		int x = cell.getxCoord();
		int y = cell.getyCoord();

		int blockX = (int)Math.floor(x/blockSize);
		int blockY = (int)Math.floor(y/blockSize);

		for(int i = 0; i < blockSize; i++){
			for(int j = 0; j < blockSize; j++){
				board[blockX * blockSize + i][blockY * blockSize + j].removePossibleValue(value);
			}
		}
	}


	public int getSize(){
		return this.size;
	}

	public Cell[] getRow(int row) throws Exception{
		if(row >= size)
			throw new Exception("Index out of bounds");

		Cell[] boardRow = new Cell[size];

		for(int i = 0; i < size; i++){
			boardRow[i] = board[i][row];
		}
		return boardRow;
	}

	public Cell[] getColumn(int column) throws Exception{
		if(column >= size)
			throw new Exception("Index out of bounds");

		return board[column];
	}

	public boolean validateCharacters(String[] line){
		for(String character : line){
			if(!characters.contains(character) && !character.equals("-")){
				return false;
			}
		}
		return true;
	}

	public void printPuzzle(){
		for(int j = 0; j < board.length; j++){
			for(int i = 0; i < board.length; i++){
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public void Update(Object obj){
		if(obj instanceof Cell){
			Cell c = (Cell)obj;
			try{
				updateColumn(c);
				updateRow(c);
				updateBlock(c);
			}
			catch (Exception ex){ System.out.println(ex.toString()); }

		}
	}



	public static void main(String[] args){
		try{
			FileInputStream in = new FileInputStream(new File("puzzle3.txt"));
			Game game = new Game(in);
			game.printPuzzle();

			System.out.println();

			for(Cell c : game.getRow(1))
				System.out.print(c);

			System.out.println();

			for(Cell c : game.getColumn(1))
				System.out.println(c);
		}
		catch(Exception ex){
			System.out.print(ex.toString());
		}
	}
}