local MAX_LENGTH = 1728

local output = {}
local current = ""

local f = io.open("input.txt", "r")
assert(f ~= nil)

while true do
    local line = f:read("l")
    if line == nil then break end

    if #current + #line + 1 > MAX_LENGTH then
        table.insert(output, current)
        current = ""
    elseif #current > 0 then
        current = current.."\n"
    end
    current = current..line
end

f:close()

if #current > 0 then
    table.insert(output, current)
end

local port = peripheral.wrap("top")
assert(port ~= nil)

port.writeIota(output)
