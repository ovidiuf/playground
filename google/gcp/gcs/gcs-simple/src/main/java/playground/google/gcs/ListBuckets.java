package playground.google.gcs;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;

public class ListBuckets {
    private Storage storageService;

    public ListBuckets(Storage storageService) {
        this.storageService = storageService;
    }

    public void run() {
        System.out.println("listing buckets");
        Page<Bucket> buckets = storageService.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }
}
