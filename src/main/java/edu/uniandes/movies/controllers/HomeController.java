/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.movies.controllers;

import edu.uniandes.movie.utils.ContentBasedUtilities;
import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;

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
    }

    public String hello() {
        return "hello World!!";
    }

}
