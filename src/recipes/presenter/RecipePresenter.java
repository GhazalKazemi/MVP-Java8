package recipes.presenter;

import java.util.List;
import recipes.model.Recipe;
import recipes.model.IQueries;
import recipes.view.IView;

public class RecipePresenter {

    IQueries qry;
    IView view;

    public RecipePresenter(IQueries iq) {
        qry = iq;
        //   view = iv;
    }

    public void bind(IView iv) {
        view = iv;
    }

    // handles call when cat Btn is clicked
    public void recCat(String cat) {
        List<Recipe> results = qry.getRecCat(cat);
        StringBuilder strBuilder = new StringBuilder("");
        for (int i = 0; i < results.size(); i++) {
            strBuilder.append(results.get(i) + "\n");
        }
        view.setOutputTxtArea(strBuilder.toString());
    }

    // handles call when add Btn is clicked
    public void newRecipe(String name, String cat, String ingr, int prepTime, int combTime) {
        int result = qry.insertNewRec(name, cat, ingr, prepTime, combTime);
        if (result == 0) {
            view.setOutputTxtArea("Error: Recipe not added");
            return;
        }
        view.setOutputTxtArea("Recipe added");
    }

    // handles call when cat & prepTiem Btn is clicked
    public void recCatPrepTime(String cat, int ptf, int pts) {
        List<Recipe> results = qry.getRecCatPrepTime(cat, ptf, pts);
        StringBuilder strBuilder = new StringBuilder("");
        for (int i = 0; i < results.size(); i++) {
            strBuilder.append(results.get(i) + "\n");
        }
        view.setOutputTxtArea(strBuilder.toString());
    }

    // handles call when Ingredient Btn is clicked
    public void numbOfRecWithMainIngr(String ingr) {
        int results = qry.getNumbOfRecWithMainIngr(ingr);
        StringBuilder strBuilder = new StringBuilder("Recipes containing ");
        strBuilder.append(ingr);
        strBuilder.append(" are ");
        strBuilder.append(results);
        view.setOutputTxtArea(strBuilder.toString());
    }

    // handles call when cat & combinedTime Btn is clicked
    public void recCatCombTime(String cat, int ctf, int cts) {
        List<Recipe> results = qry.getRecCatCombTime(cat, ctf, cts);
        StringBuilder strBuilder = new StringBuilder("");
        for (int i = 0; i < results.size(); i++) {
            strBuilder.append(results.get(i) + "\n");
        }
        view.setOutputTxtArea(strBuilder.toString());
    }

    // handles window closure
    public void close() {
        qry.close();
        System.exit(0);
    }

}
