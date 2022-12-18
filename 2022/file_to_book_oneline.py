from string import Template
import sys
from pathlib import Path

max_string = 512

full_filename = sys.argv[1]
filename = Path(full_filename).stem

strings = [""]
with open(full_filename, "r") as f:
    for c in f.read().rstrip("\n"):
        if len(strings[-1]) == max_string:
            strings.append(c)
        else:
            strings[-1] += c

command_template = Template("""give @p written_book{pages: [$pages], title: "$title", author: "$author"}""")
page_template = Template("""'{"text":"$text"}'""")

command = command_template.substitute({
    "pages": ", ".join(
        page_template.substitute({"text": string})
        for string in strings
    ),
    "title": filename,
    "author": "object_Object",
})

print(command)
