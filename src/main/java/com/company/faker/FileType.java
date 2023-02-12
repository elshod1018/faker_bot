package com.company.faker;
public enum FileType {
    /**
     * JSON TYPE FOR ****
     */
    JSON,CSV, SQL;

    public static FileType getByName(String name) {
        for ( FileType fileType : values() ) {
            if ( fileType.name().equalsIgnoreCase(name) )
                return fileType;
        }
        return FileType.JSON;
    }

}
