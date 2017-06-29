## Overview

__Amigro__ is migration tool Android, allowing declarating code, that will be runned once after application update.
Instead of most common migration libraries, Amigro is not aimed to migrate DB, but aimed to migrate business logic at whole.

## Install

## Example

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        applyMigrations();
    }

    private void applyMigrations() {
        Amigro.getInstance()
                .addMigration(1, new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * CacheManager is example
                         */
                        CacheManager.getInstance().drop("users");
                    }
                })
                .addMigration(2, new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * CacheManager is example
                         */
                        CacheManager.getInstance().drop("last_shop");
                    }
                })
                .addMigration(3, new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * FileManager is example
                         */
                        FileManager.removeOldImages();
                    }
                })
                .apply(this);
    }
}
```

Here 1, 2, 3 is the version of migrations. After applying migration with version 3, all previous (1 and 2) will never be applied.
