package detourdetective.algorithm;
import detourdetective.algorithm.DetourDetector;
import detourdetective.algorithm.DetourDetectorDefaultImpl;
public class DetourDetectorFactory {

    // The name of the class to instantiate
    private static String className = 
            new String("detourdective.algorithm.DetourDetectorDefaultImpl");
                  

    private static DetourDetector singleton = null;

    /********************** Member Functions **************************/

    public static DetourDetector getInstance() {
        // If the PredictionGenerator hasn't been created yet then do so now
        if (singleton == null) {               
            singleton=new DetourDetectorDefaultImpl();                    
        }       
        return singleton;
    }

}