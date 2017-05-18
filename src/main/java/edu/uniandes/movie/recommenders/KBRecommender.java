/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.movie.recommenders;


import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
/**
 *
 * @author juan
 */
public class KBRecommender {
    
    public void queryLocal(){
        ParameterizedSparqlString qs = new ParameterizedSparqlString( "" +
                "prefix movie: <http://www.semanticweb.org/grupo4/ontologies/2017/4/movie-ontology#>\n" +
                "\n" +
                "select ?movies where {\n" +
                "  ?resource rdfs:label ?label\n" +
                "}" );

        Literal movie = ResourceFactory.createLangLiteral( "Stealing_Beauty", "en" );
        qs.setParam( "label", movie );

        System.out.println( qs );

        QueryExecution exec = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", qs.asQuery() );

        // Normally you'd just do results = exec.execSelect(), but I want to 
        // use this ResultSet twice, so I'm making a copy of it.  
        ResultSet results = ResultSetFactory.copyResults( exec.execSelect() );

        while ( results.hasNext() ) {
            // As RobV pointed out, don't use the `?` in the variable
            // name here. Use *just* the name of the variable.
            System.out.println( results.next().get( "resource" ));
        }

        // A simpler way of printing the results.
        ResultSetFormatter.out( results );
    }
    
}
