import java.util.Scanner;

public class SPlayerInput {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        Scanner input = new Scanner(System.in);

        ecs.forEachWith((e) -> {
            System.out.print("> ");
            CInDialog inDialog = ((CInDialog) ecs.getComponent(e, CInDialog.class));
            inDialog.input = input.nextLine();
            },
                CInDialog.class
        );
    }
}
