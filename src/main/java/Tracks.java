public class Tracks {
    public String name;
    public String id;
    public int duration_ms;

    public Tracks() {
        this.name = name;
        this.id = id;
        this.duration_ms = duration_ms;
    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tracks{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", duration_ms=" + duration_ms +
                '}';
    }
}
