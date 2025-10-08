package rabbit.ui.images;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class WolfRabbitImages {
    public static Image icoRabbit;
    
    public static Image rabbitImage;
    public static Image wolfImage;
    
    public static Image catImage;
    public static Image mouseImage;

	public static Image snakeImage;
	public static Image frogImage;

    static {
        Device display = Display.getDefault();
        
        icoRabbit = new Image(display, WolfRabbitImages.class.getResourceAsStream("icoRabbit.png"));

        wolfImage = new Image(display, WolfRabbitImages.class.getResourceAsStream("wolf.png"));
        rabbitImage = new Image(display, WolfRabbitImages.class.getResourceAsStream("rabbit.png"));
        
        catImage = new Image(display, WolfRabbitImages.class.getResourceAsStream("cat.png"));
        mouseImage = new Image(display, WolfRabbitImages.class.getResourceAsStream("mouse.png"));
        
        snakeImage = new Image(display, WolfRabbitImages.class.getResourceAsStream("snake.png"));
        frogImage = new Image(display, WolfRabbitImages.class.getResourceAsStream("frog.png"));
     }
}
