/**
*Objective: Serves as a blueprint for assessment types (test and assignment) defining required
* attributes and behaviors.
* @author: Hamza Farah
* @version 1.0
* @Date Aug 19, 2025
* Bugs: none 
*/
public abstract class Assessment {
    
    /*Declare the required fields that will be shared with the sub-classes*/
    private String name;
    private String course;
    private int dueDate;        
    private double weight;
    private int numberOfMarks;
    private boolean completed;
    private String additionalNotes;
    
    /*Getters to make all attributes accessible 
    *@params: none
    *pre: Declared attributes
    *post: Returns the attributes
    */
    public String getName() { 
        return name; 
    }
    public String getCourse() { 
        return course; 
    }
    public int getDueDate() { 
        return dueDate; 
    }
    public double getWeight() { 
        return weight; 
    }
    public int getNumberOfMarks() { 
        return numberOfMarks; 
    }
    public boolean getCompleted() { 
        return completed; 
    }
    public String getAdditionalNotes() { 
        return additionalNotes; 
    }
    
    /*Setter to make all attributes modifiable 
    *@params: none
    *pre: Declared attributes
    *post: Modifiable attributes
    */
     public void setName(String name) { 
         this.name = name; 
     }
    public void setCourse(String course) { 
        this.course = course; 
    }
    public void setDueDate(int dueDate) { 
        this.dueDate = dueDate; 
    }
    public void setWeight(double weight) { 
        this.weight = weight; 
    }
    public void setNumberOfMarks(int numberOfMarks) { 
        this.numberOfMarks = numberOfMarks;
    }
    public void setCompleted(boolean completed) { 
        this.completed = completed;
    }
    public void setAdditionalNotes(String additionalNotes) { 
        this.additionalNotes = additionalNotes; 
    }
    
    /*Abstract class shared with sub-classes*/
    public abstract String returnPriority();
}
