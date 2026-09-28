# Measurements of 28 September 2026

## Parallel Radix Sort: where the cores go

The control run behind the explanation of the parallel speedup in the Radix
Sort article (https://www.happycoders.eu/algorithms/radix-sort/): on the 18
cores of the M5 Pro, the parallel variants are 5.9 (LSD) and 6.3 (MSD) times as
fast as the sequential ones at 2^26 (`../2026-09-27/radix-parallel-quiet*`).
The article of 2022 blamed code that cannot run in parallel and memory traffic,
without measuring either.

| | 2026-09-28 |
| --- | --- |
| Machine | Apple M5 Pro, 18 cores (6 "Super", 12 "Performance"), arm64, macOS, AC power |
| JDK | 27 (GA build of 2026-09-15) |
| Program | `RadixParallelPhases`: instrumented copies of `ParallelRadixSortWithArrays` and `ParallelRecursiveMsdRadixSortWithArrays`, base 10, every phase timed |
| Runs | 2 warmups at 2^22 + 10 iterations at 2^19 and 2^26, once per `-XX:ActiveProcessorCount` of 1, 2, 4, 6, 8, 12 and 18 |

At 18 cores and 2^26 (medians):

- **MSD:** the top level - counting, creating and distributing by the most
  significant digit, and the final collect - runs in the calling thread:
  183.9 of 367.2 ms. Even an infinitely fast recursion could at most halve the
  runtime.
- **LSD:** the sequential parts (negative check, maximum, bucket allocation,
  prefix sums) take 24.7 of 193.6 ms, 13 %. The parallel phases themselves do
  not scale linearly: counting is 10.7 times and distributing 7.8 times as fast
  as with one segment.

Two caveats. `ActiveProcessorCount` 1 and 2 both give a common fork-join pool
of parallelism 1, i.e. the calling thread plus one worker, so the runs with
"1 processor" are single-threaded only where the work is split into segments
(LSD count and distribute), not for MSD's recursion or LSD's collect. The
`copy` lines, meant as a bandwidth reference, write into a freshly allocated
array and pay its first-touch page faults; they are not used.

| File | Content |
| --- | --- |
| `radix-parallel-phases-p<n>.tsv` | one line per phase and sort: processors, algorithm (`lsd`, `msd`, `copy`), size, iteration, phase, nanoseconds |
| `radix-parallel-phases-p<n>.log` | the run as printed while it ran (per sort, in milliseconds) |
| `radix-parallel-phases-p<n>-run-metadata.txt` | date, machine, JDK, commit of this repository, JVM options, power source |
