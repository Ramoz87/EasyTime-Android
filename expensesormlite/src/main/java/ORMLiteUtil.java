import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.core.Model;

import java.util.List;

/**
 * Created by Oleg Tarashkevich on 20/03/2018.
 */

public class ORMLiteUtil {

    public static <M extends Model, ID> M getModelById(Dao<M, ID> dao, ID id) throws Throwable {
        return dao.queryForId(id);
    }

    public static <M extends Model, ID, P extends QueryBuilder<M, ID>> M getModel(Dao<M, ID> dao, P parameter) throws Throwable {
        return dao.query(parameter.prepare()).get(0);
    }

    public static <M extends Model, ID, P extends QueryBuilder<M, ID>> List<M> getModels(P parameter) throws Throwable {
        return parameter.query();
    }

    public static <M extends Model, ID> void saveModel(Dao<M, ID> dao, M model) throws Throwable {
        dao.createOrUpdate(model);
    }

    public static <M extends Model, ID> void deleteModel(Dao<M, ID> dao,M model) throws Throwable {
        dao.delete(model);
    }

    public static <M extends Model, ID> void deleteModels(Dao<M, ID> dao, List<M> models) throws Throwable {
        dao.delete(models);
    }

}
