# diff-tool
A tool for creating diff files from error outputs.

```
 )\ )         (      (
(()/(    (    )\ )   )\ )
 /(_))   )\  (()/(  (()/(
(_))_   ((_)  /(_))  /(_))
 |   \   (_) (_) _| (_) _|
 | |) |  | |  |  _|  |  _|
 |___/   |_|  |_|    |_|  Speeding up debugging.
```

``Usage: diff [-hV] [COMMAND]``

```
Generates diff files for inspection within intellij from error outputs
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  generate, gen, g  Runs diff tool generation comparing output log file from clipboard for
                      specific tags to find expected and actual results.

  config            Configure diff tool properties for finer tuning

  cleanup, c        Cleans up diff files inside scratch folder
  ```
