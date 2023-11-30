package persistence.sql.usecase;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import persistence.sql.vo.DatabaseField;
import persistence.sql.vo.DatabaseFields;
import persistence.sql.vo.association.OneToManyAssociation;
import persistence.sql.vo.type.TypeConverter;

public class GetFieldFromClass {
    public DatabaseFields execute(Class<?> cls) {
        Field[] declaredFields = cls.getDeclaredFields();
        return DatabaseFields.of(Arrays.stream(declaredFields)
                                       .filter(it -> !it.isAnnotationPresent(Transient.class))
                                       .map(this::convertToDatabaseField)
                                       .collect(Collectors.toList()));
    }

    private DatabaseField convertToDatabaseField(Field field) {
        String name = field.getName();
        boolean isNullable = true;
        if (field.isAnnotationPresent(Column.class)) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation.name() != null && !annotation.name().isEmpty()) {
                name = annotation.name();
            }
            isNullable = annotation.nullable();
        }
        boolean isPrimary = field.isAnnotationPresent(Id.class);
        GenerationType type = null;
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
            if (annotation.strategy() == GenerationType.IDENTITY) {
                type = GenerationType.IDENTITY;
            }
        }

        OneToManyAssociation oneToManyAssociation = null;
        if (field.isAnnotationPresent(OneToMany.class)) {
            OneToMany annotation = field.getAnnotation(OneToMany.class);
            oneToManyAssociation = new OneToManyAssociation(
                annotation.targetEntity(),
                field.getType()
            );
        }
        return DatabaseField.builder()
                            .databaseFieldName(name)
                            .originalFieldName(field.getName())
                            .databaseType(TypeConverter.convert(field))
                            .isPrimary(isPrimary)
                            .primaryKeyGenerationType(type)
                            .isNullable(isNullable)
                            .oneToManyAssociation(oneToManyAssociation)
                            .build();
    }
}
