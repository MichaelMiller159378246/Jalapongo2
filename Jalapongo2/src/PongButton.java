import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class PongButton extends Button {

		public PongButton(String string){
			Button button = new Button(string); 
				//button.setMinSize(200, 100);
				//button.setPrefSize(200, 100);
				//button.setMaxSize(200, 100);
				//button.setBorder(new Border(new BorderStroke(null,
				//		null,
				//		new CornerRadii(10),
				//		new BorderWidths(5),
				//		null)));
		}

}
