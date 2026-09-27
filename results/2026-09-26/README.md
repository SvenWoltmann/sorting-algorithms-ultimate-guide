# Measurements of 26 September 2026

Three re-runs on the same machine and JDK: Counting Sort, Insertion Sort and
Selection Sort.

## Counting Sort

The re-run behind the numbers in the Counting Sort article
(https://www.happycoders.eu/algorithms/counting-sort/). The 2020 series in
`../2020-08-30/` is the one it replaces; the article prints both, because the
gap between presorted and unsorted input is a cache effect and therefore a
property of the machine.

| | 2020-08-30 | 2026-09-26 |
| --- | --- | --- |
| Machine | Dell XPS 15 9570, Intel Core i7-8750H, 6 cores, x86, Windows | Apple M5 Pro, 18 cores, arm64, macOS |
| JDK | (of its time) | 27 (GA build of 2026-09-15) |
| Program | `UltimateTest`, `CountingSort` block of the run | size ladder for `CountingSort` only |
| Runs | 2 warmups + 50 iterations | 2 warmups + 50 iterations, up to 2^29 elements |

### Files

| File | Content |
| --- | --- |
| `counting-sort-ladder.tsv` | one line per measurement: algorithm, input order, size, nanoseconds |
| `counting-sort-ladder.log` | the same run as printed while it ran (per size, in milliseconds) |
| `counting-sort-run-metadata.txt` | date, machine, JDK, commit of this repository, iteration counts |

`UltimateTest` was not used: it runs every algorithm of the whole series and
never terminates. The ladder program measures the same way - same sizes, same
three input orders (random values from 0 to size - 1, so k = n), `System.gc()`
before each sort, warmup passes first - for `CountingSort` alone and for a
fixed number of iterations. It lives in the website repository next to the
evaluation script.

## Insertion Sort

The re-run behind the numbers in the Insertion Sort article
(https://www.happycoders.eu/algorithms/insertion-sort/). It replaces the
Insertion Sort part of the 2020 series in `../2020-05-30/`; the article keeps
the 2020 machine in its info box.

| | 2020-05-30 | 2026-09-26 |
| --- | --- | --- |
| Machine | Dell XPS 15 9570, Intel Core i7-8750H, 6 cores, x86 | Apple M5 Pro, 18 cores, arm64, macOS |
| JDK | 14 | 27 (GA build of 2026-09-15) |
| Program | `UltimateTest`, `InsertionSort` block of the run | size ladder for `InsertionSort` only |
| Runs | 2 warmups + 50 iterations | 2 warmups + 10 iterations |

The ladder measures like `UltimateTest` - same three input orders,
`System.gc()` before each sort, the 20-second rule - with two bounds that keep
the run plannable: random and descending input stop at 2^19, the largest size
the article prints, and the warm-up passes stop at 2^16 for random and
descending input (2^22 for ascending input).

| File | Content |
| --- | --- |
| `insertion-sort-ladder.tsv` | one line per measurement: algorithm, input order, size, nanoseconds |
| `insertion-sort-ladder.log` | the same run as printed while it ran (per size, in milliseconds) |
| `insertion-sort-run-metadata.txt` | date, machine, JDK, commit of this repository, iteration counts |

## Selection Sort

Superseded by `../2026-09-27/`: this run was disturbed from its eighth
iteration on by other processes on the machine (two more JVMs,
`mediaanalysisd`). It stays as the record it was; the article prints the
quiet run of the next day. The Selection-vs-Insertion comparison in the
article uses the Insertion Sort run above, same machine, same JDK.

| | 2020-05-30 | 2026-09-26 |
| --- | --- | --- |
| Machine | Dell XPS 15 9570, Intel Core i7-8750H, 6 cores, x86 | Apple M5 Pro, 18 cores, arm64, macOS |
| JDK | 14 | 27 (GA build of 2026-09-15) |
| Program | `UltimateTest`, `SelectionSort` block of the run | size ladder for `SelectionSort` only |
| Runs | 2 warmups + 50 iterations | 2 warmups + 10 iterations |

Same ladder program as for Insertion Sort. All three input orders stop at
2^19, the largest size the article prints: for Selection Sort the ascending
order is quadratic too, and the 20-second rule would have let it climb one more
doubling. Iterations 8 to 10 ran while other processes were busy (two more JVMs
and `mediaanalysisd`, see the load samples in the website repository); the
article prints medians, which those iterations do not move.

| File | Content |
| --- | --- |
| `selection-sort-ladder.tsv` | one line per measurement: algorithm, input order, size, nanoseconds |
| `selection-sort-ladder.log` | the same run as printed while it ran (per size, in milliseconds) |
| `selection-sort-run-metadata.txt` | date, machine, JDK, commit of this repository, iteration counts |
