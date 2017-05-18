/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.movie.recommenders;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
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

    public void recommend(long user, List<Long> items) {
        String movies = items.stream().map((item) -> {
            return "movie:" + item;
        }).collect(Collectors.joining(" "));
        List<String> dbpedia = queryLocal(movies);
    }

    private List<String> queryLocal(String movies) {
        String query = "prefix movie: <http://www.semanticweb.org/grupo4/ontologies/2017/4/movie-ontology#>\n"
                + "SELECT ?movie ?dbpedia \n"
                + "WHERE {\n"
                + "  ?movie owl:sameAs ?dbpedia .\n"
                + "  VALUES ?dbpedia { " + movies + " }\n"
                + "}";
        ParameterizedSparqlString qs = new ParameterizedSparqlString(query);

        System.out.println(qs);

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://localhost:8890/sparql", qs.asQuery());

        // Normally you'd just do results = exec.execSelect(), but I want to 
        // use this ResultSet twice, so I'm making a copy of it.  
        ResultSet results = ResultSetFactory.copyResults(exec.execSelect());
        List<String> data = new LinkedList<>();
        while (results.hasNext()) {
            // As RobV pointed out, don't use the `?` in the variable
            // name here. Use *just* the name of the variable.
            data.add(results.next().get("movie").asResource().getURI());
            System.out.println(results.next().get("movie"));
        }

        // A simpler way of printing the results.
        ResultSetFormatter.out(results);

        return data;
    }

    private List<String> queryCharacteristics(String movies) {
        String query = "SELECT distinct ?rel ?res (count(distinct *) as ?count)\n"
                + "WHERE {\n"
                + "?movie ?rel ?res .\n"
                + "VALUES ?movie { "+movies+"}\n"
                + "} GROUP BY ?rel ?res\n" 
                + "HAVING (count(distinct *) > 1)";
        ParameterizedSparqlString qs = new ParameterizedSparqlString(query);

        System.out.println(qs);

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

        // Normally you'd just do results = exec.execSelect(), but I want to 
        // use this ResultSet twice, so I'm making a copy of it.  
        ResultSet results = ResultSetFactory.copyResults(exec.execSelect());
        List<String> data = new LinkedList<>();
        while (results.hasNext()) {
            // As RobV pointed out, don't use the `?` in the variable
            // name here. Use *just* the name of the variable.
            data.add(results.next().get("movie").asResource().getURI());
            System.out.println(results.next().get("rel"));
        }

        // A simpler way of printing the results.
        ResultSetFormatter.out(results);

        return data;
    }

}
