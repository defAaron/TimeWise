/**
*Objective: Models a test by defining specific attribute and behaviors, while inheriting properties (attributes and behaviors)
* from the parent-class (assessments).
* @author: Hamza Farah
* @version 1.0
* @Date Aug 19, 2025
* Bugs: none 
*/
public class Test extends Assessment {
    
    /*Declare required field*/
    private int numOfQuestions;
    
    /*Constructor that initializes all parameters 
    *@params: All assessment attributes (From parent-class) and numOfQuestions
    *pre: All parameter values are declared in the parent class
    *post: Test object's parameters are initialized
    */
    public Test(String name, String course, int dueDate, double weight, 
                int numberOfMarks, boolean completed, String additionalNotes, int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
        setName(name);
        setCourse(course);
        setDueDate(dueDate);
        setCompleted(completed);
        setWeight(weight);
        setNumberOfMarks(numberOfMarks);
        setAdditionalNotes(additionalNotes);
    }
    
    /*Getter to make the field (numOfQuestion) accessible*/
    public int getNumOfQuestions() {
        return numOfQuestions;
    }
    
    /*Returns a string representation of test object
    *@params: none
    *pre: Intialized fields (name, course, dueDate, ect...)
    *post: Returns test information
    */
    @Override 
    public String toString() {
        return "Test Details - \nName: " + getName() +
           "\nCourse: " + getCourse()+
           "\nRemaining Time (in days): " + getDueDate() +
           "\nWeight: " + getWeight() +
           "\nMarks: " + getNumberOfMarks() +
           "\nCompleted: " + getCompleted() + 
           "\nNumber of questions: " + numOfQuestions +
           "\nNotes: " + getAdditionalNotes();
    }
    
    
    /* Calculates and returns the priority level of the test based on urgency through a formula
    *@params: none
    *pre: Defined methods (getCompleted(), getDueDate(), ...)
    *post: Returns a string representing the priority level (Top, high, ...)
    */
    @Override 
    public String returnPriority() {
        if (getCompleted() == true) {
            return "Completed"; 
        }
        
        if (getDueDate() < 0) {
            return "Top Priority";
        }
        double score = (100 - getDueDate()) + (getWeight() * 2) + (numOfQuestions/2) + (getNumberOfMarks()/10);
        if (score >= 200) {
            return "High Priority";
        } 
        else if (score >= 100) {
            return "Medium Priority";
        }
        else {
            return "Low Priority";
        }
    }
}
