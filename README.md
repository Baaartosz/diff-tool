# diff-tool

### Purpose

A tool for creating diff files from error outputs. Currently, during development at my client we run a lot of
unit and functional tests where error outputs give us 2 log outputs; `expected` and `actual`.

This little tool serves the purpose of minimising the time spent selecting, creating, reformatting these output logs and
lets us focus on debugging faster.

---

_**Disclaimer**: This tool is in no way finished and serves mainly as a learning experience with new libraries and
workflows. There are limitations in its functionality and its possible I may convert this into a private Intellij
plugin._

---

```
 )\ )         (      (
(()/(    (    )\ )   )\ )
 /(_))   )\  (()/(  (()/(
(_))_   ((_)  /(_))  /(_))
 |   \   (_) (_) _| (_) _|
 | |) |  | |  |  _|  |  _|
 |___/   |_|  |_|    |_|  Speeding up debugging.
 
Generates diff files for inspection within intellij from error outputs
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  generate, gen, g  Runs diff tool generation comparing output log file from clipboard for
                      specific tags to find expected and actual results.

  config            Configure diff tool properties for finer tuning
  ```

### Usage

---

Used to create a new diff file set by matching to the following regex groups;

```regex 
"(?s)(?<=expected:<).*?(?=> but)"
```

```regex
"(?s)(?<=was:<).*?(?=> within)"
```

It copies the output from your clipboard and runs through these groups and finds the expected and actual log outputs.

``diff generate <foldername>``  
_Folder creation is optional._

---

Configure where the tool outputs the diff files. Will prompt for configuration if you have not done this.

``diff config -output="<scratchFolderPath>"``  
