package database.project.library.services;

public class Util {
    public static final String EMPTY_STRING = "";
    public static final String NO_AUTHOR_FOUND = "Author not found! Check ID";
    public static final String NO_CATEGORY_FOUND = "Category not found! Check ID";
    public static final String NO_BOOK_FOUND = "Book not found! Check ID.";
    public static final String INVALID_ID = "Invalid ID!";
    public static final String AVAILABLE = "Dostepna";
    public static final String BOOKED = "Zarezerwowana";
    public static final String UNAVAILABLE = "Wypożyczona"; 
    public static final String NO_ACTIVE_USER = "Active user not found! Login first.";
    public static final String NO_BASKET = "Użytkownik nie posiada koszyka!";


    public static boolean isNumeric(String s) {
        if (s == null) {
            return false;
        }
        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
