package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;
    
    Model model;
    List<Author> autori;
    
    Author primoAutore;

    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear();
		primoAutore = boxPrimo.getValue();
		if(primoAutore == null) {
    		txtResult.setText("Scegliere un autore.");
    		return;
    	}
		List<Author>coautori= model.trovaCoautori(primoAutore);
		if(coautori.size() == 1) {
			txtResult.setText("Questo stato non ha nazioni raggiungibili via terra.");
			return;
		}
		for(Author c : coautori) {
			txtResult.appendText(c.toString()+"\n");
		}
		List<Author> nonCoautori = new ArrayList<>();
		for(Author a : this.autori)
			if(!coautori.contains(a) && !a.equals(primoAutore))
				nonCoautori.add(a);
		boxSecondo.getItems().addAll(nonCoautori);
		
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	Author secondoAutore = boxSecondo.getValue();
    	if(secondoAutore == null) {
    		txtResult.setText("Scegliere un secondo autore.");
    		return;
    	}
    	List<Paper> result = model.trovaSequenza(primoAutore,secondoAutore);
    	if(result == null) {
    		txtResult.appendText("Cammino minimo non trovato.");
    		return;
    	}	
    	for(Paper p : result) {
			txtResult.appendText(p.toString()+"\n");
		}
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.autori = model.getAutori();
    	boxPrimo.getItems().addAll(autori);
    	
    	
    }
}
