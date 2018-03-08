package recipes.model;

public class Recipe {
   private int id;
   private String name;
   private String category;
   private String mainIngredient;
   private int preparationTime;
   private int cookingTime;

   // constructors
   public Recipe() {
   }

   public Recipe( int _id, String _name, String _category, String _mainIngredient, int _preparationTime, int _cookingTime  ) {
      setID( _id );
      setName( _name );
      setCategory( _category);
      setMainIngredient( _mainIngredient );
      setPreparationTime( _preparationTime );
      setCookingTime( _cookingTime );
   }

   // getters and setters
   public void setID( int _id ) {
      id = _id;
   }

   public int getID(){
      return id;
   }

   public void setName( String _name ) {
      name =_name;
   }

   public String getName() {
      return name;
   } 
   
   public void setCategory( String _category ) {
      category = _category;
   } 

   public String getCategory() {
      return category;
   } 
    
   public void setMainIngredient( String _mainIngredient ) {
      mainIngredient = _mainIngredient;
   } 

   public String getMainIngredient() {
      return mainIngredient;
   } 
      
   public void setPreparationTime( int _preparationTime ) {
      preparationTime = _preparationTime;
   }

   public int getPreparationTime() {
      return preparationTime;
   }
   
      public void setCookingTime( int _cookingTime ) {
      cookingTime = _cookingTime;
   }

   public int getCookingTime() {
      return cookingTime;
   }
   
   @Override
   public String toString() {
       return "<"+name+","+category+","+mainIngredient+","+preparationTime+","+cookingTime+">";
   }
}

