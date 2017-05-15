/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.movie.recommenders;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;


/**
 *
 * @author juan
 */
public class ItemRecommender {

    private GenericItemBasedRecommender recommender;

    public void init() throws TasteException, IOException {
        DataModel dm = new FileDataModel(new File(""));
        ItemSimilarity is = new UncenteredCosineSimilarity(dm);
        recommender = new GenericItemBasedRecommender(dm, is);
    }
    
    public List<Long> recommend(long userID) {
        try {
            List<RecommendedItem> items = recommender.recommend(userID, 10);
            List<Long> datos = new LinkedList<>();
            items.stream().forEach((item) -> {
                System.out.println("item : " + item.getItemID());
                datos.add(item.getItemID());
            });
            
            return datos;
        } catch (TasteException ex) {
            
            return new LinkedList<>();
        }
    }
    
    public double predict(long user, long item) throws TasteException{
        return recommender.estimatePreference(user, item);
    }

}
