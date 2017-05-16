package edu.uniandes.movie.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Class that offers utilities for using the last.fm dataset on the Recommenders101 framework
 * @author Carlos Torres
 *
 */
public class ContentBasedUtilities {
	
	/**
	 * Creates a feature-weight file for the recommenders101 framework content-based recommender <br>
	 * @param inputFile
	 * @param outpurFile
         * @return HashMap
	 */
	public static HashMap<String,String> createTagFile(String inputFile){
		
		BufferedReader reed=null;
		PrintWriter pr= null;
		
                HashMap<String,String> tagMap = new HashMap<>();
		try {
			reed= new BufferedReader(new FileReader(inputFile));
			
			String line=null;
			
			reed.readLine();//header
			while((line=reed.readLine())!=null){
				String[] splitted=line.split(",");
				String tagId=splitted[0];
				String tag=splitted[1];
				tagMap.put(tag, tagId);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reed!=null)
				try {reed.close();} 
			catch (IOException e) {}
			if(pr!=null){
				pr.close();
			}
		}
                return tagMap;
	}
	
        
        public static void createTagAsociatedFile(HashMap<String,String> tagMap,
                                                    String inputFile, 
                                                    String outpurFile){
		BufferedReader reed=null;
		PrintWriter pr= null;
                PrintWriter pr2= null;
		int tagCount = 1128;
		try {
			reed= new BufferedReader(new FileReader(inputFile));
			
			pr= new PrintWriter(new File(outpurFile));
			pr2= new PrintWriter(new File("/home/carlos/data/newTags.csv"));
			String line=null;
                        
                        String tagId = "";
			
			reed.readLine();//header
			while((line=reed.readLine())!=null){
				String[] splitted=line.split(",");
                               
				String userId=splitted[0];
				String movieId=splitted[1];
                                String tag=splitted[2];
                                String timestamp=splitted[3];
                                
                                if(tagMap.containsKey(tag)){
                                    tagId = tagMap.get(tag);
                                } else {
                                    tagCount++;
                                    tagId = String.valueOf(tagCount);
                                    tagMap.put(tag, tagId);
                                    pr2.println(tagId + "," + tag);
                                }
                               
                                String outputLine = userId + "," + movieId + "," + tagId + "," +timestamp;
                                pr.println(outputLine);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reed!=null)
                        try {reed.close();} 
			catch (IOException e) {}
			if(pr!=null){
				pr.close();
			}
		}
	}
        
        public static void createVoidFile(String outpurFile){
		
		PrintWriter pr= null;
		
		try {
			
			pr= new PrintWriter(new File(outpurFile));
			
			
                        String outputLine = "hola";
                        pr.println(outputLine);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(pr!=null){
				pr.close();
			}
		}
	}

        /**
	 * Creates a feature-weight file for the recommenders101 framework content-based recommender <br>
	 * @param inputFile
	 * @param outpurFile
	 */
	public static void createFeatureWeightFile(String inputFile, String outpurFile){
		
		BufferedReader reed=null;
		PrintWriter pr= null;
		
		HashMap<String,HashMap<String,Integer>> itemId_featureId_count= new HashMap<>();
		try {
			reed= new BufferedReader(new FileReader(inputFile));
			
			pr= new PrintWriter(new File(outpurFile));
			
			String line=null;
			
			reed.readLine();//header
			while((line=reed.readLine())!=null){
				String[] splitted=line.split(",");
				String itemId=splitted[1];
				String featureId=splitted[2];
				
				
				if(!itemId_featureId_count.containsKey(itemId)){
					itemId_featureId_count.put(itemId,new HashMap<>());
				}
				
				HashMap<String,Integer> itemHashmap = itemId_featureId_count.get(itemId);
				if(!itemHashmap.containsKey(featureId)){
					itemHashmap.put(featureId, 1);
				}
				else{
					itemHashmap.put(featureId, itemHashmap.get(featureId)+1);
				}
				
				
			}
			
			for (String itemId : itemId_featureId_count.keySet()) {
				HashMap<String,Integer> itemHashmap = itemId_featureId_count.get(itemId);
				for (String featureId  : itemHashmap.keySet()) {
					// item-id;feature-id:weight feature-id weight
					Integer weight = itemHashmap.get(featureId);
					if (weight > 3) {
						String outputLine = itemId + ";" + featureId + ":"
								+ weight;

						pr.println(outputLine);
					}
					
				}
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reed!=null)
				try {reed.close();} 
			catch (IOException e) {}
			if(pr!=null){
				pr.close();
			}
		}
		
		
	}
}
