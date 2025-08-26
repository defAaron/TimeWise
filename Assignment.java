/**
*Objective: Models an assignment by defining specific attribute and behaviors, while inheriting properties (attributes and behaviors)
* from the paren-class (assessments).
* @author: Hamza Farah
* @version 1.0
* @Date Aug 19, 2025
* Bugs: none 
*/
public class Assignment extends Assessment {
    
    /*Declare required field*/
    private String format; 
    
    /*Constructor that initializes all parameters
    *@params: All assessment attributes (From parent-class) and format ()
    *pre: All parameter values are declared in the parent class
    *post: Assignment object's parameters are initialized
    */
    public Assignment(String name, String course, int dueDate, double weight, 
               int numberOfMarks, boolean completed, String additionalNotes, String format) {
        this.format = format;
        setName(name);
        setCourse(course);
        setDueDate(dueDate);
        setCompleted(completed);
        setWeight(weight);
        setNumberOfMarks(numberOfMarks);
        setAdditionalNotes(additionalNotes);
        
    }
    
    /*Getter to make the field (format) accessible*/
    public String getFormat() {
        return format;
    }
    
    /*Returns a string representation of assignment object
    *@params: none
    *pre: intialized fields (name, course, dueDate, ect....)
    *post: Returns assignment information
    */
    @Override 
    public String toString() {
        return "Assignment Details - \nName: " + getName() +
           "\nCourse: " + getCourse() +
           "\nRemaining Time (in days): " + getDueDate() +
           "\nWeight: " + getWeight() +
           "\nMarks: " + getNumberOfMarks() +
           "\nCompleted: " + getCompleted() + 
           "\nFormat: " + format +
           "\nNotes: " + getAdditionalNotes();
    }
    
    /* Calculates and returns the priority level of the test based on urgency through a formula
    *@params: none
    *pre: Defined methods (getCompleted(), getDueDate(), ...)
    *post: Returns a string representing the priority level (Top, high, ...)
    */
    public String returnPriority() {
        if (getCompleted() == true) {
            return "Completed"; 
        }
        
        if (getDueDate() < 0) {
            return "Top Priority";
        }
        
        double score = (100 - getDueDate()) + (getWeight() * 2) + (getNumberOfMarks()/10);
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
