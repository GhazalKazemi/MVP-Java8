package recipes.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class RecipeQueries implements IQueries {

    private static final String URL = "jdbc:derby://localhost:1527/recipes";
    private static final String USERNAME = "recipes";
    private static final String PASSWORD = "recipes";

    private Connection connection = null;
    private PreparedStatement selectRecipesForCategory = null; 
    private PreparedStatement selectRecipesForCategoryAndPreparationTime = null; 
    private PreparedStatement countRecipesWithMainIngredient = null;
    private PreparedStatement insertNewRecipe = null; 
     
    public RecipeQueries() { 
        
        try {
            connection = 
                DriverManager.getConnection( URL, USERNAME, PASSWORD );

            // create query that selects all recipes for a specified category
            selectRecipesForCategory = 
                connection.prepareStatement( "SELECT * FROM RECIPES WHERE CATEGORY = ?" );
            // create query that selects all recipes with a preration time within a specified range
            selectRecipesForCategoryAndPreparationTime = connection.prepareStatement( 
                "SELECT * FROM RECIPES WHERE CATEGORY = ? AND (PREPARATIONTIME >= ? AND PREPARATIONTIME <= ?)" );
         
             // create query that determines number of reservations for a specific day
            countRecipesWithMainIngredient = connection.prepareStatement( 
                "SELECT COUNT(MAININGREDIENT)FROM RECIPES WHERE MAININGREDIENT = ?" );
         
            // create query that adds a new entry into the database
            insertNewRecipe = connection.prepareStatement( 
                "INSERT INTO RECIPES " + 
                "( RECIPENAME, CATEGORY, MAININGREDIENT, PREPARATIONTIME, COOKINGTIME ) " + 
                "VALUES ( ?, ?, ?, ?, ? )" );
        }
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
            System.exit( 1 );
        }
    }
   
    private Recipe createRecipe( ResultSet rs ) throws SQLException { 
        return new Recipe(
            rs.getInt( "ID" ),
            rs.getString( "RECIPENAME" ),
            rs.getString( "CATEGORY" ),
            rs.getString( "MAININGREDIENT" ),
            rs.getInt( "PREPARATIONTIME" ),
            rs.getInt( "COOKINGTIME" ) ); 
   }
   
   // Get all recipes for a specified category
   public List< Recipe > getRecipesForCategory( String c ) {
        List< Recipe > results = null;
        ResultSet resultSet = null;
      
        try {
            // insert parameters into the prepared statement 
            selectRecipesForCategory.setString( 1, c );
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectRecipesForCategory.executeQuery(); 
            results = new ArrayList< Recipe >();
            while ( resultSet.next() )
                results.add( createRecipe( resultSet ) );
        }
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();         
        }
        finally {
            try {
                resultSet.close();
            }
            catch ( SQLException sqlException ) {
                sqlException.printStackTrace();         
                close();
            }
        } 
        return results;
    }
    
    // Get all recipes for a specified category and preparation time range  
    public List< Recipe > getRecipesForCategoryAndPreparationTime( String c, int pt1, int pt2 ) {
        List< Recipe > results = null;
        ResultSet resultSet = null;

        try {
            // insert parameters into the prepared statement 
            selectRecipesForCategoryAndPreparationTime.setString( 1, c );
            selectRecipesForCategoryAndPreparationTime.setInt( 2, pt1 );
            selectRecipesForCategoryAndPreparationTime.setInt( 3, pt2 );
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectRecipesForCategoryAndPreparationTime.executeQuery(); 
            results = new ArrayList< Recipe >();
            while ( resultSet.next() )
                results.add( createRecipe( resultSet ) );
        }
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            }
            catch ( SQLException sqlException ) {
                sqlException.printStackTrace();         
                close();
            }
        }
        return results;
    }
   
    // Get all recipes for a specified category that are within the specifiedcombined time range  
    public List< Recipe > getRecipesForCategoryAndCombinedTime( String c, int ct1, int ct2 ) {
        List< Recipe > results = null;
        ResultSet resultSet = null;
      
        try {
            // insert parameters into the prepared statement 
            selectRecipesForCategory.setString( 1, c );
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectRecipesForCategory.executeQuery(); 
            results = new ArrayList< Recipe >();
            while ( resultSet.next() ) {
                int t = resultSet.getInt( "PREPARATIONTIME" ) + resultSet.getInt( "COOKINGTIME" ); 
                if ( t >= ct1 && t <= ct2 )
                    results.add( createRecipe( resultSet ) );
            }
        }
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();         
        }
        finally {
            try {
                resultSet.close();
            }
            catch ( SQLException sqlException ) {
                sqlException.printStackTrace();         
                close();
            }
        } 
        return results;
    }
   
    // count the number of recipes that use the specified main ingredient
    public int getNumberOfRecipesUsingMainIngredient( String ingredient ) {
        List< Recipe > results = null;
        ResultSet resultSet = null;
        int count = 0; 
        
        try {
            // insert parameters into the prepared statement
            countRecipesWithMainIngredient.setString( 1, ingredient );
            // execute the query. The ResultSet consists of one row with a single value
            resultSet = countRecipesWithMainIngredient.executeQuery(); 
            resultSet.next();
            count = resultSet.getInt( 1 );
        }
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();         
        }
        finally {
            try {
                resultSet.close();
            }
            catch ( SQLException sqlException ) {
                sqlException.printStackTrace();         
                close();
            }
        }
        return count;
    }
   
    // add a recipe
    public int addRecipe( String name, String category, String ingredient, int ptime, int ctime ) {
        int result = 0;
        
        // set parameters, then execute insertNewBooking
        try {
            // insert parameters into the prepared statement
            insertNewRecipe.setString( 1, name );
            insertNewRecipe.setString( 2, category);
            insertNewRecipe.setString( 3, ingredient );
            insertNewRecipe.setInt( 4, ptime );
            insertNewRecipe.setInt( 5, ctime );
            // execute the query. The query returns the number of rows updated
            result = insertNewRecipe.executeUpdate(); 
        }
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
 
    // close the database connection
    public void close() {
        
        try {
            connection.close();
        }
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
            System.exit( 1 );
        }
    }
}

