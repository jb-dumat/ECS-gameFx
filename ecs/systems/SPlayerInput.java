import java.util.NoSuchElementException;
import java.util.Scanner;

public class SPlayerInput {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        Scanner input = new Scanner(System.in);

        ecs.forEachIfContains((e) -> {
            System.out.print("> ");
            CInDialog inDialog = ((CInDialog) ecs.getComponent(e, CInDialog.class));
            inDialog.input = "";

            try {
                inDialog.input = input.nextLine();
            } catch (NoSuchElementException ex) {
                ex.printStackTrace();
                inDialog.input = "";
                input.reset();
            }
            },
                CInDialog.class
        );
    }
}
