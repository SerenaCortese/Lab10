package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private PortoDAO dao;
	
	private AuthorIdMap autoriIdMap;
	private PaperIdMap papersIdMap;
	
	private List<Author> autori;
	private List<Paper> papers;
	
	
	private SimpleGraph<Author,DefaultEdge> grafo;
	
	
	public Model() {
		
		dao = new PortoDAO();
		
		this.autoriIdMap = new AuthorIdMap();
		this.papersIdMap = new PaperIdMap();
		
		this.autori = dao.getAllAuthors(autoriIdMap);
		this.papers = dao.getAllPapers(papersIdMap);
		
		for(Author a : this.autori) {//ogni autore ha la sua lista di paper
			a.getPapers().addAll(dao.getPapersFromAuthor(a,papersIdMap));
		}
		
		for(Paper p: this.papers) {//ogni paper ha la sua lista di autori
			p.getAutori().addAll(dao.getAuthorsFromPaper(p,autoriIdMap));
		}
	}

	public List<Author> getAutori() {
		return autori;
	}

	public void createGraph() {
		
		this.grafo = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.autori);
		
		for(Author a1: this.autori)
			for(Author a2: this.autori)
				if(!a1.equals(a2))
					for(Paper p1: a1.getPapers())
					if(a2.getPapers().contains(p1)) {
						Graphs.addEdgeWithVertices(this.grafo, a1, a2);
						break;
						}
	}
	public List<Author> trovaCoautori(Author autoreScelto) {
		
		this.createGraph();
		
		List<Author> result = new ArrayList<Author>();
		
		for(Author a2: this.autori)
			if(!autoreScelto.equals(a2))
				for(Paper p1:autoreScelto.getPapers())
				if(a2.getPapers().contains(p1)) {
					result.add(a2);
					break;
					}
		return result;
		
	}

	public List<Paper> trovaSequenza(Author primoAutore, Author secondoAutore) {
		List<Paper> articoli = new ArrayList<Paper>();
		List<Author> autoriSequenza = new ArrayList<Author>();
		//uso algoritmo di Dijkstra
		ShortestPathAlgorithm<Author, DefaultEdge> spa = new DijkstraShortestPath<Author, DefaultEdge>(this.grafo);
		try {
			GraphPath<Author, DefaultEdge> gp = spa.getPath(primoAutore, secondoAutore);
			autoriSequenza.addAll(gp.getVertexList());
			for(int i = 0; i< autoriSequenza.size()-1;i++) {
				for(Paper p: autoriSequenza.get(i).getPapers()) {
					if(autoriSequenza.get(i+1).getPapers().contains(p)) {
						articoli.add(p);
					}
				}
			}
		}catch(NullPointerException e) {
			return null;
		}
		return articoli;
	
		
	}

}
