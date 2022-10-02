// Dennis Lang
// Copyright LanDen Labs 2022
package com.all.recyclercache;

import com.all.recyclercache.scroll.ScrollItemHolder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Memory Tracking - Count active object instances.
 *
 * See MemRefs for additional approach to tracking active instance count.
 */
public class MemStats {
   public final static AtomicInteger cntViewAttached  = new AtomicInteger(0);

   // Memory Tracking solution #1
   public static void addViewHolder(ScrollItemHolder viewHolder) {
      new MemRefs(viewHolder);
   }

   // Memory Tracking solution #2
   // Track active instances using constructor and finalize - did not work with java 11+, see MemRefs
   // public final static AtomicInteger cntViewHolder  = new AtomicInteger(0);

   /*
   // Memory Tracking solution #3
   // Count active object instances using  PhantomReference - did not work with java 11+, see MemRefs

   public final static ArrayList< PhantomReference<ScrollItemHolder>> listOfHolderRefs = new ArrayList<>();
   public final static ReferenceQueue<ScrollItemHolder> refQueueHolders = new ReferenceQueue<ScrollItemHolder>();;

   public static void addViewHolder(ScrollItemHolder viewHolder) {
      synchronized (listOfHolderRefs) {
         listOfHolderRefs.add(new PhantomReference<>(viewHolder, refQueueHolders));
         Reference<? extends ScrollItemHolder> refHolder;
         do {
            refHolder = refQueueHolders.poll();
            if (refHolder != null) {
               remove(refHolder.get());
               cntViewHolder.decrementAndGet();
            }
         } while (refHolder != null);
      }
   }

   private static void remove(ScrollItemHolder ref) {
      for (int i = 0; i < listOfHolderRefs.size(); i++) {
         if (listOfHolderRefs.get(i).get() == ref) {
            listOfHolderRefs.remove(i);
            break;
         }
      }
   }
   */
}
