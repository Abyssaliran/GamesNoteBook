package chess.pgn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import chess.Chess;

/**
 * Класс для чтения игр из текстового файла в стандартной шахматной нотации
 * Portable Game Notation (PGN).
 */
public class PNGReader {
	/**
	 * @param fileName файл с расширением *.pgn
	 * @return список партий из шахмат.
	 */
	static public List<Chess> read(String fileName) {
		List<Chess> games = new ArrayList<Chess>();

		try {
			FileReader fr = new FileReader(fileName);
			Reader br = new BufferedReader(fr);

			StreamTokenizer streamTokenizer = new StreamTokenizer(br);

			List<Object> tokens = new ArrayList<Object>();

			int currentToken = streamTokenizer.nextToken();
			while (currentToken != StreamTokenizer.TT_EOF) {
				switch (streamTokenizer.ttype) {
				case '[':
					readTag(streamTokenizer);
					break;

				case StreamTokenizer.TT_NUMBER:
					streamTokenizer.pushBack();
					readMoves(streamTokenizer);
					break;

				default:
					tokens.add((char) currentToken);
					break;
				}

				currentToken = streamTokenizer.nextToken();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return games;
	}

	private static boolean isGameResult(StreamTokenizer streamTokenizer) throws IOException {
		int currentToken = streamTokenizer.nextToken();

		switch (streamTokenizer.ttype) {
		case '-': // 1-0 или 0-1
			streamTokenizer.nextToken(); // 0 или 1
			return true;
		case '/': // 1/2-1/2
			streamTokenizer.nextToken(); // 2
			streamTokenizer.nextToken(); // -
			streamTokenizer.nextToken(); // 1
			streamTokenizer.nextToken(); // /
			streamTokenizer.nextToken(); // 2
			return true;
		default:
			streamTokenizer.pushBack();
//			streamTokenizer.pushBack();
			return false;
		}
	}

	private static void readMoves(StreamTokenizer streamTokenizer) throws IOException {
		int currentToken = streamTokenizer.nextToken();

		while (currentToken != StreamTokenizer.TT_EOF) {
			switch (streamTokenizer.ttype) {
			case '[':
				streamTokenizer.pushBack();
				System.out.println();
				return;

			case StreamTokenizer.TT_WORD:
				System.out.print(" " + streamTokenizer.sval);
				break;

			case StreamTokenizer.TT_NUMBER:
				if (isGameResult(streamTokenizer))  
					return;
				System.out.print("\n" + (int) streamTokenizer.nval + ".");
				break;

			default:
				System.out.print((char) currentToken);
				break;
			}

			currentToken = streamTokenizer.nextToken();
		}
	}

	static private void readTag(StreamTokenizer streamTokenizer) throws IOException {
		int currentToken = streamTokenizer.nextToken();

		while (currentToken != StreamTokenizer.TT_EOF) {
			switch (streamTokenizer.ttype) {
			case ']':
				System.out.println();
				return;

			case StreamTokenizer.TT_WORD:
				System.out.print(streamTokenizer.sval + "=");
				break;

			default:
				System.out.print(streamTokenizer.sval);
				break;
			}

			currentToken = streamTokenizer.nextToken();
		}

	}

	public static void main(String[] args) {
		File f = new File(".");
		String root = f.getAbsolutePath();
		System.out.println("Project root: " + root);

		String png = root.replace(".", "png/");
		System.out.println("PGN root: " + png);
		System.out.println();

		read(png + "Carlsen.pgn");
	}
}
