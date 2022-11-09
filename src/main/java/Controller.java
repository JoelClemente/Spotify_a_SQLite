import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class Controller {

    public Controller() {
    }


     public static void insert(String id, String title, String release_date,String type) {
     String sql = "INSERT OR IGNORE INTO Álbumes (id,title,release_date,type) VALUES(?,?,?,?)";

     Connect connect = new Connect();

     try (Connection conn = connect.connect();
     PreparedStatement pstmt =  conn.prepareStatement(sql)) {
     pstmt.setString(1, id);
     pstmt.setString(2, title);
     pstmt.setString(3, release_date);
     pstmt.setString(4, type);
         pstmt.executeUpdate();
     } catch (SQLException e) {
     System.out.println(e.getMessage());
     }
     }

     public static void insert2(String id, String name, int popularity) {
     String sql = "INSERT OR IGNORE INTO Artistas (id,name,popularity) VALUES(?,?,?)";
     Connect connect = new Connect();
     try (Connection conn = connect.connect();
     PreparedStatement pstmt =  conn.prepareStatement(sql)) {
     pstmt.setString(1, id);
     pstmt.setString(2, name);
     pstmt.setInt(3, popularity);
     pstmt.executeUpdate();
     } catch (SQLException e) {
     System.out.println(e.getMessage());
     }
     }
     public static void insert3(String id, String name,int duration_ms) {
        String sql = "INSERT OR IGNORE INTO Tracks (id,name,duration_ms) VALUES(?,?,?)";
        Connect connect = new Connect();
        try (Connection conn = connect.connect();
        PreparedStatement pstmt =  conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, duration_ms);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }

    private final Map<String, String> artists = Map.of("Eladio Carrión","5XJDexmWFLWOkjOEjOVX3e",
            "Mora","0Q8NcsJwoCbZOHHW63su5S",
            "BadBunny","4q3ewBCX7sLwd24euuV69X",
            "Quevedo","52iwsT98xCoGgiGntTiR7K",
            "JhayCortez","0EFisYRi20PTADoJrifHrz");

    public void controller() throws Exception {
        Connect connect = new Connect();
        String dbPath = "C:/Users/leojs/IdeaProjects/spotify_DACD/spotify.db";
        try (Connection conn = connect.connect(dbPath)) {
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS Álbumes (" +
                    "id TEXT PRIMARY KEY, " +
                    "title TEXT NOT NULL, " +
                    "release_date TEXT NOT NULL, " +
                    "type TEXT NOT NULL" +
                    ")");
            statement.clearBatch();
            statement.execute("CREATE TABLE IF NOT EXISTS Artistas (" +
                    "id TEXT PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "popularity TEXT NOT NULL " +
                    ")");
            statement.clearBatch();
            statement.execute("CREATE TABLE IF NOT EXISTS Tracks (" +
                    "id TEXT PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "duration_ms TEXT NOT NULL " +
                    ")");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (String artistid : artists.values()) {

            SpotifyAccessor spotifyAccessor2 = new SpotifyAccessor();
            String response2 = spotifyAccessor2.get("/artists/" + artistid, Map.of("limit","10"));
            JsonObject jsonObject2 = new Gson().fromJson(response2, JsonObject.class);
            Artist artist2 = new Artist();
            artist2.setId(jsonObject2.get("id").getAsString());
            artist2.setName(jsonObject2.getAsJsonObject().get("name").getAsString());
            artist2.setPopularity(jsonObject2.getAsJsonObject().get("popularity").getAsInt());
            insert2(artist2.getId(),artist2.getName(),artist2.getPopularity());

            for (String artist : artists.values()) {
                SpotifyAccessor spotifyAccessor = new SpotifyAccessor();
                String response = spotifyAccessor.get("/artists/" + artistid + "/albums", Map.of("limit","10"));
                JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
                JsonArray items = jsonObject.get("items").getAsJsonArray();

                for (JsonElement item : items) {
                    Album album = new Album();
                    album.setId(item.getAsJsonObject().get("id").getAsString());
                    album.setName(item.getAsJsonObject().get("name").getAsString());
                    album.setRelease_date(item.getAsJsonObject().get("release_date").getAsString());
                    album.setType(item.getAsJsonObject().get("type").getAsString());
                    insert(album.getId(),album.getName(),album.getRelease_date(),album.getType());

                    String response3 = spotifyAccessor.get("/albums/" + item.getAsJsonObject().get("id").getAsString() + "/tracks", Map.of("limit", "10"));
                    JsonObject jsonObject3 = new Gson().fromJson(response3, JsonObject.class);
                    JsonArray items2 = jsonObject3.get("items").getAsJsonArray();
                    for (JsonElement item3 : items2) {
                        Tracks track = new Tracks();
                        track.setId(item3.getAsJsonObject().get("id").getAsString());
                        track.setName(item3.getAsJsonObject().get("name").getAsString());
                        track.setDuration_ms(item3.getAsJsonObject().get("duration_ms").getAsInt());
                        insert3(track.getId(),track.getName(), track.getDuration_ms());
                    }
                }


            }

        }
    }
}
