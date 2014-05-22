/**
 * Class that communicates with the database to store/retrieve data.
 * 
 * @author Kirsten Grace
 * @version 5.12.14
 */


package control;

import java.util.List;

import model.Conference;
import model.Manuscript;
import model.Review;
import model.User;

public class ManuscriptControl {
	
	private ManuscriptControl() {
		// Do Nothing (stop trying to create me)
	}

	public static Boolean updateManuscript(Manuscript theManuscript) {
		return false;
	}
	
	public static int createManuscript(Manuscript theManuscript) {
		return 0;
	}
	
	public static List<Manuscript> getManuscripts(Conference theCon, User theUser){
		return null;
	}
	
	public static List<Review> getReviews(Manuscript theMan, User theUser){
		return null;
	}
	
	public static List<Manuscript> getUnassignedSPC(Conference theCon, User theUser){
		return null;
	}
	
	public static List<Manuscript> getAcceptedManuscripts(Conference theCon){
		return null;
	}
	
	
}
