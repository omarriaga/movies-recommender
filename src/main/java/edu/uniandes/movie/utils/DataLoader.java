package edu.uniandes.movie.utils;

import java.io.BufferedReader;
import java.io.FileReader;

import org.recommender101.data.DataModel;
import org.recommender101.data.DefaultDataLoader;
import org.recommender101.tools.Debug;

public class DataLoader extends DefaultDataLoader {

	/**
	 * The method loads the MovieLens data from the specified file location.
	 * The method can be overwritten in a subclass
	 */
	public void loadData(DataModel dm) throws Exception {
		int counter = 0;
		// Read the file line by line and add the ratings to the data model.
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		line = reader.readLine();
		String[] tokens;
		while (line != null) {
			// Skip comment lines
			if (line.trim().startsWith("//")) {
				line = reader.readLine();
				continue;
			}
			tokens = line.split(",");
			// First, add the ratings.
                        Double rating = Double.parseDouble(tokens[2]);
			dm.addRating(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), rating.intValue());
			line = reader.readLine();
//			System.out.println("line.." + line + " (" + counter + ")");
			counter++;
//			// debugging here..
			if (maxLines != -1) {
				if (counter >= maxLines) {
					System.out.println("DataLoader: Stopping after " + (counter)  + " lines for debug");
					break;
				}
			}
		}
		Debug.log("DefaultDataLoader:loadData: Loaded " + counter + " ratings");
		Debug.log("DefaultDataLoader:loadData: " + dm.getUsers().size() + " users and " + dm.getItems().size() + " items.");
		reader.close();
		applyConstraints(dm);
	}
}
