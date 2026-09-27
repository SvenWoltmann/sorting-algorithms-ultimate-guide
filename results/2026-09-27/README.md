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
