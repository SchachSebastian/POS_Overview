### Summary

- [Runnable](#runnable)
- [Thread](#thread)
  - [start](#start)
  - [join](#join)
  - [sleep](#sleep)
  - [yield](#yield)
  - [interrupt](#interrupt)
  - [isInterrupted](#isinterrupted)
  - [ThreadLocal](#threadlocal)
  - [synchronized](#synchronized)
  - [wait/notify](#waitnotify)
- [ExecutorService](#executorservice)
  - [ThreadPoolExecutor](#threadpoolexecutor)
    - [newFixedThreadPool](#newfixedthreadpool)
    - [newCachedThreadPool](#newcachedthreadpool)
    - [newSingleThreadExecutor](#newsinglethreadexecutor)
    - [newScheduledThreadPool](#newscheduledthreadpool)
  - [Callable](#callable)
  - [Future](#future)
    - [cancel(mayInterruptIfRunning)](#cancelmayinterruptifrunning)
  - [CompletionService](#completionservice)
  - [CompletableFuture](#completablefuture)
    - [thenApply](#thenapply)
    - [thenAccept](#thenaccept)
    - [thenRun](#thenrun)
    - [thenCompose](#thencompose)
    - [thenCombine](#thencombine)
    - [Async variants](#async-variants)

## Runnable

The `Runnable` interface represents a task that can be executed by a thread.

```java
static {
  Runnable task = () -> System.out.println("Hello, World!");
  Thread thread = new Thread(task);
  thread.start();
}
```

## Thread

A thread is a lightweight process that can run concurrently with other threads in the same process.

```java
static {
  Thread thread = new Thread(() -> System.out.println("Hello, World!"));
  thread.start();
}
```

### start

The `start` method starts the thread, and the `run` method is called on the thread.

### join

The `join` method waits for the thread to finish before continuing.

```java
Thread thread = new Thread(() -> System.out.println("Hello, World!"));
thread.start();
try{
  thread.join();
} catch (InterruptedException e){
  e.printStackTrace();
}
```

### sleep

The `sleep` method pauses the current thread for a specified amount of time.

```java
Thread.sleep(1000);
```

### yield

The `yield` method gives up the current thread's turn to run.

```java
Thread thread1 = new Thread(() -> {
  for (int i = 0; i < 10; i++) {
    System.out.println("Thread 1: " + i);
    Thread.yield();
  }
});
Thread thread2 = new Thread(() -> {
  for (int i = 0; i < 10; i++) {
    System.out.println("Thread 2: " + i);
    Thread.yield();
  }
});

thread1.start();
thread2.start();
```

### interrupt

The `interrupt` method interrupts the thread, causing it to throw an `InterruptedException` if it is blocked in a
method that can throw an `InterruptedException`.
It also sets the interrupted flag to `true`. see [isInterrupted](#isinterrupted)

```java
Thread thread = new Thread(() -> {
  try {
    Thread.sleep(10000);
  } catch (InterruptedException e) {
    System.out.println("Thread interrupted");
  }
});

thread.start();
thread.interrupt();
```

### isInterrupted

The `isInterrupted` method checks if the thread has been interrupted.

```java
Thread thread = new Thread(() -> {
  while (!Thread.currentThread().isInterrupted()) {
    System.out.println("Running...");
  }
});

thread.start();
Thread.sleep(1000);
thread.interrupt();
```

### ThreadLocal

The `ThreadLocal` class provides thread-local variables. Each thread accessing the variable has its own, independently
initialized copy of the variable.

```java
static {
  ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 42);
  System.out.println(threadLocal.get());
}
```

### synchronized

The `synchronized` keyword is used to create a critical section that can be accessed by only one thread at a time.

```java
static {
  Object lock = new Object();
  synchronized (lock) {
    System.out.println("Hello, World!");
  }
}
```

### wait/notify

The `wait` method causes the current thread to wait until another thread calls the `notify` or `notifyAll` method on the
same object.

```java
static {
  Object lock = new Object();
  Thread thread1 = new Thread(() -> {
    synchronized (lock) {
      try {
        lock.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Thread 1: Hello, World!");
    }
  });
  Thread thread2 = new Thread(() -> {
    synchronized (lock) {
      System.out.println("Thread 2: Hello, World!");
      lock.notify();
    }
  });
  thread1.start();
  thread2.start();
}
// Output:
// Thread 2: Hello, World!
// Thread 1: Hello, World!
```

## ExecutorService

The `ExecutorService` interface provides a way to manage and control the execution of tasks in a multithreaded
environment.
With ThreadPoolExecutor, you can create a pool of threads and submit tasks to be executed by the threads in the pool.

```java
import java.util.concurrent.Executors;

static {
  ExecutorService executor = Executors.newCachedThreadPool();
  executor.submit(() -> System.out.println("Task 1"));
  executor.submit(() -> System.out.println("Task 2"));
  executor.shutdown();
}
```

### ThreadPoolExecutor

There are several ways to create an `ExecutorService`:
Regardless of the method used to create the `ExecutorService`, the `shutdown` method should be called to shut down
the `ExecutorService` when it is no longer needed.
Also, you can define the name of the threads in the pool by providing a `ThreadFactory` to the `newFixedThreadPool`
method.

```java
static {
  ExecutorService executor = Executors.newFixedThreadPool(2, run -> {
    Thread thread = new Thread(run);
    thread.setName("MyThread");
    return thread;
  });
  executor.submit(() -> System.out.println(Thread.currentThread().getName()));
  executor.shutdown();
}
```

#### newFixedThreadPool

Creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue.

```java
import java.util.concurrent.Executors;

static {
  ExecutorService executor = Executors.newFixedThreadPool(2);
  executor.submit(() -> System.out.println("Task 1"));
  executor.submit(() -> System.out.println("Task 2"));
  executor.shutdown();
}
```

#### newCachedThreadPool

Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are
available.

```java
import java.util.concurrent.Executors;

static {
  ExecutorService executor = Executors.newCachedThreadPool();
  executor.submit(() -> System.out.println("Task 1"));
  executor.submit(() -> System.out.println("Task 2"));
  executor.shutdown();
}
```

#### newSingleThreadExecutor

Creates a thread pool that uses a single worker thread operating off an unbounded queue.

```java
import java.util.concurrent.Executors;

static {
  ExecutorService executor = Executors.newSingleThreadExecutor();
  executor.submit(() -> System.out.println("Task 1"));
  executor.submit(() -> System.out.println("Task 2"));
  executor.shutdown();
}
```

Note that Task 2 will only be executed after Task 1 has completed, since the thread pool has only one worker thread.

#### newScheduledThreadPool

Creates a thread pool that can schedule commands to run after a given delay, or to execute periodically.

```java
import java.util.concurrent.ScheduledExecutorService;

static {
  ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
  executor.schedule(() -> System.out.println("Task 1"), 1, TimeUnit.SECONDS);
  executor.schedule(() -> System.out.println("Task 2"), 2, TimeUnit.SECONDS);
  executor.shutdown();
}
```

## Callable

The `Callable` interface is similar to `Runnable`, but it can return a result and throw a checked exception.

```java
static {
  ExecutorService executor = Executors.newCachedThreadPool();
  Callable<Integer> task = () -> {
    System.out.println("Task 1");
    return 42;
  };
  Future<Integer> future = executor.submit(task);
  try {
    Integer result = future.get();
    System.out.println("Result: " + result);
  } catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
  }
  executor.shutdown();
}
```

## Future

The `Future` interface represents the result of an asynchronous computation. It provides methods to check if the
computation is complete, retrieve the result, and cancel the computation.

```java
static {
  ExecutorService executor = Executors.newCachedThreadPool();
  Callable<Integer> task = () -> {
    System.out.println("Task 1");
    return 42;
  };
  Future<Integer> future = executor.submit(task);
  try {
    while (!future.isDone()) {
      System.out.println("Waiting for result...");
      Thread.sleep(1000);
    }
    Integer result = future.get();
    System.out.println("Result: " + result);
  } catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
  }
  executor.shutdown();
}
```

### cancel(mayInterruptIfRunning)

The `cancel` method cancels the computation if it has not already completed and wasn't started yet.
If the computation is already running, the `mayInterruptIfRunning` parameter determines whether the thread executing the
computation should be interrupted.

## CompletionService

The `CompletionService` interface provides a way to retrieve the results of asynchronous computations in the order they
complete.

```java
import java.util.concurrent.ExecutorService;

static {
  ExecutorService executor = Executors.newCachedThreadPool();
  CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
  completionService.submit(() -> {
    System.out.println("Task 1");
    return 42;
  });
  completionService.submit(() -> {
    System.out.println("Task 2");
    return 43;
  });
  try {
    for (int i = 0; i < 2; i++) {
      Integer result = completionService.take().get();
      System.out.println("Result: " + result);
    }
  } catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
  }
  executor.shutdown();
}
```

## CompletableFuture

The `CompletableFuture` class provides a way to perform asynchronous computations and handle their results using a
fluent API.
May be explicitly completed by a call to one of the `complete` methods or by a call to `cancel`.

```java
import java.util.concurrent.CompletableFuture;

static {
  CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
    System.out.println("Task 1");
    return 42;
  });
  future.thenAccept(result -> System.out.println("Result: " + result));
}
```

Besides `CompletableFuture.supplyAsync` there are other methods to create a `CompletableFuture`:

- `CompletableFuture.runAsync(Runnable runnable)`
- `CompletableFuture.completedFuture(T value)`
- `CompletableFuture.failedFuture(Throwable ex)`
- `CompletableFuture.delayedExecutor(long delay, TimeUnit unit)`

```java
import java.util.concurrent.CompletableFuture;

static {
  CompletableFuture<Integer> future = new CompletableFuture<>();
  executor.submit(() -> {
    System.out.println("Task 1");
    future.complete(42);
  });
}
```

### thenApply

The `thenApply` method applies a function to the result of the computation and returns a new `CompletableFuture` with
the result of the function.

```java
import java.util.concurrent.CompletableFuture;

static {
  CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
    System.out.println("Task 1");
    return 42;
  });
  future.thenApply(result -> result * 2)
        .thenAccept(result -> System.out.println("Result: " + result));
}
```

### thenAccept

The `thenAccept` method accepts the result of the computation and returns a new `CompletableFuture` without a result.

```java
import java.util.concurrent.CompletableFuture;

static {
  CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
    System.out.println("Task 1");
    return 42;
  });
  future.thenAccept(result -> System.out.println("Result: " + result));
}
```

### thenRun

The `thenRun` method runs a `Runnable` after the computation completes and returns a new `CompletableFuture` without a
result.

```java
import java.util.concurrent.CompletableFuture;

static {
  CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
    System.out.println("Task 1");
    return 42;
  });
  future.thenRun(() -> System.out.println("Task 2"));
}
```

### thenCompose

The `thenCompose` method applies a function to the result of the computation and returns a new `CompletableFuture`
with the result of the CompletableFuture returned by the function. It is similar to `thenApply`, but the function
returns a `CompletableFuture`. Therefor it is useful when you want to chain multiple asynchronous computations.

[thenApply vs thenCompose](https://stackoverflow.com/questions/43019126/completablefuture-thenapply-vs-thencompose)

```java
import java.util.concurrent.CompletableFuture;

static {
  CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
    System.out.println("Task 1");
    return 42;
  });
  future.thenCompose(result -> CompletableFuture.supplyAsync(() -> result * 2))
        .thenAccept(result -> System.out.println("Result: " + result));
}
```

### thenCombine

The `thenCombine` method combines the results of two computations and returns a new `CompletableFuture` with the
combined result.

```java
import java.util.concurrent.CompletableFuture;

static {
  CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
    System.out.println("Task 1");
    return 42;
  });
  CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
    System.out.println("Task 2");
    return 43;
  });
  future1.thenCombine(future2, (result1, result2) -> result1 + result2)
         .thenAccept(result -> System.out.println("Result: " + result));
}
```

### Async variants

The `thenApplyAsync`, `thenAcceptAsync`, `thenRunAsync`, `thenComposeAsync`, and `thenCombineAsync` methods are similar
to their non-async counterparts, but they run the function in a separate thread.

If you use the non async methods, the function will run in the same thread as it was called or in the thread of the
previous stage, if the Stage wasn't completed yet.

If you use the async methods, the function will run in a separate thread of the `ForkJoinPool.commonPool()` or
given `Executor`.

https://www.baeldung.com/java-completablefuture-threadpool

https://4comprehension.com/completablefuture-the-difference-between-thenapply-thenapplyasync/
