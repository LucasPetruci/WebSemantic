package org.example.Test;

import org.apache.jena.rdf.model.*;

import org.example.impl.SemanticCrawlerImpl;

public class test {

	public static void main(String[] args) {
		System.setProperty("http.read.timeout", "10000");

		String URI = "http://dbpedia.org/resource/Roger_Federer";
		Model model = ModelFactory.createDefaultModel();			
		SemanticCrawlerImpl ObjImpl = new SemanticCrawlerImpl();

		System.out.println("Iniciando coleta de dados RDF para a URI: " + URI + "\n");
		ObjImpl.search(model, URI);

		// Mostra as primeiras triplas coletadas
		System.out.println("\nExibindo até 20 triplas coletadas:\n");
		StmtIterator it = model.listStatements();
		int count = 0;
		while (it.hasNext() && count < 20) {
			Statement stmt = it.nextStatement();
			System.out.println(stmt);
			count++;
		}

		// Escreve todo o modelo em formato TTL
		System.out.println("\nModelo completo em formato Turtle:\n");
		model.write(System.out, "TTL");	
	}
}
