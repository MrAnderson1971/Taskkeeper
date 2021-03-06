package com.example.myapplication.Classes;

import com.example.myapplication.MainActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;


public class Languages
{
    private final HashMap<Keys, String> ENGLISH_DICT = new HashMap<>();
    private final HashMap<Keys, String> CHINESE_DICT = new HashMap<>();

    public static final String CHECKMARK = "" + ((char) 10004); // checkmark emoji
    public static final String CROSSMARK = "" + ((char) 10060); // cross mark 'X' emoji
    public static final String PLUSSIGN = "" + ((char) 10133); // plus emoji
    public static final String LEFTARROW = "" + ((char) 11013); // <- emoji

    /**
     * Fill the dictionaries.
     */
    public Languages()
    {
        ENGLISH_DICT.put(Keys.ADD_NEW, "Add new " + PLUSSIGN);
        ENGLISH_DICT.put(Keys.ADD_NEW_USER, "Add new user");
        ENGLISH_DICT.put(Keys.ADD_NEW_TITLE, "Add new");
        ENGLISH_DICT.put(Keys.BACK, LEFTARROW + "Back");
        ENGLISH_DICT.put(Keys.CALENDAR, "Calendar");
        ENGLISH_DICT.put(Keys.CANCEL, "Cancel " + CROSSMARK);
        ENGLISH_DICT.put(Keys.CONFIRM, "Confirm " + CHECKMARK);
        ENGLISH_DICT.put(Keys.CONFIRM_DELETION, "Confirm deletion");
        ENGLISH_DICT.put(Keys.DAILY_TASKS, "Daily tasks");
        ENGLISH_DICT.put(Keys.DAILY_TASKS_HAVE_BEEN_RESET, "Daily tasks have been reset.");
        ENGLISH_DICT.put(Keys.DATE, "Date");
        ENGLISH_DICT.put(Keys.DATE_HAS_ALREADY_PASSED, "Date has already passed.");
        ENGLISH_DICT.put(Keys.DAY_AT, "Day at:");
        ENGLISH_DICT.put(Keys.DAYS_OF_WEEK, "Day(s) of week");
        ENGLISH_DICT.put(Keys.DELETE, "Delete");
        ENGLISH_DICT.put(Keys.DELETE_SELECTED_TIME, "Delete selected time");
        ENGLISH_DICT.put(Keys.DELETE_USER, "Delete user");
        ENGLISH_DICT.put(Keys.DESCRIPTION, "Description");
        ENGLISH_DICT.put(Keys.EDIT, "Edit");
        ENGLISH_DICT.put(Keys.FRIDAY, "Friday");
        ENGLISH_DICT.put(Keys.GO_TO_DATE, "Go to date");
        ENGLISH_DICT.put(Keys.HELP, "Help");
        ENGLISH_DICT.put(Keys.HOW_MANY_TIMES, "How many times?");
        ENGLISH_DICT.put(Keys.MENU, "Menu");
        ENGLISH_DICT.put(Keys.MONDAY, "Monday");
        ENGLISH_DICT.put(Keys.NAME, "Name");
        ENGLISH_DICT.put(Keys.NAME_TAKEN, "Name taken.");
        ENGLISH_DICT.put(Keys.NEW_USER, "New User");
        ENGLISH_DICT.put(Keys.NEW_USER_ADDED, "New user added.");
        ENGLISH_DICT.put(Keys.NEXT_NOTIFICATION_AT, "Next notification at: ");
        ENGLISH_DICT.put(Keys.NEXT_PAGE, "Next page");
        ENGLISH_DICT.put(Keys.NOTHING_SELECTED, "Nothing selected.");
        ENGLISH_DICT.put(Keys.OKAY, "Okay");
        ENGLISH_DICT.put(Keys.ON, "On:");
        ENGLISH_DICT.put(Keys.MORE, "More");
        ENGLISH_DICT.put(Keys.PLEASE_FILL_IN_THE_BLANKS, "Please fill in the blanks.");
        ENGLISH_DICT.put(Keys.PROCEED, "Proceed");
        ENGLISH_DICT.put(Keys.RENAME, "Rename");
        ENGLISH_DICT.put(Keys.RESET_EVERY_MIDNIGHT, "Reset every midnight?");
        ENGLISH_DICT.put(Keys.REPEATING_NOTIFICATIONS, "Repeating notifications");
        ENGLISH_DICT.put(Keys.REPEAT_NOTIFICATION_EVERY, "Repeat notification every:");
        ENGLISH_DICT.put(Keys.SATURDAY, "Saturday");
        ENGLISH_DICT.put(Keys.SELECT_EXISTING_USER, "Select existing user");
        ENGLISH_DICT.put(Keys.SELECT_TIME, "Select time");
        ENGLISH_DICT.put(Keys.SEND_NOTIFICATION, "Send notification?");
        ENGLISH_DICT.put(Keys.SORT_BY, "Sort by:");
        ENGLISH_DICT.put(Keys.SUCCESSFUL, "Successful");
        ENGLISH_DICT.put(Keys.SUNDAY, "Sunday");
        ENGLISH_DICT.put(Keys.THURSDAY, "Thursday");
        ENGLISH_DICT.put(Keys.TIMES_COMPLETED, "Times completed: ");
        ENGLISH_DICT.put(Keys.TUESDAY, "Tuesday");
        ENGLISH_DICT.put(Keys.VIEW, "View");
        ENGLISH_DICT.put(Keys.VIEW_ALL_REMINDERS, "View all reminders");
        ENGLISH_DICT.put(Keys.WEDNESDAY, "Wednesday");

        CHINESE_DICT.put(Keys.ADD_NEW, "????????? " + PLUSSIGN);
        CHINESE_DICT.put(Keys.ADD_NEW_USER, "????????????");
        CHINESE_DICT.put(Keys.ADD_NEW_TITLE, "?????????");
        CHINESE_DICT.put(Keys.BACK, LEFTARROW + "??????");
        CHINESE_DICT.put(Keys.CALENDAR, "??????");
        CHINESE_DICT.put(Keys.CANCEL, "?????? " + CROSSMARK);
        CHINESE_DICT.put(Keys.CONFIRM, "?????? " + CHECKMARK);
        CHINESE_DICT.put(Keys.CONFIRM_DELETION, "????????????");
        CHINESE_DICT.put(Keys.DAILY_TASKS, "????????????");
        CHINESE_DICT.put(Keys.DATE, "??????");
        CHINESE_DICT.put(Keys.DATE_HAS_ALREADY_PASSED, "????????????????????????");
        CHINESE_DICT.put(Keys.DAILY_TASKS_HAVE_BEEN_RESET, "??????????????????????????????");
        CHINESE_DICT.put(Keys.DAY_AT, "?????????");
        CHINESE_DICT.put(Keys.DAYS_OF_WEEK, "?????????");
        CHINESE_DICT.put(Keys.DELETE, "??????");
        CHINESE_DICT.put(Keys.DELETE_SELECTED_TIME, "??????????????????");
        CHINESE_DICT.put(Keys.DELETE_USER, "????????????");
        CHINESE_DICT.put(Keys.DESCRIPTION, "??????");
        CHINESE_DICT.put(Keys.EDIT, "???");
        CHINESE_DICT.put(Keys.FRIDAY, "?????????");
        CHINESE_DICT.put(Keys.GO_TO_DATE, "?????????");
        CHINESE_DICT.put(Keys.HELP, "?????????"); // technically translates to "instructions manual"
        CHINESE_DICT.put(Keys.HOW_MANY_TIMES, "?????????");
        CHINESE_DICT.put(Keys.MENU, "??????"); // TODO: check accuracy
        CHINESE_DICT.put(Keys.MONDAY, "?????????");
        CHINESE_DICT.put(Keys.MORE, "??????");
        CHINESE_DICT.put(Keys.NAME, "??????");
        CHINESE_DICT.put(Keys.NAME_TAKEN, "????????????????????");
        CHINESE_DICT.put(Keys.NEW_USER, "?????????");
        CHINESE_DICT.put(Keys.NEW_USER_ADDED, "??????????????????");
        CHINESE_DICT.put(Keys.NEXT_NOTIFICATION_AT, "??????????????????");
        CHINESE_DICT.put(Keys.NEXT_PAGE, "?????????");
        CHINESE_DICT.put(Keys.NOTHING_SELECTED, "????????????????????????");
        CHINESE_DICT.put(Keys.OKAY, "??????");
        CHINESE_DICT.put(Keys.ON, "?????????");
        CHINESE_DICT.put(Keys.PLEASE_FILL_IN_THE_BLANKS, "??????????????????");
        CHINESE_DICT.put(Keys.PROCEED, "??????");
        CHINESE_DICT.put(Keys.RENAME, "?????????");
        CHINESE_DICT.put(Keys.REPEATING_NOTIFICATIONS, "???????????????");
        CHINESE_DICT.put(Keys.REPEAT_NOTIFICATION_EVERY, "???????????????");
        CHINESE_DICT.put(Keys.RESET_EVERY_MIDNIGHT, "???????????????????????????");
        CHINESE_DICT.put(Keys.SATURDAY, "?????????");
        CHINESE_DICT.put(Keys.SELECT_EXISTING_USER, "???????????????");
        CHINESE_DICT.put(Keys.SELECT_TIME, "?????????");
        CHINESE_DICT.put(Keys.SEND_NOTIFICATION, "???????????????");
        CHINESE_DICT.put(Keys.SORT_BY, "???????????????");
        CHINESE_DICT.put(Keys.SUCCESSFUL, "?????????");
        CHINESE_DICT.put(Keys.SUNDAY, "?????????");
        CHINESE_DICT.put(Keys.THURSDAY, "?????????");
        CHINESE_DICT.put(Keys.TIMES_COMPLETED, "?????????????????????");
        CHINESE_DICT.put(Keys.TUESDAY, "?????????");
        CHINESE_DICT.put(Keys.VIEW, "??????");
        CHINESE_DICT.put(Keys.VIEW_ALL_REMINDERS, "??????????????????");
        CHINESE_DICT.put(Keys.WEDNESDAY, "?????????");

        // Make sure all terms have English and Chinese translations.
        //assert ensureSameSize(ENGLISH_DICT, CHINESE_DICT) : "Dictionary sizes are different.";
        if (!ensureSameSize(ENGLISH_DICT, CHINESE_DICT))
        {
            /*
            Actual assert statement not working here.
             */
            throw new AssertionError("Dictionary sizes are different.");
        }
    }

    /**
     * Ensures that all dictionaries are the same size.
     * If dictionaries are not the same size, then
     * we know that some phrases are missing from a dictionary.
     * Easier to catch this stuff now than trying to find a
     * "null" somewhere in the program.
     *
     * @param maps
     * @return
     */
    private boolean ensureSameSize(HashMap<Keys, String>... maps)
    {
        if (maps.length == 0)
        {
            return true;
        }

        Stack<Integer> lengths = new Stack<>();
        lengths.push(maps[0].size());
        for (HashMap<Keys, String> m : maps)
        {
            if (lengths.peek() != m.size())
            {
                return false;
            }
            lengths.push(m.size());
        }
        return true;
    }

    /**
     * Retrieve a word from the dictionary
     *
     * @param key
     * @return corresponding string
     */
    public String get(Keys key)
    {
        switch (MainActivity.lang)
        {
            case ENGLISH:
                return ENGLISH_DICT.get(key);
            case CHINESE:
                return CHINESE_DICT.get(key);
            default:
                throw new UnsupportedOperationException("Language not recognized.");
        }
    }

    public static Locale getLocale()
    {
        switch (MainActivity.lang)
        {
            case ENGLISH:
                return Locale.ENGLISH;
            case CHINESE:
                return Locale.CHINESE;
            default:
                throw new UnsupportedOperationException("Language not recognized.");
        }
    }
}
