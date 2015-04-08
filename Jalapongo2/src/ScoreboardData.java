import javafx.beans.property.SimpleStringProperty;

public class ScoreboardData extends GameScreen {
	private final SimpleStringProperty name;
	private final SimpleStringProperty timeElapse;
	private final SimpleStringProperty powerupsUsed;
	
	private ScoreboardData(String uName,String time, String powerups){
		this.name = new SimpleStringProperty(uName);
		this.timeElapse = new SimpleStringProperty(time);
		this.powerupsUsed = new SimpleStringProperty(powerups);
	}
	
	public String getName(){
		return name.get();
	}
	
	public void setName(String uName){
		name.set(uName);
	}
	
	public void setTimeElapse(String time){
		timeElapse.set(time);
	}
	
	
	
}
