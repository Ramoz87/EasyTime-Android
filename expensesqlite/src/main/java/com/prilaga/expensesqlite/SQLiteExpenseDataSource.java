//package com.prilaga.expensesqlite;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.paralect.core.DataSource;
//import com.paralect.expense.BaseExpense;
//
//import java.sql.SQLException;
//import java.util.List;
//
///**
// * Created by Oleg Tarashkevich on 06/03/2018.
// */
//
//public class SQLiteExpenseDataSource<EXPENSE extends BaseExpense> extends DataSource<EXPENSE> {
//
//    private Context context;
//    private SQLiteDatabase database;
//
//
//    public SQLiteExpenseDataSource(Context context, SQLiteDatabase database) {
//        this.context = context;
//        this.database = database;
//    }
//
//    // region Basic methods
//    @Override
//    public void saveModel(EXPENSE expense) throws SQLException {
////        String query = "";
////
////        if (data == null) {
////            return new CreateOrUpdateStatus(false, false, 0);
////        }
////        ID id = extractId(data);
////        // assume we need to create it if there is no id
////        if (id == null || !idExists(id)) {
////            int numRows = create(data);
////            return new CreateOrUpdateStatus(true, false, numRows);
////        } else {
////            int numRows = update(data);
////            return new CreateOrUpdateStatus(false, true, numRows);
////        }
////
////        public String INSERT_ITEM = "INSERT INTO note_table_%s ('item_text','item_mark','item_date') VALUES ('%s', '%s', '%s')";
//    }
//
//    @Override
//    public EXPENSE saveAndGetModel(EXPENSE expense) throws SQLException {
////        // save
////        saveModel(expense);
////        // retrieve
////        String query = "SELECT * FROM `" + EXPENSE_TABLE_NAME + "` ORDER BY `" + EXPENSE_ID + "` DESC LIMIT 1";
////        Cursor cursor = database.rawQuery(query, null);
////        cursor.moveToFirst();
////
////        long expenseId = cursor.getLong(0);
////        String name = cursor.getString(1);
////        long value = cursor.getLong(2);
////        long creationDate = cursor.getLong(3);
////        String type = cursor.getString(4);
////        float discount = cursor.getFloat(5);
////        String jobId = cursor.getString(6);
////        String materialId = cursor.getString(7);
////        String workTypeId = cursor.getString(8);
////
////        Expense ex = new Expense();
////        ex.setId(expenseId);
////        ex.setName(name);
////        ex.setValue(value);
////        ex.setCreationDate(creationDate);
////        ex.setType(type);
////        ex.setDiscount(discount);
////        ex.setJobId(jobId);
////        ex.setMaterialId(materialId);
////        ex.setWorkTypeId(workTypeId);
//
//        return null;
//    }
//
//    @Override
//    public long deleteModel(EXPENSE expense) throws SQLException {
//        long id = expense.getId();
////        dao.delete(expense);
//        return id;
//    }
//
//    @Override
//    public EXPENSE getModelById(long id) throws SQLException {
//        return null;
//    }
//
//    @Override
//    public List<EXPENSE> getModels() {
//        return null;
//    }
//    // endregion
//
//    // region Additional methods
//
//    /**
//     * Using for query expenses by date and jobId
//     *
//     * @param jobId is field of Job object
//     * @param date  should be in "yyyy-MM-dd" format
//     * @return list of expenses
//     */
//    public List<EXPENSE> getExpenses(String jobId, String date) throws SQLException {
////        boolean hasDate = !TextUtils.isEmpty(date);
////        QueryBuilder<EXPENSE, Long> qb = dao.queryBuilder();
////        Where where = qb.where().eq(JOB_ID, jobId);
////        if (hasDate) {
////
////            Date time = ExpenseUtil.dateFromString(date, ExpenseUtil.SHORT_DATE_FORMAT);
////
////            Calendar yesterday = Calendar.getInstance();
////            yesterday.setTime(time);
////
////            Calendar tomorrow = Calendar.getInstance();
////            tomorrow.setTime(time);
////            tomorrow.add(Calendar.DATE, 1);
////
////            long beforeTime = yesterday.getTimeInMillis();
////            long afterTime = tomorrow.getTimeInMillis();
////            where.and().between(CREATION_DATE, beforeTime, afterTime);
////
////        }
////        List<EXPENSE> expenses = qb.orderBy(CREATION_DATE, false).query();
////        "SELECT * FROM `expenses` WHERE (`jobId` = '75ae502a-e818-49c6-851a-c79dc4f16da4' AND `creationDate` BETWEEN 1521172800000 AND 1521259200000 ) ORDER BY `creationDate` DESC ";
//        return null;
//    }
//
//    /**
//     * Using for query expenses by searching name and expense type
//     *
//     * @param jobId       is field of Job object
//     * @param searchQuery for searching in name field
//     * @param expenseType
//     * @return list of expenses
//     */
//    public List<EXPENSE> getExpenses(String jobId, String searchQuery, @ExpenseUnit.Type String expenseType) throws SQLException {
////        QueryBuilder<EXPENSE, Long> qb = dao.queryBuilder();
////
//////            // Doesn't work in case of case sensitive
//////            qb.distinct().selectColumns("name");
////
////        Where where = qb.where().eq(JOB_ID, jobId);
////
////        if (!TextUtils.isEmpty(searchQuery))
////            where.and().like(NAME, "%" + searchQuery + "%");
////        "SELECT * FROM `expenses` WHERE ((`jobId` = '75ae502a-e818-49c6-851a-c79dc4f16da4' AND `name` LIKE '%h%' ) AND `type` = 'Other' ) ";
////
////        if (!TextUtils.isEmpty(expenseType))
////            where.and().eq(TYPE, expenseType);
////        "SELECT * FROM `expenses` WHERE (`jobId` = '75ae502a-e818-49c6-851a-c79dc4f16da4' AND `type` = 'Other' ) ";
////
////        List<EXPENSE> expenses = qb.query();
//        return null;
//    }
//
//    /**
//     * @param jobId is field of Job object
//     * @return total count of expenses for Job object with jobId field
//     */
//    public long getTotalExpensesCount(String jobId) throws SQLException {
////        Where where = dao.queryBuilder().where().eq(JOB_ID, jobId);
////        long totalCount = where.countOf();
//        return 0;
//    }
//    // endregion
//}
