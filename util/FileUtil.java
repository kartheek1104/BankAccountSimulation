package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Account;

public class FileUtil {

    private static final String DATA_FOLDER = "data";
    private static final String ACCOUNTS_FILE = "accounts.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Map<String, Account> loadAccounts() {
        try {
            File file = new File(DATA_FOLDER, ACCOUNTS_FILE);
            if (!file.exists()) {
                return new java.util.HashMap<>();
            }
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<Map<String, Account>>() {}.getType();
                return gson.fromJson(reader, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new java.util.HashMap<>();
        }
    }

    public static void saveAccounts(Map<String, Account> accounts) {
        try {
            File dir = new File(DATA_FOLDER);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, ACCOUNTS_FILE);
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(accounts, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Add these methods for transaction history ---

    public static List<String> loadHistory(String accountNumber) {
        try {
            File file = new File(DATA_FOLDER, accountNumber + "_history.json");
            if (!file.exists()) {
                return new ArrayList<>();
            }
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<List<String>>() {}.getType();
                return gson.fromJson(reader, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveHistory(String accountNumber, List<String> history) {
        try {
            File dir = new File(DATA_FOLDER);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, accountNumber + "_history.json");
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(history, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
