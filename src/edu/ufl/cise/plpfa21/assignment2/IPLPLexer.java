package edu.ufl.cise.plpfa21.assignment2;

public interface IPLPLexer {
	
	IPLPToken nextToken() throws LexicalException;
}
