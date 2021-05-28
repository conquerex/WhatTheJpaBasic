package jpql;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MyH3Dialect extends H2Dialect {
    public MyH3Dialect() {
        registerFunction("group_concat",
                new StandardSQLFunction("group_concat",
                        StandardBasicTypes.STRING)
        );
    }
}
