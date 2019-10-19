import java.util.ArrayList;
import java.util.List;

public class SDialogOutput {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        List<Entity> toRemove = new ArrayList<>();

        ecs.forEachWith((e) -> {
            System.out.println(((COutDialog) ecs.getComponent(e, COutDialog.class)).text);
            toRemove.add(e);
        },
                COutDialog.class);

        for (Entity e : toRemove) {
            ecs.remove(e);
        }
   }
}
