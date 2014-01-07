package org.gololang.microbenchmarks.fibonacci;

import org.gololang.microbenchmarks.support.CodeLoader;
import org.openjdk.jmh.annotations.*;

import java.lang.invoke.MethodHandle;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class FibonacciMicroBenchmark {

  @State(Scope.Thread)
  static public class State40 {
    long n = 40L;
  }

  @State(Scope.Thread)
  static public class State30 {
    long n = 30L;
  }

  @State(Scope.Thread)
  static public class GoloState {
    MethodHandle fib;

    @Setup(Level.Trial)
    public void prepare() {
      fib = new CodeLoader().golo("fibonacci", "fib", 1);
    }
  }

  @GenerateMicroBenchmark
  public long baseline_java_30(State30 state) {
    return JavaRecursiveFibonacci.withPrimitives(state.n);
  }

  @GenerateMicroBenchmark
  public long baseline_java_boxing_30(State30 state) {
    return JavaRecursiveFibonacci.withBoxing(state.n);
  }

  @GenerateMicroBenchmark
  public Object golo_30(State30 state30, GoloState goloState) throws Throwable {
    return goloState.fib.invokeExact((Object) state30.n);
  }

  @GenerateMicroBenchmark
  public long baseline_java_40(State40 state) {
    return JavaRecursiveFibonacci.withPrimitives(state.n);
  }

  @GenerateMicroBenchmark
  public long baseline_java_boxing_40(State40 state) {
    return JavaRecursiveFibonacci.withBoxing(state.n);
  }

  @GenerateMicroBenchmark
  public Object golo_40(State40 state40, GoloState goloState) throws Throwable {
    return goloState.fib.invokeExact((Object) state40.n);
  }
}
