package systems;

import com.mycompany.mavenproject1.FXMLController;
import com.mycompany.mavenproject1.MainApp;
import ecs.*;
import components.*;
import javafx.application.Platform;

/**
 * The SDialogOutput is a system which provides
 * a way to write an output dialog upon the output
 * stream.
 * If you wish to change the stream, you can make a new
 * stream component and a new system to handle it (A socket
 * communication dialog for example)
 */
public class SGuiDialogOutput {
    /**
     * Fetch all output dialog entity and write them
     * on the output stream, then removes it.
     */
    public static void update() {
        FXMLController controller = MainApp.controller;
        EcsManager ecs = EcsManager.getInstance();
        
        ecs.forEachIfContains((e) -> {
            COutDialog dialog = ecs.getComponent(e, COutDialog.class);

            Platform.runLater(() -> {
                if (dialog.text != null)
                    controller.consoleBox.setText(dialog.text);    
            });
        },
                COutDialog.class);
   }
}
