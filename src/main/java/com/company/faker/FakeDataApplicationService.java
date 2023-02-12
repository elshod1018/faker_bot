package com.company.faker;


import com.github.javafaker.*;
import com.github.javafaker.service.RandomService;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

import static com.company.faker.FieldType.*;


public class FakeDataApplicationService {
    private static final AtomicLong id = new AtomicLong(1);
    private static final Faker faker = new Faker();
    private static final Country country = faker.country();
    private static final Address address = faker.address();
    private static final Book book = faker.book();
    private static final Name name = faker.name();
    private static final Lorem lorem = faker.lorem();
    private static final RandomService random = faker.random();
    private static final PhoneNumber phoneNumber = faker.phoneNumber();
    public static final Map<FieldType, BiFunction<Integer, Integer, Object>> functions = new HashMap<>() {{
        put(ID, (a, b) -> id.incrementAndGet());
        put(UUID, (a, b) -> java.util.UUID.randomUUID());
        put(BOOK_TITLE, (a, b) -> book.title());
        put(FieldType.BOOK_AUTHOR, (a, b) -> book.author());
        put(POST_TITLE, (a, b) -> String.join(" ", lorem.words(random.nextInt(a, b))));
        put(POST_BODY, (a, b) -> String.join("", lorem.paragraphs(random.nextInt(a, b))));
        put(FIRSTNAME, (a, b) -> name.firstName());
        put(LASTNAME, (a, b) -> name.lastName());
        put(USERNAME, (a, b) -> name.username());
        put(FULLNAME, (a, b) -> name.fullName());
        put(BLOOD_GROUP, (a, b) -> name.bloodGroup());
        put(EMAIL, (a, b) -> name.username() + "@" + (random.nextBoolean() ? "gmail.com" : "mail.ru"));
        put(GENDER, (a, b) -> random.nextBoolean() ? "MALE" : "FEMALE");
        put(PHONE, (a, b) -> phoneNumber.cellPhone());
        put(LOCAlDATE, (a, b) -> {
            int year = random.nextInt(1900, Year.now().getValue() - 1);
            int month = random.nextInt(1, 12);
            YearMonth yearMonth = YearMonth.of(year, month);
            int day = random.nextInt(1, yearMonth.getMonth().length(yearMonth.isLeapYear()));
            return LocalDate.of(year, month, day);
        });
        put(COUNTRY_CODE, (a, b) -> country.countryCode3());
        put(COUNTRY_ZIP_CODE, (a, b) -> address.zipCode());
        put(CAPITAL, (a, b) -> country.capital());
        put(WORD, (a, b) -> lorem.word());
        put(WORDS, (a, b) -> lorem.words(random.nextInt(2, 4)));
        put(PARAGRAPH, (a, b) -> lorem.paragraph());
        put(PARAGRAPHS, (a, b) -> lorem.paragraphs(random.nextInt(2, 4)));
        put(AGE, random::nextInt);
        put(RANDOM_INT, random::nextInt);
        put(LETTERS, (a, b) -> lorem.characters(3, 4, true));
    }};

    public static final List<FieldType> BLACK_LIST = List.of(
            AGE, WORDS, PARAGRAPHS, RANDOM_INT, POST_TITLE, POST_BODY, LETTERS
    );

    public static File processRequest(FakeDataGenerateRequest fakeDataGenerateRequest) {
        var fileType = fakeDataGenerateRequest.getFileType();
        var fileName = fakeDataGenerateRequest.getFileName() + "." + fileType.name().toLowerCase();
        var rowsCount = fakeDataGenerateRequest.getCount();
        var fields = fakeDataGenerateRequest.getFields();
        return switch (fileType) {
            case JSON -> new File(generateDataAsJson(rowsCount, fileName, fields));
            case CSV -> new File(generateDataAsCsv(rowsCount, fileName, fields));
            case SQL -> new File(generateDataAsSql(rowsCount, fileName, fields));
        };
    }

    private static String generateDataAsSql(int rowsCount, String fileName, Set<Field> fields) {

        return "";
    }

    private static String generateDataAsCsv(int rowsCount, String fileName, Set<Field> fields) {
        synchronized (FakeDataApplicationService.class) {
            java.io.File file = new java.io.File(fileName);
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                StringJoiner stringJoiner = new StringJoiner(", ", "", "\n");
                for (Field field : fields) {
                    stringJoiner.add(field.getFieldName());
                }
                fileWriter.write(stringJoiner.toString());
                for (int i = 0; i < rowsCount; i++) {
                    stringJoiner = new StringJoiner(", ", "", "\n");
                    for (Field field : fields) {
                        BiFunction<Integer, Integer, Object> integerIntegerObjectBiFunction = functions.get(field.getFieldType());
                        stringJoiner.add(integerIntegerObjectBiFunction.apply(10, 50).toString());
                    }
                    fileWriter.write(stringJoiner.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    //    private String generateDataAsJson(int rowsCount, String fileName, Set<Field> fields) {
//        synchronized (FakeDataApplicationService.class) {
//            var result = new StringJoiner(",\n", "[", "]");
//            for (int i = 0; i < rowsCount; i++) {
//                var row = new StringJoiner(", ", "{", "}");
//                for (Field field : fields)
//                    row.add(field.getPatternAsJson());
//                result.add(row.toString());
//            }
//            Path path = Path.of(fileName);
//            try {
//                if (Files.notExists(path))
//                    Files.createFile(path);
//                Files.writeString(path, result.toString(), StandardOpenOption.TRUNCATE_EXISTING);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return path.toAbsolutePath().toString();
//        }
//    }

    private static String generateDataAsJson(int rowsCount, String fileName, Set<Field> fields) {
        synchronized (FakeDataApplicationService.class) {
            Path path = Path.of(fileName);
            try {
                if (Files.notExists(path)) {
                    Files.createFile(path);
                }
                Files.writeString(path, "[", StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < rowsCount / 1000; i++) {
                var result = new StringJoiner(",\n", "", ",");
                for (int j = 0; j < 1000; j++) {
                    var row = new StringJoiner(", ", "{", "}");
                    for (Field field : fields)
                        row.add(field.getPatternAsJson());
                    result.add(row.toString());
                }
                try {
                    Files.writeString(path, result.toString(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            var result = new StringJoiner(",\n", "", ",");
            for (int i = 0; i < rowsCount % 1000 - 1; i++) {
                var row = new StringJoiner(", ", "{", "}");
                for (Field field : fields)
                    row.add(field.getPatternAsJson());
                result.add(row.toString());
            }
            try {
                Files.writeString(path, result.toString(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }

            var row = new StringJoiner(", ", "{", "}");
            for (Field field : fields)
                row.add(field.getPatternAsJson());
            try {
                Files.writeString(path, row + "]", StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return path.toAbsolutePath().toString();
        }
    }
}
