import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class GameData {
 
	private String player1Time,
				   player2Time,
				   player3Time,
				   player4Time;
	
    private TableView<PlayerData> table = new TableView<PlayerData>();
    private final ObservableList<PlayerData> data;
    
    public GameData(GameScreen gameScreen, Stage primaryStage) {
    	player1Time = gameScreen.getP1Time();
    	player2Time = gameScreen.getP2Time();
    	player3Time = gameScreen.getP3Time();
    	player4Time = gameScreen.getP4Time();

    	data = FXCollections.observableArrayList(
    	                new PlayerData("Player 1", player1Time, "We can add a stat here"),
    	                new PlayerData("Player 2", player2Time, "All we have to do is"),
    	                new PlayerData("Player 3", player3Time, "Create another stat"),
    	                new PlayerData("Player 4", player4Time, "And then make a getter")
    	            );
    	start(primaryStage);
    }
 
    //@Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);
 
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn playerCol = new TableColumn("Player");
        playerCol.setMinWidth(100);
        playerCol.setCellValueFactory(
                new PropertyValueFactory<PlayerData, String>("name"));
 
        TableColumn timeOutCol = new TableColumn("Time Out");
        timeOutCol.setMinWidth(100);
        timeOutCol.setCellValueFactory(
                new PropertyValueFactory<PlayerData, String>("timeOut"));
 
        TableColumn somethingElseCol = new TableColumn("Something Else");
        somethingElseCol.setMinWidth(200);
        somethingElseCol.setCellValueFactory(
                new PropertyValueFactory<PlayerData, String>("somethingElse"));
 
        table.setItems(data);
        table.getColumns().addAll(playerCol, timeOutCol, somethingElseCol);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
    public static class PlayerData {
 
        private final SimpleStringProperty name;
        private final SimpleStringProperty timeOut;
        private final SimpleStringProperty somethingElse;
 
        private PlayerData(String name, String timeOut, String somethingElse) {
        	this.name = new SimpleStringProperty(name);
        	this.timeOut = new SimpleStringProperty(timeOut);
        	this.somethingElse = new SimpleStringProperty(somethingElse);
        }
        
        public String getName() {
        	return name.get();
        }
        
        public String getTimeOut() {
        	return timeOut.get();
        }
        
        public String getSomethingElse() {
        	return somethingElse.get();
        }
    }

} 