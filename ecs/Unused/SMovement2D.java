import java.util.Iterator;
import java.util.Map;

public class SMovement2D implements ISystemRunnable {
    public void run() {
        EcsManager ecs = EcsManager.getInstance();

        Map<Entity, IComponent> playerInputs = ecs.getComponentsPool().get("CPlayerInputs");
        Map<Entity, IComponent> position2D = ecs.getComponentsPool().get("CPosition2D");
        Map<Entity, IComponent> velocity = ecs.getComponentsPool().get("CVelocity");

        Iterator<Map.Entry<Entity, IComponent>> iter = playerInputs.entrySet().iterator();
/*        while (iter.hasNext()) {
            Map.Entry<Entity, IComponent> entry = iter.next();

            Entity entityTmp = entry.getKey();
            CPlayerInputsCLI comTmp = (CPlayerInputsCLI)entry.getValue();

            System.out.println(entityTmp.getEntityID());
            System.out.println(String.valueOf(comTmp.moveX));
            System.out.println(((CPosition2D)position2D.get(entityTmp)).x);
            System.out.println(((CVelocity)velocity.get(entityTmp)).velocity);

            System.out.println();

            CPosition2D posTmp = (CPosition2D)position2D.get(entityTmp);
            CVelocity velTmp = (CVelocity)velocity.get(entityTmp);
            if (comTmp.moveX) {
                // Check if can go

                posTmp.x += velTmp.velocity;
            } else if (comTmp.moveY) {
                // Check if can go
                posTmp.y += velTmp.velocity;
            }

            System.out.println(entityTmp.getEntityID());
            System.out.println(String.valueOf(comTmp.moveX));
            System.out.println(((CPosition2D)position2D.get(entityTmp)).x);
            System.out.println(((CVelocity)velocity.get(entityTmp)).velocity);

        }
*/    }

}
