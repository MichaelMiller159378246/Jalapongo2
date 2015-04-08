import javafx.beans.property.SimpleStringProperty;

public class ScoreboardData extends GameScreen {
	private final SimpleStringProperty name;
	private final SimpleStringProperty timeElapsed;
	private final SimpleStringProperty powerupsUsed;
	
	public ScoreboardData(String uName,String time, String powerups){
		this.name = new SimpleStringProperty(uName);
		this.timeElapsed = new SimpleStringProperty(time);
		this.powerupsUsed = new SimpleStringProperty(powerups);
	}
	
	public String getName(){
		return name.get();
	}
	
	public void setName(String uName){
		name.set(uName);
	}
	
	public String getTimeElapsed(){
		return timeElapsed.get();
	}
	
	public void setTimeElapsed(String time){
		timeElapsed.set(time);
	}
	
	public String getPowerupsUsed(){
		return powerupsUsed.get();
	}
	
	public void setPowerupsUsed(String powerups){
		powerupsUsed.set(powerups);
	}
	
	
	
	
	
	
}
