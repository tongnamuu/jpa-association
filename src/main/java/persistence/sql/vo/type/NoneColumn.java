package persistence.sql.vo.type;

public class NoneColumn implements DatabaseType {
    private static final NoneColumn instance = new NoneColumn();

    private NoneColumn() {

    }

    public static NoneColumn getInstance() {
        return instance;
    }
}
