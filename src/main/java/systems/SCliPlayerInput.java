package systems;

import core.*;
import ecs.*;
import components.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This system iterates over all entities which
 * contains an InputComponent and open create a
 * command from this input.
 */
public class SCliPlayerInput {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        Scanner input = new Scanner(System.in);

        ecs.forEachIfContains((e) -> {
            System.out.print("> ");
            CInPlayer inDialog = (ecs.getComponent(e, CInPlayer.class));
            inDialog.input = "";

            try {
                if (input.hasNext())
                    inDialog.input = input.nextLine();

            } catch (NoSuchElementException ex) {
                GameCore.getInstance().getLogger().warning(ex.getMessage());
                inDialog.input = "";
                input.reset();
            }
            },
                CInPlayer.class
        );
    }
}
