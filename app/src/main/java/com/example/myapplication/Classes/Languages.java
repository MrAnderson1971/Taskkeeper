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

        CHINESE_DICT.put(Keys.ADD_NEW, "加新的 " + PLUSSIGN);
        CHINESE_DICT.put(Keys.ADD_NEW_USER, "加新用户");
        CHINESE_DICT.put(Keys.ADD_NEW_TITLE, "加新的");
        CHINESE_DICT.put(Keys.BACK, LEFTARROW + "回去");
        CHINESE_DICT.put(Keys.CALENDAR, "日历");
        CHINESE_DICT.put(Keys.CANCEL, "取消 " + CROSSMARK);
        CHINESE_DICT.put(Keys.CONFIRM, "确认 " + CHECKMARK);
        CHINESE_DICT.put(Keys.CONFIRM_DELETION, "确认删除");
        CHINESE_DICT.put(Keys.DAILY_TASKS, "日常任务");
        CHINESE_DICT.put(Keys.DATE, "日期");
        CHINESE_DICT.put(Keys.DATE_HAS_ALREADY_PASSED, "日期已经过去了。");
        CHINESE_DICT.put(Keys.DAILY_TASKS_HAVE_BEEN_RESET, "日常任务都被重启了。");
        CHINESE_DICT.put(Keys.DAY_AT, "每天：");
        CHINESE_DICT.put(Keys.DAYS_OF_WEEK, "星期几");
        CHINESE_DICT.put(Keys.DELETE, "删掉");
        CHINESE_DICT.put(Keys.DELETE_SELECTED_TIME, "删掉选的时间");
        CHINESE_DICT.put(Keys.DELETE_USER, "删掉用户");
        CHINESE_DICT.put(Keys.DESCRIPTION, "描述");
        CHINESE_DICT.put(Keys.EDIT, "改");
        CHINESE_DICT.put(Keys.FRIDAY, "星期五");
        CHINESE_DICT.put(Keys.GO_TO_DATE, "去日期");
        CHINESE_DICT.put(Keys.HELP, "说明书"); // technically translates to "instructions manual"
        CHINESE_DICT.put(Keys.HOW_MANY_TIMES, "几次？");
        CHINESE_DICT.put(Keys.MENU, "菜单"); // TODO: check accuracy
        CHINESE_DICT.put(Keys.MONDAY, "星期一");
        CHINESE_DICT.put(Keys.MORE, "更多");
        CHINESE_DICT.put(Keys.NAME, "名字");
        CHINESE_DICT.put(Keys.NAME_TAKEN, "名字·用过了。");
        CHINESE_DICT.put(Keys.NEW_USER, "新用户");
        CHINESE_DICT.put(Keys.NEW_USER_ADDED, "用户加上了。");
        CHINESE_DICT.put(Keys.NEXT_NOTIFICATION_AT, "下个通知在：");
        CHINESE_DICT.put(Keys.NEXT_PAGE, "下一页");
        CHINESE_DICT.put(Keys.NOTHING_SELECTED, "没有选择的东西。");
        CHINESE_DICT.put(Keys.OKAY, "好的");
        CHINESE_DICT.put(Keys.ON, "每个：");
        CHINESE_DICT.put(Keys.PLEASE_FILL_IN_THE_BLANKS, "请填写空格。");
        CHINESE_DICT.put(Keys.PROCEED, "继续");
        CHINESE_DICT.put(Keys.RENAME, "换名字");
        CHINESE_DICT.put(Keys.REPEATING_NOTIFICATIONS, "重复的通知");
        CHINESE_DICT.put(Keys.REPEAT_NOTIFICATION_EVERY, "重复通知：");
        CHINESE_DICT.put(Keys.RESET_EVERY_MIDNIGHT, "每个半夜要重启吗？");
        CHINESE_DICT.put(Keys.SATURDAY, "星期六");
        CHINESE_DICT.put(Keys.SELECT_EXISTING_USER, "选现有用户");
        CHINESE_DICT.put(Keys.SELECT_TIME, "选时间");
        CHINESE_DICT.put(Keys.SEND_NOTIFICATION, "送通知吗？");
        CHINESE_DICT.put(Keys.SORT_BY, "排序方式：");
        CHINESE_DICT.put(Keys.SUCCESSFUL, "成功了");
        CHINESE_DICT.put(Keys.SUNDAY, "星期天");
        CHINESE_DICT.put(Keys.THURSDAY, "星期四");
        CHINESE_DICT.put(Keys.TIMES_COMPLETED, "做完了多少次：");
        CHINESE_DICT.put(Keys.TUESDAY, "星期二");
        CHINESE_DICT.put(Keys.VIEW, "浏览");
        CHINESE_DICT.put(Keys.VIEW_ALL_REMINDERS, "浏览每个通知");
        CHINESE_DICT.put(Keys.WEDNESDAY, "星期三");

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
