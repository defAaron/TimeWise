# TimeWise – Assessment Tracker

TimeWise is a simple Java console application that helps students keep track of their assessments, deadlines, and completion status.

## Features

### 📋 Assessment Management
- **Add Assessment:** Input the assessment name, weightage, due date, and completion status  
- **Remove Assessment:** Remove an existing assessment from the list  
- **Search Assessments:** Search for an assessment by name  
- **List All Assessments:** Display all assessments in the format:  
Assessment Name --> Weightage %
- **List Overdue Assessments:** Display overdue assessments in the format:  
Assessment Name --> X days overdue
- **Change Completion Status:** Mark an assessment as complete or incomplete  

### 🖥️ User Interface
- Text-based console menu with options 0–7  
- Input validation to handle invalid entries gracefully  

### 🚧 Future Features (Planned)
- File auto-saving and loading for persistence  
- More detailed statistics for upcoming and overdue assessments  
- Integration with calendar APIs for automatic reminders  

## Technology Stack
- **Language:** Java  
- **Environment:** Console application (command-line interface)  
- **Data Handling:** `ArrayList` for storing assessment data  

## Getting Started

### Prerequisites
- Java 17 or higher  
- Command-line access  

### Installation
Clone the repository:
```bash
git clone <repository-url>
cd timewise
Compile the Java program:

bash
javac Main.java
Run the program:

bash
java Main
Project Structure
bash
timewise/
├── Main.java       # Main Java program containing all logic
└── README.md       # Project documentation
