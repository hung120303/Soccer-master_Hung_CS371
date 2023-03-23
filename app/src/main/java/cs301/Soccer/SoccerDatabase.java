package cs301.Soccer;

import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private Hashtable<String, SoccerPlayer> database = new Hashtable<>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {
        if(!database.containsKey(firstName + "**" + lastName)){
            database.put(firstName + "**" + lastName, new SoccerPlayer(firstName, lastName, uniformNumber, teamName));
            return true;
        }
        else
            return false;
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        if(database.containsKey(firstName + "**" + lastName)){
            database.remove(firstName + "**" + lastName);
            return true;
        }
        else
            return false;
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        return database.get(firstName + "**" + lastName);
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        if (database.containsKey(firstName + "**" + lastName)) {
            database.get(firstName + "**" + lastName).bumpGoals();
            return true;
        }
        else{
            return false;
    }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        if (database.containsKey(firstName + "**" + lastName)) {
            database.get(firstName + "**" + lastName).bumpYellowCards();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        if (database.containsKey(firstName + "**" + lastName)) {
            database.get(firstName + "**" + lastName).bumpRedCards();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        if(teamName == null){
            return database.size();
        }
        else{
            int count = 0;
            Enumeration<String> players = database.keys();
            while(players.hasMoreElements()){
                if(database.get(players.nextElement()).getTeamName().equals(teamName)){
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        if(idx > numPlayers(teamName)){
            return null;
        }
        else{
            Enumeration<String> players = database.keys();
            for(int i =0; i< idx; i++){
                players.nextElement();
            }
            if(teamName == null){
                while(players.hasMoreElements()){
                    return database.get(players.nextElement());
                }
            }
            else{
                while(players.hasMoreElements()){
                    if(database.get(players.nextElement()).getTeamName().equals(teamName)){
                        return database.get(players.nextElement());
                    }
                    else
                        players.nextElement();
                }

            }
        }
        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String firstName = scanner.nextLine();
                String lastName = scanner.nextLine();
                String teamName = scanner.nextLine();
                int uniform = Integer.parseInt(scanner.nextLine());
                int goals = Integer.parseInt(scanner.nextLine());
                int yellowCards = Integer.parseInt(scanner.nextLine());
                int redCards = Integer.parseInt(scanner.nextLine());
                SoccerPlayer sp = new SoccerPlayer(firstName, lastName, uniform,teamName);
                for(int i = 0; i < goals; i++){
                    sp.bumpGoals();
                }
                for(int i = 0; i < yellowCards; i++){
                    sp.bumpYellowCards();
                }
                for(int i = 0; i < redCards; i++){
                    sp.bumpRedCards();
                }
                removePlayer(firstName, lastName);
                addPlayer(firstName, lastName, uniform, teamName);
            }
        }
        catch(FileNotFoundException e){
            return false;
        }
        return true;
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        FileWriter fw = null;
        try{
            fw = new FileWriter(file);
        }
        catch(IOException e){
            return false;
        }
            PrintWriter pw = new PrintWriter(fw);
            Enumeration<String> players = database.keys();
            while (players.hasMoreElements()) {
                String key = players.nextElement();
                pw.println(logString(database.get(key).getFirstName()));
                pw.println(logString(database.get(key).getLastName()));
                pw.println(logString(database.get(key).getTeamName()));
                pw.println(logString(Integer.toString(database.get(key).getUniform())));
                pw.println(logString(Integer.toString(database.get(key).getGoals())));
                pw.println(logString(Integer.toString(database.get(key).getYellowCards())));
                pw.println(logString(Integer.toString(database.get(key).getRedCards())));
            }
            pw.close();
            return true;
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
//        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        HashSet<String> teams = new HashSet<String>();
        Enumeration<String> list = database.keys();
        while(list.hasMoreElements()){
            teams.add(database.get(list.nextElement()).getTeamName());
        }
        return teams;
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if(database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
