package playground.google.gcs;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class Main {
    public static void main(String[] args) {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        new ListBuckets(storage).run();
    }
}
