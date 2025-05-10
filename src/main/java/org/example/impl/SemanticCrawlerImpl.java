package org.example.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.OWL;
import org.example.Crawler.SemanticCrawler;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class SemanticCrawlerImpl implements SemanticCrawler {
	private Set<String> URIsVisitadas = new HashSet<>(); 
	private CharsetEncoder enc = Charset.forName("ISO-8859-1").newEncoder();
	private Model tempModel = ModelFactory.createDefaultModel();
	
	public SemanticCrawlerImpl() {}

	public void search(Model model, String resourceURI) {
		try {
			if (!enc.canEncode(resourceURI)) {
				return;
			}

			if (URIsVisitadas.contains(resourceURI)) {
				return;
			}

			URIsVisitadas.add(resourceURI);
			tempModel.read(resourceURI); 

			StmtIterator getTriplas = tempModel.listStatements(tempModel.createResource(resourceURI), null, (RDFNode) null);
			model.add(getTriplas);

			StmtIterator statements = tempModel.listStatements(tempModel.createResource(resourceURI), OWL.sameAs, (RDFNode) null);
			while (statements.hasNext()) {
				Statement statement = statements.nextStatement();
				RDFNode object = statement.getObject();

				if (object.isAnon()) {
					trataNoEmBranco(model, (Resource) object);
				} else if (object.isURIResource()) {
					String objURI = object.asResource().getURI();
					if (enc.canEncode(objURI) && !URIsVisitadas.contains(objURI)) {
						search(model, objURI); 
					}
				}
			}

			StmtIterator reverseStatements = tempModel.listStatements(null, OWL.sameAs, tempModel.createResource(resourceURI));
			while (reverseStatements.hasNext()) {
				Statement reverseStmt = reverseStatements.nextStatement();
				Resource subject = reverseStmt.getSubject();

				if (subject.isAnon()) {
					trataNoEmBranco(model, subject);
				} else {
					String subjURI = subject.getURI();
					if (enc.canEncode(subjURI) && !URIsVisitadas.contains(subjURI)) {
						search(model, subjURI);
						
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Erro ao tentar abrir URI: " + e.getMessage());
		} catch (NoClassDefFoundError error) {
			System.err.println("Erro de classe não encontrada ao tentar abrir URI: " + error.getMessage());
		}
	}

	private void trataNoEmBranco(Model model, Resource object) {
		StmtIterator stmSNosEmBranco = tempModel.listStatements(object, null, (RDFNode) null);
		while (stmSNosEmBranco.hasNext()) {
			Statement stmNoAtual = stmSNosEmBranco.nextStatement();
			model.add(stmNoAtual);
		}

		stmSNosEmBranco = tempModel.listStatements(object, null, (RDFNode) null);
		while (stmSNosEmBranco.hasNext()) {
			Statement stmNoAtual = stmSNosEmBranco.nextStatement();
			RDFNode obj = stmNoAtual.getObject();
			if (obj.isAnon()) {
				trataNoEmBranco(model, (Resource) obj);
			}
		}
	}
}
