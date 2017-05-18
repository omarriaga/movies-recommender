/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.movie.recommenders;

import edu.uniandes.movie.utils.DataLoader;
import edu.uniandes.movie.utils.ContentBasedUtilities;
import java.io.IOException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.recommender101.data.DataModel;
import org.recommender101.recommender.extensions.contentbased.ContentBasedRecommender;


/**
 *
 * @author juan
 */
public class ContentRecommender {

    public static void init() throws Exception {
        System.out.println("Cargando modelo");
        DataModel model = new DataModel() ;
        DataLoader loader= new DataLoader();
        System.out.println("Cargando modelo terminado");
        loader.setFilename("/home/carlos/data/ratings2.csv");
        loader.loadData(model);
        System.out.println("Creando archivo de pesos");
        ContentBasedUtilities.createFeatureWeightFile("/home/carlos/data/tagsWithId.csv", "/home/carlos/data/tag_weight.txt");
        System.out.println("Creando recomendador");
        ContentBasedRecommender recommender= new ContentBasedRecommender();
        ContentBasedRecommender.dataDirectory="/home/carlos/data";
        recommender.setDataModel(model);
        recommender.setWordListFile("movies.csv");
        recommender.setFeatureWeightFile("tag_weight.txt");
        recommender.init();
        System.out.println("Creando recomendaciones");
        
        /*List<Integer> lista = recommender.recommendItems(2);
        int i=1;
        for(Integer a :lista){
                System.out.println("recomendación "+i+": "+a+":"+recommender.predictRating(2, a));
                i++;
        }*/
    }

}
