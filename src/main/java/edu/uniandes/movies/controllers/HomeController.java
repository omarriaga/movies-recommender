/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.movies.controllers;

import edu.uniandes.movie.recommenders.ContentRecommender;
import edu.uniandes.movie.utils.ContentBasedUtilities;
import edu.uniandes.movie.utils.DataLoader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import org.recommender101.data.DataModel;
import org.recommender101.eval.metrics.Precision;
import org.recommender101.eval.metrics.Recall;
import org.recommender101.recommender.extensions.contentbased.ContentBasedRecommender;

/**
 *
 * @author juan
 */
@ManagedBean
@Named(value = "homeController")
@ViewScoped
public class HomeController implements Serializable {

    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
    }

    @PostConstruct
    public void init() {
        System.out.println("init********************************");
        HashMap<String, String> tagMap = ContentBasedUtilities.createTagFile("/home/carlos/data/genome-tags.csv");
        //ContentBasedUtilities.createTagAsociatedFile(tagMap, "/home/carlos/data/tags.csv", "/home/carlos/data/tagsWithId.csv");
        //ContentBasedUtilities.createVoidFile("/home/carlos/data/prueba.csv");
        System.out.println("end init****************************");
        /*ContentRecommender a = new ContentRecommender();
        try {
            System.out.println("init content rec****************************");
            a.init();
            System.out.println("content rec****************************");
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        System.out.println("Cargando modelo");
        DataModel model = new DataModel() ;
        DataLoader loader= new DataLoader();
        System.out.println("Cargando modelo terminado");
        loader.setFilename("/home/carlos/data/ratings2.csv");
        try {
            loader.loadData(model);
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Creando archivo de pesos");
        ContentBasedUtilities.createFeatureWeightFile("/home/carlos/data/tagsWithId.csv", "/home/carlos/data/tag_weight.txt");
        System.out.println("Creando recomendador");
        ContentBasedRecommender recommender= new ContentBasedRecommender();
        ContentBasedRecommender.dataDirectory="/home/carlos/data";
        recommender.setDataModel(model);
        recommender.setWordListFile("movies.csv");
        recommender.setFeatureWeightFile("tag_weight.txt");
        try {
            recommender.init();
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*System.out.println("Creando recomendaciones");
        List<Integer> lista = recommender.recommendItems(2);
        List<Integer> lista2 = recommender.recommendItemsByRatingPrediction(2);
        int i=1;
        for(Integer a :lista){
                System.out.println("recomendación "+i+": "+a+":"+recommender.predictRating(258000, a));
                i++;
        }
        i=1;
        for(Integer a :lista2){
                System.out.println("recomendación2 "+i+": "+a+":"+recommender.predictRating(258000, a));
                i++;
        }*/
        System.out.println("Finalizo recomendaciones");
        
        DataModel model_test = new DataModel();
        System.out.println("Cargando modelo");
        loader.setFilename("/home/carlos/data/ratings2.csv");
        try {
            loader.loadData(model_test);
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Precision precision_metric = new Precision();
        precision_metric.setRecommender(recommender);
        precision_metric.setTestDataModel(model_test);
        precision_metric.setTrainingDataModel(model);
        precision_metric.initialize();
        precision_metric.setTargetSet("allrelevantintestset");

        Recall recall_metric = new Recall();
        recall_metric.setRecommender(recommender);
        recall_metric.setTestDataModel(model_test);
        recall_metric.setTrainingDataModel(model);
        recall_metric.initialize();
        recall_metric.setTargetSet("allrelevantintestset");

        System.out.println("iniciando evaluacion "+model_test.getUsers().size()+" usuarios");
        //for (int user : model_test.getUsers()){
                //System.out.println("evaluando usuario" +user);
                //recommender.recommendItemsByRatingPrediction(user)
                List<Integer> listaa = recommender.recommendItems(255000).subList(0,10);
                precision_metric.addRecommendations(255000, listaa );
                recall_metric.addRecommendations(255000, listaa );
        //}
        System.out.println("precision: "+precision_metric.getEvaluationResult());
        System.out.println("Recall: "+recall_metric.getEvaluationResult());
    }

    public String hello() {
        return "hello World!!";
    }

}
