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
