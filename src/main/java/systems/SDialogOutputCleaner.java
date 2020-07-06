package systems;

import ecs.*;
import components.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The SDialogOutput is a system which provides
 * a way to write an output dialog upon the output
 * stream.
 * If you wish to change the stream, you can make a new
 * stream component and a new system to handle it (A socket
 * communication dialog for example)
 */
public class SDialogOutputCleaner {
    /**
     * Fetch all output dialog entity and write them
     * on the output stream, then removes it.
     */
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        List<Entity> toRemove = new ArrayList<>();

        ecs.forEachIfContains((e) -> {
            toRemove.add(e);
        },
                COutDialog.class);

        toRemove.forEach(e -> { ecs.remove(e); });
   }
}
