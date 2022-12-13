from string import Template
import sys
from pathlib import Path

max_string = 1728
max_lines = 512 # needs testing
line_separator = "\\\\n"

full_filename = sys.argv[1]
filename = Path(full_filename).stem

strings = [""]
current_lines = 0
with open(full_filename, "r") as f:
    for line in f.read().splitlines():
        line += line_separator

        if len(strings[-1]) + len(line) > max_string or current_lines >= max_lines:
            strings.append(line)
            current_lines = 1
        else:
            strings[-1] += line
            current_lines += 1

command_template = Template("""give @p written_book{pages: [$pages], title: "$title", author: "$author"}""")
page_template = Template("""'{"text":"$text"}'""")

command = command_template.substitute({
    "pages": ", ".join(
        page_template.substitute({"text": string.removesuffix(line_separator)})
        for string in strings
    ),
    "title": filename,
    "author": "object_Object",
})

print(command)
