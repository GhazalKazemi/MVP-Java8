package recipes.model;

import java.util.List;

public interface IQueries {   
    public List< Recipe > getRecipesForCategory( String c );  
    public List< Recipe > getRecipesForCategoryAndPreparationTime( String c, int pt1, int pt2 );
    public List< Recipe > getRecipesForCategoryAndCombinedTime( String c, int ct1, int ct2 );
    public int getNumberOfRecipesUsingMainIngredient( String ingredient );
    public int addRecipe( String name, String category, String ingredient, int ptime, int ctime );   
    public void close();
}
