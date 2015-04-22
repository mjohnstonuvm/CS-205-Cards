import java.sql.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DatabaseTest{
//method to connect to the database
    public static Connection connectDB(){
      Connection conn = null; 
       try{
         MysqlDataSource dataSource = new MysqlDataSource();
         dataSource.setUser("mmai_admin");
         dataSource.setPassword("ypq6ICtpFplN");
         dataSource.setServerName("webdb.uvm.edu");
         dataSource.setPort(3306);
         dataSource.setDatabaseName("MMAI_CS205");
         String userName = "My Mai";
         conn = dataSource.getConnection();
         System.out.println("connected to the database");
         }catch(Exception e){
         System.out.println("connection to database failed");
         }  
         return conn;     
      }
   //What I get to work with 
   //PlayerName
   //difficulty
   //Score
   //Win
   
   public static void main(String[] args) throws SQLException{
   
      //Fields that are passed using the game
      String player = "newUser6";
      String diff = "Easy";
      boolean winBool = true;
      int playerScore = 9; 
      
      //Data Fields 
      int winTot; 
      int win;
      double winPercentage;
      int totalPts; 
      double avgPts;
      int timesPlayed;
      
      if(winBool)
         win = 1;
      else
         win = 0; 
       
                 
      //Creates a connection to the database
      Connection conn = connectDB();
      //Check if player is in the database already
      Statement stmt = conn.createStatement(); 
      ResultSet rs = stmt.executeQuery("Select * FROM PlayerData Where pmkPlayerName = '" + player + "'");
      
      //if the player isn't, add them to the database
      if(!rs.next()){
         Statement insertNewUser = conn.createStatement();
         switch (diff){
            case "Hard":
               System.out.println("difficulty = hard");
               insertNewUser.executeUpdate("Insert into PlayerData(pmkPlayerName, gamesPlayed, winH, totalPointsH, timesPlayedH) values ('"+ player +"',1,"+win+"," + playerScore + ",1)");
               break;
            case "Medium":
               System.out.println("difficulty = medium");
               insertNewUser.executeUpdate("Insert into PlayerData(pmkPlayerName, gamesPlayed, winM, totalPointsM, timesPlayedM) values ('"+ player +"',1,"+win+"," + playerScore + ",1)");
               break;
            case "Easy":
               System.out.println("difficulty = easy");
               insertNewUser.executeUpdate("Insert into PlayerData(pmkPlayerName, gamesPlayed, winE, totalPointsE, timesPlayedE) values ('"+ player +"',1,"+win+"," + playerScore + ",1)"); 
               break; 
         }  
         
         Statement queryNewUserData = conn.createStatement(); 
         ResultSet newUserData = queryNewUserData.executeQuery("Select * FROM PlayerData Where pmkPlayerName = '" + player + "'");  
         while(newUserData.next()){
            //String playerName = rs.getString("pmkPlayerName");
            switch(diff){
               case "Hard":
                  winTot = newUserData.getInt("winH"); // reinsert this into the database later! 
                  timesPlayed = newUserData.getInt("timesPlayedH"); //increases by 1 every game ****
                  winPercentage = (float)(winTot)/(timesPlayed)*100;
                  totalPts = newUserData.getInt("totalPointsH") + playerScore; 
                  avgPts = totalPts / timesPlayed; 
                  //System.out.println("Player Name: " + playerName);
                  System.out.println("Data for User: " + player);
                  System.out.println("Hard Games Won: " + winTot);
                  System.out.println("times played on hard: " + timesPlayed);
                  System.out.printf("Win percentage on hard: %.2f\n", winPercentage);
                  System.out.println("Total points played on hard: " + totalPts);
                  System.out.println("Average Score on Hard: " + avgPts); 
                  Statement updateNew = conn.createStatement(); 
                  updateNew.executeUpdate("Update PlayerData Set winH = " + winTot + ", timesPlayedH = "+ timesPlayed +", winPercentageH = "+ winPercentage +", totalPointsH = "+ totalPts +", avgPtsH = "+ avgPts +" where pmkPlayerName = '"+ player+"'");
                  break;
               case "Medium":
                  winTot = newUserData.getInt("winM"); // reinsert this into the database later! 
                  timesPlayed = newUserData.getInt("timesPlayedM"); //increases by 1 every game ****
                  winPercentage = (float)(winTot)/(timesPlayed)*100;
                  totalPts = newUserData.getInt("totalPointsM") + playerScore; 
                  avgPts = totalPts / timesPlayed; 
                  //System.out.println("Player Name: " + playerName);
                  System.out.println("Data for User: " + player);
                  System.out.println("Medium Games Won: " + winTot);
                  System.out.println("Times played on Medium: " + timesPlayed);
                  System.out.printf("Win percentage on Medium: %.2f\n", winPercentage);
                  System.out.println("Total points played on Medium: " + totalPts);
                  System.out.println("Average Score on Medium: " + avgPts); 
                  Statement updateNewM = conn.createStatement(); 
                  updateNewM.executeUpdate("Update PlayerData Set winM = " + winTot + ", timesPlayedM = "+ timesPlayed +", winPercentageM = "+ winPercentage +", totalPointsM = "+ totalPts +", avgPtsM = "+ avgPts +" where pmkPlayerName = '"+ player+"'");
                  break;
                  
               case "Easy":
                  winTot = newUserData.getInt("winE"); // reinsert this into the database later! 
                  timesPlayed = newUserData.getInt("timesPlayedE"); //increases by 1 every game ****
                  winPercentage = (float)(winTot)/(timesPlayed)*100;
                  totalPts = newUserData.getInt("totalPointsE") + playerScore; 
                  avgPts = totalPts / timesPlayed; 
                  //System.out.println("Player Name: " + playerName);
                  System.out.println("Data for User: " + player);
                  System.out.println("Easy Games Won: " + winTot);
                  System.out.println("Times played on Easy: " + timesPlayed);
                  System.out.printf("Win percentage on Easy: %.2f\n", winPercentage);
                  System.out.println("Total points played on Easy: " + totalPts);
                  System.out.println("Average Score on Easy: " + avgPts); 
                  Statement updateNewE = conn.createStatement(); 
                  updateNewE.executeUpdate("Update PlayerData Set winM = " + winTot + ", timesPlayedM = "+ timesPlayed +", winPercentageM = "+ winPercentage +", totalPointsM = "+ totalPts +", avgPtsM = "+ avgPts +" where pmkPlayerName = '"+ player+"'");
                  break;               
         }
         }
         //insertNewUser.executeUpdate("Insert into PlayerData(pmkPlayerName, gamesPlayed) values ('"+ player +"', 1)");
      }else{
      //user exists, query their user data
         System.out.println("User Exists");
         Statement queryData = conn.createStatement(); 
         ResultSet userData = queryData.executeQuery("Select * FROM PlayerData Where pmkPlayerName = '" + player + "'");         
         while(userData.next()){
            //String playerName = rs.getString("pmkPlayerName");
            switch(diff){
               case "Hard":
                  winTot = userData.getInt("winH") + win; // reinsert this into the database later! 
                  timesPlayed = userData.getInt("timesPlayedH") + 1 ; //increases by 1 every game ****
                  winPercentage = (float)(winTot)/(timesPlayed)*100;
                  totalPts = userData.getInt("totalPointsH") + playerScore; 
                  avgPts = totalPts / timesPlayed; 
                  //System.out.println("Player Name: " + playerName);
                  System.out.println("Data for User: " + player);
                  System.out.println("Hard Games Won: " + winTot);
                  System.out.println("times played on hard: " + timesPlayed);
                  System.out.printf("Win percentage on hard: %.2f\n", winPercentage);
                  System.out.println("Total points played on hard: " + totalPts);
                  System.out.println("Average Score on Hard: " + avgPts); 
                  Statement update = conn.createStatement(); 
                  update.executeUpdate("Update PlayerData Set winH = " + winTot + ", timesPlayedH = "+ timesPlayed +", winPercentageH = "+ winPercentage +", totalPointsH = "+ totalPts +", avgPtsH = "+ avgPts +" where pmkPlayerName = '"+ player+"'");
                  break;
               case "Medium":
                  winTot = userData.getInt("winM") + win; // reinsert this into the database later! 
                  timesPlayed = userData.getInt("timesPlayedM") + 1 ; //increases by 1 every game ****
                  winPercentage = (float)(winTot)/(timesPlayed)*100;
                  totalPts = userData.getInt("totalPointsM") + playerScore; 
                  avgPts = totalPts / timesPlayed; 
                  //System.out.println("Player Name: " + playerName);
                  System.out.println("Data for User: " + player);
                  System.out.println("Medium Games Won: " + winTot);
                  System.out.println("Times played on Medium: " + timesPlayed);
                  System.out.printf("Win percentage on Medium: %.2f\n", winPercentage);
                  System.out.println("Total points played on Medium: " + totalPts);
                  System.out.println("Average Score on Medium: " + avgPts); 
                  Statement updateM = conn.createStatement(); 
                  updateM.executeUpdate("Update PlayerData Set winM = " + winTot + ", timesPlayedM = "+ timesPlayed +", winPercentageM = "+ winPercentage +", totalPointsM = "+ totalPts +", avgPtsM = "+ avgPts +" where pmkPlayerName = '"+ player+"'");
                  break;
                  
               case "Easy":
                  winTot = userData.getInt("winE") + win; // reinsert this into the database later! 
                  timesPlayed = userData.getInt("timesPlayedE") + 1 ; //increases by 1 every game ****
                  winPercentage = (float)(winTot)/(timesPlayed)*100;
                  totalPts = userData.getInt("totalPointsE") + playerScore; 
                  avgPts = totalPts / timesPlayed; 
                  //System.out.println("Player Name: " + playerName);
                  System.out.println("Data for User: " + player);
                  System.out.println("Easy Games Won: " + winTot);
                  System.out.println("Times played on Easy: " + timesPlayed);
                  System.out.printf("Win percentage on Easy: %.2f\n", winPercentage);
                  System.out.println("Total points played on Easy: " + totalPts);
                  System.out.println("Average Score on Easy: " + avgPts); 
                  Statement updateE = conn.createStatement(); 
                  updateE.executeUpdate("Update PlayerData Set winE = " + winTot + ", timesPlayedE = "+ timesPlayed +", winPercentageE = "+ winPercentage +", totalPointsE = "+ totalPts +", avgPtsE = "+ avgPts +" where pmkPlayerName = '"+ player+"'");
                  break;               
         }
         }

                  
      }
      //SCOREBOARD
      Statement queryTop5 = conn.createStatement(); 
      ResultSet top5Data = null;
      switch(diff){
         case "Hard":
            top5Data = queryTop5.executeQuery("Select pmkPlayerName, winH as winVar, winPercentageH as winPerVar, avgPtsH as avgPtsVar From PlayerData ORDER by winH DESC Limit 3");
            break;
         case "Medium":
            top5Data = queryTop5.executeQuery("Select pmkPlayerName, winM as winVar, winPercentageM as winPerVar, avgPtsM as avgPtsVar From PlayerData ORDER by winM DESC Limit 3");
            break;
         case "Easy":
            top5Data = queryTop5.executeQuery("Select pmkPlayerName, winE as winVar, winPercentageE as winPerVar, avgPtsE as avgPtsVar From PlayerData ORDER by winE DESC Limit 3");
            break;
      }
      int rank = 1; 
      System.out.println("Rank      Top Players    # of wins      Win Percent    Average Points");

      while(top5Data.next()){
      String topPlayer = top5Data.getString("pmkPlayerName");
      int numOfWins = top5Data.getInt("winVar");
      double wPercent = top5Data.getInt("winPerVar"); 
      double avgScore = top5Data.getInt("avgPtsVar"); 
      System.out.println(rank + "      " + topPlayer + "       " + numOfWins + "       " + wPercent + "     " + avgScore);
      rank++; 
      }

   }//main
}//class
      