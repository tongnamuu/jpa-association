package persistence.sql.vo.association;

public class OneToManyAssociation {
    private final Class<?> entityClass;
    private final Class<?> type;

    public OneToManyAssociation(Class<?> entityClass, Class<?> type) {
        this.entityClass = entityClass;
        this.type = type;
    }
}
