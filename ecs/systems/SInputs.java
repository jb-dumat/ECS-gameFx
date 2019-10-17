import java.util.Scanner;

public class SInputs {
    public static void compute() {
        EcsManager ecs = EcsManager.getInstance();
        Scanner input = new Scanner(System.in);

        MultiMap<Entity, IComponent> inDialog = ecs.getComponentsPool().get("CInDialog");

        for (Entity dialogEntity : inDialog.keySet()) {
            System.out.print("> ");
            String inputText = input.nextLine();

            if (inputText == null) {
                ecs.attachComponent(new Entity(), new CCommandInput("Unknown command", null));
                continue;
            }

            ((CInDialog)inDialog.get(dialogEntity).toArray()[0]).text = inputText;
        }
    }
}
