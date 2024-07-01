/*
 * Dennis Lang
 * Copyright (c) LanDen Labs 2022.
 */
package com.all.recyclercache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Memory tracking - Count active object instances.
 * <p>
 * https://www.beyondjava.net/how-to-count-java-objects-in-memory
 */
public class MemRefs extends WeakReference<Object> {

   public static ReferenceQueue<Object> queueOfDead = new ReferenceQueue<>();
   public static HashMap<Long, MemRefs> keepGcFromRemovingUs = new HashMap<>();

   static {
      new BuryDeadThread().start();
   }

   public final static AtomicInteger objectsCreated  = new AtomicInteger(0);
   public final static AtomicInteger activeObjects  = new AtomicInteger(0);
   private final long currentQueue;

   public MemRefs(Object object) {
      super(object, queueOfDead);
      currentQueue = objectsCreated.getAndIncrement();
      keepGcFromRemovingUs.put(currentQueue, this);
      activeObjects.getAndIncrement();
   }

   public void bury() {
      activeObjects.decrementAndGet();
      keepGcFromRemovingUs.remove(currentQueue);
   }

   static class  BuryDeadThread extends Thread {
      public void run() {
         System.out.println("BuryDeadThread started");
         while (true) {
            MemRefs ref;
            try {
               ref = (MemRefs) MemRefs.queueOfDead.remove();
               ref.bury();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   }
}