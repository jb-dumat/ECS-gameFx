import java.util.Arrays;
import java.util.Vector;

/**
 * The SInputParser is a system which
 * parses all inputs from players.
 */
public class SInputParser {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((e) -> {
            CInPlayer input = ecs.getComponent(e, CInPlayer.class);

            if (input == null)
                return;

            String[] words = input.input.split("\\s+");
            Vector<String> wordsVector = new Vector<String>(Arrays.asList(input.input.split("\\s+")));

            if (wordsVector.size() > 0) {
                wordsVector.remove(0);
            }

            ecs.put(e, new CCommandInput(words[0], wordsVector));
            input.input = "";
           },
                CInPlayer.class);
    }
}
