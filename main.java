import IA.Session;
import IA.Storage;

import javax.xml.bind.SchemaOutputResolver;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class main {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public static void main (String[] args){
        ArrayList<Session> sessionList = Storage.load();
        mainMenu(sessionList);
        Storage.save(sessionList);
    }

    public static void mainMenu(ArrayList<Session> sessionList){
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Meditation Calender!");
        System.out.println("What would you like to do?");
        System.out.println(" -Start New Session (\"S\") \n -View Previous Sessions (\"P\") \n -Advanced Statistics (\"A\") \n -Quit (\"Q\")");
        String direction = scan.nextLine();
        if (direction.equalsIgnoreCase("s")){
            newSesh(sessionList);
        }
        if (direction.equalsIgnoreCase("p")){
            previousSession(sessionList);
        }
        if (direction.equalsIgnoreCase("a")){
            stats();
        }
        if (direction.equalsIgnoreCase("q")){
            return;
        }
    }



    public static void newSesh(ArrayList<Session> sessionList) {
        Session session = new Session();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start Now? (Yes or Go Back)");
        String shouldStart = scanner.nextLine();
        if (shouldStart.equalsIgnoreCase("Yes")) {
            session.setStart(new Date());
            System.out.println("Type \"Stop\" when you are finished.");
            String nowS = scanner.nextLine();
            if (nowS.equalsIgnoreCase("Stop")) {
                session.setEnd(new Date());

                long durationSeconds = (session.getEnd().getTime() - session.getStart().getTime()) / 1000;
                System.out.println("duration: " + durationSeconds + " seconds");
            }
            System.out.println("How would you rate your session? (1-5)");
            int seshRating = scanner.nextInt();
            scanner.nextLine();
            session.setRating(seshRating);
            System.out.println("Would you like to provide any addition information about your session? (Yes or No)");
            String ifDescription = scanner.nextLine();
            if (ifDescription.equalsIgnoreCase("yes")){
                System.out.println("Type in your description.");
                String description = scanner.nextLine();
                session.setDescription(description);
            }

            sessionList.add(session);

        }
        if (shouldStart.equalsIgnoreCase("Go back")){
            mainMenu(sessionList);
        }
    }
    public static void previousSession(ArrayList<Session> sessionList){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which day would you like to view? (MM/dd/yyyy)");
        Date day = null;
        while(day == null) {
            String dateString = scanner.nextLine();
            try {
                day = formatter.parse(dateString);
            }
            catch(ParseException cause){
                System.out.println("Invalid day format");
            }
        }
        List<Session> filteredSessions = filterSessions(day, sessionList);
        if (filteredSessions.size() == 0){
            System.out.println("No sessions found for that day.");
            previousSession(sessionList);
        }
        Session selectedSession = selectSession(filteredSessions);
        boolean ifAnswer = false;
        while(ifAnswer == false){
            System.out.println("Do you want to view the rating? (Yes or No)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")){
                System.out.println(selectedSession.getRating());
                ifAnswer = true;
            } else if (response.equalsIgnoreCase("no")){
                ifAnswer = true;
            }
        }
        ifAnswer = false;
        while(ifAnswer == false){
            System.out.println("Do you want to view the description? (Yes or No)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")){
                System.out.println(selectedSession.getDescription());
                ifAnswer = true;
            } else if (response.equalsIgnoreCase("no")){
                ifAnswer = true;
            }
    }
        ifAnswer = false;
        while(ifAnswer == false){
            System.out.println("Would you like to view another?");
            String another = scanner.nextLine();
            if(another.equalsIgnoreCase("yes")){
                ifAnswer = true;
                previousSession(sessionList);
            } if(another.equalsIgnoreCase("no")){
                ifAnswer = true;
                mainMenu(sessionList);
            }
        }
    }

    public static List<Session> filterSessions(Date day, List<Session> sessionList){
        List<Session> resultList = new ArrayList<Session>();
        for(Session session : sessionList){
            Date start = session.getStart();
            if(start.getYear() == day.getYear() && start.getMonth() == day.getMonth() && start.getDay() == day.getDay()){
                resultList.add(session);
            }
        }
        return resultList;
    }

    public static void listSessions(List<Session> sessionList){
       for(int i = 0; i < sessionList.size(); i++) {
           Session session = sessionList.get(i);
           System.out.println("#" + (i+1) + " " + session.getStart());
       }
    }

    public static Session selectSession(List<Session> sessionList){
        listSessions(sessionList);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the session you wish to view by its index.");
        Integer selection = null;
        while(selection == null){
            selection = scanner.nextInt();
            selection -= 1;
            if (selection < 0 || selection >= sessionList.size()){
                System.out.println("Invalid session index.");
                selection = null;
            }
        }
        return sessionList.get(selection);
    }
    public static void stats(){

    }
}
