package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.Map;


public class AuthorIdMap {
	
	private Map<Integer, Author> map;

	public AuthorIdMap() {
		this.map = new HashMap<>();
	}
	
	public Author get(Author author) {
		Author old = map.get(author.getId());
		if(old==null) {
			//nella mappa non c'è questo corso => LO AGGIUNGO
			map.put(author.getId(), author);
			return author;
		}
		
		//avevo già inserito quell'oggetto
		return old;
	
	}
	
	public Author get(int id) {
		return map.get(id);
	}
	
	public void put(Author autore, int id) {
		map.put(id, autore);
	}


}
