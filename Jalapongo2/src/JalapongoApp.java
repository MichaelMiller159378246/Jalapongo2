import javafx.application.Application;
import javafx.stage.Stage;

//JalapongoApp class is the main class to start the program
public class JalapongoApp extends Application{

	public static void main(String[] args){
		JalapongoApp.launch(args); // Calls the start method
	}

	@Override
	public void start(Stage arg0) throws Exception {
		new GameGUI(arg0); // Runs the GUI
	}
}