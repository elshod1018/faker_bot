package com.company.state;

public enum GenerateDataState implements State {
    FILE_NAME,
    FILE_TYPE,
    ROW_COUNT,
    FIELDS,
    GENERATE,
    BLOCKED
}
