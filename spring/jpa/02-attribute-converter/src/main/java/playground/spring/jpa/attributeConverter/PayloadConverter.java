package playground.spring.jpa.attributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PayloadConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {

        String s = swapLetters(attribute);

        System.out.println("convertToDatabaseColumn(" + attribute + ") -> " + s);

        return s;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {

        String s = swapLetters(dbData);

        System.out.println("convertToEntityAttribute(" + dbData + ") -> " + s);

        return s;
    }

    private String swapLetters(String s) {

        StringBuilder sb = new StringBuilder();

        for (int i = s.length() - 1; i >= 0; i--) {

            sb.append(s.charAt(i));
        }

        return sb.toString();
    }
}
