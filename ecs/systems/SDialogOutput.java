import java.util.ArrayList;
import java.util.List;

public class SDialogOutput {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        List<Entity> toRemove = new ArrayList<>();

        ecs.forEachIfContains((e) -> {
            System.out.println(((COutDialog) ecs.getComponent(e, COutDialog.class)).text);
            toRemove.add(e);
        },
                COutDialog.class);

        toRemove.forEach(e -> { ecs.remove(e); });
   }
}
