package service;

import exception.BirthDayException;
import exception.EmailException;
import exception.FullNameException;
import exception.PhoneException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorService {

    public static void dayFormatCheck(String birthday) throws BirthDayException {
        // ^ asserts the start of the string.
        // \d{4} matches exactly four digits (0-9) for the year in the format YYYY.
        // \d{2} matches exactly two digits (0-9) for the month in the format MM.
        // \d{2} matches exactly two digits (0-9) for the day in the format DD.
        // $ asserts the end of the string.
        String regex = "^\\d{4}-\\d{2}-\\d{2}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(birthday);

        if (!matcher.matches()) {
            throw new BirthDayException("BirthDayException");
        }
    }

    public static void phoneCheck(String phone) throws PhoneException {
        // \d{10} matches exactly 10 digits (0-9).
        String regex = "^\\d{10}$";


        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(phone);


        if (!matcher.matches()) {
            throw new PhoneException("PhoneException");
        }
    }

    public static void emailCheck(String email) throws EmailException {
        /*
         * [a-zA-Z0-9_+&*-]+ matches one or more characters from the set [a-zA-Z0-9_+&*-]. This represents the local
         *  part of the email address before the '@' symbol. It allows letters (both uppercase and lowercase), numbers,
         *  and certain special characters like underscore _, plus +, ampersand &, asterisk *, and hyphen -.
         *
         *(?:\.[a-zA-Z0-9_+&*-]+)* matches zero or more occurrences of a period . followed by the same set of characters
         * as above. This allows for a domain name that contains multiple parts separated by dots, like example.domain.com.
         *
         * (?:[a-zA-Z0-9-]+\.)+ matches one or more occurrences of a domain name component. This allows for a domain name
         *  like example. or subdomain.example. It ends with a dot to allow for multiple subdomains.
         *
         * [a-zA-Z]{2,7} matches the top-level domain (TLD) part of the email address. It allows for letters (2 to 7
         * characters in length) and represents domain suffixes like .com, .org, .net, etc.
         * */
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


        Pattern pattern = Pattern.compile(regex);


        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new EmailException("EmailException");
        }
    }

    public static void nameCheck(String name) throws FullNameException {
        /*
         * [\p{L}]+ matches one or more Unicode letters (\p{L} matches any kind of letter from any language).
         *
         * (\s[\p{L}]+)* matches zero or more occurrences of a space \s followed by one or more Unicode letters.
         *  This allows for multiple words separated by spaces.
         * */
        String regex = "^([a-zA-Z]{1,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{1,}\\s?([a-zA-Z]{1,})?\\s?([a-zA-Z]{1,})?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new FullNameException("FullNameException");
        }
    }


}
