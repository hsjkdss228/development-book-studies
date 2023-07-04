package effectivejava.items.chapter2.item6;

public class RomanNumeralCheckerDefault implements RomanNumeralChecker {
    @Override
    public boolean isRomanNumeral(String string) {
        return string.matches("^(?=[MDCLXVI])M*(C[MD]|D?C*)(X[CL]|L?X*)(I[XV]|V?I*)$");
    }
}
