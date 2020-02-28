package DB;

import Contract.Contract;

public class DBRequestFormer {
    private static final String INSERT_REQUEST = "INSERT IGNORE INTO " + Contract.TABLE_NAME + "("
            + Contract.TableColoumns.COLUMN_PRODUCT_ID + ", "
            + Contract.TableColoumns.COLUMN_TITLE + ", "
            + Contract.TableColoumns.COLUMN_COST + ") VALUES (?, ?, ?)";

    private static final String UPDATE_REQUEST = "UPDATE " + Contract.TABLE_NAME + " SET "
            + Contract.TableColoumns.COLUMN_COST + " = ? WHERE "
            + Contract.TableColoumns.COLUMN_TITLE + " = ?";

    private static final String SELECT_REQUEST_PRICE_IN_RANGE = "SELECT * FROM " + Contract.TABLE_NAME+ " WHERE "
            + Contract.TableColoumns.COLUMN_COST + " >= ? AND "
            + Contract.TableColoumns.COLUMN_COST + " <= ?";

    private static final String SELECT_REQUEST_PRICE_BY_NAME = "SELECT " + Contract.TableColoumns.COLUMN_COST
            + " FROM " + Contract.TABLE_NAME + " WHERE "
            + Contract.TableColoumns.COLUMN_TITLE + " = ?";

    private static final String SELECT_FOR_CHECK_ADD = "SELECT * FROM " + Contract.TABLE_NAME + " WHERE "
            + Contract.TableColoumns.COLUMN_PRODUCT_ID + " = ? OR "
            + Contract.TableColoumns.COLUMN_TITLE + " = ?";

    private static final String SELECT_ALL = "SELECT * FROM " + Contract.TABLE_NAME;

    private static final String DELETE_PRODUCT_REQUEST = "DELETE FROM " + Contract.TABLE_NAME + " WHERE "
            + Contract.TableColoumns.COLUMN_TITLE + " = ?";

    private static final String CREATE_TABLE = "CREATE TABLE " + Contract.TABLE_NAME + " ("
            + Contract.TableColoumns.COLUMN_ID + " INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
            + Contract.TableColoumns.COLUMN_PRODUCT_ID + " INT NOT NULL UNIQUE, "
            + Contract.TableColoumns.COLUMN_TITLE + " VARCHAR(30) NOT NULL UNIQUE, "
            + Contract.TableColoumns.COLUMN_COST + " INT NOT NULL)";

}
