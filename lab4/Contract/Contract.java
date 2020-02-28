package Contract;

public class Contract {
    public static final String USER_NAME = "001";
    public static final String USER_PASSWORD = "QWERTY";
    public static final String DRIVER ="org.apache.derby.jdbc.EmbeddedDriver";
    public static final String TABLE_NAME = "Products";

    public static abstract class TableColoumns{

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PRODUCT_ID = "prodid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_COST = "cost";
        public static final int COLUMN_ID_INDEX = 0;
        public static final int COLUMN_PRODUCT_ID_INDEX = 1;
        public static final int COLUMN_TITLE_INDEX = 2;
        public static final int COLUMN_COST_INDEX = 3;
    }
}

