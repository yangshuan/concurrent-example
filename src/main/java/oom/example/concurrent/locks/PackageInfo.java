/**
 * Blocking Lock: synchronized, ReentrantLock, wait
 * Mutex Lock: sleeping/waiting lock, similar with blocking Lock,
 *             but in my opinion ReentrantLock.tryLock() is another kind of implementation of mutex lock.
 * Optimistic Lock: e.g. CAS
 * Pessimistic Lock: Synchronized, ReentrantLock
 * Biased Lock: An object is "biased" toward the thread which first acquires its monitor via a monitorenter bytecode
 *              or synchronized method invocation; subsequent monitor-related operations can be performed
 *              by that thread without using atomic operations resulting in much better performance,
 *              particularly on multiprocessor machines.
 * Object Lock: synchronized, ReentrantLock
 * Thread Lock: Blocking Lock between threads
 * Lock eliminate: String concatString(String str1, String str2, String str3) {
 *                      return str1 + str2 + str3;
 *                 }
 * Lock expansion: String concatString(String str1, String str2, String str3) {
 *                      StringBuilder builder = new StringBuilder();
 *                      builder.append(str1);
 *                      builder.append(str2);
 *                      builder.append(str3);
 *                      return builder.toString();
 *                 }
 */