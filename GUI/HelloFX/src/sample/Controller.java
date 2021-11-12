package sample;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import main.*;
import main.enums.*;

public class Controller implements Initializable {
    public String[] inputs = {"", ""};      //algorithm, state
    private String[] algorithmsClass = {"BFS", "DFS", "A* Manhattan", "A* Euclidean"};
    private String[] analysis = {"Taken Time: ", "Total Moves: ", "Depth: "};
    private String[] rowColumn = {"01", "02", "03", "11", "12", "13", "21", "22", "23"};
    public Alert a = new Alert(Alert.AlertType.WARNING);
    private Direction[] moves;
    public int i = 0;
    private PuzzleSolver puzzleSolver = new PuzzleSolver();
    private Solution solution;

    @FXML
    private ChoiceBox<String> algorithms;
    @FXML
    private Button solve;
    @FXML
    private TextField state;
    @FXML
    private ListView<String> viewList;
    @FXML
    private GridPane grid;
    @FXML
    private StackPane stack0;
    @FXML
    private StackPane stack1;
    @FXML
    private StackPane stack2;
    @FXML
    private StackPane stack3;
    @FXML
    private StackPane stack4;
    @FXML
    private StackPane stack5;
    @FXML
    private StackPane stack6;
    @FXML
    private StackPane stack7;
    @FXML
    private StackPane stack8;
    @FXML
    private Button nextBtn;
    @FXML
    private Label algoId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithms.getItems().addAll(algorithmsClass);
        algorithms.setOnAction(this::getAlgorithm);
        state.setOnKeyPressed(this::change);
        solve.setOnAction(this::solver);
        nextBtn.setOnAction(this::nextMove);
    }

    public void nextMove(ActionEvent actionEvent) {
        int size = moves.length;
        if (i == size - 1 || size == 0) {
            nextBtn.setDisable(true);
            solve.setDisable(false);
        }
        if(i < size){
            moving(moves, i);
        }
        i++;
    }

    public void change(KeyEvent keyEvent) {
        state.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    state.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (state.getText().length() > 9) {
                    String s = state.getText().substring(0, 9);
                    state.setText(s);
                }
            }
        });

    }
    public void getAlgorithm(ActionEvent event) {
        inputs[0] = algorithms.getValue();
        algoId.setText(inputs[0]);
    }
    public void solver(ActionEvent event) {
        i = 0;
        String tokenState = state.getText();
        if(inputs[0] == "" || !isValidState(tokenState)){
            a.setContentText("Make sure to fill all the fields\nfor the initial state, enter 9 distinct integers from 0 to 8 with no spacing\nex: 013546728");
            a.show();
        } else{
            viewList.getItems().removeAll(analysis);
            analysis = new String[]{"Taken Time: ", "Total Moves: ", "Depth: "};
            inputs[1] = tokenState;
            setGrid(inputs[1]);
            puzzleSolver.setInitialState(inputs[1]);
            switch (inputs[0]) {
                case "BFS":
                    solution = puzzleSolver.runAlgorithm(AlgorithmName.BFS);
                    break;
                case "DFS":
                    solution = puzzleSolver.runAlgorithm(AlgorithmName.DFS);
                    break;
                case "A* Manhattan":
                    solution = puzzleSolver.runAlgorithm(AlgorithmName.AStarManhattan);
                    break;
                case "A* Euclidean":
                    solution = puzzleSolver.runAlgorithm(AlgorithmName.AStarEuclidean);
                    break;
            }
            moves = new Direction[solution.getMoves().size()];
            for (int i = 0; i < moves.length; i++) {
                moves[i] = solution.getMoves().get(i).getDirection();
            }
            analysis[0] = analysis[0] + solution.getRunningTime() + " ms";
            analysis[1] = analysis[1] + solution.getPathCost();
            analysis[2] = analysis[2] + solution.getSearchDepth();
            System.out.println("\n\nInitial state: " + inputs[1] + "\n-----------------------------------------");
            System.out.println("Used algorithm: " + inputs[0] + "\n-----------------------------------------");
            System.out.println("Moves:");
            for (Move move : solution.getMoves()) {
                System.out.println("Next state: " + move.getState() + "\tdirection: " + move.getDirection());
            }
            System.out.println("-----------------------------------------");
            System.out.println("Expanded nodes:");
            for (int j = 0; j < solution.getExpandedNodes().size(); j++) {
                if (j == solution.getExpandedNodes().size() - 1){
                    System.out.print(solution.getExpandedNodes().get(j));
                }else {
                    System.out.print(solution.getExpandedNodes().get(j) + " ==> ");
                }
                if((j + 1) % 5 == 0)
                    System.out.println();
            }
            System.out.println("\n-----------------------------------------");
            System.out.println("Number of expanded nodes: " + solution.getExpandedNodes().size());
            System.out.println("Path Cost: " + solution.getPathCost());
            System.out.println("Search Depth: " + solution.getSearchDepth());
            System.out.println("Running Time: " + solution.getRunningTime() + " ms");

            viewList.getItems().addAll(analysis);
            nextBtn.setDisable(false);
            solve.setDisable(true);

        }
    }
    private boolean isValidState (String str) {
        int size = str.length();
        if (size < 9 || str.contains("9"))
            return false;
        for (int i = 0; i < size - 1 ; i++) {
            for (int j = i+1; j < size; j++) {
                if (str.charAt(i) == str.charAt(j))
                    return false;
            }
        }
        return true;
    }
    public void fade (Node stack) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(stack);
        fade.setDuration(Duration.millis(1000));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    public void moving(Direction[] moves, int i){
        Node stk;
        int col = GridPane.getColumnIndex(stack0);
        int row = GridPane.getRowIndex(stack0);
        switch (moves[i]) {
            case UP:
                row--;
                break;
            case DOWN:
                row++;
                break;
            case RIGHT:
                col++;
                break;
            case LEFT:
                col--;
                break;
            default:
                break;
        }
        int finalRow = row;
        int finalCol = col;
        stk = grid.getChildren().stream().filter(node -> GridPane.getRowIndex(node) == finalRow)
                .filter(node -> GridPane.getColumnIndex(node) == finalCol).findFirst().get();
        swap(stack0, stk);
    }
    private void setGrid (String state){
        StackPane stackPane0 = stack0, stackPane1 = stack1, stackPane2 = stack2, stackPane3 = stack3, stackPane4 = stack4, stackPane5 = stack5, stackPane6 = stack6, stackPane7 = stack7, stackPane8 = stack8;
        grid.getChildren().removeAll(stackPane0, stackPane1, stackPane2, stackPane3, stackPane4, stackPane5, stackPane6, stackPane7, stackPane8);
        for (int i = 0; i < state.length(); i++) {
            StackPane stk;
            switch (state.charAt(i)) {
                case '1':
                    stk = stack1;
                    break;
                case '2':
                    stk = stack2;
                    break;
                case '3':
                    stk = stack3;
                    break;
                case '4':
                    stk = stack4;
                    break;
                case '5':
                    stk = stack5;
                    break;
                case '6':
                    stk = stack6;
                    break;
                case '7':
                    stk = stack7;
                    break;
                case '8':
                    stk = stack8;
                    break;
                default:
                    stk = stack0;
                    break;
            }
            fade(stk);
            grid.add(stk, (int) rowColumn[i].charAt(1), (int) rowColumn[i].charAt(0));
        }
    }
    private void swap (Node stk1, Node stk2){
        Node stackPane0 = stk1, stackPane1 = stk2;
        int r1 = GridPane.getRowIndex(stk1);
        int r2 = GridPane.getRowIndex(stk2);
        int c1 = GridPane.getColumnIndex(stk1);
        int c2 = GridPane.getColumnIndex(stk2);
        grid.getChildren().removeAll(stackPane0, stackPane1);
        grid.add(stk1, c2, r2);
        grid.add(stk2,c1, r1);
        fade(stk1);
        fade(stk2);
    }
}
