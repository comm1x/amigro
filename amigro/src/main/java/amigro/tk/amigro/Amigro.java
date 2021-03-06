package amigro.tk.amigro;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.TreeSet;

public class Amigro {
    private static final String TAG = "Amigro";
    private static final Amigro INSTANCE = new Amigro();
    public static final String MIGRATION_KEY = TAG + "_version";

    private final TreeSet<Migration> tasks = new TreeSet<>();

    private Amigro() {

    }

    public static Amigro getInstance() {
        return INSTANCE;
    }

    public void apply(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int latestVersion = prefs.getInt(MIGRATION_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();

        if (isFirstInstall(context, latestVersion)) {
            if (tasks.size() > 0) {
                editor.putInt(MIGRATION_KEY, tasks.last().getVersion());
                editor.apply();
            }
            return;
        }


        for (Migration task : tasks) {
            if (task.getVersion() <= latestVersion)
                continue;

            task.execute();

            Log.d(TAG, "Done migration at version: " + task.getVersion());
            editor.putInt(MIGRATION_KEY, task.getVersion());
            latestVersion = task.getVersion();
        }
        editor.apply();
        tasks.clear();
    }

    public boolean isFirstInstall(Context context, int latestVersion) {
        try {
            long firstInstallTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
            Log.d(TAG, "First Install Time: " + firstInstallTime + " Last update time: " + lastUpdateTime);
            return latestVersion <= 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Amigro addMigration(Migration migrateToVersionTask) {
        tasks.add(migrateToVersionTask);
        return this;
    }

    public Amigro addMigration(int version, Runnable migration) {
        tasks.add(new Migration(version, migration));
        return this;
    }
}
