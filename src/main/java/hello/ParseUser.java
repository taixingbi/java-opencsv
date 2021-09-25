package hello;

import java.io.*;
import java.util.*;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import hello.model.User;

public class ParseUser {
    public List<User> parse(String reponsePayload) throws FileNotFoundException {
        System.out.println("\n------------------------parse------------------------");
        String FILENAME= "User.csv";
        CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
//        CSVReader csvReader = new CSVReaderBuilder(new StringReader(reponsePayload)).withSkipLines(0)
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(FILENAME)).withSkipLines(0)
                .withCSVParser(parser).build();

        CsvToBean csvToBean = new CsvToBeanBuilder<User>(csvReader).withType(User.class)
                .withMappingStrategy(MappingStrategy()).build();

        List<User> users = csvToBean.parse();

        for (User user : users) System.out.println(user);
        System.out.println(users.get(0).getName() );

        System.out.println("------------------------parse end------------------------\n");
        return users;
    }

    private HeaderColumnNameTranslateMappingStrategy<User> MappingStrategy (){
        HeaderColumnNameTranslateMappingStrategy<User> strategy =
                new HeaderColumnNameTranslateMappingStrategy<User>();
        strategy.setType(User.class);
        strategy.setColumnMapping(setColumnMapping());
        return strategy;
    }

    private Map<String, String> setColumnMapping(){
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("id", "id");
        mapping.put("name", "name");
        mapping.put("email", "email");
        return mapping;
    }
}
