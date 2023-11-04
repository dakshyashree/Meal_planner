import java.io.*;
import java.util.*;
interface Meal
{
    String getName();
}
class BasicMeal implements Meal, Serializable
{
    String name;

    public BasicMeal(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
}
class BreakfastMeal extends BasicMeal
{
    public BreakfastMeal(String name)
    {
        super(name);
    }
}
class LunchMeal extends BasicMeal
{
    public LunchMeal(String name)
    {
        super(name);
    }
}
class DinnerMeal extends BasicMeal
{
    public DinnerMeal(String name)
    {
        super(name);
    }
}
class Snack extends BasicMeal
{
    public Snack(String name)
    {
        super(name);
    }
}
class Day implements Serializable
{
    String name;
    BreakfastMeal breakfast;
    LunchMeal lunch;
    DinnerMeal dinner;
    Snack snack;
    public Day(String name)
    {
        this.name = name;
    }
    public void setBreakfast(BreakfastMeal b)
    {
        breakfast = b;
    }
    public void setLunch(LunchMeal l)
    {
        lunch = l;
    }
    public void setDinner(DinnerMeal d)
    {
        dinner = d;
    }
    public void setSnack(Snack s)
    {
        snack = s;
    }
    public String getName()
    {
        return name;
    }
    public void printDayPlan()
    {
        System.out.println(name + ":");
        System.out.println("Breakfast: " + breakfast.getName());
        System.out.println("Lunch: " + lunch.getName());
        System.out.println("Dinner: " + dinner.getName());
        System.out.println("Snack: " + snack.getName());
        System.out.println();
    }
}
class MealPlanner implements Serializable
{
    List<Day> daysOfWeek;
    String[] breakfastOptions = {"Oatmeal", "Pancakes", "Eggs and Toast", "Upma", "Poha", "Paratha","Aloo Parantha","Sandwich","Aloo Gobhi","Omelete","Vada","Aloo Bhaji"};
    String[] lunchOptions = {"Sandwich", "Salad", "Soup", "Biryani", "Roti with Curry", "Thali","Rajma Chawal","Dal Roti","Dal chawal","Punjabli Dal Tadhka","Fried Rice with Paneer","Khichidi with curd"};
    String[] dinnerOptions = {"Grilled Chicken", "Spaghetti", "Steak", "Sushi", "Pizza", "Burger", "Fried Rice", "Tacos", "Curry", "Dal Makhani", "Butter Chicken", "Paneer Tikka Masala","Aloo MUtter","Malai Kofta","Pasta","Chicken Tacos","Tacos"};
    String[] snackOptions = {"Fruits", "Nuts", "Popcorn", "Yogurt", "Samosa", "Pakoras", "Chaat", "Dosa","Momos","Tacos","Milkshake","cupcake","Garlic Bread","Tea","Coffee"};
    public MealPlanner()
    {
        daysOfWeek = new ArrayList<>();
        for (String dayName : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"})
        {
            daysOfWeek.add(new Day(dayName));
        }
    }
    public void generateMealPlan(double bmi)
    {
        Random random = new Random();
        for (Day day : daysOfWeek)
        {
            BreakfastMeal breakfast = new BreakfastMeal(breakfastOptions[random.nextInt(breakfastOptions.length)]);
            LunchMeal lunch = new LunchMeal(lunchOptions[random.nextInt(lunchOptions.length)]);
            DinnerMeal dinner = new DinnerMeal(dinnerOptions[random.nextInt(dinnerOptions.length)]);
            Snack snack = new Snack(snackOptions[random.nextInt(snackOptions.length)]);
            if (bmi < 18.5) {
                breakfast = new BreakfastMeal("High Protein " + breakfast.getName());
                lunch = new LunchMeal("High Protein " + lunch.getName());
                dinner = new DinnerMeal("High Protein " + dinner.getName());
            } else if (bmi >= 25) {
                breakfast = new BreakfastMeal("Low Calorie " + breakfast.getName());
                lunch = new LunchMeal("Low Calorie " + lunch.getName());
                dinner = new DinnerMeal("Low Calorie " + dinner.getName());
            }
            day.setBreakfast(breakfast);
            day.setLunch(lunch);
            day.setDinner(dinner);
            day.setSnack(snack);
            day.printDayPlan();
        }
    }
    public void saveToFile(String fileName)
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(this);
            System.out.println("Meal plan saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error: Unable to save meal plan.");
            e.printStackTrace();
        }
    }
    public static MealPlanner loadFromFile(String fileName)
    {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName)))
        {
            return (MealPlanner) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            System.err.println("Error: Unable to load meal plan.");
            e.printStackTrace();
            return null;
        }
    }
}
public class Meal_planner
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your weight (in kg): ");
        double weight = scanner.nextDouble();
        System.out.print("Enter your height (in meters): ");
        double height = scanner.nextDouble();
        double bmi = calculateBMI(weight, height);
        System.out.println("Meal Plan for the next two weeks:");
        MealPlanner planner = new MealPlanner();
        planner.generateMealPlan(bmi);
        planner.saveToFile("meal_plan.ser");
        MealPlanner loadedPlanner = MealPlanner.loadFromFile("meal_plan.ser");
        if (loadedPlanner != null) {
            System.out.println("\nLoaded Meal Plan:");
            loadedPlanner.generateMealPlan(bmi);
        }
    }
    public static double calculateBMI(double weight, double height)
    {
        return weight / (height * height);
    }
}
