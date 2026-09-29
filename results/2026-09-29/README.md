# Quicksort measurements, 29 September 2026

The run behind the numbers in the Quicksort article
(https://www.happycoders.eu/algorithms/quicksort/), on JDK 27 (GA build of
2026-09-15). It replaces the JDK 26 run of 2026-09-02 in `../2026-09-02/`;
the 2020 series in `../2020-07-18/` is still there for the comparison across
six years and a change of architecture. Against JDK 26, the ladder medians are
1 to 5 % lower at every size; the ranking of the pivot strategies is unchanged.

| | 2026-09-02 | 2026-09-29 |
| --- | --- | --- |
| Machine | Apple M5 Pro, 18 cores, arm64, macOS | the same, AC power |
| JDK | 26.0.1 | 27 (GA build of 2026-09-15) |
| This repository | | commit `8839949` |

Two parts, both on 2026-09-29: the series (00:29 to 02:01, everything in one
JVM per program) and the final comparison of the five algorithms in the
article's last chart (09:18 to 09:52, one JVM per algorithm).

## Files

| File | Program | Runs |
| --- | --- | --- |
| `compare-quicksorts.log`, `-run2`, `-run3` | `CompareQuicksorts` | 15 warmups + 50 iterations, three times |
| `compare-improved-quicksort.log` | `CompareImprovedQuicksort` | 15 warmups + 50 iterations |
| `compare-improved-dual-pivot-quicksort.log` | `CompareImprovedDualPivotQuicksort` | 15 warmups + 50 iterations |
| `quicksort-ladder.tsv`, `.log` | size ladder of the three pivot strategies, Dual-Pivot Quicksort, `Arrays.sort()` and the two Insertion Sort combinations (thresholds 48 and 64, see below), one TSV line per measurement | 2 warmups + 10 iterations, up to 2^28 elements |
| `quicksort-equal-elements.tsv`, `.log` | `QuicksortSimple` on arrays of equal elements | 3 warmups + 20 iterations |
| `run-metadata.txt` | date, machine, JDK, commit, iteration counts of the series | |
| `quicksort-series-2026-09-29-load-samples.log` | `top` every 30 seconds while the series ran: load, idle, the three busiest processes | |
| `quicksort-ladder-isolated.tsv`, `.log` | the final comparison: `QuicksortVariant1(MIDDLE)`, `QuicksortImproved(64)`, `DualPivotQuicksort(THIRDS)`, `DualPivotQuicksortImproved(48)` and `Arrays.sort()`, each in a JVM of its own | 2 warmups + 10 iterations each, up to 2^28 elements |
| `quicksort-ladder-isolated-run-metadata.txt` | date, machine, JDK, commit, thresholds of the final comparison | |
| `quicksort-ladder-isolated-load-samples.log` | `top` every 30 seconds while the final comparison ran | |

`UltimateTest` was not used for the size ladders: it runs every algorithm of
the whole series and never terminates. The ladder program measures the same
way - same sizes, same three input orders, warmup passes first - for the
algorithms this article discusses and for a fixed number of iterations. It
lives in the website repository next to the evaluation script.

## Why the final comparison runs one JVM per algorithm

In the shared JVM of the series ladder, `DualPivotQuicksortImproved` (there
with threshold 64) took 19.5 s at 2^28 - more than `DualPivotQuicksort` without
Insertion Sort in the same JVM (17.1 s) and far more than in a JVM of its own
(16.4 s, threshold 48; the sweep puts 48 and 64 within 0.4 % of each other).
The other three algorithms land within 1 % of their shared-ladder values. So
the shared JVM changed the result more than the differences the chart ranks,
and the five lines of the article's last chart all come from
`quicksort-ladder-isolated.tsv`. The series ladder stays the source of the
per-strategy tables.

At 2^28 unsorted, against regular Quicksort (MIDDLE, 17,617 ms): Quicksort
with Insertion Sort -9.1 %, Dual-Pivot Quicksort -2.9 %, Dual-Pivot Quicksort
with Insertion Sort -7.1 %, `Arrays.sort()` -16.0 %. Quicksort with Insertion
Sort is ahead of the Dual-Pivot combination at every size from 2^10 to 2^28
(1.0 to 7.9 %), as in the two threshold sweeps (260.4 vs. 268.0 ms).

## Why CompareQuicksorts ran three times

The ranking of the three algorithm variants is not reproducible across JVM
runs: `QuicksortVariant3(RIGHT)` comes in 4th, 1st and 2nd, and a single
variant varies by up to 6 % between two runs, while the variants differ by
1-2 %. The extreme case is `QuicksortSimple`: 373 ms median in run 1 against
304 and 301 ms in runs 2 and 3 - not as an outlier, but in every one of the 50
iterations of run 1 (366 to 383 ms), with the machine at 92 % idle. What
holds in all three runs: RIGHT beats MIDDLE for every variant, and MEDIAN3 and
RANDOM trail both.

## Caveats

- Spotlight (`mds_stores`) indexed twice during the series ladder: 00:31 to
  00:34 at about one core and 00:58 to 00:59 at up to 3.8 cores, during
  iteration 5. Without iteration 5, no median from 2^16 on moves by more than
  1.43 %; below that, single points under a millisecond move by up to 8.8 %.
- During the final comparison, background processes used up to 1.2 cores
  while the MIDDLE and Quicksort-with-Insertion-Sort JVMs ran (lowest idle
  78 %). From 2^20 on, dropping any single iteration moves no median by more
  than 2.3 %.
- `run-metadata.txt` announces a `quicksort-ladder-tuned.tsv`; that follow-up
  did not run (a bug in the script that reads the sweep optima, fixed
  afterwards). The final comparison in its own JVMs replaced it.
