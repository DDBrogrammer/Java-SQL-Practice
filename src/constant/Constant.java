package constant;

public class Constant {
    public static class EMPLOYEE_TYPE {
        public static final int EXPERIENCE = 0;

        public static final int FRESHER = 1;

        public static final int INTERN = 2;
    }

    public static class USER_ANSWER {
        public static final String YES = "y";

        public static final String NO = "n";
    }

    public static class USER_MAIN_INPUT {
        public static final int INSERT = 1;
        public static final int EDIT = 2;
        public static final int DELETE = 3;
        public static final int SEARCH = 4;
        public static final int EXIT = 5;
    }

    public static class USER_SEARCH_OPTION {
        public static final int ALL_EXPERIENCE = 1;
        public static final int ALL_FRESHER = 2;
        public static final int ALL_INTERN = 3;
        public static final int BY_ID = 4;
        public static final int BY_NAME = 5;
    }

    public static class DEFAULT_INPUT {
        public static final String STRING = "";
        public static final int INTEGER = -1;

    }
}
