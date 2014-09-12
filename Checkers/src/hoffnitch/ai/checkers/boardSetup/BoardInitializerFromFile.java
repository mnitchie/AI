package hoffnitch.ai.checkers.boardSetup;

import hoffnitch.ai.checkers.GameState;
import hoffnitch.ai.checkers.Piece;
import hoffnitch.ai.checkers.PieceColor;
import hoffnitch.ai.checkers.Position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * BoardInitializer that saves/loads to/from file
 * 
 * Files must be of the following format:
 * 	4 lines of comma-separated integers
 * 	line 1: black uncrowned indices
 * 	line 2: black crowned indices
 * 	line 3: red uncrowned indices
 * 	line 4: red crowned indices
 */
public class BoardInitializerFromFile extends BoardInitializer
{
	private boolean loaded = false;
	private ArrayList<Integer> blackUncrowned;
	private ArrayList<Integer> blackCrowned;
	private ArrayList<Integer> redUncrowned;
	private ArrayList<Integer> redCrowned;
	
	public BoardInitializerFromFile() {
		blackUncrowned = new ArrayList<Integer>();
		blackCrowned = new ArrayList<Integer>();
		redUncrowned = new ArrayList<Integer>();
		redCrowned = new ArrayList<Integer>();
	}

	@Override
	public void setBoard(GameState board)
	{
		clearBoard(board);
		
		setPieces(board, blackUncrowned, PieceColor.DARK, false);
		setPieces(board, blackCrowned, PieceColor.DARK, true);
		setPieces(board, redUncrowned, PieceColor.LIGHT, false);
		setPieces(board, redCrowned, PieceColor.LIGHT, true);
	}
	
	private void setPieces(GameState board, ArrayList<Integer> indices, PieceColor color, boolean isCrowned) {
		for (int index: indices) {
			Position position = new Position(index);
			Piece piece = new Piece(color, position);
			piece.setCrowned(isCrowned);
			board.setPiece(piece, position);
		}
	}
	
	public void getBoard(GameState board) {
		blackUncrowned.clear();
		blackCrowned.clear();
		redUncrowned.clear();
		redCrowned.clear();
		
		for (int index = 1; index < 33; index++) {
			Piece piece = board.getPieceAtPosition(index);
			if (piece != null) {
				if (piece.color == PieceColor.DARK) {
					if (piece.isCrowned())
						blackCrowned.add(index);
					else
						blackUncrowned.add(index);
				} else {
					if (piece.isCrowned())
						redCrowned.add(index);
					else
						redUncrowned.add(index);
				}
			}
		}
	}
	
	public void loadFile(String fileName) throws IOException {
		loadFile(new File(fileName));
	}
	
	public void loadFile(File file) throws IOException {
		loaded = false;
		BufferedReader reader = new BufferedReader(new FileReader(file));

		readIndices(reader.readLine(), blackUncrowned);
		readIndices(reader.readLine(), blackCrowned);
		readIndices(reader.readLine(), redUncrowned);
		readIndices(reader.readLine(), redCrowned);
		
		reader.close();
		loaded = true;
			
	}
	
	public void saveFile(String fileName) throws FileNotFoundException {
		saveFile(new File(fileName));
	}
	
	public void saveFile(File file) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(file);

		writer.println(writeIndices(blackUncrowned));
		writer.println(writeIndices(blackCrowned));
		writer.println(writeIndices(redUncrowned));
		writer.println(writeIndices(redCrowned));
		
		writer.close();
	}
	
	private String writeIndices(ArrayList<Integer> indices) {
		String indexStrings = "";
		
		if (indices.size() > 0)
			indexStrings += indices.get(0);
		
		for (int i = 1; i < indices.size(); i++)
			indexStrings += "," + indices.get(i);
		
		return indexStrings;
	}
	
	private void readIndices(String line, ArrayList<Integer> indices) {
		
		indices.clear();
		
		if (line.length() > 0) {
			String[] indexStrings = line.split(",");
			
			for (String indexString: indexStrings)
				indices.add(Integer.parseInt(indexString));
		}
	}

	public boolean isLoaded()
	{
		return loaded;
	}
	
}
