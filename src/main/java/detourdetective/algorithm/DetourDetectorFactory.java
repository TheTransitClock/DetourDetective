package detourdetective.algorithm;

public class DetourDetectorFactory {

	private static DetourDetector detector = null;

	/********************** Member Functions **************************/

	public static DetourDetector getInstance(String type) {
		// If the PredictionGenerator hasn't been created yet then do so now

		if (type.equals("detourdetective.algorithm.DetourDetectorDefaultImpl"))
			detector = new DetourDetectorDefaultImpl();
		if (type.equals("detourdetective.algorithm.DetourDetectorDiscreteFrechet"))
			detector = new DetourDetectorDiscreteFrechet();

		return detector;
	}

}