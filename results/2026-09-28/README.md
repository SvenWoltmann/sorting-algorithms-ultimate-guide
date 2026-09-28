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

## Heapsort vs. Bottom-Up Heapsort: CPU counters

The run behind the explanation of the gap between the two variants in the
Heapsort article (https://www.happycoders.eu/algorithms/heapsort/). On the
size ladder of 2026-09-26 (`../2026-09-27/heapsort*`), Bottom-Up Heapsort is
as fast as Heapsort up to 2^16 random elements, 2.4 times as slow at 2^26, and
2.0 to 2.2 times as slow on presorted input at 2^26 and 2^28 - although it needs fewer
comparisons, reads and writes.

| | 2026-09-28 |
| --- | --- |
| Machine | Apple M5 Pro, 18 cores, arm64, macOS, AC power |
| JDK | 27 (GA build of 2026-09-15) |
| Program | `HeapsortCounters`: `Heapsort` and `BottomUpHeapsort` of this repository (commit `d3a8c1c`), unchanged, with CPU performance counters read through kperf |
| Runs | 2 warmups up to 2^20 + 5 iterations, 2^10 to 2^26 in steps of 2^2, random, ascending and descending input |

Arrays with fewer than 2^22 elements are sorted in batches, so every
measurement covers 2^22 elements (4,096 arrays of 2^10, 1,024 of 2^12, and so
on); from 2^22 on, one array per measurement. The counter values are deltas for
the whole batch; per element means divided by size × arrays.

At 2^26 random, per element (medians): 873 cycles for Heapsort, 2,098 for
Bottom-Up Heapsort. Both miss L1D about equally often (26.3 and 23.6 loads),
but the load/store unit of Bottom-Up Heapsort waits 1,528 cycles on old L1D
misses, that of Heapsort 226 - the difference covers the whole gap. Heapsort
mispredicts 13.5 conditional branches, Bottom-Up Heapsort 0.8. On presorted
input, neither waits on memory (2^26 ascending: 1.0 and 0.8 cycles), and
Bottom-Up Heapsort needs 15.2 to 15.9 cycles per tree level from 2^14 on.

One caveat: below 2^14 on presorted input, the batch sorts 4,096 identical
arrays in a row, and the branch predictor learns them (0.03 mispredictions per
element for Heapsort at 2^10 ascending). Those sizes do not carry over to the
single-array ladder. From 2^16 on, the batch runs 1.5 to 6.4 % faster than
the ladder, and the ratio between the variants agrees within 4 %.

| File | Content |
| --- | --- |
| `heapsort-counters.tsv` | one line per measurement: algorithm, input order, size, arrays in the batch, iteration, nanoseconds, then the deltas of FIXED_CYCLES, FIXED_INSTRUCTIONS, INST_BRANCH_COND, BRANCH_COND_MISPRED_NONSPEC, INST_INT_LD, L1D_CACHE_MISS_LD_NONSPEC, LDST_UNIT_WAITING_OLD_L1D_CACHE_MISS and L1D_TLB_MISS_NONSPEC |
| `heapsort-counters.log` | the same run as printed while it ran |
| `heapsort-counters-run-metadata.txt` | date, machine, JDK, commit of this repository, program, iteration counts |
| `heapsort-jit-assembly/` | the final C2 compilations of `Heapsort.heapify()`, `BottomUpHeapsort.findLeaf()` and `Heapsort.sort()` with both variants inlined, printed with `-XX:+PrintAssembly` (capstone hsdis) from the same program, run once without counters |

The machine code shows why the counters differ: `Heapsort.heapify()` picks the
larger child with a branch (`cmp` / `b.le`), `BottomUpHeapsort.findLeaf()`
with a conditional select (`cmp` / `csel`), followed directly by the `lsl`
that computes the index of the next level - so the next load waits for the
previous one.
