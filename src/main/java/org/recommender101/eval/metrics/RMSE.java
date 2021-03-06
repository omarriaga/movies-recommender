/** DJ **/
package org.recommender101.eval.metrics;

import org.recommender101.data.Rating;
import org.recommender101.eval.interfaces.PredictionEvaluator;
import org.recommender101.gui.annotations.R101Class;

/**
 * Calculates the RMSE
 * @author DJ
 *
 */
@R101Class(name="Root-mean-square error (RMSE)", description="Calculates the Root-mean-square error")
public class RMSE extends PredictionEvaluator {

	/**
	 * We calculate the errors
	 */
	float errorAccumulator;
	
	/**
	 * The number of predictions
	 */
	int predictionCount;
	
	// =====================================================================================

	@Override
	public void addTestPrediction(Rating r, float prediction) {
		if (!Float.isNaN(prediction)) {
			Float rating = r.rating;
			if (rating != null) {
				float error = (float) Math.abs(Math.pow(rating - prediction,2));
				errorAccumulator += error;
				predictionCount++;
			}
		}
		
	}

	// =====================================================================================
  /**
	 * Return the mean error
	 */
	@Override
	public float getPredictionAccuracy() {
		return (float) Math.sqrt(errorAccumulator / (float) predictionCount);
	}

	/**
	 * Used for evaluation aggregation
	 */
	public String toString() {
		return "RMSE";
	}
	
}
