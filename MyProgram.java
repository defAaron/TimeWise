/**
 * This program is an interactive study tracker that allows the user to input 
 * upcoming assessments when planning study sessions. This program allows users 
 * to add, delete, and search assignments and tests based on dates and courses. 
 * The program makes use of File IO to save information and display results. 
 * 
 * @authors    Aaron Dutta, Hamza Farah, Patrick Chung
 * @version    1.0
 * @date       2025-08-18
 */

import java.util.*;
import java.io.*;
import java.time.*;
import java.time.temporal.*;
public class MyProgram {
        
    //defining static variables
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Assessment> userAssessments = new ArrayList<Assessment>();
    static LocalDate currentDate = LocalDate.now();
    public static int choice = 0;
    
    public static void main(String[] args)
    {
        login();
        loadFile();
        
        System.out.println("Welcome to TimeWise!");
        System.out.println("--------------------");
        
        do {
            
            //print menu options
            menuOption();
            try{
                System.out.print("\nOption: ");
                choice = Integer.parseInt(sc.nextLine());
            }
            
            //handle invalid input
            catch(NumberFormatException e){
                System.out.println("\nInvalid input, please enter an integer from 0 to 7.");
                continue;
            }
            switch(choice){
                
                //user wants to erase old assessments
                case 0: eraseAllAssessments(); break;
                
                //user wants to add an assessment
                case 1: addAssessment(); break;
                    
                //user wants to remove an assessment
                case 2: removeAssessment();break;
                
                //user wants to search for an assessment    
                case 3: searchAssessments(); break;
                
                //user wants to receive a list of their assessments    
                case 4: listAssessments(); break;
                    
                //user wants to receive a list of their overdue assessments
                case 5: listOverdue(); break;
                
                //user wants to change completion status    
                case 6: completionStatus(); break;
                 
                //user wants to save file   
                case 7: saveFile(); System.out.println("Got that, see you next time!"); break;
                
                //invalid input handle
                default: System.out.println("\n Invalid input, please enter an integer between 0 and 7"); continue;
            }
        } while(choice != 7); 
    }
    
    /*Clears the content in the arraylist and file
    *pre: ArrayList contains elements
    *post: Reset the array and file for the user
    * Author: Hamza
    */
    public static void eraseAllAssessments() {
        userAssessments.clear();
        List<String> lines = new ArrayList<>();  
        try {
            BufferedReader reader = new BufferedReader(new FileReader("assessments.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("assessments.txt"));
            reader.close();
            if (!lines.isEmpty()) {
                writer.write(lines.get(0));  
                writer.newLine();  
            }
            writer.close();
            System.out.println("Content has been erased.\n");

        } catch (IOException e) {
            System.out.println("File wasn't found.\n");
        }
    }
    
    /* 
    * prints the menu options for the user to choose from
    *pre: none
    *post: prints menu in console
    * Author: Aaron
    */
    public static void menuOption()
    {
        //print menu options
        System.out.println("\nWhat would you like to do?");
        System.out.println("0)   Erase Content");
        System.out.println("1)   Add an assessment.");
        System.out.println("2)   Remove an assessment.");
        System.out.println("3)   Search for an assessment.");
        System.out.println("4)   List all assessments.");
        System.out.println("5)   List all overdue assessments.");
        System.out.println("6)   Change completion status.");
        System.out.println("7)   Exit program.");
    }
    
    /*  Allows the user to update the completion status of a specific assessment.
     *  pre: Declared arraylist
     *  post: updates completion status based on user input
     *  Author: Hamza and Patrick
     */
    public static void completionStatus(){
        
        //variable declaration
        String name;
        String course;
        String input;
        int index;
        boolean validInput;
        
        //returns if the user has no assessments
        if (userAssessments.isEmpty()) {
            System.out.println("\nSorry, you don't have any assessments yet.");
            return;
        }
        
        //prompts user for assessment name
        System.out.print("\nAssessment Name: ");
        name = sc.nextLine();
        
        //prompts user for course name
        System.out.print("Course: ");
        course = sc.nextLine();
        index = nameCourseSearch(name, course);
        if (index != -1){
            do {
                System.out.print("Is this assessment completed? (Y/N): ");
                input = sc.nextLine();
                if (input.equalsIgnoreCase("Y")) {
                    userAssessments.get(index).setCompleted(true);
                    System.out.println("Assessment \"" + userAssessments.get(index).getName() + "\" is now completed.");
                    validInput = true;
                } else if (input.equalsIgnoreCase("N")) {
                    userAssessments.get(index).setCompleted(false);
                    System.out.println("Assessment \"" + userAssessments.get(index).getName() + "\" is now incomplete.");
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please try again");
                    validInput = false;
                }
            } while(!validInput);
        }
        else {
            System.out.println("Assessment not found.");
        }
        
        //return to menu
        System.out.println("Returning to menu...\n");

    }
    
    /* Allows the user to search for an assessment though the name.
    *pre: ArrayList contains elements
    *post: Displays the details of the found assessment; otherwise displays a message if not found
    * Author: Hamza & Aaron
    */
    public static void searchAssessments()
    {
        System.out.print("\nAssessment Name: ");
        String searchName = sc.nextLine();
        boolean found = false;
        if(userAssessments.isEmpty()){
            System.out.println("\nSorry, we couldn't find any assessments to list.");
            return;
        }
        for (Assessment a : userAssessments) {
            if (a.getName().equalsIgnoreCase(searchName)) {
                System.out.println("Assessment Found");
                System.out.println(a.toString());     
                System.out.println("Priority: " + a.returnPriority());
                found = true;
                break;  
            }
        }
        if(!found){
             System.out.println("Assessment couldn't be found.");
        }
        
        //prompts user to return to menu
        System.out.println("\n(Press enter to return to menu)");
        sc.nextLine();
        System.out.println("Returning to menu...\n");
    }
    
    /* 
    * Lists all assessments in ordered format
    * pre: none
    * post: prints all assessments along with their weightages
    * Author: Aaron
    */ 
    public static void listAssessments() {
        if(userAssessments.isEmpty()) {
            System.out.println("\nSorry, we couldn't find any assessments to list.\n");
            return;
        }
        sortAssessment();
        System.out.println("\n-----------------------");
        System.out.println("| List of Assessments |");
        System.out.println("-----------------------");
        for(Assessment a : userAssessments) {
            System.out.println(a.getName() + " -> " + a.getWeight() + "% Priority : " + a.returnPriority());
        }
        
        //prompts user to return to menu
        System.out.println("\n(Press enter to return to menu)");
        sc.nextLine();
        System.out.println("Returning to menu...\n");
    }
    
    /* 
    * Lists all overdue assessments
    * pre: none
    * post: prints all assessments along with days overdue
    * Author: Aaron
    */ 
    public static void listOverdue() {
        if (userAssessments.isEmpty()){
            System.out.println("\nSorry, we couldn't find any assessments to list.");
            return;
        }
        boolean found = false;
        System.out.println("\n-----------------------");
        System.out.println("| Overdue Assessments |");
        System.out.println("-----------------------");
        for (Assessment a : userAssessments){
            int due = a.getDueDate();
            if (due < 0 && !a.getCompleted()){
                found = true;
                int overdueDays = Math.abs(due);
                String plural = (overdueDays == 1) ? " day overdue" : " days overdue";
                System.out.println(a.getName() + " --> " + overdueDays + plural);
            }
        }
        if (!found){
            System.out.println("You have no overdue assessments!");
        }
        
        //prompts user to return to menu
        System.out.println("\n(Press enter to return to menu)");
        sc.nextLine();
        System.out.println("Returning to menu...\n");
    }
    
    /*
     *  The login method is used to verify the user and load their saved assessments
     *  Parameters: none
     *  Return: none
     *  Pre: properly formatted login information file
     *  Post: user logged in
     *  Author: Patrick Chung
     */
    public static void login() {
        //constant declaration
        final String FILE_NAME = "password.txt";
         
        //variable declarations
        String correctPassword = ""; //will be a different String once read from the file
        String password;
        boolean successfulLogin = false;
        
        //loads user login information
        try {
            
            //creates BufferedReader
            BufferedReader in = new BufferedReader(new FileReader(FILE_NAME));
            
            //reads password from file
            correctPassword = in.readLine();
            
            //closes reader
            in.close();
        } catch (IOException e){
            System.out.println("Problem reading " + FILE_NAME);
        }
         
        //loops until user can log in
        do {
            
            //prompts user for password
            System.out.print("Password: ");
            password = sc.nextLine();
             
            //checks if password is correct
            if (password.equals(correctPassword)){
                successfulLogin = true;
            }
            
            //success message or try again
            if (successfulLogin){
                System.out.println("\nLogin Successful.\n");
            }
            else {
                System.out.println("Incorrect password. Please try again.\n");
            }
        } while(!successfulLogin);
    }
    
    /*
     *  The saveFile method saves the users assessments to a file
     *  Parameters: none
     *  Return: none
     *  Pre: user logged in
     *  Post: Assessments saved to file
     *  Author: Patrick Chung
     */
    public static void saveFile() {
        //constant declaration
        final String FILE_NAME = "assessments.txt";
        
        //object to compare to find Class
        Test test = new Test("", "", 0, 0, 0, false, "", 0);
        Test tempTest;
        Assignment tempAssignment;
        try {
            
            //creates BufferedWriter
            BufferedWriter out = new BufferedWriter(new FileWriter(FILE_NAME, false));
            
            //writes out date saved
            out.write("" + currentDate);
            out.newLine();
            
            //writes out ArrayList
            for (int i = 0; i < userAssessments.size(); i++){
                
                //writes out shared attributes
                out.write(userAssessments.get(i).getName());
                out.write(",");
                out.write(userAssessments.get(i).getCourse());
                out.write(",");
                out.write(String.format("%d", userAssessments.get(i).getDueDate()));
                out.write(",");
                out.write(String.format("%f", userAssessments.get(i).getWeight()));
                out.write(",");
                out.write(String.format("%d", userAssessments.get(i).getNumberOfMarks()));
                out.write(",");
                if (userAssessments.get(i).getCompleted()){
                    out.write("completed");
                }
                else {
                    out.write("incomplete");
                }
                out.write(",");
                out.write(userAssessments.get(i).getAdditionalNotes());
                out.write(",");
                
                //finds if assessment is assignment or test
                if (userAssessments.get(i).getClass() == test.getClass()){
                    
                    //stores object of current index in Test object in order to use Test methods
                    tempTest = (Test) userAssessments.get(i);
                    out.write(String.format("%d", tempTest.getNumOfQuestions()));
                } 
                else {
                    
                    //stores object of current index in ASsignment object in order to use Assignment methods
                    tempAssignment = (Assignment) userAssessments.get(i);
                    out.write(tempAssignment.getFormat());
                }
                
                //creates new line
                out.newLine();
            }
            
            //closes writer
            out.close();
        } catch (IOException e){
            System.out.println("Problem writing to " + FILE_NAME);
        }
    }
    
    /*
     *  The loadFile method loads the users assessments from a file
     *  
     *  Parameters: none
     *  Return: none
     *  Pre: properly formatted file
     *  Post: Assessments loaded to ArrayList from file
     *
     *  Author: Patrick Chung
     */
    public static void loadFile() {
        
        //constant declaration
        final String FILE_NAME = "assessments.txt";
        
        //variable declarations
        String[] assessmentParts;
        String line;
        String[] dateParts;
        LocalDate lastSaved;
        int daysSinceSave = 0;
        int numQuestions;
        boolean isComplete = false;
        
        try {
            
            //creates BufferedReader
            BufferedReader in = new BufferedReader(new FileReader(FILE_NAME));
            
            //reads date last saved
            if ((line = in.readLine()) != null){
                dateParts = line.split("-");
                lastSaved = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                
                //calculates days since last saved to subtract from due date numbers
                daysSinceSave = (int) lastSaved.until(currentDate, ChronoUnit.DAYS);
            }
            
            //reads until EoF
            while ((line = in.readLine()) != null){
                assessmentParts = line.split(",");
                switch (assessmentParts[5]){
                    case "incomplete":
                        isComplete = false;
                        break;
                    case "completed":
                        isComplete = true;
                        break;
                }
                
                //checks for test or assessment
                try {
                    numQuestions = Integer.parseInt(assessmentParts[7]);
                    
                    //creates new Test object and adds to ArrayList
                    userAssessments.add(new Test(assessmentParts[0], assessmentParts[1], Integer.parseInt(assessmentParts[2]) - daysSinceSave, Double.parseDouble(assessmentParts[3]), Integer.parseInt(assessmentParts[4]), isComplete, assessmentParts[6], numQuestions));
                } catch (NumberFormatException e) {
                    
                    //creates new Assignment object and adds to ArrayList
                    userAssessments.add(new Assignment(assessmentParts[0], assessmentParts[1], Integer.parseInt(assessmentParts[2]) - daysSinceSave, Double.parseDouble(assessmentParts[3]),Integer.parseInt(assessmentParts[4]), isComplete, assessmentParts[6], assessmentParts[7]));              
                    }
            }
            
            //closes reader
            in.close();
        } catch (IOException e){
            System.out.println("Problem reading " + FILE_NAME);
        }
    }
    
    /*
     *  The addAssessment method prompts the user to add a new assessment
     *  Parameters: none
     *  Return: none
     *  Pre: none
     *  Post: new assessment created
     *  Author: Patrick Chung
     */
    public static void addAssessment() {
        
        //variable declarations
        String name;
        String course;
        int dueDate = 0;
        double weight = 0;
        int numMarks = 0;
        int numQuestions = 0;
        boolean isTest = false;
        String format = "";
        String notes;
        boolean isComplete = false;
        int currentYear;
        int currentMonth;
        int currentDay;
        boolean validInput;
        String input;
        int dueYear = 0;
        int dueMonth = 0;
        int dueDay = 0;
        LocalDate dateDue;
        int foundCombo;
        do {
            
            //prompts user for assessment name
            System.out.print("\nAssessment Name: ");
            name = sc.nextLine();
            
            //prompts user for course name
            System.out.print("Course Name: ");
            course = sc.nextLine();
            foundCombo = nameCourseSearch(name, course);
            if (foundCombo == -1){
                validInput = true;
            }
            else {
                validInput = false;
                System.out.println("This assessment already exists. Please try a different name.");
            }
            
        } while(!validInput);
        
        //gets current date
        currentYear = currentDate.getYear();
        currentMonth = currentDate.getMonthValue();
        currentDay = currentDate.getDayOfMonth();
        
        //prompts user for year
        do {
            System.out.print("Year of Due Date: ");
            input = sc.nextLine();
            validInput = numValidation(input, true, true, true);
            
            //checks if year is before current year, if input was valid
            if (validInput){
                dueYear = Integer.parseInt(input);
                if (dueYear < currentYear){
                    System.out.println("Please enter either the current year or a year in the future.\n");
                    validInput = false;
                }
            }
        } while(!validInput);
        
        //prompts user for month
        do {
            System.out.print("Month of Due Date (Number): ");
            input = sc.nextLine();
            validInput = numValidation(input, true, true, true);
            if (validInput){
                //checks if month is before current month, if due year is the same as current year
                dueMonth = Integer.parseInt(input);
                if (dueYear == currentYear && dueMonth < currentMonth){
                    System.out.println("Please enter either the current month or a month in the future.\n");
                    validInput = false;
                }
                
                //checks if month is a valid month
                if (dueMonth > 12){
                    System.out.println("Please enter a valid month.\n");
                    validInput = false;
                }
            }
        } while(!validInput);
        
        //prompt user for day
        do {
            System.out.print("Due Day: ");
            input = sc.nextLine();
            validInput = numValidation(input, true, true, true);
            if (validInput){
                dueDay = Integer.parseInt(input);
                
                //checks if day is before current day, if the due year and month are the same as current date
                if (dueYear == currentYear && dueMonth == currentMonth && dueDay < currentDay){
                    System.out.println("Please enter either today or a day in the future.\n");
                    validInput = false;
                }
                
                //checks for leap years and days in a month
                if ((dueMonth == 2 && dueYear % 4 == 0 && (dueYear % 100 != 0 || dueYear % 400 == 0)) && dueDay > 29){ //February, divisible by 4, not divisible by 100, unless also divisible by 400
                    System.out.println("February only has 29 days in a leap year. Please try again.\n");
                    validInput = false;
                }
                if ((dueMonth == 2 && dueYear % 4 == 0 && (dueYear % 100 != 0 || dueYear % 400 == 0)) && dueDay == 29){
                    validInput = true;
                }
                else if (dueMonth == 2 && dueDay > 28){
                    System.out.println("February only has 28 days. Please try again.\n");
                    validInput = false;
                }
                else if ((dueMonth == 1 || dueMonth == 3 || dueMonth == 5 || dueMonth == 7 || dueMonth == 8 || dueMonth == 10 || dueMonth == 12) && dueDay > 31){
                    System.out.println("The inputted month only has 31 days. Please try again.\n");
                    validInput = false;
                }
                else if ((dueMonth == 4 || dueMonth == 6 || dueMonth == 9 || dueMonth == 11) && dueDay > 30){
                    System.out.println("The inputted month only has 30 days. Please try again.\n");
                    validInput = false;
                }
            }
        } while(!validInput);
        
        //calculates days until due
        dateDue = LocalDate.of(dueYear, dueMonth, dueDay);
        dueDate = (int) currentDate.until(dateDue, ChronoUnit.DAYS);
        
        //prompts user for weight of assessment
        do {
            System.out.print("Weightage (%): ");
            input = sc.nextLine();
            validInput = numValidation(input, false, true, false);
            
            //checks weight is under 100%
            if (validInput){
                weight = Integer.parseInt(input);
                if (weight > 100){
                    System.out.println("The weight cannot be over 100%!\n");
                    validInput = false;
                }
            }
        } while(!validInput);
        
        //prompts user for number of marks
        do {
            System.out.print("Number of Marks: ");
            input = sc.nextLine();
            validInput = numValidation(input, true, true, false);
        } while (!validInput);
        
        //parses to int
        numMarks = Integer.parseInt(input);
        
        //prompts user for assessment type
        do {
            System.out.print("Test or Assignment? (T/A): ");
            input = sc.nextLine();
            
            //input validation
            switch (input){
                case "T":
                case "t":
                    isTest = true;
                    validInput = true;
                    break;
                case "A":
                case "a":
                    isTest = false;
                    validInput = true;
                    break;
                default:
                    System.out.println("Please enter a valid option!\n");
                    validInput = false;
            }
        } while (!validInput);
        
        //proceeds according to assessment type
        if (isTest){
            
            //prompts user for number of questions
            do {
                System.out.print("Number of Questions: ");
                input = sc.nextLine();
                validInput = numValidation(input, true, true, true);
            } while(!validInput);
            
            //parses to int
            numQuestions = Integer.parseInt(input);
        }
        else {
            //prompts user for assignment format
            System.out.print("Assignment Format: ");
            format = sc.nextLine() + " ";
        }
        
        //prompts user for additional notes
        System.out.print("Additional Notes: ");
        notes = sc.nextLine() + " ";
        
        //creates new assessment based on type
        if (isTest){
            userAssessments.add(new Test(name, course, dueDate, weight, numMarks, isComplete, notes, numQuestions));
        }
        else {
            userAssessments.add(new Assignment(name, course, dueDate, weight, numMarks, isComplete, notes, format));   
        }
        System.out.println("Assessment successfully added.");
        
        //saves file
        saveFile();
        
        System.out.println("Returning to menu...\n");
    }
    
    /*
     *  The removeAssessment method prompts the user to remove an assessment
     *  Parameters: none
     *  Return: none
     *  Pre: none
     *  Post: new assessment created
     *  Author: Hamza and Patrick Chung
     */
    public static void removeAssessment() {
        
        //variable declaration
        String name;
        String course;
        int index;
        
        //returns to menu if arraylist is empty
        if(userAssessments.isEmpty()){
            System.out.println("\nSorry, we couldn't find any assessments to remove.");
            return;
        } 
        System.out.print("\nAssessment Name: ");
        name = sc.nextLine();
        System.out.print("Course: ");
        course = sc.nextLine();
        index = nameCourseSearch(name, course);
        if (index == -1){
            System.out.println("Sorry, assessment was not found.");
        }
        else {
            userAssessments.remove(index);
            System.out.println("Assessment was successfully removed.");
            saveFile();
        }
        
        System.out.println("Returning to menu...\n");
    }
    
    /*
     *  The nameCourseSearch method performs a linear search on the assessments ArrayList for a matching name-course combination
     *  Parameters:
     *      String name - the name of the assessment
     *      String course - the name of the course
     *  Return:
     *      int - the index of the found object, or -1
     */
    public static int nameCourseSearch(String name, String course) {
        
        //linear search through entire ArrayList until found
        for (int i = 0; i < userAssessments.size(); i++) {
            if (userAssessments.get(i).getName().equalsIgnoreCase(name)) {
                if (userAssessments.get(i).getCourse().equalsIgnoreCase(course)){
                    return i;
                }
            }
        } 
        
        //returns -1 if not found
        return -1;
    }
    
    /*Sorts the list of user assessments in descending order using the bubble sort algorithm
    * based on their priority level. Uses a another method (sortingHelper) to help rank priorities numerically.
    *@params: none
    *pre: The ArrayList (userAssessments) must be initialized
    *post: The Array list and file information is sorting into descending format
    * Author: Hamza
    */
    public static void sortAssessment() {
        for (int x = 0; x < userAssessments.size(); x++) {
            for (int y = 0; y < userAssessments.size() - 1; y++) {
                int element1 = sortingHelper(userAssessments.get(y).returnPriority());
                int element2 = sortingHelper (userAssessments.get(y + 1).returnPriority());
                if (element1 < element2) {
                    Assessment z = userAssessments.get(y);
                    userAssessments.set(y, userAssessments.get(y + 1));
                    userAssessments.set(y + 1, z);
                }
            }
        }
    }
    
    /* Converts a priority level into a integer value to help the sorting method (sortAssessment())
    *@params: priority - the string representing the priority level
    *pre: none
    *post: Returns an integer between 0–4 based on priority or -1 if non of the condidtions apply
    * Author: Hamza
    */
    public static int sortingHelper(String priority) {
        
        if (priority.equals("Top Priority")) {
            return 4;
        }
        else if (priority.equals("High Priority")) {
            return 3;
        }
        else if (priority.equals("Medium Priority")) {
            return 2;
        } 
        else if (priority.equals("Low Priority")) {
            return 1;
        }
        else if (priority.equals("Completed")) {
            return 0;
        } 
        return -1;
    }
    
    /*
     *  The numValidation method is used to validate numeric user inputs
     *  Parameters:
     *      String input - user input
     *      boolean negativeCheck - makes method check input for negatives
     *      boolean intCheck - makes method check input for only integers
     *      boolean zeroCheck - makes method check input for zero
     *  Return:
     *      boolean - whether or not the input is valid
     *  Pre: none
     *  Post: boolean returned for input validation
     *  Author: Patrick Chung
     */
    public static boolean numValidation(String input, boolean intCheck, boolean negativeCheck, boolean zeroCheck) {
        //variable declaration
        double parsedDouble = 0;
        int parsedInt = 0;
        boolean isValid = true;
        
        //checks for non-numbers and negatives
        try {
            //parses int if intCheck, otherwise parses variable to double
            if (intCheck){
                parsedInt = Integer.parseInt(input);
                
                //changes double variable to match int variable for checks
                parsedDouble = parsedInt;
            }
            else {
                parsedDouble = Double.parseDouble(input);
            }
            
            //checks for negatives if negativeCheck
            if (negativeCheck && (parsedDouble < 0 || parsedInt < 0)){
                System.out.println("Input cannot be zero. Please try again.\n");
                isValid = false;
            }
            
            //checks for zero if zeroCheck
            if (zeroCheck && (parsedDouble == 0 || parsedInt == 0)){
                System.out.println("input cannot be zero. Please try again.\n");
                isValid = false;
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid input. Please try again.\n");
            isValid = false;
        }
        return isValid;
    }
}
