package amigro.tk.amigro;

public class Migration implements Comparable {
    private int version;
    private Runnable migration;

    public Migration(int version, Runnable migration) {
        this.version = version;
        this.migration = migration;
    }

    @Override
    public int compareTo(Object another) {
        return Integer.valueOf(version).compareTo(((Migration)another).getVersion());
    }

    public int getVersion() {
        return version;
    }

    public void execute() {
        if (migration != null) {
            migration.run();
        }
    }
}
