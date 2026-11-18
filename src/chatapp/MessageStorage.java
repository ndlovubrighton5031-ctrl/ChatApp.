package chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Attribution:
 *   Google, 2024. Gson User Guide.
 *   https://github.com/google/gson/blob/main/UserGuide.md
 */
public class MessageStorage {
    private final String filePath = "messages.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Append message to JSON file
    public void saveMessage(Message msg) {
        List<Message> list = getList();
        list.add(msg);
        saveList(list);
    }

    // Read JSON into a list
    private List<Message> getList() {
        try {
            String json = Files.readString(Paths.get(filePath));
            Type type = new TypeToken<List<Message>>(){}.getType();
            List<Message> list = gson.fromJson(json, type);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // Write list to JSON file
    private void saveList(List<Message> list) {
        try {
            String json = gson.toJson(list);
            Files.writeString(Paths.get(filePath), json);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}