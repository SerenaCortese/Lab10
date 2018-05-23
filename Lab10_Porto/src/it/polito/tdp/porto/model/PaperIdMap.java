package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.Map;

public class PaperIdMap {

	private Map<Integer, Paper> map;

	public PaperIdMap() {
		this.map = new HashMap<>();
	}
	
	public Paper get(Paper paper) {
		Paper old = map.get(paper.getEprintid());
		if(old==null) {
			//nella mappa non c'è questo corso => LO AGGIUNGO
			map.put(paper.getEprintid(), paper);
			return paper;
		}
		
		//avevo già inserito quell'oggetto
		return old;
	
	}
	
	public Paper get(int id) {
		return map.get(id);
	}
	
	public void put(Paper paper, int id) {
		map.put(id, paper);
	}

}
