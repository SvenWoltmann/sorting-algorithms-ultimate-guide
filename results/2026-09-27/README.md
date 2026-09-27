# Measurements of 27 September 2026

## Selection Sort

The run behind the numbers in the Selection Sort article
(https://www.happycoders.eu/algorithms/selection-sort/). It replaces the run
of 2026-09-26 in `../2026-09-26/`, which was disturbed from its eighth
iteration on by other processes on the machine (two more JVMs,
`mediaanalysisd`); this one ran on a quiet machine, every measurement on
C2-compiled code (JIT log in the website repository next to the ladder).
The comparison with Insertion Sort in the article uses the Insertion Sort
run of 2026-09-26, same machine, same JDK.

| | 2020-05-30 | 2026-09-27 |
| --- | --- | --- |
| Machine | Dell XPS 15 9570, Intel Core i7-8750H, 6 cores, x86 | Apple M5 Pro, 18 cores, arm64, macOS |
| JDK | 14 | 27 (GA build of 2026-09-15) |
| Program | `UltimateTest`, `SelectionSort` block of the run | size ladder for `SelectionSort` only |
| Runs | 2 warmups + 50 iterations | 2 warmups + 10 iterations |

Same ladder program as for Insertion Sort in `../2026-09-26/`; all three
input orders stop at 2^19, the largest size the article prints.

| File | Content |
| --- | --- |
| `selection-sort-quiet-ladder.tsv` | one line per measurement: algorithm, input order, size, nanoseconds |
| `selection-sort-quiet-ladder.log` | the same run as printed while it ran (per size, in milliseconds) |
| `selection-sort-quiet-run-metadata.txt` | date, machine, JDK, commit of this repository, iteration counts |

## Radix Sort

The four runs behind the numbers in the Radix Sort article
(https://www.happycoders.eu/algorithms/radix-sort/). They replace the four
logs of 2022 in `../2022-07-13/`, one run per log. The night runs of the same
series (not copied here) were slowed down by a macOS background task
(`dasd`) that started at 0:01 - in `radix`, the medians of recursive MSD Radix
Sort with base 1000 were up to 15 % higher than in the repeat -, so all four
were repeated on the quiet machine in the morning; the `-quiet` in the file
names marks the repeats.

| | 2022-07-13 | 2026-09-27 |
| --- | --- | --- |
| Machine | 6-core Intel Core i7, x86 | Apple M5 Pro, 18 cores, arm64, macOS |
| JDK | 18 | 27 (GA build of 2026-09-15) |
| Size ladders | `UltimateTest`, 40 iterations (parallel variants: 20) | size ladder for the algorithms of each 2022 log, 2 warmups + 10 iterations |
| Base comparison | `CompareRadixSorts`, stopped after 70 iterations | `RadixBaseComparison`, 3 warmups + 10 iterations |

| Run | 2022 log | Content |
| --- | --- | --- |
| `radix-quiet` | `RadixSort_UltimateTest.txt` | 8 variants (dynamic lists, arrays, Counting Sort with bases 10 and 100, recursive MSD with 10 and 1000), random, ascending and descending input up to 2^26 |
| `radix-parallel-quiet` | `RadixSort_UltimateTest_parallel.txt` | LSD and MSD Radix Sort with arrays, sequential and parallel, random input up to 2^26 |
| `radix-vs-quicksort-quiet` | `RadixSort_UltimateTest_Quicksort_ArraysSort.txt` | `Arrays.sort()` and dual-pivot Quicksort up to 2^28, Radix Sort with base 256 up to 2^29, random input |
| `radix-bases-quiet` | `RadixSort_VariousAlgorithmsAndBases.txt` | the 108 algorithm/base combinations of `CompareRadixSorts` at 5,555,555 random elements |

The size ladders measure like `UltimateTest` - `System.gc()` before each sort,
the 20-second rule - and stop each algorithm at the largest size its 2022 log
reached. `RadixBaseComparison` measures like `CompareRadixSorts` - one random
array per iteration, each algorithm sorts a copy, in a freshly shuffled order -
with a fixed number of iterations: at about 100 seconds per iteration in 2022,
the 15 warmups and 100 iterations of `CompareRadixSorts` take over three
hours. Both programs live in the website repository next to the evaluation
scripts.

| File | Content |
| --- | --- |
| `radix*-quiet.tsv` | one line per measurement: algorithm, input order (not in `radix-bases-quiet`), size, nanoseconds |
| `radix*-quiet.log` | the same run as printed while it ran (per sort, in milliseconds) |
| `radix*-quiet-run-metadata.txt` | date, machine, JDK, commit of this repository, program, iteration counts |

## Mergesort

The run behind the numbers in the Mergesort article
(https://www.happycoders.eu/algorithms/merge-sort/): the runtime table, the
Mergesort runtime chart and the Mergesort-vs-Quicksort chart. It replaces
`Test_Results_Mergesort.txt` in `../2020-05-30/` and, for the Quicksort side
of the comparison, the `QuicksortVariant1(pivot: MIDDLE)` lines of
`../2020-07-18/UltimateTest_Quicksort.log`; the article keeps the 2020 machine
and the 2020 ratios in its info box and in the comparison paragraph. The same
run feeds the Heapsort-vs-Quicksort-vs-Mergesort chart of the Heapsort
article (see below).

| | 2020-05-30 / 2020-07-18 | 2026-09-27 |
| --- | --- | --- |
| Machine | Dell XPS 15 9570, Intel Core i7-8750H, 6 cores, x86 | Apple M5 Pro, 18 cores, arm64, macOS |
| JDK | 14 | 27 (GA build of 2026-09-15) |
| Program | `UltimateTest`, `MergeSort` block and `QuicksortVariant1(pivot: MIDDLE)` block of the runs | size ladder for `MergeSort` and `QuicksortVariant1(pivot: MIDDLE)` |
| Runs | 2 warmups + 50 iterations | 2 warmups + 10 iterations |

The ladder measures like `UltimateTest` - the same three input orders,
`System.gc()` before each sort, the 20-second rule - and stops Mergesort at
2^28 for every input order and Quicksort at 2^28 for random and 2^29 for
presorted input, the sizes the 2020 runs reached. It started at 07:03 local
time on a quiet machine; a first run of the same profile on 2026-09-26 at
00:02 local time overlapped the `dasd` background task and is not kept here
(Mergesort within 1 %, Quicksort on presorted input up to 10 % slower).

| File | Content |
| --- | --- |
| `mergesort-vs-quicksort-quiet.tsv` | one line per measurement: algorithm, input order, size, nanoseconds |
| `mergesort-vs-quicksort-quiet.log` | the same run as printed while it ran (per sort, in milliseconds) |
| `mergesort-vs-quicksort-quiet-run-metadata.txt` | date, machine, JDK, commit of this repository, program, iteration counts |

## Heapsort

The three runs behind the numbers in the Heapsort article
(https://www.happycoders.eu/algorithms/heapsort/). They replace
`UltimateTest_Heapsort.log` in `../2020-08-13/`; the article keeps the 2020
numbers beside the new ones where they differ.

| | 2020-08-13 | 2026-09-26/27 |
| --- | --- | --- |
| Machine | Dell XPS 15 9570, Intel Core i7-8750H, 6 cores, x86 | Apple M5 Pro, 18 cores, arm64, macOS |
| JDK | 14 | 27 (GA build of 2026-09-15) |
| Program | `UltimateTest`, Heapsort block of the run | size ladder for the algorithms of the 2020 log, and for Mergesort and Quicksort |
| Runs | 2 warmups + 50 iterations | 2 warmups + 10 iterations |

| Run | Content |
| --- | --- |
| `heapsort` | Heapsort and Bottom-Up Heapsort, random input up to 2^26, ascending and descending input up to 2^28; started on 2026-09-26 at 23:23 local time, before the `dasd` task |
| `heapsort-slow-comparisons-quiet-isb4` | `HeapsortSlowComparisons` and `BottomUpHeapsortSlowComparisons`, random input up to 2^23, with `-XX:+UnlockDiagnosticVMOptions -XX:OnSpinWaitInst=isb -XX:OnSpinWaitInstCount=4` |
| `mergesort-vs-quicksort-quiet` | `MergeSort` and `QuicksortVariant1` with the pivot in the middle, as in 2020, random, ascending and descending input up to 2^28 (Quicksort presorted: 2^29) - for the comparison with Heapsort |
| `i7-12700h/heapsort-slow-comparisons` | the slow-comparison variants as above, on a Dell XPS 17 (Intel Core i7-12700H, x86, WSL2) with JDK 27 and without further JVM options |

The slow-comparison variants delay each comparison with
`Thread.onSpinWait()`. On x86, HotSpot compiles that to `PAUSE`, the delay
of the 2020 run. On arm64, JDK 27 compiles it to one `YIELD` by default,
which costs next to nothing on the M5 Pro: in `heapsort.tsv`, the two
slow-comparison variants run as fast as the plain ones, and their lines are
not used. The `-isb4` run makes each call four `ISB` instructions instead;
the i7-12700H run is the cross-check with `PAUSE`. Both give Bottom-Up
Heapsort the same lead as 2020 - 1.74 to 1.79 (M5 Pro) and 1.77 to 1.82
(i7-12700H) times as fast from 2^14 to 2^22, against 1.76 to 1.80 in 2020.

The size ladders measure like `UltimateTest` - `System.gc()` before each sort,
the 20-second rule. The program lives in the website repository next to the
evaluation scripts.

| File | Content |
| --- | --- |
| `heapsort*.tsv`, `mergesort-vs-quicksort-quiet.tsv` | one line per measurement: algorithm, input order, size, nanoseconds |
| `heapsort*.log`, `mergesort-vs-quicksort-quiet.log` | the same run as printed while it ran (per sort, in milliseconds) |
| `*-run-metadata.txt` | date, machine, JDK, commit of this repository, program, iteration counts, JVM options where set |

### Evening runs: base 4096 and two control runs

The article compares Quicksort with Radix Sort in base 4096, the sweet spot of
`radix-bases-quiet`, and names two causes the morning runs alone do not show.
These runs are the evidence: `radix-4096-vs-256` and `radix-dynamic-lists-gc`
ran on AC power between 19:26 and 19:36, `radix-256-gc-region32m` at 13:25 on
battery.

| Run | Content |
| --- | --- |
| `radix-4096-vs-256` | Radix Sort with arrays, base 4096 and base 256, random input up to 2^29. Base 256 is the anchor to `radix-vs-quicksort-quiet`: it matches the morning within 0.0 to 3.9 % from 2^15 to 2^28, so the Quicksort and `Arrays.sort()` numbers of the morning stand beside base 4096 in one chart. |
| `radix-256-gc-region32m` | Radix Sort with base 256 alone, run with `-XX:G1HeapRegionSize=32m` and a GC log. With the default 8 MiB regions, about half the buckets are G1 humongous objects from 2^28 on, and the sort takes 2.9 times as long as at 2^27; with 32 MiB regions, 2.2 times. |
| `radix-dynamic-lists-gc` | Radix Sort with dynamic lists (base 10) alone, three input orders up to 2^26, with a GC log. GC pauses take 1.9 of the 4.4 seconds at 2^26 with random input; without them the runtime grows linearly. |

XProtect remediation (macOS) used most of a core from 19:30:46 to 19:32:24,
during iteration 10 of `radix-4096-vs-256` and iterations 1 to 3 of
`radix-dynamic-lists-gc`; the medians are over all ten iterations. The GC log
of the dynamic-lists run is compressed (`.log.gz`, 10.8 MB unpacked).

Further diagnostics of the base-256 jump at 2^28 - phase timing, CPU counters,
staggered bucket starts - stay in the website repository; they narrow the
cause down to L1D load misses while writing into humongous buckets, but not
further.
