package com.paralect.easytimedataormlite.request;

import com.example.paralect.easytime.model.Expense;
import com.example.paralect.easytime.model.ExpenseUnit;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.paralect.datasource.core.Entity;
import com.paralect.datasource.core.EntityConverter;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Oleg Tarashkevich on 22/03/2018.
 */

public class ExpenseConverter implements EntityConverter<ExpenseConverter.ExpenseScheme, Expense, QueryBuilder<ExpenseConverter.ExpenseScheme, Long>> {

    // region Fields constants
    public static final String EXPENSE_TABLE_NAME = "expenses";
    public static final String EXPENSE_ID = "expenseId";
    public static final String NAME = "name";
    public static final String DISCOUNT = "discount";
    public static final String VALUE = "value";
    public static final String UNIT_NAME = "unitName";
    public static final String CREATION_DATE = "creationDate";
    public static final String TYPE = "type";
    public static final String JOB_ID = "jobId";
    public static final String MATERIAL_ID = "materialId";
    public static final String WORK_TYPE_ID = "workTypeId";
    // endregion

    @Override
    public ExpenseScheme wrap(Expense ex) {
        ExpenseScheme in = new ExpenseScheme();
        in.setId(ex.getId());
        in.setName(ex.getName());
        in.setValue(ex.getValue());
        in.setCreationDate(ex.getCreationDate());
        in.setType(ex.getType());
        in.setDiscount(ex.getDiscount());
        in.setJobId(ex.getJobId());
        in.setMaterialId(ex.getMaterialId());
        in.setWorkTypeId(ex.getWorkTypeId());
        return in;
    }

    @Override
    public Expense unwrap(ExpenseScheme in) {
        Expense ex = new Expense();
        ex.setId(in.getId());
        ex.setName(in.getName());
        ex.setValue(in.getValue());
        ex.setCreationDate(in.getCreationDate());
        ex.setType(in.getType());
        ex.setDiscount(in.getDiscount());
        ex.setJobId(in.getJobId());
        ex.setMaterialId(in.getMaterialId());
        ex.setWorkTypeId(in.getWorkTypeId());
        return ex;
    }

    @Override
    public QueryBuilder<ExpenseConverter.ExpenseScheme, Long> getParameter() {
        return null;
    }

    @Override
    public Class<ExpenseScheme> getClazz() {
        return ExpenseScheme.class;
    }

    public static class ExpenseScheme implements Entity<Long> {

        @DatabaseField(columnName = EXPENSE_ID, generatedId = true)
        private long id;

        @DatabaseField(columnName = NAME)
        private String name;

        @DatabaseField(columnName = VALUE)
        private long value;

        @DatabaseField(columnName = CREATION_DATE)
        private long creationDate;

        @ExpenseUnit.Type
        @DatabaseField(columnName = TYPE)
        private String type;

        @DatabaseField(columnName = DISCOUNT, dataType = DataType.FLOAT)
        private float discount;

        @DatabaseField(columnName = JOB_ID)
        private String jobId;

        @DatabaseField(columnName = MATERIAL_ID)
        private String materialId;

        @DatabaseField(columnName = WORK_TYPE_ID)
        private String workTypeId;

        // region Getters & Setters
        @Override
        public void setId(Long aLong) {

        }

        @Override
        public Long getId() {
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public long getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(long creationDate) {
            this.creationDate = creationDate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public float getDiscount() {
            return discount;
        }

        public void setDiscount(float discount) {
            this.discount = discount;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }

        public String getWorkTypeId() {
            return workTypeId;
        }

        public void setWorkTypeId(String workTypeId) {
            this.workTypeId = workTypeId;
        }
        // endregion
    }
    
    public void main(Class clazz) throws Exception {
        final Something oldAnnotation = (Something) clazz.getAnnotations()[0];
        System.out.println("oldAnnotation = " + oldAnnotation.someProperty());
        Annotation newAnnotation = new Something() {

            @Override
            public String someProperty() {
                return "another value";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return oldAnnotation.annotationType();
            }
        };
        Field field = Class.class.getDeclaredField("annotations");
        field.setAccessible(true);
        Map<Class<? extends Annotation>, Annotation> annotations = (Map<Class<? extends Annotation>, Annotation>) field.get(Foobar.class);
        annotations.put(Something.class, newAnnotation);

        Something modifiedAnnotation = (Something) Foobar.class.getAnnotations()[0];
        System.out.println("modifiedAnnotation = " + modifiedAnnotation.someProperty());
    }

    @Something(someProperty = "some value")
    public static class Foobar {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Something {

        String someProperty();
    }
}
