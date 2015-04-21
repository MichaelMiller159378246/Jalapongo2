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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
 
public class GameData {
 
	private String player1Time,
				   player2Time,
				   player3Time,
				   player4Time, 
				   player1Name,
				   player2Name,
				   player3Name,
				   player4Name;
	
	private String firstPlaceTime,
				   firstPlaceName,
				   secondPlaceTime,
				   secondPlaceName,
				   thirdPlaceTime,
				   thirdPlaceName,
				   fourthPlaceTime,
				   fourthPlaceName;
	
    private TableView<PlayerData> table = new TableView<PlayerData>();
    private final ObservableList<PlayerData> data;
    
    public GameData(GameScreen gameScreen, Stage primaryStage) {
    	player1Time = gameScreen.getP1Time();
    	player1Name = gameScreen.getP1Name();
    	player2Time = gameScreen.getP2Time();
    	player2Name = gameScreen.getP2Name();
    	player3Time = gameScreen.getP3Time();
    	player3Name = gameScreen.getP3Name();
    	player4Time = gameScreen.getP4Time();
    	player4Name = gameScreen.getP4Name();
    	
    	//determine first place data
    	int winner = gameScreen.checkWin();
    	
    	if(winner == 4){
    		firstPlaceName = player4Name;
    		player4Time = "Winner";
    	}
    	else if(winner == 3){
    		firstPlaceName = player3Name;
    		player3Time = "Winner";
    	}
    	else if(winner == 2){
    		firstPlaceName = player2Name;
    		player2Time = "Winner";
    	}
    	else if (winner == 1){
    		firstPlaceName = player1Name;
    		player1Time = "Winner";
    	}
    	
    	data = FXCollections.observableArrayList(
    	                new PlayerData(player1Name, player1Time, "We can add a stat here"),
    	                new PlayerData(player2Name, player2Time, "All we have to do is"),
    	                new PlayerData(player3Name, player3Time, "Create another stat"),
    	                new PlayerData(player4Name, player4Time, "And then make a getter")
    	            );
    	start(primaryStage);
    }
 
    //@Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Scoreboard");
        stage.setWidth(450);
        stage.setHeight(500);
 
        final Label label = new Label("Scoreboard");
        label.setFont(new Font("Arial", 20));
        label.setTextAlignment(TextAlignment.CENTER);
        
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
