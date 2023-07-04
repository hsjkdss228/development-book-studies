package effectivejava.items.chapter2.item6;

import java.util.regex.Pattern;

public class RomanNumeralCheckerWithCachedPattern implements RomanNumeralChecker {
    private static final Pattern ROMAN = Pattern.compile(
        "^(?=[MDCLXVI])M*(C[MD]|D?C*)(X[CL]|L?X*)(I[XV]|V?I*)$"
    );

    @Override
    public boolean isRomanNumeral(String string) {
        return ROMAN.matcher(string).matches();
    }
}
