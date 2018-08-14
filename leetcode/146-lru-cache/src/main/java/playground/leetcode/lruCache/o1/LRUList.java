package playground.leetcode.lruCache.o1;

public class LRUList {

    private Payload lru;
    private Payload mru;

    public LRUList() {

        this.lru = null;
        this.mru = null;
    }

    public void setMRU(Payload p) {

        if (lru == p) {

            // it's LRU

            // if it's also the MRU, this is an edge case we eliminate quickly

            if (mru == p) {

                // nothing to do
                return;
            }

            // it's LRU but not MRU, there's a payload after it in list

            p.next.prev = null;
            lru = p.next;
            p.next = null;
            mru.next = p;
            p.prev = mru;
            p.next = null;
            mru = p;
        }
        else if (mru == p) {

            // it's MRU, nothing to do
        }
        else if (p.prev == null && p.next == null) {

            // new payload, not in list

            if (mru == null && lru == null) {

                // list empty
                lru = p;
                mru = p;
            }
            else {

                // list has at least one element
                mru.next = p;
                p.prev = mru;
                mru = p;
            }
        }
        else {


            // it's in-between
            p.prev.next = p.next;
            p.next.prev = p.prev;
            p.prev = mru;
            mru.next = p;
            p.next = null;
            mru = p;

        }
    }

    /**
     * May return null if the list is empty
     **/
    public Payload removeLRU() {

        if (lru == null) {

            return null;
        }

        Payload going;

        if (lru.next == null) {

            //
            // last one
            //

            going = lru;

            lru = null;
            mru = null;
        }
        else {

            going = lru;
            lru = going.next;
            going.next.prev = null;
            going.next = null;
        }

        going.next = null;
        going.prev = null;

        return going;
    }
}