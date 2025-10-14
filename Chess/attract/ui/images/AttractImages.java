package attract.ui.images;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class AttractImages {
    public static Image icoAttract;

    public static final Image bearImage;
    public static final Image pandaImage;

    static {
        icoAttract = new Image(Display.getCurrent(), AttractImages.class.getResourceAsStream("icoAttract.png"));

        bearImage = new Image(Display.getCurrent(), AttractImages.class.getResourceAsStream("bear.png"));
        pandaImage = new Image(Display.getCurrent(), AttractImages.class.getResourceAsStream("panda.png"));
    }
}
