package analisis;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;

public class SparqlSearch {
	
	public static void main(String[] args) {

        String value1 = "Probabilidad";
        String value2 = "Intuicionismo";

        ResultSet result1 = search(value1);
        ResultSet result2 = search(value2);
        
        comparison(result1, result2);

    }

    static ResultSet search(String value) {
        ParameterizedSparqlString qs = new ParameterizedSparqlString(""
                + "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
                + "select distinct ?nameNarrower3 ?nameNarrower2 ?nameNarrower1 ?nameConcept where {\n"
                + "	?narrower3 a skos:Concept ;\n"
                + "	skos:prefLabel ?nameNarrower3 ;\n"
                + "	skos:broader ?narrower2 .\n"
                + "	?narrower2 skos:prefLabel ?nameNarrower2 ;\n"
                + "	skos:broader ?narrower1 .\n"
                + "	?narrower1 skos:prefLabel ?nameNarrower1 ;\n"
                + "	skos:topConceptOf ?concept .\n"
                + "	?concept skos:prefLabel ?nameConcept .\n"
                + "	FILTER (regex(?nameNarrower3, ?label))\n"
                + "	FILTER (LANG(?nameNarrower3)='es')\n"
                + "	FILTER (LANG(?nameNarrower2)='es')\n"
                + "	FILTER (LANG(?nameNarrower1)='es')\n"
                + "	FILTER (LANG(?nameConcept)='es')\n"
                + "}");

        Literal subject = ResourceFactory.createLangLiteral(value, "es");
        qs.setParam("label", subject);
        System.out.println(qs);

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://localhost:8890/sparql", qs.asQuery());

        // Normally you'd just do results = exec.execSelect(), but I want to 
        // use this ResultSet twice, so I'm making a copy of it.  
        ResultSet results = ResultSetFactory.copyResults(exec.execSelect());

        // A simpler way of printing the results.
//        ResultSetFormatter.out(results);

        return results;

//        while (results.hasNext()) {
//            // As RobV pointed out, don't use the `?` in the variable
//            // name here. Use *just* the name of the variable.
//            System.out.println(results.next().get("nameNarrower3"));
//        }
//
    }
    
    static double comparison (ResultSet result1, ResultSet result2){
        double score = 0;
        
        QuerySolution res1 = result1.next();
        QuerySolution res2 = result2.next();
        
        ResultSetFormatter.out(result1);
        ResultSetFormatter.out(result2);
        
//        System.out.println("Result 1: " + res1);
//        System.out.println("Result 2: " + res2);
//        
//        System.out.println("res1 nameNarrower3 " + res1.get("nameNarrower3"));
//        System.out.println("res1 nameNarrower2 " + res1.get("nameNarrower2"));
//        
//        System.out.println("res2 nameNarrower3 " + res2.get("nameNarrower3"));
//        System.out.println("res2 nameNarrower2 " + res2.get("nameNarrower2"));

        
        if (res1.get("nameNarrower3").equals(res2.get("nameNarrower3"))) {
            score = 1;
        } else if (res1.get("nameNarrower2").equals(res2.get("nameNarrower2"))) {
            score = 0.7;
        } else if (res1.get("nameNarrower1").equals(res2.get("nameNarrower1"))) {
            score = 0.4;
        } else {
            score = 0;
        }
        System.out.println("score = " + score);
        return score;
        
    }

}
