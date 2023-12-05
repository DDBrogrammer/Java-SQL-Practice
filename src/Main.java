import constant.Constant;
import service.ScannerService;
import ui.UIManager;

public class Main {
    public static void main(String[] args) {
        UIManager uiManager = new UIManager();
        Integer useChoise;
        boolean stopApp = false;
        APPLICATION_LOOP:
        do {
            uiManager.showAllEmployee();
            System.out.println("Application");
            System.out.println("Enter 1: To insert ");
            System.out.println("Enter 2: To update");
            System.out.println("Enter 3: To delete ");
            System.out.println("Enter 4: To search ");
            System.out.println("Enter 5: To exit");
            useChoise = ScannerService.getIntInput("");
            switch (useChoise) {
                case Constant.USER_MAIN_INPUT.INSERT:
                    int type = Constant.DEFAULT_INPUT.INTEGER;
                    while (type != Constant.EMPLOYEE_TYPE.EXPERIENCE
                            && type != Constant.EMPLOYEE_TYPE.INTERN
                            && type != Constant.EMPLOYEE_TYPE.FRESHER) {
                        type = ScannerService.getIntInput("Enter type [1] EXPERIENCE / [2] FRESHER / [3] INTERN:");
                    }
                    uiManager.insert(type);
                    break;
                case Constant.USER_MAIN_INPUT.EDIT:
                    uiManager.update();
                    break;
                case Constant.USER_MAIN_INPUT.DELETE:
                    uiManager.delete();
                    break;
                case Constant.USER_MAIN_INPUT.SEARCH:
                    uiManager.searchEmployee();
                    break;
                case Constant.USER_MAIN_INPUT.EXIT:
                    break APPLICATION_LOOP;
                default:
                    System.out.println("input must in range 1->5");
                    continue;
            }
            boolean stopAskYesNo = false;
            while (!stopAskYesNo) {
                String answer = ScannerService.getStringInput("Do you want continue ? [y/n]: ");
                if (answer.equalsIgnoreCase(Constant.USER_ANSWER.NO) || answer.equalsIgnoreCase(
                        Constant.USER_ANSWER.YES)) {
                    stopAskYesNo = true;
                    if (answer.equalsIgnoreCase(Constant.USER_ANSWER.NO)) {
                        stopApp = true;
                    }
                }
            }

        }
        while (!stopApp);
        System.out.println("STOP !!!");
    }
}
