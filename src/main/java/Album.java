import java.util.List;

public class Album {
    public String id;
    public String name;
    public String release_date;
    public String type;

    public Album(String id, String name, String release_date,String type) {
        this.id = id;
        this.name = name;
        this.release_date = release_date;
        this.type = type;
    }


    public Album(){

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", release_date='" + release_date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
