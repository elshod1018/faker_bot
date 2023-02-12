package com.company.faker;

import lombok.*;

import java.util.function.BiFunction;


@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = {"fieldName", "fieldType"})
public class Field {
    private String fieldName;
    private FieldType fieldType;
    private BiFunction<Integer, Integer, Object> func;
    private final int min = 10;
    private final int max = 50;

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public Field(String fieldName, FieldType fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.func = FakeDataApplicationService.functions.get(fieldType);
    }

    public String getPatternAsJson() {
        return fieldType.getRowAsJson(fieldName, func.apply(min, max));
    }

    @Override
    public String toString() {
        return "\033[1;92m%s : %s \033[0m\n".formatted(fieldName, fieldType.name());
    }
}
