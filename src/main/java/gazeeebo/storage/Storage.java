
package gazeeebo.storage;


import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;


import gazeeebo.commands.specialization.ModuleCategory;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.DoAfter;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Task;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Storage {

    private String[] relativePath
            = {"Save.txt", "/Save.txt"};
    private String[] relativePathPassword
            = {"Password.txt", "/Password.txt"};
    private String[] relativePathContact
            = {"Contact.txt", "/Contact.txt"};
    private String[] relativePathExpenses
            = {"Expenses.txt", "/Expenses.txt"};
    private String[] relativePathPlaces
            = {"Places.txt", "/Places.txt"};
    private String[] relativePathTrivia
            = {"Trivia.txt", "/Trivia.txt"};
    private String[] relativePathCAP
            = {"CAP.txt", "/CAP.txt"};
    private String[] relativePathSpecialization
            = {"Specialization.txt", "/Specialization.txt"};
    private String[] relativePathStudyPlanner
            = {"Study_Plan.txt", "/Study_Plan.txt"};
    private String[] relativePathCompletedElectives
            = {"CompletedElectives.txt", "/CompletedElectives.txt"};
    private String[] relativePathPrerequisite
            = {"Prerequisite.txt", "/Prerequisite.txt"};
    private String[] getrelativePathGoal
            = {"goal.txt", "/goal.txt"};
    private String[] getrelativePathModule
            = {"modules.txt", "/modules.txt"};
    private String[] getrelativeNoteDaily
            = {"NoteDaily.txt", "/NoteDaily.txt"};
    private String[] getrelativeNoteWeekly
            = {"NoteMonthly.txt", "/NoteMonthly.txt"};
    private String[] getrelativeNoteMonthly
            = {"NoteWeekly.txt", "/NoteWeekly.txt"};
    private String relativePathExpensesResource
            = "Expenses.txt";
    private String relativePathTriviaResource
            = "Trivia.txt";
    private String relativePathSpecializationResource
            = "Specialization.txt";
    private String relativePathStudyPlannerResource
            = "Study_Plan.txt";
    private String relativePathCompletedElectivesResource
            = "CompletedElectives.txt";
    private String relativePathPrerequisiteResource
            = "Prerequisite.txt";
    //@@author jessteoxizhi

    /**
     * Check if there are save txt file in the directory, if there is not, create a new txt file and copy
     * preloaded data into the new txt file
     * @throws IOException exception when there is an error read the txt file
     */
    public void startUp() throws IOException {
        ArrayList<String[]> resourcelist = new ArrayList<>();
        resourcelist.add(relativePath);
        resourcelist.add(relativePathPassword);
        resourcelist.add(relativePathContact);
        resourcelist.add(relativePathExpenses);
        resourcelist.add(relativePathExpenses);
        resourcelist.add(relativePathPlaces);
        resourcelist.add(relativePathTrivia);
        resourcelist.add(relativePathCAP);
        resourcelist.add(relativePathSpecialization);
        resourcelist.add(relativePathStudyPlanner);
        resourcelist.add(relativePathCompletedElectives);
        resourcelist.add(relativePathPrerequisite);
        resourcelist.add(getrelativePathGoal);
        resourcelist.add(getrelativeNoteDaily);
        resourcelist.add(getrelativeNoteWeekly);
        resourcelist.add(getrelativeNoteMonthly);
        resourcelist.add(getrelativePathModule);
        for (String[] path : resourcelist) {
            File tmpDir = new File(path[0]);
            boolean exists = tmpDir.exists();
            if (!exists) {
                InputStream inputStream
                        = Storage.class.getResourceAsStream(path[1]);
                Scanner sc = new Scanner(inputStream);
                FileWriter fw = new FileWriter(path[0], true);
                String s;
                while (sc.hasNext()) {
                    s = sc.nextLine() + "\n"; // read a line
                    fw.write(s); // write to output file
                    fw.flush();
                }
                sc.close();
                fw.close();
            }
        }
    }

    public void writeToExpensesFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathExpensesResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }


    public HashMap<LocalDate, ArrayList<String>> readFromExpensesFile() throws FileNotFoundException {
        HashMap<LocalDate, ArrayList<String>> expenses = new HashMap<LocalDate, ArrayList<String>>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        File f = new File(relativePathExpensesResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            ArrayList<String> itemAndPriceList = new ArrayList<>();
            String[] split = sc.nextLine().split("\\|");
            LocalDate dateOfPurchase = LocalDate.parse(split[0], fmt);
            boolean isEqual = false;
            for (LocalDate key : expenses.keySet()) {
                if (dateOfPurchase.equals(key)) { //if date equal
                    expenses.get(key).add(split[1]);
                    isEqual = true;
                }
            }
            if (isEqual == false) {
                itemAndPriceList.add(split[1]);
                expenses.put(dateOfPurchase, itemAndPriceList);
            }
        }
        return expenses;
    }

    /**
     * This method read Trivia.txt, get users' past inputs from the file.
     * @return hash-map of keywords and inputs
     * @throws FileNotFoundException
     */
    public Map<String, ArrayList<String>> Read_Trivia() throws FileNotFoundException {
        Map<String, ArrayList<String>> CommandMemory = new HashMap<>();

        File f = new File(relativePathTriviaResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String InputCommand = sc.nextLine();
            if (CommandMemory.containsKey(InputCommand.split(" ")[0])) {
                ArrayList<String> oldlist = new ArrayList<String>(CommandMemory.get(InputCommand.split(" ")[0]));
                if (!oldlist.contains(InputCommand)) {
                    oldlist.add(InputCommand);
                    CommandMemory.put(InputCommand.split(" ")[0], oldlist);
                }
            } else {
                ArrayList<String> newlist = new ArrayList<String>();
                newlist.add(InputCommand);
                CommandMemory.put(InputCommand.split(" ")[0], newlist);
            }
        }
        sc.close();
        return CommandMemory;
    }

    /**
     * This method writes to Trivia.txt file, record down updates in record of user inputs.
     * @param fileContent
     * @throws IOException
     */
    public void Storage_Trivia(String fileContent) throws IOException {
        File file = new File(relativePathTriviaResource);
        if (file.exists() && !file.canWrite()) {
            System.out.println("File exists and it is read only, making it writable");
            file.setWritable(true);
        }
        try {
            FileWriter fileWriter = new FileWriter(relativePathTriviaResource, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(fileContent);
            bufferedWriter.flush();
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeToSpecializationFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathSpecializationResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public HashMap<String, ArrayList<ModuleCategory>> readFromSpecializationFile() throws IOException {
        HashMap<String, ArrayList<ModuleCategory>> specMap = new HashMap<>();

        File file = new File(relativePathSpecializationResource);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String[] split = sc.nextLine().split("\\|");
            ArrayList<ModuleCategory> moduleBD = new ArrayList<>();
            ModuleCategory mC = new ModuleCategory(split[2]);
            moduleBD.add(mC);
            specMap.put(split[1], moduleBD);
        }

        return specMap;
    }

    public void writeToCompletedElectivesFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathCompletedElectivesResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    public HashMap<String, ArrayList<String>> readFromCompletedElectivesFile() throws IOException {
        HashMap<String, ArrayList<String>> completedEMap = new HashMap<>();
        File file = new File(relativePathCompletedElectivesResource);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            ArrayList<String> completedElectiveList = new ArrayList<>();
            String[] split = sc.nextLine().split("\\|");
            String checkKey = split[0];
            boolean isEqual = false;
            for (String key : completedEMap.keySet()) {
                if (checkKey.equals(key)) { //if date equal
                    completedEMap.get(key).add(split[1]);
                    isEqual = true;
                }
                if (isEqual == false) {
                    completedElectiveList.add(split[1]);
                    completedEMap.put(checkKey, completedElectiveList);
                }
            }
        }
        //}
        return completedEMap;
    }

    /**
     * This method reads from Study_Plan.txt, get users' current module plan
     * @return double ArrayList storing the table.
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> Read_StudyPlan() throws IOException {
        ArrayList<ArrayList<String>> studyplan = new ArrayList<ArrayList<String>>();
        File file = new File(relativePathStudyPlannerResource);
        Scanner sc = new Scanner(file);
        for (int i = 0; i < 8; i++) {
            if (sc.hasNext()) {
                String[] split = sc.nextLine().split(" ");
                ArrayList<String> temp = Arrays.stream(split).collect(Collectors.toCollection(ArrayList::new));
                studyplan.add(temp);
            } else {
                ArrayList<String> temp = new ArrayList<String>();
                studyplan.add(temp);
            }
        }
        // }
        return studyplan;
    }

    /**
     * This method writes to Study_Plan.txt, updates changes in module plan.
     * @param fileContent
     * @throws IOException
     */
    public void Storage_StudyPlan(String fileContent) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(relativePathStudyPlannerResource));
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This method reads from prerequisite txt file, gets information about courses' prerequisites.
     * @return
     * @throws IOException
     */
    public HashMap<String, ArrayList<String>> readFromPrerequisiteFile() throws IOException {
        HashMap<String, ArrayList<String>> PrerequisiteList = new HashMap<String, ArrayList<String>>();
        File file = new File(relativePathPrerequisiteResource);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String WholeSentence = sc.nextLine();
            String head = WholeSentence.split(" ")[0];
            ArrayList<String> Prerequisites = new ArrayList<String>();
            for (int i = 1; i < WholeSentence.split(" ").length; i++) {
                Prerequisites.add(WholeSentence.split(" ")[i]);
            }
            PrerequisiteList.put(head, Prerequisites);
        }
        return PrerequisiteList;
    }
}