package detourdetective;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public class DetourDetectorApp {
    public static void main(String[] args) {
    	
    	CommandLineParser parser = new DefaultParser();

    	// create the Options
    	Options options = new Options();
    	options.addOption("R", "route", false, "This is the route we want to check..");
    	options.addOption("D", "date", false, "The date we want to check this route on..");
    	

    	try {
    	    // parse the command line arguments
    	    CommandLine line = parser.parse(options, args);

    	   
    	    if (line.hasOption("route")) {
    	        
    	        System.out.println(line.getOptionValue("route"));
    	    }
    	    if (line.hasOption("date")) {
    	        
    	        System.out.println(line.getOptionValue("date"));
    	    }
    	    
    	}
    	catch (ParseException exp) {
    	    System.out.println("Unexpected exception:" + exp.getMessage());
    	}     
    
    }
}
