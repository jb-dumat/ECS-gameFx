import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class SCommandMaker {
    public static void compute() {
        EcsManager ecs = EcsManager.getInstance();

        MultiMap<Entity, IComponent> inputs = ecs.getComponentsPool().get("CInDialog");

        for (Entity inEntity : inputs.keySet()) {
            String inputText = ((CInDialog)inputs.get(inEntity).toArray()[0]).text;
            Entity target = ((CInDialog)inputs.get(inEntity).toArray()[0]).target;

            String[] words = inputText.split("\\s+");
            Vector<String> wordsVector = new Vector<String>(Arrays.asList(words));
            if (wordsVector.size() < 1) {
                continue;
            }

            wordsVector.remove(0);
            ecs.attachComponent(target, new CCommandInput(words[0], wordsVector));
        }
    }
}
