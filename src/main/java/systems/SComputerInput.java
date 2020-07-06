package systems;

import ai.SBrainlessDecisionMaker;
import ecs.*;
import components.*;

/**
 * This system iterates over all entities which
 * contains an InputComponent and open create a
 * command from this input.
 */
public class SComputerInput {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((e) -> {

                    CComputerLevel computer = ecs.getComponent(e, CComputerLevel.class);

                    switch (computer.aiLevel) {
                        case BRAINLESS: SBrainlessDecisionMaker.makeDecision(); break;
                        // Add AI level here
                        default: break;
                    }

                },
                CComputerLevel.class
        );
    }
}
