# Quicksort measurements, 2 September 2026

The re-run behind the numbers in the Quicksort article
(https://www.happycoders.eu/algorithms/quicksort/). The 2020 series in
`../2020-07-18/` is the one it replaces; comparing the two shows how six years
and a change of architecture moved the numbers.

| | 2020-07-18 | 2026-09-02 |
| --- | --- | --- |
| Machine | Dell XPS 15 9570, Intel Core i7-8750H, 6 cores, x86, Windows | Apple M5 Pro, 18 cores, arm64, macOS |
| JDK | (of its time) | 26.0.1 |

## Files

| File | Program | Runs |
| --- | --- | --- |
| `compare-quicksorts.log`, `-run2`, `-run3` | `CompareQuicksorts` | 15 warmups + 50 iterations, three times |
| `compare-improved-quicksort.log` | `CompareImprovedQuicksort` | 15 warmups + 50 iterations |
| `compare-improved-dual-pivot-quicksort.log` | `CompareImprovedDualPivotQuicksort` | 15 warmups + 50 iterations |
| `quicksort-ladder.tsv` | size ladder, one TSV line per measurement | 2 warmups + 10 iterations, up to 2^28 elements |
| `quicksort-ladder-tuned.tsv` | the same ladder for the tuned insertion-sort thresholds (96 / 128) | 2 warmups + 10 iterations |
| `quicksort-equal-elements.tsv` | `QuicksortSimple` on arrays of equal elements | 3 warmups + 20 iterations |

`UltimateTest` was not used for the size ladder: it runs every algorithm of the
whole series and never terminates. The ladder program measures the same way -
same sizes, same three input orders, warmup passes first - for the algorithms
this article discusses and for a fixed number of iterations. It lives in the
website repository next to the evaluation script.

## Why CompareQuicksorts ran three times

The ranking of the three algorithm variants is not reproducible across JVM
runs: a single algorithm varies by up to 9.8 % between two runs, while the
variants differ by 2-4 %. Run 2 puts `QuicksortVariant3(RIGHT)` first, runs 1
and 3 put it fifth. One run of 50 iterations looks convincing and still carries
no statement about a 3 % difference.
