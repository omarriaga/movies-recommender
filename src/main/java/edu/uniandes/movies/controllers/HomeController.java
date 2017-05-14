/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.movies.controllers;

import edu.uniandes.movie.utils.ContentBasedUtilities;
import static edu.uniandes.movie.utils.ContentBasedUtilities.createTagAsociatedFile;
import static edu.uniandes.movie.utils.ContentBasedUtilities.createTagFile;
import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author juan
 */
@Named(value = "homeController")
@ViewScoped
public class HomeController implements Serializable {

    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
    }
    
    @PostConstruct
    public void init(){
        System.out.println("init********************************");
        HashMap<String,String> tagMap = ContentBasedUtilities.createTagFile("/data/genome-tags.cvs");
        ContentBasedUtilities.createTagAsociatedFile(tagMap, "/data/tags.cvs", "/data/tagsWithId.cvs");
        System.out.println("end init****************************");
    }
    
    public String helloWorld(){
        return "hello world";
    }
    
}
