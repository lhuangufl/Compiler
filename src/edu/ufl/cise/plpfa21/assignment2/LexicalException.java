package edu.ufl.cise.plpfa21.assignment2;


@SuppressWarnings("serial")
public class LexicalException extends Exception {

	public LexicalException(String message, int line, int charPositionInLine) {
		super( "Lexical Error " + line + ":" + charPositionInLine + "  " + message);
	}

}
