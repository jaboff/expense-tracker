package sample;
import java.net.URL;
import java.net.URL.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import java.time.LocalDate;
import java.util.ResourceBundle;
import java.net.URL;

public class Controller implements Initializable
{
    ExpenseList expenseList = new ExpenseList();

    @FXML TableView<Expense> view_tableView;
    @FXML TableColumn<Expense, String> view_nameColumn;
    @FXML TableColumn<Expense, Double> view_amountColumn;
    @FXML TableColumn<Expense, String> view_categoryColumn;
    @FXML TableColumn<Expense, String> view_dateColumn;
    @FXML TableColumn<Expense, String> view_noteColumn;

    @FXML ComboBox<String> view_filterCombo;
    @FXML ComboBox<String> view_dateFilterPane;


    // Graphics pane content
    @FXML   private BarChart<?,?> expenseChart;
    @FXML   private CategoryAxis x;
    @FXML   private NumberAxis y;
    @FXML   private Text total;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        //* Set up options for controls
        ObservableList<String> filterOptions = FXCollections.observableArrayList();
        filterOptions.add("No filter");
        filterOptions.add("Name");
        filterOptions.add("Category");
        filterOptions.add("Amount");
        filterOptions.add("Date");
        filterOptions.add("Recurring");
         //*/
        view_filterCombo = new ComboBox<>(filterOptions);
        //"No filter", "Name", "Category", "Amount", "Date", "Recurring");


        // Load expenseList from save data

        updateTable();
    }

    @FXML
    public void updateTable()
    {
        /* Create columns for each expense entry
        TableColumn<Expense, String> nameCol = new TableColumn<Expense, String>("Name");
        TableColumn<Expense, Double> amountCol = new TableColumn<Expense, Double>("Amount");
        TableColumn<Expense, String> categoryCol = new TableColumn<Expense, String>("Category");
        TableColumn<Expense, String> dateCol = new TableColumn<Expense, String>("Date");
        //*/

        // Set the factory values for each entry
        // These will ensure that the each field in an expense will map to the correct column
        view_nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        view_amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        view_categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        view_dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        view_noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Display the column on the table
        //view_tableView.getColumns().addAll(nameCol, amountCol, categoryCol, dateCol);


        // Create a sample expense list and populate it with data
        //* Then add that sample data to the table for testing
        ExpenseList exList = new ExpenseList();
        for (int i = 0; i < 10; i++)
        {
            Expense randExpense = new Expense("Item"+i, i, "Grocery", new Date(), "A note");
            exList.addExpense(randExpense);
        }
        //*/

        view_tableView.setItems(ExpenseList.getList());
    }


    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("Hello");
    }

    @FXML
    private void changeFilterOptions(ActionEvent event)
    {
        System.out.println("Hello");
    }

    @FXML public void initialize(){
        setUpChartByCategory();
    }

    private void setUpChartByCategory(){
        XYChart.Series set1 = new XYChart.Series();
        Expense e1 = new Expense("Apple", 1.99, "Food", new Date(), "Golden Delicious");
        Expense e2 = new Expense("Banana", 2.99, "Food", new Date(), "Fruit Salad");
        Expense e3 = new Expense("Watermelon", 4.99, "Food", new Date(), "Yummy Yummy");
        Expense e4 = new Expense("Watermelon", 4.99, "Inorganic", new Date(), "Yummy Yummy");
        expenseList.addExpense(e1);
        expenseList.addExpense(e2);
        expenseList.addExpense(e3);
        expenseList.addExpense(e4);
        ArrayList<String> categoriesThatHaveAlreadyBeenComputed = new ArrayList<>();
        double sumOfSums = 0;
        for(int o = 0; o < expenseList.getSize(); o++)
        {
            if (!(categoriesThatHaveAlreadyBeenComputed.contains(expenseList.getExpense(o).getCategory()))){
                expenseList.filterByCategory(expenseList.getExpense(o).getCategory());
                double sum = 0;
                for (int m = 0; m < expenseList.getFilteredList().size(); m++){
                    sum += expenseList.getFilteredList().get(m).getAmount();
                }
                sumOfSums += sum;
                set1.getData().add(new XYChart.Data(expenseList.getExpense(o).getCategory(), sum));
                categoriesThatHaveAlreadyBeenComputed.add(expenseList.getExpense(o).getCategory());
            }

        }
        total.setText("$" + sumOfSums);
        expenseChart.getData().addAll(set1);
    }
}
